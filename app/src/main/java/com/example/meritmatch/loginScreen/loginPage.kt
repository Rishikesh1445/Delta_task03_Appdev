package com.example.meritmatch.loginScreen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.meritmatch.AppViewModel
import com.example.meritmatch.sealedClass.LoginScreen


@Composable
fun loginPage(navMain: NavController, context: Context, viewModel: AppViewModel){
    val loginNav = rememberNavController()

    NavHost(navController = loginNav, startDestination = LoginScreen.SignIn.route ){
        composable(LoginScreen.SignIn.route){
            SignIn(loginNav, navMain, viewModel, context)
        }
        composable(LoginScreen.SignUp.route){
            Signup(loginNav, viewModel, context)
        }
    }
}

