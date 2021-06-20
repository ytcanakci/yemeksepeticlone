package com.ytcexample.yemeksepeticlone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    var nameFromFB: ArrayList<String> = ArrayList()
    var manyFromFB: ArrayList<Int> = ArrayList()
    var adapter : cartRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        getCart()
        var layoutManager = LinearLayoutManager(this)
        recyclercartView.layoutManager = layoutManager

        adapter = cartRecyclerAdapter(nameFromFB,manyFromFB)
        recyclercartView.adapter = adapter

    }

    fun getCart(){

        var email = auth.currentUser?.email.toString()

        db.collection("Carts").document("$email").collection("List").addSnapshotListener { snapshot, exception ->
            if(exception != null){
                Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()
            } else{
                if (snapshot != null) {
                    if(!snapshot.isEmpty){
                        val documents = snapshot.documents
                        nameFromFB.clear()
                        manyFromFB.clear()
                        for (document in documents) {


                            val name = document.id as String
                            val many = document.get("many")
                            val manyx = many.toString().toInt()
                            if(manyx>0) {

                                nameFromFB.add(name)
                                manyFromFB.add(manyx)



                                adapter!!.notifyDataSetChanged()
                            }



                        }
                    }
                }
            }
        }

        db.collection("Carts").document("$email").collection("fee").addSnapshotListener { snapshot, exception ->
            if(exception != null){
                Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()
            } else{
                if (snapshot != null) {
                    if(!snapshot.isEmpty){
                        val documents = snapshot.documents

                        for (document in documents) {
                            val fee = document.get("fee")
                            val feeView: TextView = findViewById(R.id.feeView)
                            feeView.text = fee.toString()


                        }
                    }
                }
            }
        }
        db.collection("Carts").document("$email").collection("currRestaurant").addSnapshotListener { snapshot, exception ->
            if(exception != null){
                Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()
            } else{
                if (snapshot != null) {
                    if(!snapshot.isEmpty){
                        val documents = snapshot.documents
                        var restaurant = documents[0].get("res").toString()
                        val nameView: TextView = findViewById(R.id.resnameText)
                        nameView.text = "Selected Restaurant: " + restaurant.toString()



                    }
                }
            }
        }
    }
    fun clearClicked(view: View){
        var email = auth.currentUser?.email.toString()
        val intent = Intent(applicationContext,HomePage::class.java)

        db.collection("Carts").document("$email").collection("List").document("Adana").update("many",0)
        db.collection("Carts").document("$email").collection("List").document("Et Döner").update("many",0)
        db.collection("Carts").document("$email").collection("List").document("Et Şiş").update("many",0)
        db.collection("Carts").document("$email").collection("List").document("Köfte").update("many",0)
        db.collection("Carts").document("$email").collection("List").document("Lahmacun").update("many",0)
        db.collection("Carts").document("$email").collection("List").document("Tavuk Şiş").update("many",0)
        db.collection("Carts").document("$email").collection("List").document("Urfa").update("many",0)
        db.collection("Carts").document("$email").collection("fee").document("fee").update("fee",0)



        startActivity(intent)
        finish()

    }

    fun completeClicked(view: View){
        var email = auth.currentUser?.email.toString()
        val intent = Intent(applicationContext,HomePage::class.java)


        db.collection("Carts").document("$email").collection("currRestaurant").addSnapshotListener { snapshot, exception ->
            if(exception != null){
                Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()
            } else{
                if (snapshot != null) {
                    if(!snapshot.isEmpty){
                        val documents = snapshot.documents
                        var restaurant = documents[0].get("res").toString()
                        var howmany = nameFromFB.size
                        howmany -= 1
                        val orderMap = hashMapOf<String,Any>()

                        for (i in 0..howmany) {
                            var food = nameFromFB[i]
                            orderMap.put("$food",manyFromFB[i])
                        }
                        orderMap.put("account",email)
                        orderMap.put("fee",feeView.text.toString().toInt())
                        db.collection("Restaurants").document("$restaurant").collection("Orders").add(orderMap)
                        Toast.makeText(applicationContext,"Order is completed",Toast.LENGTH_LONG).show()
                        startActivity(intent)



                    }
                }
            }
        }



        db.collection("Carts").document("$email").collection("List").document("Adana").update("many",0)
        db.collection("Carts").document("$email").collection("List").document("Et Döner").update("many",0)
        db.collection("Carts").document("$email").collection("List").document("Et Şiş").update("many",0)
        db.collection("Carts").document("$email").collection("List").document("Köfte").update("many",0)
        db.collection("Carts").document("$email").collection("List").document("Lahmacun").update("many",0)
        db.collection("Carts").document("$email").collection("List").document("Tavuk Şiş").update("many",0)
        db.collection("Carts").document("$email").collection("List").document("Urfa").update("many",0)
        db.collection("Carts").document("$email").collection("fee").document("fee").update("fee",0)












    }
}