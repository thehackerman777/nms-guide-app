package com.nmsguide.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.nmsguide.app.ui.theme.*

/**
 * Cajita de tip decorativa.
 * Muestra un ícono de "💡" seguido del texto del tip,
 * todo dentro de un contenedor con borde izquierdo cyan.
 */
@Composable
fun TipBox(
    text: String,
    icon: String = "💡",
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(NmsGold.copy(alpha = 0.08f))
            .padding(12.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = icon,
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = NmsGold
        )
    }
}
