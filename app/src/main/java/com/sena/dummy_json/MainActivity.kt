package com.sena.dummy_json

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sena.dummy_json.ui.theme.DummyjsonTheme
import dagger.hilt.android.AndroidEntryPoint
import com.sena.dummy_json.ui.screen.ProductScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DummyjsonTheme {
                ProductScreen()
                }
            }
        }
    }


