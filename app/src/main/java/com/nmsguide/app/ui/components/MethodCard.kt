package com.nmsguide.app.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nmsguide.app.data.model.GuideMethod
import com.nmsguide.app.ui.theme.*

/**
 * Card expandible que muestra un método completo.
 * Animaciones suaves en expansión, diseño limpio y minimalista.
 */
@Composable
fun MethodCard(
    method: GuideMethod,
    index: Int,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    // Animación de rotación para el ícono expandir/colapsar
    val rotateAngle by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = tween(300),
        label = "expandRotate"
    )

    // Altura animada para la card
    val cardElevation by animateFloatAsState(
        targetValue = if (expanded) 4f else 2f,
        animationSpec = tween(200),
        label = "cardElev"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = cardElevation.dp)
    ) {
        Column {
            // ─── Cabecera (siempre visible) ─────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    // Número de método con diseño limpio
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = PrimaryIndigo.copy(alpha = 0.12f),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "${index + 1}",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryIndigo
                            )
                        }
                    }

                    Column {
                        Text(
                            text = method.title,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTextPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            TagBadge(text = method.difficulty, type = "difficulty")
                            TagBadge(text = method.type, type = "type")
                            TagBadge(text = method.speed, type = "speed")
                        }
                    }
                }

                // Indicador expandir/colapsar animado
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (expanded) "Colapsar" else "Expandir",
                    tint = AppTextMuted,
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(rotateAngle)
                )
            }

            // ─── Contenido expandible con animación más suave ───────────
            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn(animationSpec = tween(300)) +
                        expandVertically(animationSpec = tween(300)),
                exit = fadeOut(animationSpec = tween(200)) +
                        shrinkVertically(animationSpec = tween(200))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Línea divisoria
                    Divider(
                        color = Neutral700,
                        thickness = 0.5.dp,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Tags
                    if (method.tags.isNotEmpty()) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            method.tags.forEach { tag ->
                                TagBadge(text = tag, type = "tag")
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    // Pasos numerados con mejor tipografía
                    if (method.steps.isNotEmpty()) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = PrimaryIndigo.copy(alpha = 0.06f)
                        ) {
                            Text(
                                text = "Pasos",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.SemiBold,
                                color = PrimaryIndigo,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))

                        method.steps.forEachIndexed { i, step ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                // Círculo numerado con opacidad
                                Surface(
                                    shape = RoundedCornerShape(50),
                                    color = PrimaryIndigo.copy(alpha = 0.15f),
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = "${i + 1}",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = PrimaryIndigo
                                        )
                                    }
                                }
                                Text(
                                    text = step,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = AppTextPrimary,
                                    modifier = Modifier.weight(1f),
                                    lineHeight = androidx.compose.ui.unit.TextUnit(22f, androidx.compose.ui.unit.TextUnitType.Sp)
                                )
                            }
                        }
                    }

                    // Tips como cards elevadas
                    if (method.tips.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(14.dp))
                        method.tips.forEach { tip ->
                            Spacer(modifier = Modifier.height(8.dp))
                            TipBox(text = tip)
                        }
                    }
                }
            }
        }
    }
}
