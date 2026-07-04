package com.nmsguide.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nmsguide.app.ui.theme.*

/**
 * Componente de estado vacío.
 * Muestra un ícono grande (emoji) + título + descripción opcional.
 */
@Composable
fun EmptyState(
    icon: String = "📭",
    title: String,
    description: String = "",
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = icon,
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = NmsTextPrimary,
            textAlign = TextAlign.Center
        )

        if (description.isNotBlank()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = NmsTextSecondary,
                textAlign = TextAlign.Center
            )
        }
    }
}
