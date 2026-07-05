@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.nmsguide.app.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nmsguide.app.data.AppUpdate
import com.nmsguide.app.data.UpdateChecker
import com.nmsguide.app.ui.theme.*
import kotlinx.coroutines.launch

/**
 * Pantalla de ajustes.
 * Muestra versión, actualizaciones, créditos, enlace a GitHub y opción de compartir.
 */
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // ─── Estado del UpdateChecker ───────────────────────────────────────
    val updateChecker = remember { UpdateChecker(context) }
    var updateState by remember { mutableStateOf<AppUpdate?>(null) }
    var isChecking by remember { mutableStateOf(false) }
    var checkError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Ajustes",
                        style = MaterialTheme.typography.titleLarge,
                        color = AppTextPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppBackground
                )
            )
        },
        modifier = modifier
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // ─── Encabezado decorativo ──────────────────────────────────
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = AppSurface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🚀",
                        style = MaterialTheme.typography.displayLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "NMS Guide",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = AppTextPrimary
                    )
                    Text(
                        text = "Guía offline de No Man's Sky",
                        style = MaterialTheme.typography.bodySmall,
                        color = AppTextSecondary
                    )
                }
            }

            // ─── Versión ────────────────────────────────────────────────
            SettingsItem(
                icon = Icons.Default.Info,
                iconTint = PrimaryIndigo,
                title = "Versión",
                subtitle = "1.0.0"
            )

            // ─── Actualizaciones ─────────────────────────────────────────
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = AppSurface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.SystemUpdate,
                            contentDescription = null,
                            tint = SecondaryTeal,
                            modifier = Modifier.size(24.dp)
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Actualizaciones",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = AppTextPrimary
                            )
                            Text(
                                text = "Versión actual: 1.0.0",
                                style = MaterialTheme.typography.bodySmall,
                                color = AppTextSecondary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Botón de buscar actualizaciones
                    Button(
                        onClick = {
                            isChecking = true
                            checkError = false
                            scope.launch {
                                val result = updateChecker.checkForUpdate()
                                updateState = result
                                isChecking = false
                                checkError = false
                            }
                        },
                        enabled = !isChecking,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryIndigo,
                            contentColor = AppTextPrimary,
                            disabledContainerColor = PrimaryIndigo.copy(alpha = 0.5f)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (isChecking) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                color = AppTextPrimary,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Buscando…")
                        } else {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Buscar actualizaciones")
                        }
                    }

                    // Resultado de la búsqueda
                    AnimatedVisibility(
                        visible = updateState != null,
                        enter = fadeIn() + slideInVertically { it / 2 },
                        exit = fadeOut()
                    ) {
                        updateState?.let { update ->
                            Spacer(modifier = Modifier.height(12.dp))

                            if (update.isAvailable) {
                                // Hay actualización disponible
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = SecondaryTeal.copy(alpha = 0.1f)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(14.dp)
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.NewReleases,
                                                contentDescription = null,
                                                tint = SecondaryTeal,
                                                modifier = Modifier.size(20.dp)
                                            )
                                            Text(
                                                text = "Nueva versión ${update.latestVersion} disponible",
                                                style = MaterialTheme.typography.bodyMedium,
                                                fontWeight = FontWeight.SemiBold,
                                                color = SecondaryTeal
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(8.dp))
                                        OutlinedButton(
                                            onClick = {
                                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(update.downloadUrl))
                                                context.startActivity(intent)
                                            },
                                            shape = RoundedCornerShape(8.dp),
                                            colors = ButtonDefaults.outlinedButtonColors(
                                                contentColor = SecondaryTeal
                                            ),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Download,
                                                contentDescription = null,
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text("Descargar desde GitHub")
                                        }
                                    }
                                }
                            } else {
                                // No hay actualización
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = AppSuccess.copy(alpha = 0.1f)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(14.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = null,
                                            tint = AppSuccess,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Text(
                                            text = "Estás al día",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.SemiBold,
                                            color = AppSuccess
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // ─── Créditos ───────────────────────────────────────────────
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = AppSurface)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = AppError,
                        modifier = Modifier.size(24.dp)
                    )
                    Column {
                        Text(
                            text = "Créditos",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTextPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Desarrollada con ❤️ por la comunidad de viajeros.\n\nDatos basados en el universo de No Man's Sky de Hello Games.\n\nIconos por Emoji & Material Design.",
                            style = MaterialTheme.typography.bodySmall,
                            color = AppTextSecondary
                        )
                    }
                }
            }

            // ─── GitHub ─────────────────────────────────────────────────
            SettingsItem(
                icon = Icons.Default.Code,
                iconTint = TertiaryAmber,
                title = "GitHub",
                subtitle = "github.com/giutaca/nms-guide-app"
            )

            // ─── Compartir ──────────────────────────────────────────────
            SettingsItem(
                icon = Icons.Default.Share,
                iconTint = PrimaryIndigo,
                title = "Compartir app",
                subtitle = "Recomiéndala a otros viajeros"
            ) {
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "¡Descubre NMS Guide — la guía offline definitiva para No Man's Sky!")
                    type = "text/plain"
                }
                context.startActivity(Intent.createChooser(shareIntent, "Compartir"))
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

/**
 * Item de ajustes reutilizable: ícono + título + subtítulo + click opcional.
 */
@Composable
private fun SettingsItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: androidx.compose.ui.graphics.Color,
    title: String,
    subtitle: String,
    onClick: (() -> Unit)? = null
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = AppSurface),
        onClick = { onClick?.invoke() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(24.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTextPrimary
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTextSecondary
                )
            }
            if (onClick != null) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = AppTextMuted,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
