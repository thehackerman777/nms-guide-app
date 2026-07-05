package com.nmsguide.app.data

import android.content.Context
import android.content.pm.PackageManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.concurrent.TimeUnit

/**
 * Modelo que representa el estado de una actualización disponible.
 */
data class AppUpdate(
    val latestVersion: String,
    val downloadUrl: String,
    val isAvailable: Boolean
)

/**
 * Verificador de actualizaciones vía GitHub Releases API.
 * Consulta https://api.github.com/repos/thehackerman777/nms-guide-app/releases/latest
 * y compara la versión del tag con la versión instalada.
 *
 * Usa OkHttp para la petición HTTP.
 */
class UpdateChecker(private val context: Context) {

    companion object {
        private const val GITHUB_API_URL =
            "https://api.github.com/repos/thehackerman777/nms-guide-app/releases/latest"
        private const val GITHUB_RELEASES_URL =
            "https://github.com/thehackerman777/nms-guide-app/releases/latest"

        private val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    /** Obtiene la versión actual instalada desde el PackageManager */
    private fun getCurrentVersion(): String {
        return try {
            val pkgInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            pkgInfo.versionName ?: "1.0.0"
        } catch (e: PackageManager.NameNotFoundException) {
            "1.0.0"
        }
    }

    /**
     * Consulta la API de GitHub Releases para obtener la última versión disponible.
     *
     * @return AppUpdate con la info de la última versión
     */
    suspend fun checkForUpdate(): AppUpdate {
        return withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder()
                    .url(GITHUB_API_URL)
                    .header("Accept", "application/vnd.github.v3+json")
                    .header("User-Agent", "NMS-Guide-App")
                    .build()

                val response = client.newCall(request).execute()
                val body = response.body?.string()

                if (!response.isSuccessful || body == null) {
                    return@withContext noUpdate()
                }

                val json = JSONObject(body)
                val latestTag = json.optString("tag_name", "") // ej: "v1.1.0"
                val downloadUrl = json.optString("html_url", GITHUB_RELEASES_URL)

                // Normalizar: quitar "v" inicial si existe
                val latestVersion = latestTag.removePrefix("v")
                val currentVersion = getCurrentVersion()

                val isAvailable = compareVersions(latestVersion, currentVersion) > 0

                AppUpdate(
                    latestVersion = latestVersion,
                    downloadUrl = downloadUrl,
                    isAvailable = isAvailable
                )
            } catch (e: Exception) {
                noUpdate()
            }
        }
    }

    /**
     * Retorna la URL base de las releases en GitHub.
     */
    fun getDownloadUrl(): String = GITHUB_RELEASES_URL

    /** Retorna un AppUpdate indicando que no hay actualización disponible */
    private fun noUpdate() = AppUpdate(
        latestVersion = getCurrentVersion(),
        downloadUrl = GITHUB_RELEASES_URL,
        isAvailable = false
    )

    /**
     * Compara dos versiones semánticas (MAJOR.MINOR.PATCH).
     * Retorna >0 si v1 > v2, <0 si v1 < v2, 0 si son iguales.
     */
    private fun compareVersions(v1: String, v2: String): Int {
        val parts1 = v1.split(".").map { it.toIntOrNull() ?: 0 }
        val parts2 = v2.split(".").map { it.toIntOrNull() ?: 0 }

        for (i in 0 until maxOf(parts1.size, parts2.size)) {
            val p1 = parts1.getOrElse(i) { 0 }
            val p2 = parts2.getOrElse(i) { 0 }
            if (p1 != p2) return p1 - p2
        }
        return 0
    }
}
