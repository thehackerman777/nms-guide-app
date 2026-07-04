package com.nmsguide.app.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.nmsguide.app.data.model.CategoryMeta
import com.nmsguide.app.ui.theme.*

/**
 * Card de categoría para la pantalla principal.
 * Muestra: ícono grande, título, resumen y una barra de acento de color.
 */
@Composable
fun GuideCard(
    category: CategoryMeta,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val accentColor = try {
        Color(android.graphics.Color.parseColor(category.color))
    } catch (_: Exception) {
        NmsCyan
    }

    // Animación sutil al presionar
    var pressed by remember { mutableStateOf(false) }
    val elevation by animateDpAsState(
        targetValue = if (pressed) 2.dp else 6.dp,
        animationSpec = tween(150),
        label = "cardElevation"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onClick()
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = NmsSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation)
    ) {
        Column(
            modifier = Modifier.padding(0.dp)
        ) {
            // Barra de acento de color
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(accentColor)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Ícono
                Text(
                    text = category.icon,
                    style = MaterialTheme.typography.displayMedium
                )

                Column(modifier = Modifier.weight(1f)) {
                    // Título
                    Text(
                        text = category.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = NmsTextPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Resumen
                    Text(
                        text = category.summary,
                        style = MaterialTheme.typography.bodySmall,
                        color = NmsTextSecondary,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Flecha indicadora
                Text(
                    text = "›",
                    style = MaterialTheme.typography.titleLarge,
                    color = accentColor
                )
            }
        }
    }
}
