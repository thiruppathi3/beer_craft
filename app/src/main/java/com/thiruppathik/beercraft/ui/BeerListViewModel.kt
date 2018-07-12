package com.thiruppathik.beercraft.ui

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import com.thiruppathik.beercraft.data.BeerRepository
import com.thiruppathik.beercraft.data.BeerRepositoryImpl
import com.thiruppathik.beercraft.db.Beer
import com.thiruppathik.beercraft.model.DataLoadState

/**
 * Created by Thiruppathi.K on 6/23/2018.
 */


class BeerListViewModel(_application: Application) : AndroidViewModel(_application) {

    private val beerRepository: BeerRepository

    init {
        beerRepository = BeerRepositoryImpl(_application)
    }

    fun getBeerList(): LiveData<PagedList<Beer>> {
        return beerRepository.getBeers()
    }

    fun dataLoadStatus(): LiveData<DataLoadState> {
        return beerRepository.getDataLoadStatus()
    }
}