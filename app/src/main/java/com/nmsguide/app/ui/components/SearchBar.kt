package com.nmsguide.app.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nmsguide.app.ui.theme.*

/**
 * Barra de búsqueda reutilizable con ícono de lupa y botón de limpiar.
 * Usa colores sobrios y bordes redondeados.
 *
 * @param query Texto actual de búsqueda
 * @param onQueryChange Callback cuando el texto cambia
 * @param placeholder Texto placeholder
 */
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    placeholder: String = "Buscar…",
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        placeholder = {
            Text(
                text = placeholder,
                color = AppTextMuted,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar",
                tint = PrimaryIndigo
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Limpiar búsqueda",
                        tint = AppTextMuted
                    )
                }
            }
        },
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = AppTextPrimary,
            unfocusedTextColor = AppTextPrimary,
            cursorColor = PrimaryIndigo,
            focusedBorderColor = PrimaryIndigo,
            unfocusedBorderColor = Neutral600,
            focusedContainerColor = AppSurfaceVariant,
            unfocusedContainerColor = AppSurfaceVariant
        ),
        singleLine = true
    )
}
