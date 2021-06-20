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

class listRecyclerAdapter(private val nameArray: ArrayList<String>, private val listener: OnItemClickListener) : RecyclerView.Adapter<listRecyclerAdapter.ListHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {


        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_view_row,parent,false)

        return ListHolder(view)
    }


    override fun getItemCount(): Int {
        return   nameArray.size
    }


    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        val position = nameArray[position]
        holder.recyclerRestaurantText?.text = position



    }





    inner class ListHolder(view: View) : RecyclerView.ViewHolder(view),View.OnClickListener{
            val intent: Intent? = null
            var recyclerRestaurantText : TextView? = null



        init {

            recyclerRestaurantText = view.findViewById(R.id.recyclerRestaurantText)

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