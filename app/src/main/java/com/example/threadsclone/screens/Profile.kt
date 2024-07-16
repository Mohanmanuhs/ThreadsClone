package com.example.threadsclone.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.threadsclone.navigation.NavRoutes
import com.example.threadsclone.viewmodel.AuthViewModel

@Composable
fun ProfileScreen(navController: NavHostController, modifier: Modifier = Modifier,authViewModel: AuthViewModel = viewModel()) {
    val firebaseUser by authViewModel.firebaseUser.collectAsState()
    LaunchedEffect(firebaseUser) {
        if(firebaseUser==null){
            navController.navigate(NavRoutes.Login.route){
                popUpTo(navController.graph.startDestinationId){
                    inclusive=true
                }
                launchSingleTop=true
            }
        }
    }
    Box(modifier =  modifier.fillMaxSize()) {
        Text(modifier = Modifier
            .align(Alignment.Center)
            .clickable {
                authViewModel.logout()
            }, text = "LogOut")
    }
}