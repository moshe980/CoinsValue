package com.giniapps.coinsvalue.model.dal

import com.giniapps.coinsvalue.model.RatesService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CoinRatesRepository : CoinsDao {
    private val dollarTag = "USD"
    private val currentDollar = 1.045293

    override suspend fun getCoins(): Map<String, Double> {
        val coins = withContext(Dispatchers.IO) {
            RatesService.create().getCoinRates().rates
        }
        val dollarValue = coins[dollarTag] ?: currentDollar
        val sortedMap = coins.toList().sortedBy { (_, value) -> value }.toMap().filter {
            it.value > dollarValue
        }
        return sortedMap
    }


}