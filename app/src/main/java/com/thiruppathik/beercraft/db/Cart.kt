package com.thiruppathik.beercraft.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Thiruppathi.K on 6/23/2018.
 */

@Entity(tableName = "cart")
data class Cart(@PrimaryKey @field:SerializedName("id") var id: Int,
                @field:SerializedName("abv") var abv: String,
                @field:SerializedName("ibu") var ibu: String,
                @field:SerializedName("name") var name: String,
                @field:SerializedName("style") var style: String,
                @field:SerializedName("ounces") var ounces: Double) : Serializable