package com.thiruppathik.beercraft.ui

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.thiruppathik.beercraft.R
import com.thiruppathik.beercraft.db.Beer
import kotlinx.android.synthetic.main.beer_list_item.view.*

/**
 * Created by Thiruppathi.K on 6/23/2018.
 */


class BeerPagedListAdapter(private val itemClickListener: ItemClickListener) : PagedListAdapter<Beer, BeerViewHolder>(diffCallback) {

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        var beerData = getItem(position)
        if (beerData != null) {
            holder.bindTo(beerData, itemClickListener)
        } else {
            holder.clear()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder = BeerViewHolder(parent)

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Beer>() {
            override fun areItemsTheSame(oldItem: Beer, newItem: Beer): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Beer, newItem: Beer): Boolean = oldItem == newItem
        }
    }
}

class BeerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.beer_list_item, parent, false)) {

    var item: Beer? = null

    fun clear() {
        itemView.name.text = null
        itemView.image.setImageURI(null)
    }

    fun bindTo(item: Beer?, itemClickListener: ItemClickListener) {
        this.item = item
        itemView.name.text = item?.name
        itemView.ounces.text = "Ounces: ${item?.ounces}"
        itemView.style.text = item?.style
        itemView.alcohol.text = "Alcohol: ${item?.abv}"
        itemView.add.setOnClickListener {
            if (item != null) {
                itemClickListener.onItemClick(adapterPosition, item)
            }
        }
    }
}