package com.nmsguide.app.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nmsguide.app.ui.screens.*
import com.nmsguide.app.ui.theme.*
import com.nmsguide.app.viewmodel.GuideViewModel

/**
 * Rutas de navegación de NMS Guide.
 */
object Routes {
    const val HOME = "home"
    const val SEARCH = "search"
    const val FAVORITES = "favorites"
    const val SETTINGS = "settings"
    const val CATEGORY = "category/{categoryId}"
    const val DETAIL = "detail/{articleId}"

    fun category(categoryId: String) = "category/$categoryId"
    fun detail(articleId: String) = "detail/$articleId"
}

/**
 * Elemento del Bottom Navigation Bar.
 */
data class BottomNavItem(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

/** Items del Bottom Navigation */
val bottomNavItems = listOf(
    BottomNavItem(Routes.HOME, "Guías", Icons.Filled.Book, Icons.Outlined.Book),
    BottomNavItem(Routes.SEARCH, "Buscar", Icons.Filled.Search, Icons.Outlined.Search),
    BottomNavItem(Routes.FAVORITES, "Favoritos", Icons.Filled.Favorite, Icons.Outlined.Favorite),
    BottomNavItem(Routes.SETTINGS, "Ajustes", Icons.Filled.Settings, Icons.Outlined.Settings)
)

/** Rutas que muestran el BottomNavBar */
private val bottomNavRoutes = bottomNavItems.map { it.route }.toSet()

/**
 * Navegación principal de la app.
 * Incluye NavigationBar inferior y NavHost con transiciones suaves.
 */
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: GuideViewModel = viewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Verificar si debemos mostrar el BottomNav
    val showBottomBar = currentRoute in bottomNavRoutes

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(
                    currentRoute = currentRoute,
                    onItemClick = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        },
        containerColor = AppBackground
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(innerPadding),
            enterTransition = {
                fadeIn(animationSpec = tween(300)) + slideInVertically(
                    animationSpec = tween(300)
                ) { it / 8 }
            },
            exitTransition = {
                fadeOut(animationSpec = tween(250))
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(250))
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(250)) + slideOutVertically(
                    animationSpec = tween(250)
                ) { it / 8 }
            }
        ) {
            // ─── HOME ─────────────────────────────────────────────────────
            composable(Routes.HOME) {
                val categories by viewModel.categories.collectAsState()

                HomeScreen(
                    categories = categories,
                    onCategoryClick = { categoryId ->
                        viewModel.loadCategory(categoryId)
                        navController.navigate(Routes.category(categoryId))
                    }
                )
            }

            // ─── SEARCH ──────────────────────────────────────────────────
            composable(Routes.SEARCH) {
                val allArticles by viewModel.allArticles.collectAsState()

                SearchScreen(
                    allArticles = allArticles,
                    onArticleClick = { articleId ->
                        viewModel.selectArticle(articleId)
                        navController.navigate(Routes.detail(articleId))
                    }
                )
            }

            // ─── FAVORITES ───────────────────────────────────────────────
            composable(Routes.FAVORITES) {
                val favoritesIds by viewModel.favoriteIds.collectAsState()
                val allArticles by viewModel.allArticles.collectAsState()

                FavoritesScreen(
                    favoriteArticleIds = favoritesIds,
                    allArticles = allArticles,
                    onArticleClick = { articleId ->
                        viewModel.selectArticle(articleId)
                        navController.navigate(Routes.detail(articleId))
                    }
                )
            }

            // ─── SETTINGS ────────────────────────────────────────────────
            composable(Routes.SETTINGS) {
                SettingsScreen()
            }

            // ─── CATEGORY ────────────────────────────────────────────────
            composable(
                route = Routes.CATEGORY,
                arguments = listOf(navArgument("categoryId") { type = NavType.StringType })
            ) { backStackEntry ->
                val categoryId = backStackEntry.arguments?.getString("categoryId") ?: return@composable
                val title by viewModel.activeCategoryTitle.collectAsState()

                // Cargar contenido si no está cargado
                LaunchedEffect(categoryId) {
                    viewModel.loadCategory(categoryId)
                }

                val articles = viewModel.getArticlesForCategory(categoryId)

                CategoryScreen(
                    categoryTitle = title,
                    articles = articles,
                    onArticleClick = { articleId ->
                        viewModel.selectArticle(articleId)
                        navController.navigate(Routes.detail(articleId))
                    },
                    onBack = { navController.popBackStack() }
                )
            }

            // ─── DETAIL ──────────────────────────────────────────────────
            composable(
                route = Routes.DETAIL,
                arguments = listOf(navArgument("articleId") { type = NavType.StringType })
            ) {
                val article by viewModel.selectedArticle.collectAsState()
                val isFav by viewModel.isSelectedFavorite.collectAsState()

                DetailScreen(
                    article = article,
                    isFavorite = isFav,
                    onToggleFavorite = {
                        article?.let { viewModel.toggleFavorite(it.id) }
                    },
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}

/**
 * Bottom Navigation Bar con estilo Material 3 sobrio.
 */
@Composable
private fun BottomNavBar(
    currentRoute: String?,
    onItemClick: (String) -> Unit
) {
    NavigationBar(
        containerColor = AppSurface,
        contentColor = AppTextPrimary,
        tonalElevation = 0.dp
    ) {
        bottomNavItems.forEach { item ->
            val selected = currentRoute == item.route

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                    )
                },
                selected = selected,
                onClick = { onItemClick(item.route) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = PrimaryIndigo,
                    selectedTextColor = PrimaryIndigo,
                    unselectedIconColor = AppTextSecondary,
                    unselectedTextColor = AppTextSecondary,
                    indicatorColor = PrimaryIndigo.copy(alpha = 0.12f)
                )
            )
        }
    }
}
