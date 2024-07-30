package com.example.threadsclone.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.threadsclone.R
import com.example.threadsclone.navigation.NavRoutes
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController, modifier: Modifier = Modifier
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(modifier = Modifier.size(200.dp).clip(RoundedCornerShape(20.dp)),contentScale = ContentScale.Fit, imageVector = ImageVector.vectorResource(id = R.drawable.thumbnail), contentDescription = "logo")
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