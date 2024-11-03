package com.aki.jetareader.navigation

import com.aki.jetareader.screens.ReaderSplashScreen

enum class ReaderScreens {
    SplashScreen,
    LoginScreen,
    CreateAccountScreen,
    ReaderHomeScreen ,
    SearchScreen,
    UpdateScreen,
    ReaderStatsScreen;

    companion object {
        fun fromRoute(route:String):ReaderScreens
        =when(route?.substringBefore("/")){
            SplashScreen.name-> SplashScreen
            LoginScreen.name-> LoginScreen
            CreateAccountScreen.name-> CreateAccountScreen
            ReaderHomeScreen.name-> ReaderHomeScreen
            SearchScreen.name-> SearchScreen
            UpdateScreen.name-> UpdateScreen
            ReaderStatsScreen.name-> ReaderStatsScreen
            null->ReaderHomeScreen
            else ->throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}