package com.thiruppathik.beercraft.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.thiruppathik.beercraft.Injection
import com.thiruppathik.beercraft.R
import com.thiruppathik.beercraft.db.Beer
import com.thiruppathik.beercraft.db.Cart
import com.thiruppathik.beercraft.model.DataLoadState
import kotlinx.android.synthetic.main.beer_list_fragment.*

/**
 * Created by Thiruppathi.K on 6/24/2018.
 */


class BeerListFragment : Fragment(), ItemClickListener {
    private var adapter: BeerPagedListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return if (view != null) view else
            inflater.inflate(R.layout.beer_list_fragment, container, false)
    }

    override fun onItemClick(position: Int, beer: Beer?) {
        var cart = Cart(beer!!.id, beer.abv, beer.ibu, beer.name, beer.style, beer.ounces)
        Injection.provideCache(context!!).insert(cart, {
            activity!!.runOnUiThread {
                Toast.makeText(activity, "Item added to cart", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState == null) {
            adapter = BeerPagedListAdapter(this)
            listview.adapter = adapter

            val beerListViewModel = ViewModelProviders.of(this).get(BeerListViewModel::class.java)
            listview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            listview.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            beerListViewModel.dataLoadStatus().observe(this, Observer { dataLoadStatus ->
                if (dataLoadStatus == DataLoadState.FAILED) {
                    Log.d("DataLoadStatus", "Failed")
                    if (adapter != null && adapter!!.itemCount > 0) {
                        showEmptyView(false)
                        Toast.makeText(activity, "We have completed loading your data!!", Toast.LENGTH_SHORT).show()
                    } else {
                        showEmptyView(true)
                    }
                }
            })

            beerListViewModel.getBeerList().observe(this, Observer { pagedList ->
                if (adapter != null)
                    adapter!!.submitList(pagedList)
            })
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    private fun showEmptyView(show: Boolean) {
        if (show) {
            emptyview.visibility = View.VISIBLE
            listview.visibility = View.GONE
        } else {
            emptyview.visibility = View.GONE
            listview.visibility = View.VISIBLE
        }
    }
}