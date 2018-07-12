package com.thiruppathik.beercraft

import com.thiruppathik.beercraft.api.BeerService
import com.thiruppathik.beercraft.db.Beer
import org.junit.Before
import retrofit2.Response

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ApiUnitTest {
    var response: Response<ArrayList<Beer>>? = null

    @Before
    fun setup() {
        response = BeerService.create().getBeers().execute()
    }
}
