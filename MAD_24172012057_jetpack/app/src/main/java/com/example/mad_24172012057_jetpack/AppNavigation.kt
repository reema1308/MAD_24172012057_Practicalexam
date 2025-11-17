package com.example.mad_24172012057_jetpack

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mad_24172012057_jetpack.screen.LoginUI
import com.example.mad_24172012057_jetpack.screen.RegisterUI

@Composable
fun AppNavigation(navController: String) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginUI(navController)
        }

        composable("register") {
            RegisterUI(navController)
        }
    }
}

