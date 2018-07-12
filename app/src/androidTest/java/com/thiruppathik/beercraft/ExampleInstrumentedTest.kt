package com.thiruppathik.beercraft

import android.support.test.InstrumentationRegistry
import android.support.test.filters.MediumTest
import android.support.test.runner.AndroidJUnit4
import com.thiruppathik.beercraft.api.BeerService
import com.thiruppathik.beercraft.db.Beer
import com.thiruppathik.beercraft.db.BeerDatabase
import com.thiruppathik.beercraft.db.Cart
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Response


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@MediumTest
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    var response: Response<ArrayList<Beer>>? = null

    @Before
    fun setup() {
        response = BeerService.create().getBeers().execute()
    }

    @Test
    fun checkResponse() {
        assertTrue("Response Success", response != null && response!!.isSuccessful)
        assertTrue("Has Data!", response!!.body() != null && response!!.body()!!.size > 0)
    }

    @Test
    fun checkDBOperations() {
        var data = response!!.body()!!
        var beer = data.get(0)
        val context = InstrumentationRegistry.getTargetContext()
        val cache = Injection.provideCache(context!!)
        cache.clearCartData {
            var cart = Cart(beer!!.id, beer.abv, beer.ibu, beer.name, beer.style, beer.ounces)
            cache.insert(cart, {
                cache.clearCartData {
                    assertTrue("Data Insert Check!!",
                            (cart.id == it.get(0).id))
                }
            })
        }

        data.forEach {
            var cachedData = BeerDatabase.getInstance(context).beersDao().beerDataById(it.id)
            assertTrue("Data Insert Check!!",
                    (cachedData.id == it.id))
        }
    }
}
