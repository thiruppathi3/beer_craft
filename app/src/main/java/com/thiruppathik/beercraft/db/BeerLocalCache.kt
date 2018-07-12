package com.thiruppathik.beercraft.db

import android.util.Log
import java.util.concurrent.Executor

/**
 * Created by Thiruppathi.K on 6/24/2018.
 */

class BeerLocalCache(private val beerDatabase: BeerDatabase, private val ioExecutor: Executor) {
    fun insert(beerData: List<Beer>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            Log.d("BeerCache", "inserting ${beerData.size} data")
            beerDatabase.beersDao().insert(beerData)
            insertFinished()
        }
    }

    fun insert(beerData: Cart, insertFinished: () -> Unit) {
        ioExecutor.execute {
            Log.d("BeerCache", "inserting ${beerData}")
            beerDatabase.cartDao().insert(beerData)
            insertFinished()
        }
    }

    fun clear(clearFinished: () -> Unit) {
        ioExecutor.execute {
            Log.d("BeerCache", "data cleared")
            beerDatabase.beersDao().clear()
            clearFinished()
        }
    }

    fun clearCartData(clearFinished: (data: List<Cart>) -> Unit) {
        ioExecutor.execute {
            Log.d("BeerCache", "data cleared")
            val data = beerDatabase.cartDao().cartData()
            clearFinished(data)
        }
    }

    fun deleteCartDataById(cart: Cart, clearFinished: () -> Unit) {
        ioExecutor.execute {
            Log.d("BeerCache", "data cleared")
            beerDatabase.cartDao().deleteCartDataByIdentifier(cart.id)
            clearFinished()
        }
    }

    fun beerDataByOffset(start: Int, end: Int): List<Beer> {
        return beerDatabase.beersDao().beerDataByOffset(start, end)
    }
}