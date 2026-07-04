package com.nmsguide.app.data.model

import kotlinx.serialization.Serializable

/**
 * Modelos de datos para la guía offline de No Man's Sky.
 * Todo el contenido se serializa desde JSON en assets/.
 */

// ─── Índice de categorías ──────────────────────────────────────────────

@Serializable
data class CategoryIndex(
    val categories: List<CategoryMeta>
)

@Serializable
data class CategoryMeta(
    val id: String,
    val title: String,
    val icon: String,
    val color: String,
    val summary: String
)

// ─── Contenido de una categoría ─────────────────────────────────────────

@Serializable
data class CategoryContent(
    val categoryId: String,
    val articles: List<Article>
)

// ─── Artículo individual ────────────────────────────────────────────────

@Serializable
data class Article(
    val id: String,
    val title: String,
    val summary: String,
    val order: Int,
    val methods: List<GuideMethod> = emptyList()
)

// ─── Método / guía paso a paso ──────────────────────────────────────────

@Serializable
data class GuideMethod(
    val title: String,
    val difficulty: String,
    val type: String,
    val speed: String,
    val tags: List<String> = emptyList(),
    val steps: List<String> = emptyList(),
    val tips: List<String> = emptyList()
)

// ─── Tips rápidos ───────────────────────────────────────────────────────

@Serializable
data class TipItem(
    val id: String,
    val title: String,
    val content: List<String>,
    val icon: String = "💡",
    val priority: Int = 5
)

@Serializable
data class TipsContent(
    val categoryId: String,
    val tips: List<TipItem>
)
