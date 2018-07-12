package com.thiruppathik.beercraft.data

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import com.thiruppathik.beercraft.db.Beer
import com.thiruppathik.beercraft.model.DataLoadState

/**
 * Created by Thiruppathi.K on 6/23/2018.
 */
interface BeerRepository {
    fun getBeers(): LiveData<PagedList<Beer>>
    fun getDataLoadStatus(): LiveData<DataLoadState>
}