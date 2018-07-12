package com.thiruppathik.beercraft

import android.content.Context
import com.thiruppathik.beercraft.db.BeerDatabase
import com.thiruppathik.beercraft.db.BeerLocalCache
import java.util.concurrent.Executors

/**
 * Created by Thiruppathi.K on 6/24/2018.
 */


object Injection {
    fun provideCache(context: Context): BeerLocalCache {
        val database = BeerDatabase.getInstance(context)
        return BeerLocalCache(database, Executors.newSingleThreadExecutor())
    }
}