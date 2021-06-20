package com.ytcexample.yemeksepeticlone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_restaurant_list.*


class RestaurantList : AppCompatActivity(), listRecyclerAdapter.OnItemClickListener {
    private lateinit var db:FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    var nameFromFB: ArrayList<String> = ArrayList()
    var adapter : listRecyclerAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_list)


        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        getRestaurantList()
        var layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        adapter = listRecyclerAdapter(nameFromFB, this)
        recyclerView.adapter = adapter

    }


    fun getRestaurantList(){
        db.collection("Restaurants").addSnapshotListener { snapshot, exception ->
            if (exception != null){
                Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()
            } else{
                if(snapshot!=null){
                if(!snapshot.isEmpty){
                    nameFromFB.clear()
                    val documents = snapshot.documents
                    for(document in documents){
                       val name = document.id as String
                        nameFromFB.add(name).toString()

                        adapter!!.notifyDataSetChanged()

                    }
                }
                }
            }
        }
    }



    override fun onItemClick(position: Int) {
        val intent = Intent(applicationContext,RestaurantDetails::class.java)
        var email = auth.currentUser?.email.toString()
        val restaurantName = nameFromFB[position]
        val resMap = hashMapOf<String,String>()
        resMap["res"] = restaurantName
        db.collection("Carts").document("$email").collection("currRestaurant").document("res").set(resMap)

        startActivity(intent)
        finish()

    }
}


