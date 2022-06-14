package com.giniapps.coinsvalue.model.entity

data class CoinEntity(
    val base: String,
    val date: String,
    val rates: Map<String, Double>,
    val success: Boolean,
    val timestamp: Int
)