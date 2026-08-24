package sjnenterprises.household.sjnhomego.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val AppColorScheme = lightColorScheme(
    primary = BrandPrimary,
    onPrimary = BrandSurface,
    primaryContainer = BrandChip,
    onPrimaryContainer = BrandPrimaryDark,
    secondary = BrandAccent,
    onSecondary = BrandOnSurface,
    background = BrandBackground,
    onBackground = BrandOnSurface,
    surface = BrandSurface,
    onSurface = BrandOnSurface,
    surfaceVariant = BrandChip,
    onSurfaceVariant = BrandMuted,
    outline = BrandBorder,
    error = BrandWarning,
)

@Composable
fun ProductAppMBWRKTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = AppTypography,
        content = content,
    )
}

