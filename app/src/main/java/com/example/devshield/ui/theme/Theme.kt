package com.example.devshield.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.devshield.R
import androidx.compose.material3.Typography

val ZenDots = FontFamily(
    Font(R.font.zendots_regular, weight = FontWeight.Normal)
)
val AppTypography = Typography(
    displayLarge = Typography().displayLarge.copy(fontFamily = ZenDots),
    displayMedium = Typography().displayMedium.copy(fontFamily = ZenDots),
    displaySmall = Typography().displaySmall.copy(fontFamily = ZenDots),
    headlineLarge = Typography().headlineLarge.copy(fontFamily = ZenDots),
    headlineMedium = Typography().headlineMedium.copy(fontFamily = ZenDots),
    headlineSmall = Typography().headlineSmall.copy(fontFamily = ZenDots),
    titleLarge = Typography().titleLarge.copy(fontFamily = ZenDots),
    titleMedium = Typography().titleMedium.copy(fontFamily = ZenDots),
    titleSmall = Typography().titleSmall.copy(fontFamily = ZenDots),
    bodyLarge = Typography().bodyLarge.copy(fontFamily = ZenDots),
    bodyMedium = Typography().bodyMedium.copy(fontFamily = ZenDots),
    bodySmall = Typography().bodySmall.copy(fontFamily = ZenDots),
    labelLarge = Typography().labelLarge.copy(fontFamily = ZenDots),
    labelMedium = Typography().labelMedium.copy(fontFamily = ZenDots),
    labelSmall = Typography().labelSmall.copy(fontFamily = ZenDots)
)

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun DevShieldTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
