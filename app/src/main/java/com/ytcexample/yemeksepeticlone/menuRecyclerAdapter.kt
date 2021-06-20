package com.ytcexample.yemeksepeticlone

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class menuRecyclerAdapter(private val menuFromFB: ArrayList<String>,private val priceFromFB: ArrayList<Int>, private val listener: OnItemClickListener) : RecyclerView.Adapter<menuRecyclerAdapter.MenuHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_view_row_menu,parent,false)
        return MenuHolder(view)
    }

    override fun getItemCount(): Int {
        return menuFromFB.size
    }

    override fun onBindViewHolder(holder: MenuHolder, position: Int) {
        holder.recyclerRestaurantText?.text = menuFromFB[position]
        holder.recyclerPriceText?.text = priceFromFB[position].toString()
    }

    inner class MenuHolder(view: View) : RecyclerView.ViewHolder(view),View.OnClickListener {
        var recyclerRestaurantText : TextView? = null
        var recyclerPriceText: TextView? = null

        init{
            recyclerRestaurantText = view.findViewById(R.id.recyclerRestaurantText)
            recyclerPriceText = view.findViewById(R.id.recyclerPriceText)
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
            listener.onItemClick(position)
            }
        }

    }
        interface  OnItemClickListener{
            fun onItemClick(position: Int)
        }




}