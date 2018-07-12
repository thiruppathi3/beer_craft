package com.thiruppathik.beercraft.ui

import com.thiruppathik.beercraft.db.Cart

/**
 * Created by Thiruppathi.K on 6/24/2018.
 */

interface CartItemClickListener {
    public fun onItemClick(position: Int, beer: Cart?)
}