package com.example.levelupmovil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.levelupmovil.ui.MainScreen
import com.example.levelupmovil.ui.theme.LevelUpMovilTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LevelUpMovilTheme {
                MainScreen()
            }
        }
    }
}

