package com.example.meritmatch.sealedClass

sealed class LoginScreen(val route: String){
    object SignIn: LoginScreen("SignIn")
    object SignUp: LoginScreen( "SignUp")
}