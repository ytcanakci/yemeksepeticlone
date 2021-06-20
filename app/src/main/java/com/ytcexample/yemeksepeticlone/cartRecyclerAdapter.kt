package com.ytcexample.yemeksepeticlone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class cartRecyclerAdapter(private val nameFromFB: ArrayList<String>,private val manyFromFB: ArrayList<Int>) : RecyclerView.Adapter<cartRecyclerAdapter.CartHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_view_cart,parent,false)
        return CartHolder(view)
    }

    override fun getItemCount(): Int {
        return nameFromFB.size
    }

    override fun onBindViewHolder(holder: CartHolder, position: Int) {
        holder.recyclercartfoodText?.text = nameFromFB[position]
        holder.recyclercartmanyText?.text = manyFromFB[position].toString()
    }

    class CartHolder(view: View) : RecyclerView.ViewHolder(view){

        var recyclercartfoodText : TextView? = null
        var recyclercartmanyText : TextView? = null

        init {
            recyclercartfoodText = view.findViewById(R.id.recyclercartfoodText)
            recyclercartmanyText = view.findViewById(R.id.recyclercartmanyText)
        }
    }
}