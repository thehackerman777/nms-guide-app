@file:OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
package com.nmsguide.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nmsguide.app.data.model.Article
import com.nmsguide.app.ui.components.ArticleCard
import com.nmsguide.app.ui.components.EmptyState
import com.nmsguide.app.ui.theme.*

/**
 * Pantalla de categoría — lista de artículos con animación de entrada.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    categoryTitle: String,
    articles: List<Article>,
    onArticleClick: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showContent by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        showContent = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = categoryTitle,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTextPrimary
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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppBackground
                )
            )
        },
        modifier = modifier
    ) { padding ->
        if (articles.isEmpty()) {
            EmptyState(
                icon = "📜",
                title = "Sin artículos",
                description = "Esta categoría aún no tiene contenido.",
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
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                itemsIndexed(articles.sortedBy { it.order }) { index, article ->
                    AnimatedVisibility(
                        visible = showContent,
                        enter = fadeIn(animationSpec = androidx.compose.animation.core.tween(300 + index * 80)) +
                                slideInVertically(
                                    animationSpec = androidx.compose.animation.core.tween(300 + index * 80)
                                ) { it / 4 }
                    ) {
                        ArticleCard(
                            article = article,
                            onClick = { onArticleClick(article.id) },
                            modifier = Modifier.animateItemPlacement()
                        )
                    }
                }

                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}
