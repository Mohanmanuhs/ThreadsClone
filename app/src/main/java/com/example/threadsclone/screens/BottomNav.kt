package com.example.threadsclone.screens


import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.threadsclone.navigation.NavRoutes
import com.example.threadsclone.navigation.model.NavBarItem


@Composable
fun BottomNavScreen(modifier: Modifier = Modifier) {
    val navController1 = rememberNavController()

    Scaffold(bottomBar = {
        MyBottomBar(navController1)
    }) { innerPadding ->
        NavHost(
            navController = navController1,
            modifier = Modifier.padding(innerPadding),
            startDestination = NavRoutes.Home.route
        ) {
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
                ProfileScreen()
            }
            composable(NavRoutes.AddThreads.route){
                AddThreadsScreen()
            }
        }
    }
}

@Composable
fun MyBottomBar(navController1: NavHostController) {
    val backStackEntry = navController1.currentBackStackEntryAsState()
    val listOfNavBarItems = listOf(
        NavBarItem(
            "Home", NavRoutes.Home.route, Icons.Rounded.Home
        ), NavBarItem(
            "Search", NavRoutes.Search.route, Icons.Rounded.Search
        ), NavBarItem(
            "Profile", NavRoutes.Profile.route, Icons.Rounded.Person
        ), NavBarItem(
            "Notification", NavRoutes.Notification.route, Icons.Rounded.Notifications
        ), NavBarItem(
            "Add Threads", NavRoutes.AddThreads.route, Icons.Rounded.Add
        )
    )
    BottomAppBar {
        listOfNavBarItems.forEach {
            val selected = it.route == backStackEntry.value?.destination?.route

            NavigationBarItem(selected = selected,
                onClick = { navController1.navigate(it.route){
                    popUpTo(navController1.graph.findStartDestination().id){
                        saveState = true
                    }
                    launchSingleTop=true
                } },
                icon = { Icon(imageVector = it.icon, contentDescription = it.title) })
        }
    }
}
