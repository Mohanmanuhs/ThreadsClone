package com.example.threadsclone.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.threadsclone.screens.AddThreadsScreen
import com.example.threadsclone.screens.BottomNavScreen
import com.example.threadsclone.screens.HomeScreen
import com.example.threadsclone.screens.LoginScreen
import com.example.threadsclone.screens.NotificationScreen
import com.example.threadsclone.screens.ProfileScreen
import com.example.threadsclone.screens.RegisterScreen
import com.example.threadsclone.screens.SearchScreen
import com.example.threadsclone.screens.SplashScreen


sealed class NavRoutes(val route:String){

    data object Home:NavRoutes("Home")
    data object Login:NavRoutes("Login")
    data object Register:NavRoutes("Register")

    data object BottomNav:NavRoutes("BottomNav")

    data object Search:NavRoutes("Search")

    data object Splash:NavRoutes("Splash")

    data object Profile:NavRoutes("Profile")

    data object AddThreads:NavRoutes("AddThreads")

    data object Notification:NavRoutes("Notification")
}


@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {

    NavHost(navController = navController, startDestination = NavRoutes.Splash.route) {
        composable(NavRoutes.Splash.route) {
            SplashScreen(navController)
        }
        composable(NavRoutes.Home.route) {
            HomeScreen()
        }
        composable(NavRoutes.Search.route) {
            SearchScreen()
        }
        composable(NavRoutes.Notification.route) {
            NotificationScreen()
        }
        composable(NavRoutes.Profile.route) {
            ProfileScreen(navController)
        }
        composable(NavRoutes.AddThreads.route){

            AddThreadsScreen(navController)
        }
        composable(NavRoutes.BottomNav.route) {
            BottomNavScreen(navController)
        }
        composable(NavRoutes.Login.route){

            LoginScreen(navController)
        }
        composable(NavRoutes.Register.route) {
            RegisterScreen(navController)
        }

    }


}