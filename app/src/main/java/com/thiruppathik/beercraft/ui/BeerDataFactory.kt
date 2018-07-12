package com.thiruppathik.beercraft.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import android.content.Context
import com.thiruppathik.beercraft.Injection
import com.thiruppathik.beercraft.api.BeerService
import com.thiruppathik.beercraft.db.Beer

/**
 * Created by Thiruppathi.K on 6/23/2018.
 */


class BeerDataFactory(_context: Context) : DataSource.Factory<Int, Beer>() {
    private val context: Context

    init {
        context = _context
    }

    val dataSourceLiveData: MutableLiveData<BeerNetworkDataSource> = MutableLiveData()
    override fun create(): DataSource<Int, Beer> {
        val beerDataSource = BeerNetworkDataSource(BeerService.create(), Injection.provideCache(context))
        dataSourceLiveData.postValue(beerDataSource)
        return beerDataSource
    }
}