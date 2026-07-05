package com.nmsguide.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nmsguide.app.ui.theme.*

/**
 * Cajita de tip tipo "note card" con borde izquierdo decorativo.
 * Diseño elevado y suave, sin neón.
 */
@Composable
fun TipBox(
    text: String,
    icon: String = "💡",
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppSurfaceContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = icon,
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                color = AppTextSecondary,
                lineHeight = androidx.compose.ui.unit.TextUnit(18f, androidx.compose.ui.unit.TextUnitType.Sp),
                modifier = Modifier.weight(1f)
            )
        }
    }
}
