package com.giniapps.coinsvalue.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giniapps.coinsvalue.model.dal.CoinRatesRepository
import com.giniapps.coinsvalue.view.boundary.CoinBoundary
import kotlinx.coroutines.launch

class Viewmodel : ViewModel() {
    private val _coins = MutableLiveData<MutableList<CoinBoundary>>()
    private var conList = mutableListOf<CoinBoundary>()
    private val dollarRate = 1.045293
    val coins: LiveData<MutableList<CoinBoundary>> get() = _coins
    private val euroValue = 5

    private val CoinsDao = CoinRatesRepository()

    fun getCoins() {
        viewModelScope.launch {
            val coins=CoinsDao.getCoins()
            val dollar = coins["USD"] ?: dollarRate

            coins.forEach {
                if (it.value > dollar) {
                    conList.add(CoinBoundary(it.key, it.value*euroValue, true))

                } else {
                    conList.add(CoinBoundary(it.key, it.value*euroValue, false))

                }
            }
            _coins.value = conList

        }


    }


}