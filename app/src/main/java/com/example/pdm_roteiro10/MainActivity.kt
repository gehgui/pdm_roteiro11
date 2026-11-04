package com.example.pdm_roteiro10

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pdm_roteiro10.ui.navigation.AppNavigation
import com.example.pdm_roteiro10.ui.theme.Pdm_roteiro10Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Pdm_roteiro10Theme {
                AppNavigation()
            }
        }
    }
}
