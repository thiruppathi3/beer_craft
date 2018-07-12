package com.thiruppathik.beercraft.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.thiruppathik.beercraft.Injection
import com.thiruppathik.beercraft.R
import com.thiruppathik.beercraft.api.BeerService
import com.thiruppathik.beercraft.db.Beer
import kotlinx.android.synthetic.main.activity_splash.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        loadData();
    }

    private fun loadData() {
        try {
            progressBar.visibility = View.VISIBLE
            val beerCache = Injection.provideCache(this);
            BeerService.create().getBeers().enqueue(object : Callback<ArrayList<Beer>> {
                override fun onResponse(call: Call<ArrayList<Beer>>?, response: Response<ArrayList<Beer>>?) {
                    var data: ArrayList<Beer> = response!!.body()!!
                    if (data != null) {
                        var id = 1;
                        for (beer in data) {
                            beer.identifier = id
                            id++
                        }
                        beerCache.clear {
                            beerCache.insert(data, {
                                Log.d("DataInserted in Cache", "page 1")
                                startMainActivity()
                            })
                        }
                    } else {
                        Toast.makeText(this@SplashActivity, "Something went wrong, couldn't refresh data from server", Toast.LENGTH_LONG).show()
                        startMainActivity()
                    }
                }

                override fun onFailure(call: Call<ArrayList<Beer>>?, t: Throwable?) {
                    startMainActivity()
                }
            });
        } catch (ex: IOException) {
            Log.e("Network Error", "Failed to fetch data from network")
            Toast.makeText(this, "Something went wrong :(", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startMainActivity() {
        runOnUiThread() {
            progressBar.visibility = View.GONE
        }
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
