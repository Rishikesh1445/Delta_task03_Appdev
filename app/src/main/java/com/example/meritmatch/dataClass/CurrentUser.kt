package com.example.meritmatch.dataClass

import com.example.meritmatch.retrofit.dataclass.Task
import com.example.meritmatch.retrofit.dataclass.TransactionResponse
import com.example.meritmatch.retrofit.dataclass.UsersName

data class CurrentUser(
    val userid: Int? = null,
    val username: String? = null,
    val karmaPoint: Double? = null,
    val reserved: Int? = null,
    val completed: Int? = null,
    val reputation: Double? = null,
    val tasks: List<Task> = listOf( Task(0,"","", "",0.0, false, false)),
    val rtasks: List<Task> = listOf( Task(0,"","", "",0.0, false, false)),
    val ctasks: List<Task> = listOf( Task(0,"","", "",0.0, false, false)),
    val ptasks: List<Task> = listOf( Task(0,"","", "",0.0, false, false)),
    val transactionsUser : TransactionResponse? = null,
    val locations: List<String> = listOf(),
    val otherUsers: List<UsersName> = listOf(),
    val karmaFilter: Pair<Int?, Int?> = Pair(null,null),
    val uiState: UiState = UiState.Success
){
    enum class UiState{
        Loading, Success
    }
}
