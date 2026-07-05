package com.nmsguide.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nmsguide.app.data.model.Article
import com.nmsguide.app.ui.components.MethodCard
import com.nmsguide.app.ui.theme.*

/**
 * Pantalla de detalle de artículo.
 * Muestra título, resumen y lista expandible de métodos.
 * Incluye botón de favorito en la barra superior.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    article: Article?,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = article?.title ?: "Artículo",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTextPrimary,
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = PrimaryIndigo
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onToggleFavorite) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite
                            else Icons.Default.FavoriteBorder,
                            contentDescription = if (isFavorite) "Quitar de favoritos"
                            else "Agregar a favoritos",
                            tint = if (isFavorite) AppError else AppTextSecondary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppBackground
                )
            )
        },
        modifier = modifier
    ) { padding ->
        if (article == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Artículo no encontrado",
                    style = MaterialTheme.typography.bodyLarge,
                    color = AppTextSecondary
                )
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // ─── Resumen como card elevada ─────────────────────────────
            if (article.summary.isNotBlank()) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = AppSurfaceContainer),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Text(
                        text = article.summary,
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppTextSecondary,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Divisor sutil
                Divider(
                    color = Neutral700,
                    thickness = 1.dp
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            // ─── Indicador de cantidad de métodos ──────────────────────
            if (article.methods.isNotEmpty()) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = PrimaryIndigo.copy(alpha = 0.1f)
                ) {
                    Text(
                        text = "${article.methods.size} método${if (article.methods.size != 1) "s" else ""} disponible${if (article.methods.size != 1) "s" else ""}",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Medium,
                        color = PrimaryIndigo,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
            }

            // ─── Lista de métodos ──────────────────────────────────────
            article.methods.forEachIndexed { index, method ->
                MethodCard(
                    method = method,
                    index = index,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            // ─── Sin métodos ───────────────────────────────────────────
            if (article.methods.isEmpty()) {
                Spacer(modifier = Modifier.height(24.dp))
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = AppSurfaceContainer),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Text(
                        text = "Próximamente: métodos detallados para este tema.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppTextSecondary,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}
