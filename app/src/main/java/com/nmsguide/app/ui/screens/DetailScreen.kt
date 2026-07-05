package com.nmsguide.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
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
                        color = NmsTextPrimary,
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = NmsCyan
                        )
                    }
                },
                actions = {
                    // Botón de favorito
                    IconButton(onClick = onToggleFavorite) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite
                            else Icons.Default.FavoriteBorder,
                            contentDescription = if (isFavorite) "Quitar de favoritos"
                            else "Agregar a favoritos",
                            tint = if (isFavorite) NmsPink else NmsTextSecondary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = NmsBackground
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
                    color = NmsTextSecondary
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
            // Resumen
            if (article.summary.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = NmsSurfaceVariant
                ) {
                    Text(
                        text = article.summary,
                        style = MaterialTheme.typography.bodyMedium,
                        color = NmsTextSecondary,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                // Divisor decorativo
                Divider(
                    color = NmsGray700,
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // Indicador de cantidad de métodos
            if (article.methods.isNotEmpty()) {
                Text(
                    text = "${article.methods.size} método${if (article.methods.size != 1) "s" else ""} disponible${if (article.methods.size != 1) "s" else ""}",
                    style = MaterialTheme.typography.labelLarge,
                    color = NmsCyan,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            // Lista de métodos
            article.methods.forEachIndexed { index, method ->
                MethodCard(
                    method = method,
                    index = index,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }

            // Si no hay métodos
            if (article.methods.isEmpty()) {
                Spacer(modifier = Modifier.height(32.dp))
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = NmsSurfaceVariant
                ) {
                    Text(
                        text = "Próximamente: métodos detallados para este tema.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = NmsTextSecondary,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            // Espacio inferior
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}
