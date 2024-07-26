package com.example.meritmatch

sealed class UserScreen(val route: String) {
    object account : UserScreen("account")
    object tasks : UserScreen("tasks")
    object reserved : UserScreen("reserved")
    object completed : UserScreen("completed")
    object transactions : UserScreen("transactions")
    object upload: UserScreen("upload")
    object logout : UserScreen("logout")
    object search : UserScreen("search")
    object game : UserScreen("game")
}