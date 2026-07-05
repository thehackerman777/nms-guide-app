package com.nmsguide.app.data

import android.content.Context
import com.nmsguide.app.data.model.Article
import com.nmsguide.app.data.model.CategoryContent
import com.nmsguide.app.data.model.CategoryIndex
import com.nmsguide.app.data.model.CategoryMeta
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

/**
 * Repositorio único de datos.
 * Carga archivos JSON desde assets/ y los expone como Flows reactivos.
 */
class GuideRepository(private val context: Context) {

    private val json = Json { ignoreUnknownKeys = true }

    // Cache en memoria para evitar recargas
    private val categoryIndexFlow = MutableStateFlow<List<CategoryMeta>?>(null)
    private val categoryContentCache = mutableMapOf<String, MutableStateFlow<CategoryContent?>>()
    private val allArticlesCache = MutableStateFlow<List<Article>?>(null)

    /** Carga el índice de categorías desde category_index.json */
    fun loadCategoryIndex(): Flow<List<CategoryMeta>> {
        if (categoryIndexFlow.value == null) {
            try {
                val text = context.assets.open("data/categories.json")
                    .bufferedReader().use { it.readText() }
                val index = json.decodeFromString<CategoryIndex>(text)
                categoryIndexFlow.value = index.categories
            } catch (e: Exception) {
                categoryIndexFlow.value = emptyList()
            }
        }
        return categoryIndexFlow.map { it ?: emptyList() }
    }

    /** Carga el contenido de una categoría (artículos) */
    fun loadCategoryContent(categoryId: String): Flow<CategoryContent?> {
        val cached = categoryContentCache[categoryId]
        if (cached != null) return cached

        val state = MutableStateFlow<CategoryContent?>(null)
        categoryContentCache[categoryId] = state

        try {
            val text = context.assets.open("data/${categoryId}.json")
                .bufferedReader().use { it.readText() }
            val content = json.decodeFromString<CategoryContent>(text)
            state.value = content
        } catch (e: Exception) {
            state.value = null
        }

        return state
    }

    /** Carga todos los artículos de todas las categorías */
    fun loadAllArticles(): Flow<List<Article>> {
        if (allArticlesCache.value != null) return allArticlesCache.map { it ?: emptyList() }

        val all = mutableListOf<Article>()
        try {
            val index = listOfNotNull(categoryIndexFlow.value).flatten()
            if (index.isNotEmpty()) {
                for (cat in index) {
                    val text = context.assets.open("data/${cat.id}.json")
                        .bufferedReader().use { it.readText() }
                    val content = json.decodeFromString<CategoryContent>(text)
                    all.addAll(content.articles)
                }
            }
            allArticlesCache.value = all
        } catch (_: Exception) {
            // Si falla, dejamos la lista vacía
            allArticlesCache.value = emptyList()
        }

        return allArticlesCache.map { it ?: emptyList() }
    }

    /** Busca artículos cuyo título o resumen contenga la query */
    fun searchArticles(query: String): Flow<List<Article>> {
        val q = query.lowercase().trim()
        return loadAllArticles().map { articles ->
            if (q.isEmpty()) {
                emptyList()
            } else {
                articles.filter { article ->
                    article.title.lowercase().contains(q) ||
                    article.summary.lowercase().contains(q) ||
                    article.methods.any { method ->
                        method.title.lowercase().contains(q) ||
                        method.tags.any { it.lowercase().contains(q) }
                    }
                }
            }
        }
    }
}
