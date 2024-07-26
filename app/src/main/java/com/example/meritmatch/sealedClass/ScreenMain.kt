package com.example.meritmatch

sealed class ScreenMain(val route: String) {
    object loginPage:ScreenMain("loginPage")
    object userScreen: ScreenMain("userScreen")
}