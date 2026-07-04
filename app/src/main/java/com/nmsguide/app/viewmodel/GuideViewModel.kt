package com.nmsguide.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nmsguide.app.data.FavoritesManager
import com.nmsguide.app.data.GuideRepository
import com.nmsguide.app.data.model.Article
import com.nmsguide.app.data.model.CategoryMeta
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel principal de NMS Guide.
 * Gestiona el estado de categorías, artículos, búsqueda y favoritos.
 */
class GuideViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = GuideRepository(application)
    private val favoritesManager = FavoritesManager(application)

    // ─── Categorías ──────────────────────────────────────────────────────

    private val _categories = MutableStateFlow<List<CategoryMeta>>(emptyList())
    val categories: StateFlow<List<CategoryMeta>> = _categories.asStateFlow()

    // ─── Contenido de categoría ──────────────────────────────────────────

    private val _categoryArticles = MutableStateFlow<Map<String, List<Article>>>(emptyMap())
    val categoryArticles: StateFlow<Map<String, List<Article>>> = _categoryArticles.asStateFlow()

    // ─── Todos los artículos ─────────────────────────────────────────────

    private val _allArticles = MutableStateFlow<List<Article>>(emptyList())
    val allArticles: StateFlow<List<Article>> = _allArticles.asStateFlow()

    // ─── Búsqueda ────────────────────────────────────────────────────────

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<Article>>(emptyList())
    val searchResults: StateFlow<List<Article>> = _searchResults.asStateFlow()

    // ─── Favoritos ───────────────────────────────────────────────────────

    private val _favoriteIds = MutableStateFlow<Set<String>>(emptySet())
    val favoriteIds: StateFlow<Set<String>> = _favoriteIds.asStateFlow()

    // ─── Estado de carga ────────────────────────────────────────────────

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // ─── Artículo seleccionado para detalle ──────────────────────────────

    private val _selectedArticle = MutableStateFlow<Article?>(null)
    val selectedArticle: StateFlow<Article?> = _selectedArticle.asStateFlow()

    // ─── Categoría activa ────────────────────────────────────────────────

    private val _activeCategoryId = MutableStateFlow<String?>(null)
    val activeCategoryId: StateFlow<String?> = _activeCategoryId.asStateFlow()

    private val _activeCategoryTitle = MutableStateFlow("")
    val activeCategoryTitle: StateFlow<String> = _activeCategoryTitle.asStateFlow()

    // ─── Favorito del artículo seleccionado ──────────────────────────────

    private val _isSelectedFavorite = MutableStateFlow(false)
    val isSelectedFavorite: StateFlow<Boolean> = _isSelectedFavorite.asStateFlow()

    init {
        loadData()
        observeFavorites()
        observeSearch()
    }

    /** Carga inicial de datos */
    private fun loadData() {
        viewModelScope.launch {
            repository.loadCategoryIndex().collect { cats ->
                _categories.value = cats
                _isLoading.value = false

                // Precargar todos los artículos
                loadAllArticles()
            }
        }
    }

    /** Carga todos los artículos de todas las categorías */
    private fun loadAllArticles() {
        viewModelScope.launch {
            repository.loadAllArticles().collect { articles ->
                _allArticles.value = articles
            }
        }
    }

    /** Carga los artículos de una categoría específica */
    fun loadCategory(categoryId: String) {
        viewModelScope.launch {
            val cat = _categories.value.find { it.id == categoryId }
            _activeCategoryId.value = categoryId
            _activeCategoryTitle.value = cat?.title ?: categoryId

            repository.loadCategoryContent(categoryId).collect { content ->
                if (content != null) {
                    val updated = _categoryArticles.value.toMutableMap()
                    updated[categoryId] = content.articles
                    _categoryArticles.value = updated
                }
            }
        }
    }

    /** Selecciona un artículo para la pantalla de detalle */
    fun selectArticle(articleId: String) {
        val article = _allArticles.value.find { it.id == articleId }
        _selectedArticle.value = article
        // Revisar si es favorito
        _isSelectedFavorite.value = _favoriteIds.value.contains(articleId)
    }

    // ─── Búsqueda ────────────────────────────────────────────────────────

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private fun observeSearch() {
        viewModelScope.launch {
            _searchQuery.debounce(300).collect { query ->
                if (query.isBlank()) {
                    _searchResults.value = emptyList()
                } else {
                    repository.searchArticles(query).collect { results ->
                        _searchResults.value = results
                    }
                }
            }
        }
    }

    // ─── Favoritos ───────────────────────────────────────────────────────

    private fun observeFavorites() {
        viewModelScope.launch {
            favoritesManager.getFavorites().collect { ids ->
                _favoriteIds.value = ids
                // Actualizar estado del artículo seleccionado
                _selectedArticle.value?.let { article ->
                    _isSelectedFavorite.value = ids.contains(article.id)
                }
            }
        }
    }

    fun toggleFavorite(articleId: String) {
        viewModelScope.launch {
            favoritesManager.toggleFavorite(articleId)
        }
    }

    fun isFavorite(articleId: String): StateFlow<Boolean> {
        val state = MutableStateFlow(_favoriteIds.value.contains(articleId))
        viewModelScope.launch {
            favoritesManager.isFavorite(articleId).collect { isFav ->
                state.value = isFav
            }
        }
        return state.asStateFlow()
    }

    /** Devuelve los artículos favoritos como lista */
    fun getFavoriteArticles(): List<Article> {
        return _allArticles.value.filter { it.id in _favoriteIds.value }
    }

    /** Obtiene artículos de una categoría */
    fun getArticlesForCategory(categoryId: String): List<Article> {
        return _categoryArticles.value[categoryId] ?: emptyList()
    }

    override fun onCleared() {
        super.onCleared()
        // Cleanup if needed
    }
}
