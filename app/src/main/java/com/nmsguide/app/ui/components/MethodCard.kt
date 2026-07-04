package com.nmsguide.app.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nmsguide.app.data.model.GuideMethod
import com.nmsguide.app.ui.theme.*

/**
 * Card expandible que muestra un método completo.
 * Al tocar, se despliega con animación mostrando pasos y tips.
 */
@Composable
fun MethodCard(
    method: GuideMethod,
    index: Int,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = NmsSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier.padding(0.dp)) {
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
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Número de método
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = NmsCyan.copy(alpha = 0.15f),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "${index + 1}",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = NmsCyan
                            )
                        }
                    }

                    Column {
                        Text(
                            text = method.title,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = NmsTextPrimary
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            TagBadge(text = method.difficulty, type = "difficulty")
                            TagBadge(text = method.type, type = "type")
                            TagBadge(text = method.speed, type = "speed")
                        }
                    }
                }

                // Indicador expandir/colapsar
                Text(
                    text = if (expanded) "▲" else "▼",
                    color = NmsTextMuted,
                    style = MaterialTheme.typography.labelLarge
                )
            }

            // ─── Contenido expandible ───────────────────────────────────
            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Tags
                    if (method.tags.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            method.tags.forEach { tag ->
                                TagBadge(text = tag, type = "tag")
                            }
                        }
                    }

                    // Pasos
                    if (method.steps.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Pasos:",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = NmsCyan
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        method.steps.forEachIndexed { i, step ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 3.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(50),
                                    color = NmsCyan.copy(alpha = 0.2f),
                                    modifier = Modifier.size(22.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = "${i + 1}",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = NmsCyan
                                        )
                                    }
                                }
                                Text(
                                    text = step,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = NmsTextPrimary,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }

                    // Tips
                    if (method.tips.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        method.tips.forEach { tip ->
                            Spacer(modifier = Modifier.height(6.dp))
                            TipBox(text = tip)
                        }
                    }
                }
            }
        }
    }
}
