package com.thiruppathik.beercraft.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.thiruppathik.beercraft.Injection
import com.thiruppathik.beercraft.R
import com.thiruppathik.beercraft.db.BeerLocalCache
import com.thiruppathik.beercraft.db.Cart
import kotlinx.android.synthetic.main.activity_cart.*


/**
 * Created by Thiruppathi.K on 6/23/2018.
 */

class CartActivity : AppCompatActivity(), CartItemClickListener {
    var adapter: CartAdapter? = null
    private val cache: BeerLocalCache

    init {
        cache = Injection.provideCache(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        listview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = CartAdapter(null, this)
        listview.adapter = adapter
        refreshCartData();
    }

    override fun onItemClick(position: Int, beer: Cart?) {
        var cart = Cart(beer!!.id, beer.abv, beer.ibu, beer.name, beer.style, beer.ounces)
        Injection.provideCache(this).deleteCartDataById(cart, {
            runOnUiThread {
                Toast.makeText(this, "Item deleted from cart", Toast.LENGTH_SHORT).show()
            }
            refreshCartData()
        })
    }

    private fun refreshCartData() {
        cache.clearCartData() {
            runOnUiThread {
                adapter!!.items = it
                adapter!!.notifyDataSetChanged()

                if (it != null && it.size > 0) {
                    listview.visibility = View.VISIBLE
                    emptyview.visibility = View.GONE
                } else {
                    listview.visibility = View.GONE
                    emptyview.visibility = View.VISIBLE
                }
            }
        }
    }
}
