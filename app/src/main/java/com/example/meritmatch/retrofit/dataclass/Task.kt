package com.example.meritmatch.retrofit.dataclass


data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val location: String,
    val karmaPoints: Double,
    val reserved: Boolean,
    val completed: Boolean,
    val shown: Boolean,
){
    fun doesMatchSearchQuery(query: String): Boolean{
        val matchingCombinations = listOf(
                "$title",
            "$description",
            "${karmaPoints.toString()}",
            "$location"
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}
