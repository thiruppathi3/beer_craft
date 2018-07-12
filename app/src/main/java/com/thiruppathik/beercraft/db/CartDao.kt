package com.thiruppathik.beercraft.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

/**
 * Created by Thiruppathi.K on 6/24/2018.
 */

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(beer: Cart)

    @Query("SELECT * FROM cart")
    fun cartData(): List<Cart>

    @Query("DELETE FROM cart")
    fun clearCartData()

    @Query("DELETE FROM cart WHERE id=" + ":id")
    fun deleteCartDataByIdentifier(id: Int)

}