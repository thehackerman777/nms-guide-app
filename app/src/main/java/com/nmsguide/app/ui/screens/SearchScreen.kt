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
import com.nmsguide.app.ui.components.SearchBar
import com.nmsguide.app.ui.theme.*

/**
 * Pantalla de búsqueda con filtrado en tiempo real.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    allArticles: List<Article>,
    onArticleClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var query by remember { mutableStateOf("") }

    // Filtrar artículos según la query
    val filteredArticles = remember(query, allArticles) {
        if (query.isBlank()) {
            emptyList()
        } else {
            val q = query.lowercase().trim()
            allArticles.filter { article ->
                article.title.lowercase().contains(q) ||
                article.summary.lowercase().contains(q) ||
                article.methods.any { m ->
                    m.title.lowercase().contains(q) ||
                    m.tags.any { it.lowercase().contains(q) }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Buscar",
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
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            // Barra de búsqueda
            SearchBar(
                query = query,
                onQueryChange = { query = it },
                placeholder = "Buscar guías, recetas, planetas…"
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Resultados
            if (query.isBlank()) {
                // Estado inicial: sugerencias
                EmptyState(
                    icon = "🔍",
                    title = "¿Qué quieres aprender hoy?",
                    description = "Busca cualquier tema: recetas, construcción, plantas, tecnología…",
                    modifier = Modifier.fillMaxSize()
                )
            } else if (filteredArticles.isEmpty()) {
                // Sin resultados
                EmptyState(
                    icon = "🌌",
                    title = "Sin resultados",
                    description = "No encontramos nada para «$query». Intenta con otras palabras.",
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                // Resultados
                Surface(
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
                    color = PrimaryIndigo.copy(alpha = 0.08f)
                ) {
                    Text(
                        text = "${filteredArticles.size} resultado${if (filteredArticles.size != 1) "s" else ""}",
                        style = MaterialTheme.typography.labelMedium,
                        color = PrimaryIndigo,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(filteredArticles, key = { it.id }) { article ->
                        ArticleCard(
                            article = article,
                            onClick = { onArticleClick(article.id) }
                        )
                    }

                    item { Spacer(modifier = Modifier.height(80.dp)) }
                }
            }
        }
    }
}
