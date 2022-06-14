package com.giniapps.coinsvalue.model.dal

interface CoinsDao {
    suspend fun getCoins(): Map<String, Double>

}