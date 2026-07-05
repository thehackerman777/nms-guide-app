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
 * Colores suaves/pastel sin neón.
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
        "more" -> Pair(AppSurfaceVariant, AppTextMuted)
        else -> Pair(Neutral700, Neutral300) // tag genérico
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

/** Mapea texto de dificultad a colores suaves (sin neón) */
private fun getDifficultyColors(difficulty: String): Pair<Color, Color> {
    return when (difficulty.lowercase()) {
        "fácil", "facil", "easy" -> Pair(DiffEasy.copy(alpha = 0.15f), DiffEasy)
        "media", "medium" -> Pair(DiffMedium.copy(alpha = 0.15f), DiffMedium)
        "difícil", "dificil", "hard" -> Pair(DiffHard.copy(alpha = 0.15f), DiffHard)
        "extremo", "extreme" -> Pair(DiffExtreme.copy(alpha = 0.15f), DiffExtreme)
        else -> Pair(Neutral700, Neutral300)
    }
}

/** Mapea tipo de método a colores suaves */
private fun getTypeColors(type: String): Pair<Color, Color> {
    return when (type.lowercase()) {
        "crafting", "fabricación" -> Pair(TypeCrafting.copy(alpha = 0.15f), TypeCrafting)
        "combate", "combat" -> Pair(TypeCombat.copy(alpha = 0.15f), TypeCombat)
        "exploración", "exploration" -> Pair(TypeExploration.copy(alpha = 0.15f), TypeExploration)
        "comercio", "trade" -> Pair(TypeTrade.copy(alpha = 0.15f), TypeTrade)
        "construcción", "building" -> Pair(TypeBuilding.copy(alpha = 0.15f), TypeBuilding)
        else -> Pair(PrimaryIndigo.copy(alpha = 0.15f), PrimaryIndigo)
    }
}

/** Mapea velocidad a colores suaves */
private fun getSpeedColors(speed: String): Pair<Color, Color> {
    return when (speed.lowercase()) {
        "rápido", "rapido", "fast" -> Pair(SpeedFast.copy(alpha = 0.15f), SpeedFast)
        "lento", "slow" -> Pair(SpeedSlow.copy(alpha = 0.15f), SpeedSlow)
        "media", "medium" -> Pair(SpeedMedium.copy(alpha = 0.15f), SpeedMedium)
        else -> Pair(Neutral700, Neutral300)
    }
}
