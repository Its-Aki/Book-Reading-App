package com.aki.jetareader.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aki.jetareader.screens.ReaderSplashScreen
import com.aki.jetareader.screens.home.HomeScreen
import com.aki.jetareader.screens.login.LoginScreen
import com.aki.jetareader.screens.search.BookSearchViewModel
import com.aki.jetareader.screens.search.SearchScreen
import com.aki.jetareader.screens.stats.StatsScreen
import com.aki.jetareader.screens.update.BookUpdateScreen

@Composable
fun ReaderNavigation() {
    val navController= rememberNavController()
    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name){


        composable(route=ReaderScreens.SplashScreen.name){
            ReaderSplashScreen(navController=navController)
        }
        composable(route=ReaderScreens.LoginScreen.name){
            LoginScreen(navController=navController)
        }
        composable(route=ReaderScreens.ReaderHomeScreen.name){
            HomeScreen(navController=navController)
        }
        composable(route=ReaderScreens.ReaderStatsScreen.name){
            StatsScreen(navController=navController)
        }
        composable(route=ReaderScreens.SearchScreen.name){
            val searchViewModel=hiltViewModel<BookSearchViewModel>()
            SearchScreen(navController=navController, viewModel = searchViewModel)
        }
        composable(route=ReaderScreens.CreateAccountScreen.name){
            LoginScreen(navController=navController)
        }
        composable(route=ReaderScreens.UpdateScreen.name){
            BookUpdateScreen(navController=navController)
        }

    }
}