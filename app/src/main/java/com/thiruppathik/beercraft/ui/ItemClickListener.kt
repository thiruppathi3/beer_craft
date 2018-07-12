package com.thiruppathik.beercraft.ui

import com.thiruppathik.beercraft.db.Beer

/**
 * Created by Thiruppathi.K on 6/24/2018.
 */

interface ItemClickListener {
    public fun onItemClick(position: Int, beer: Beer?)
}