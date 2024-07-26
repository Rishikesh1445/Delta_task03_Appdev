package com.example.meritmatch.dataClass

import com.example.meritmatch.retrofit.dataclass.notify

data class Notification(
    val toShow: List<Int> = listOf(0),
    val toShowString: List<String> = listOf(""),
    val once : Boolean = false,
    val forApi: List<notify> = listOf()
)
