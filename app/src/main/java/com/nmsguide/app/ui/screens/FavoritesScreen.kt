package com.nmsguide.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nmsguide.app.data.model.Article
import com.nmsguide.app.ui.components.ArticleCard
import com.nmsguide.app.ui.components.EmptyState
import com.nmsguide.app.ui.theme.*

/**
 * Pantalla de favoritos.
 * Muestra los artículos guardados como favoritos por el usuario.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    favoriteArticleIds: Set<String>,
    allArticles: List<Article>,
    onArticleClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Filtrar solo los artículos favoritos
    val favoriteArticles = remember(favoriteArticleIds, allArticles) {
        allArticles.filter { it.id in favoriteArticleIds }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Favoritos",
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
        if (favoriteArticles.isEmpty()) {
            EmptyState(
                icon = "⭐",
                title = "Aún no tienes favoritos",
                description = "Explora las guías y guarda tus artículos favoritos para tenerlos siempre a mano.",
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
                items(favoriteArticles, key = { it.id }) { article ->
                    ArticleCard(
                        article = article,
                        onClick = { onArticleClick(article.id) },
                        modifier = Modifier.animateItem()
                    )
                }

                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}
