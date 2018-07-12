package com.thiruppathik.beercraft.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.content.Context
import com.thiruppathik.beercraft.db.Beer
import com.thiruppathik.beercraft.model.DataLoadState
import com.thiruppathik.beercraft.ui.BeerDataFactory

/**
 * Created by Thiruppathi.K on 6/23/2018.
 */

class BeerRepositoryImpl(_context: Context) : BeerRepository {
    private val beerDataFactory: BeerDataFactory
    private lateinit var beers: LiveData<PagedList<Beer>>

    init {
        beerDataFactory = BeerDataFactory(_context)
    }

    companion object {
        private val PAGE_SIZE: Int = 3
    }

    override fun getDataLoadStatus(): LiveData<DataLoadState> {
        return Transformations.switchMap(beerDataFactory.dataSourceLiveData) { dataSource -> dataSource.loadState }
    }

    override fun getBeers(): LiveData<PagedList<Beer>> {
        val config: PagedList.Config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(PAGE_SIZE * 2)
                .setPageSize(PAGE_SIZE).build()

        beers = LivePagedListBuilder(beerDataFactory, config).setInitialLoadKey(1).build()
        return beers
    }
}