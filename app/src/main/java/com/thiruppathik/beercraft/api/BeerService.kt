package com.thiruppathik.beercraft.api

import com.thiruppathik.beercraft.db.Beer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Created by Thiruppathi.K on 6/23/2018.
 */

private const val TAG = "BeerService"

interface BeerService {
    @GET("beercraft")
    fun getBeers(): Call<ArrayList<Beer>>

    companion object {
        private const val BASE_URL = "http://starlord.hackerearth.com/"

        fun create(): BeerService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                    .addInterceptor(logger)
                    .build()
            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(BeerService::class.java)
        }
    }
}