package com.example.threadsclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.threadsclone.navigation.NavGraph
import com.example.threadsclone.ui.theme.ThreadsCloneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThreadsCloneTheme {
                    val navController = rememberNavController()
                    NavGraph(navController = navController)
            }
        }
    }
}