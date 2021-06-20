package com.ytcexample.yemeksepeticlone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home_page.*

class HomePage : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        auth = FirebaseAuth.getInstance()



        db = FirebaseFirestore.getInstance()

        getDataFromFirestore()

    }

    fun accountClicked(view: View){
        val intent = Intent(applicationContext,AccountPage::class.java)
        startActivity(intent)

    }
    fun listClicked(view: View) {
        val intent = Intent(applicationContext,RestaurantList::class.java)
        startActivity(intent)
    }

    fun cartClicked(view: View){
        val intent = Intent(applicationContext,CartActivity::class.java)
        startActivity(intent)



    }

    fun getDataFromFirestore(){
        var email = "x"

        if(auth.currentUser!= null){
             email = auth.currentUser?.email.toString()
        } else{
            email = intent.getStringExtra("email").toString()
        }




        db.collection("Users").whereEqualTo("email", email).addSnapshotListener { snapshot, exception ->
            if(exception != null){
                Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()
            } else{
                if (snapshot != null) {
                    if(!snapshot.isEmpty){
                        val documents = snapshot.documents

                        for (document in documents) {
                            val name = document.get("Name") as String
                            val surname = document.get("Surname") as String
                            val fullname = "$name $surname"


                            val nameView: TextView = findViewById(R.id.emailText1)
                            nameView.text = fullname.toString()



                        }
                    }
                }
            }
        }
    }

}