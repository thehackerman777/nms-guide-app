package com.nmsguide.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.nmsguide.app.ui.navigation.AppNavigation
import com.nmsguide.app.ui.theme.AppBackground
import com.nmsguide.app.ui.theme.NMSGuideTheme

/**
 * Actividad principal de NMS Guide.
 * Configura el tema oscuro Material 3, edge-to-edge y monta la navegación Compose.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NMSGuideTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = AppBackground
                ) {
                    AppNavigation()
                }
            }
        }
    }
}
