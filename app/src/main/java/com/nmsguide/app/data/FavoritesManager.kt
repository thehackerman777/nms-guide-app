package com.nmsguide.app.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/** Extension property para DataStore de la app */
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "nms_guide_prefs")

/**
 * Gestiona los IDs de artículos favoritos usando DataStore Preferences.
 */
class FavoritesManager(private val context: Context) {

    companion object {
        private val FAVORITES_KEY = stringPreferencesKey("favorite_article_ids")
        private const val SEPARATOR = ","
    }

    /** Flow que emite el conjunto de IDs favoritos */
    fun getFavorites(): Flow<Set<String>> {
        return context.dataStore.data.map { prefs ->
            val raw = prefs[FAVORITES_KEY] ?: ""
            if (raw.isEmpty()) emptySet()
            else raw.split(SEPARATOR).filter { it.isNotBlank() }.toSet()
        }
    }

    /** Flow que indica si un artículo específico es favorito */
    fun isFavorite(articleId: String): Flow<Boolean> {
        return getFavorites().map { it.contains(articleId) }
    }

    /** Alterna el estado de favorito para un artículo */
    suspend fun toggleFavorite(articleId: String) {
        context.dataStore.edit { prefs ->
            val raw = prefs[FAVORITES_KEY] ?: ""
            val current = if (raw.isEmpty()) mutableSetOf()
            else raw.split(SEPARATOR).filter { it.isNotBlank() }.toMutableSet()

            if (current.contains(articleId)) {
                current.remove(articleId)
            } else {
                current.add(articleId)
            }

            prefs[FAVORITES_KEY] = current.joinToString(SEPARATOR)
        }
    }
}
