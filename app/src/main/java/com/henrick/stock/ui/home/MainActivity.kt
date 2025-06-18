package com.henrick.stock.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.henrick.stock.ui.theme.StockAppTheme
import com.henrick.stock.viewmodels.StockViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StockAppTheme {
                val stockViewModel: StockViewModel by viewModels()
                StockApp(stockViewModel)
            }
        }
    }
}

