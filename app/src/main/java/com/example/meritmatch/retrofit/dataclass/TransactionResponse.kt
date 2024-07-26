package com.example.meritmatch.retrofit.dataclass

data class TransactionResponse(
    val lent: Double,
    val received: Double,
    val reservedYou: List<String>,
    val reservedOther: List<String>,
)