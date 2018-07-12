package com.thiruppathik.beercraft.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

/**
 * Created by Thiruppathi.K on 6/24/2018.
 */

@Database(
        entities = [Beer::class, Cart::class],
        version = 1,
        exportSchema = false
)
abstract class BeerDatabase : RoomDatabase() {

    abstract fun beersDao(): BeerDao
    abstract fun cartDao(): CartDao

    companion object {

        @Volatile
        private var INSTANCE: BeerDatabase? = null

        fun getInstance(context: Context): BeerDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE
                            ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        BeerDatabase::class.java, "BeerCraft.db")
                        .build()
    }
}