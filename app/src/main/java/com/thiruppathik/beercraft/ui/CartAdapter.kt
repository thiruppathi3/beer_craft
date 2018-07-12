package com.thiruppathik.beercraft.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thiruppathik.beercraft.R
import com.thiruppathik.beercraft.db.Cart
import kotlinx.android.synthetic.main.beer_list_item.view.*

class CartAdapter(var items: List<Cart>?, private val itemClickListener: CartItemClickListener) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return if (items != null) items!!.size else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.beer_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(items!!.get(position), itemClickListener)
    }
}

class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    var item: Cart? = null

    fun bindTo(item: Cart?, itemClickListener: CartItemClickListener) {
        this.item = item
        itemView.add.text = "Delete"
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