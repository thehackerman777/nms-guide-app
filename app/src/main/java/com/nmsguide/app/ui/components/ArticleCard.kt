package com.nmsguide.app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nmsguide.app.data.model.Article
import com.nmsguide.app.ui.theme.*

/**
 * Card de artículo para listas de categoría.
 * Tipografía mejorada, elevaciones suaves, diseño minimalista.
 */
@Composable
fun ArticleCard(
    article: Article,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Título con mejor tipografía
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = AppTextPrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                letterSpacing = 0.15.sp
            )

            // Resumen
            if (article.summary.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = article.summary,
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTextSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 18.sp
                )
            }

            // Métodos disponibles
            if (article.methods.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    article.methods.take(3).forEach { method ->
                        TagBadge(
                            text = method.difficulty,
                            type = "difficulty"
                        )
                    }
                    if (article.methods.size > 3) {
                        TagBadge(
                            text = "+${article.methods.size - 3}",
                            type = "more"
                        )
                    }
                }
            }
        }
    }
}
