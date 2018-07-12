package com.thiruppathik.beercraft.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

/**
 * Created by Thiruppathi.K on 6/24/2018.
 */

@Dao
interface BeerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(beer: List<Beer>)

    @Query("SELECT * FROM beers" +
            " ORDER BY id ASC")
    fun beer(): List<Beer>

    @Query("DELETE FROM beers")
    fun clear()

    @Query("SELECT * FROM beers WHERE id= " + ":id")
    fun beerDataById(id: Int): Beer

    @Query("SELECT * FROM beers WHERE identifier>" + ":start" + " AND identifier<=" + ":end")
    fun beerDataByOffset(start: Int, end: Int): List<Beer>
}