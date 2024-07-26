package com.example.meritmatch.retrofit.dataclass

data class Userdetail(
    val id: Int,
    val karmaPoints: Double,
    val username: String,
    val reputation: Double,
    val reserved: Int,
    val completed: Int,
)
