package com.nmsguide.app.ui.screens

import android.content.Intent
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
import com.nmsguide.app.R
import com.nmsguide.app.ui.theme.*

/**
 * Pantalla de ajustes.
 * Muestra versión, créditos, enlace a GitHub y opción de compartir.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Ajustes",
                        style = MaterialTheme.typography.titleLarge,
                        color = NmsTextPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = NmsBackground
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
                colors = CardDefaults.cardColors(containerColor = NmsSurface)
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
                        color = NmsCyan
                    )
                    Text(
                        text = "Guía offline de No Man's Sky",
                        style = MaterialTheme.typography.bodySmall,
                        color = NmsTextSecondary
                    )
                }
            }

            // ─── Versión ────────────────────────────────────────────────
            SettingsItem(
                icon = Icons.Default.Info,
                iconTint = NmsCyan,
                title = "Versión",
                subtitle = context.getString(R.string.app_version)
            )

            // ─── Créditos ───────────────────────────────────────────────
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = NmsSurface)
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
                        tint = NmsPink,
                        modifier = Modifier.size(24.dp)
                    )
                    Column {
                        Text(
                            text = "Créditos",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = NmsTextPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = context.getString(R.string.credits_text),
                            style = MaterialTheme.typography.bodySmall,
                            color = NmsTextSecondary
                        )
                    }
                }
            }

            // ─── GitHub ─────────────────────────────────────────────────
            SettingsItem(
                icon = Icons.Default.Code,
                iconTint = NmsPurple,
                title = "GitHub",
                subtitle = context.getString(R.string.github_url)
            )

            // ─── Compartir ──────────────────────────────────────────────
            SettingsItem(
                icon = Icons.Default.Share,
                iconTint = NmsGold,
                title = "Compartir app",
                subtitle = "Recomiéndala a otros viajeros"
            ) {
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, context.getString(R.string.setting_share_text))
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
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = NmsSurface),
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
                    color = NmsTextPrimary
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = NmsTextSecondary
                )
            }
            if (onClick != null) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = NmsTextMuted,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
