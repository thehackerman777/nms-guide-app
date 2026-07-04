package com.nmsguide.app.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nmsguide.app.ui.theme.*

/**
 * Badge pequeño para mostrar dificultad, tipo, velocidad o tags.
 *
 * @param text Texto del badge
 * @param type Categoría visual: "difficulty", "type", "speed", "tag", "more"
 */
@Composable
fun TagBadge(
    text: String,
    type: String = "tag",
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor) = when (type) {
        "difficulty" -> getDifficultyColors(text)
        "type" -> getTypeColors(text)
        "speed" -> getSpeedColors(text)
        "more" -> Pair(NmsSurfaceVariant, NmsTextSecondary)
        else -> Pair(NmsGray700, NmsGray300) // tag genérico
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(6.dp),
        color = bgColor
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = textColor,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        )
    }
}

/** Mapea texto de dificultad a colores */
private fun getDifficultyColors(difficulty: String): Pair<Color, Color> {
    return when (difficulty.lowercase()) {
        "fácil", "facil", "easy" -> Pair(DifficultyEasy.copy(alpha = 0.15f), DifficultyEasy)
        "media", "medium" -> Pair(DifficultyMedium.copy(alpha = 0.15f), DifficultyMedium)
        "difícil", "dificil", "hard" -> Pair(DifficultyHard.copy(alpha = 0.15f), DifficultyHard)
        "extremo", "extreme" -> Pair(DifficultyExtreme.copy(alpha = 0.15f), DifficultyExtreme)
        else -> Pair(NmsGray700, NmsGray300)
    }
}

/** Mapea tipo de método a colores */
private fun getTypeColors(type: String): Pair<Color, Color> {
    return when (type.lowercase()) {
        "crafting", "fabricación" -> Pair(NmsGreen.copy(alpha = 0.15f), NmsGreen)
        "combate", "combat" -> Pair(NmsPink.copy(alpha = 0.15f), NmsPink)
        "exploración", "exploration" -> Pair(NmsPurple.copy(alpha = 0.15f), NmsPurple)
        "comercio", "trade" -> Pair(NmsGold.copy(alpha = 0.15f), NmsGold)
        "construcción", "building" -> Pair(NmsOrange.copy(alpha = 0.15f), NmsOrange)
        else -> Pair(NmsCyan.copy(alpha = 0.15f), NmsCyan)
    }
}

/** Mapea velocidad a colores */
private fun getSpeedColors(speed: String): Pair<Color, Color> {
    return when (speed.lowercase()) {
        "rápido", "rapido", "fast" -> Pair(NmsGreen.copy(alpha = 0.15f), NmsGreen)
        "lento", "slow" -> Pair(NmsOrange.copy(alpha = 0.15f), NmsOrange)
        "media", "medium" -> Pair(NmsGold.copy(alpha = 0.15f), NmsGold)
        else -> Pair(NmsGray700, NmsGray300)
    }
}
