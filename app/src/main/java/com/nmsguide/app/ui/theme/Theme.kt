package com.nmsguide.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

/**
 * Tema oscuro NMS para Material 3.
 * Define una paleta oscura con acentos cyan, rosa y dorado.
 */
private val NmsDarkColorScheme = darkColorScheme(
    primary = NmsCyan,
    onPrimary = NmsBackground,
    primaryContainer = NmsCyan.copy(alpha = 0.15f),
    onPrimaryContainer = NmsCyan,

    secondary = NmsPink,
    onSecondary = NmsBackground,
    secondaryContainer = NmsPink.copy(alpha = 0.15f),
    onSecondaryContainer = NmsPink,

    tertiary = NmsGold,
    onTertiary = NmsBackground,
    tertiaryContainer = NmsGold.copy(alpha = 0.15f),
    onTertiaryContainer = NmsGold,

    background = NmsBackground,
    onBackground = NmsTextPrimary,

    surface = NmsSurface,
    onSurface = NmsTextPrimary,
    surfaceVariant = NmsSurfaceVariant,
    onSurfaceVariant = NmsTextSecondary,

    surfaceTint = NmsCyan,

    error = NmsError,
    onError = NmsBackground,
    errorContainer = NmsError.copy(alpha = 0.15f),
    onErrorContainer = NmsError,

    outline = NmsGray600,
    outlineVariant = NmsGray700,

    inverseSurface = NmsTextPrimary,
    inverseOnSurface = NmsBackground,
    inversePrimary = NmsCyan.copy(alpha = 0.8f)
)

/**
 * Tema principal de NMS Guide.
 * Envuelve toda la UI con Material 3 + paleta oscura NMS.
 */
@Composable
fun NMSGuideTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = NmsDarkColorScheme,
        typography = NmsTypography,
        content = content
    )
}
