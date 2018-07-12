package com.thiruppathik.beercraft.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import com.thiruppathik.beercraft.api.BeerService
import com.thiruppathik.beercraft.db.Beer
import com.thiruppathik.beercraft.db.BeerLocalCache
import com.thiruppathik.beercraft.model.DataLoadState

/**
 * Created by Thiruppathi.K on 6/23/2018.
 */


class BeerNetworkDataSource(_beerService: BeerService, _cache: BeerLocalCache) : PageKeyedDataSource<Int, Beer>() {

    private val beerService: BeerService
    private val cache: BeerLocalCache
    val loadState: MutableLiveData<DataLoadState>

    val PER_PAGE: Int = 10

    //    val MIN_ID: Int
    init {
        beerService = _beerService
        cache = _cache
//        MIN_ID = cache.minIdentifier()
        loadState = MutableLiveData()
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Beer>) {
        var result: Pair<List<Beer>, Int?> = getData(1, false)
        callback.onResult(result.first, null, 2)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Beer>) {

        var result: Pair<List<Beer>, Int?> = getData(params.key, true)
        callback.onResult(result.first, result.second)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Beer>) {
        var result: Pair<List<Beer>, Int?> = getData(params.key, false)
        callback.onResult(result.first, result.second)
    }

    fun getData(page: Int, isPrev: Boolean): Pair<List<Beer>, Int?> {
        var data: List<Beer>?
        var index: Int?
        var endIndex = page * PER_PAGE
        var startIndex = endIndex - PER_PAGE
        data = cache.beerDataByOffset(startIndex, endIndex)
        if (data != null && data.isNotEmpty()) {
            val prevKey = (if (page > 1) page - 1 else null)
            val nextKey = page + 1
            loadState.postValue(DataLoadState.LOADED)
            index = if (isPrev) prevKey else nextKey
        } else {
            data = emptyList()
            index = null
            loadState.postValue(DataLoadState.FAILED)
        }
        return Pair(data, index)
    }
}