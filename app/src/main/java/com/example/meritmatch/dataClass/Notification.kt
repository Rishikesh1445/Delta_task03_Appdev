package com.example.meritmatch.dataClass

import com.example.meritmatch.retrofit.dataclass.notify

data class Notification(
    val alreadySeen: List<Int> = listOf(0),
    val toShow: List<Int> = listOf(0),
    val forApi: List<notify> = listOf()
)
