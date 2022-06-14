package com.giniapps.coinsvalue.model

import com.giniapps.coinsvalue.model.entity.CoinEntity
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RatesService {

    @GET("txT1A97z")
    suspend fun getCoinRates(): CoinEntity


    companion object {
        fun create(): RatesService {
            val client = OkHttpClient.Builder()
                .addInterceptor(TokenInterceptor())
                .build()

            return Retrofit
                .Builder()
                .client(client)
                .baseUrl("https://pastebin.com/raw/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RatesService::class.java)
        }
    }
}

class TokenInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var original = chain.request()
        //TODO: hide api key in gradle so we don't share it in github
        val url = original.url.newBuilder()
            .addQueryParameter("access_key", "p1d7E5DWLdxFJUAs08LeasZVVqYT4d5e").build()
        original = original.newBuilder().url(url).build()
        return chain.proceed(original)
    }
}