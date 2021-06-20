package com.ytcexample.yemeksepeticlone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_restaurant_details.*
import kotlinx.android.synthetic.main.activity_restaurant_list.*

class RestaurantDetails : AppCompatActivity() , menuRecyclerAdapter.OnItemClickListener{
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    var menuFromFB: ArrayList<String> = ArrayList()
    var priceFromFB: ArrayList<Int> = ArrayList()
    var adapter : menuRecyclerAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_details)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        getMenu()
        var layoutManager = LinearLayoutManager(this)
        recyclerView3.layoutManager = layoutManager

        adapter = menuRecyclerAdapter(menuFromFB,priceFromFB,this)
        recyclerView3.adapter = adapter





    }

    fun getMenu(){



        db.collection("Allmenu").addSnapshotListener { snapshot, exception ->
            if (exception != null){
                Toast.makeText(applicationContext,exception.localizedMessage.toString(), Toast.LENGTH_LONG).show()
            } else{
                if(snapshot!=null){
                    if(!snapshot.isEmpty){
                        menuFromFB.clear()
                        priceFromFB.clear()
                        val documents = snapshot.documents
                        for(document in documents){

                            val item = document.id as String
                            menuFromFB.add(item).toString()

                            val price = document.get("price").toString().toInt()


                            priceFromFB.add(price)

                            adapter!!.notifyDataSetChanged()

                        }

                    }
                }
            }
        }
    }

    override fun onItemClick(position: Int) {
        var email = auth.currentUser?.email.toString()
        var food = menuFromFB[position].toString()
        var price = priceFromFB[position]
        Toast.makeText(applicationContext,"$food added to cart",Toast.LENGTH_LONG).show()




        db.collection("Carts").document("$email").collection("List").document("$food").update("many",FieldValue.increment(1))
        for(i in 1..price){
            db.collection("Carts").document("$email").collection("fee").document("fee").update("fee",FieldValue.increment(1))
        }

    }

    fun gotocartClicked(view: View){
        val intent = Intent(applicationContext,CartActivity::class.java)
        val restaurantName = intent.getStringExtra("restaurantName")

        intent.putExtra("restaurantName",restaurantName)
        startActivity(intent)
    }


}