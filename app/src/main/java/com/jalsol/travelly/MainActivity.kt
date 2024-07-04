package com.jalsol.travelly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jalsol.travelly.ui.TravellyApp
import com.jalsol.travelly.ui.theme.TravellyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TravellyTheme {
                TravellyApp()
            }
        }
    }

}