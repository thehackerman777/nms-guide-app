@file:OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
package com.nmsguide.app.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nmsguide.app.data.model.CategoryMeta
import com.nmsguide.app.ui.components.EmptyState
import com.nmsguide.app.ui.components.GuideCard
import com.nmsguide.app.ui.theme.*
import kotlinx.coroutines.launch

/**
 * Pantalla principal — lista de categorías de guías.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    categories: List<CategoryMeta>,
    onCategoryClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    var showTitle by remember { mutableStateOf(false) }

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
                        enter = fadeIn()
                    ) {
                        Column {
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
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = NmsBackground
                )
            )
        },
        modifier = modifier
    ) { padding ->
        if (categories.isEmpty()) {
            EmptyState(
                icon = "🚀",
                title = "Cargando guías…",
                description = "Preparando todo lo que necesitas para explorar el universo.",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            )
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

                // Espacio para el BottomNav
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}
