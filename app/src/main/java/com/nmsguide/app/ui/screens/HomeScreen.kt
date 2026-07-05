@file:OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
package com.nmsguide.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nmsguide.app.data.model.CategoryMeta
import com.nmsguide.app.ui.components.EmptyState
import com.nmsguide.app.ui.components.GuideCard
import com.nmsguide.app.ui.theme.*
import kotlinx.coroutines.delay

/**
 * Pantalla principal — lista de categorías de guías con skeleton loading.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    categories: List<CategoryMeta>,
    onCategoryClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showTitle by remember { mutableStateOf(false) }
    val isLoading by remember(categories) { mutableStateOf(categories.isEmpty()) }

    // Animación de entrada
    LaunchedEffect(Unit) {
        showTitle = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    AnimatedVisibility(
                        visible = showTitle,
                        enter = fadeIn(animationSpec = tween(400))
                    ) {
                        Column {
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
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppBackground
                )
            )
        },
        modifier = modifier
    ) { padding ->
        if (categories.isEmpty()) {
            // ─── Skeleton Loading ──────────────────────────────────────
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(6) {
                    SkeletonCard()
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(categories) { index, category ->
                    GuideCard(
                        category = category,
                        onClick = { onCategoryClick(category.id) },
                        modifier = Modifier.animateItemPlacement()
                    )
                }

                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}

/**
 * Card skeleton / shimmer para mostrar mientras cargan los datos.
 * Usa un gradiente animado que simula el efecto shimmer de carga.
 */
@Composable
private fun SkeletonCard() {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val shimmerAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerOffset"
    )

    val shimmerColors = listOf(
        AppSurface,
        AppSurfaceVariant,
        AppSurface
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(shimmerAnim.value, 0f),
        end = Offset(shimmerAnim.value + 300f, 0f)
    )

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AppSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(0.dp)) {
            // Barra superior
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(AppSurfaceVariant)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Placeholder del ícono
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(brush)
                )
                Column(modifier = Modifier.weight(1f)) {
                    // Placeholder del título
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(16.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(brush)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    // Placeholder del resumen
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(12.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(brush)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(12.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(brush)
                    )
                }
            }
        }
    }
}
