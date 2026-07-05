package com.nmsguide.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

/**
 * Esquema de colores oscuros Material 3 para NMS Guide.
 * Paleta sobria y profesional: indigo + teal + ámbar tenue.
 */
private val NmsDarkColorScheme = darkColorScheme(
    // ─── Primario: Indigo profundo ─────────────────────────────────────
    primary = PrimaryIndigo,
    onPrimary = OnPrimaryIndigo,
    primaryContainer = PrimaryContainerIndigo,
    onPrimaryContainer = PrimaryIndigo,

    // ─── Secundario: Teal suave ────────────────────────────────────────
    secondary = SecondaryTeal,
    onSecondary = OnSecondaryTeal,
    secondaryContainer = SecondaryContainerTeal,
    onSecondaryContainer = SecondaryTeal,

    // ─── Terciario: Ámbar tenue ────────────────────────────────────────
    tertiary = TertiaryAmber,
    onTertiary = OnTertiaryAmber,
    tertiaryContainer = TertiaryContainerAmber,
    onTertiaryContainer = TertiaryAmber,

    // ─── Fondo ─────────────────────────────────────────────────────────
    background = AppBackground,
    onBackground = AppTextPrimary,
    surface = AppSurface,
    onSurface = AppTextPrimary,
    surfaceVariant = AppSurfaceVariant,
    onSurfaceVariant = AppTextSecondary,

    surfaceTint = PrimaryIndigo,

    // ─── Errores ───────────────────────────────────────────────────────
    error = AppError,
    onError = AppBackground,
    errorContainer = AppError.copy(alpha = 0.15f),
    onErrorContainer = AppError,

    // ─── Bordes / outlines ─────────────────────────────────────────────
    outline = Neutral600,
    outlineVariant = Neutral700,

    // ─── Inversos ──────────────────────────────────────────────────────
    inverseSurface = AppTextPrimary,
    inverseOnSurface = AppBackground,
    inversePrimary = PrimaryIndigo.copy(alpha = 0.8f)
)

/**
 * Tema principal de NMS Guide.
 * Envuelve toda la UI con Material 3 + paleta oscura profesional.
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
