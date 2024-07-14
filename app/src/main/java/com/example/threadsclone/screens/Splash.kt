package com.example.threadsclone.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.threadsclone.navigation.NavRoutes
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController, modifier: Modifier = Modifier
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(modifier = Modifier.align(Alignment.Center), text = "SplashScreen")
    }
    LaunchedEffect(key1 = true) {
        delay(2000)
        if(FirebaseAuth.getInstance().currentUser!=null) {
            navController.navigate(NavRoutes.BottomNav.route){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop=true
            }
        }else {
            navController.navigate(NavRoutes.Login.route){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop=true
            }
        }
    }
}