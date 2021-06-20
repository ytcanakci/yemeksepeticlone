package com.ytcexample.yemeksepeticlone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_account_page.*

class AccountPage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_page)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
    }

    fun logoutClicked(view: View){

        auth.signOut()

        val intent = Intent(applicationContext,MainActivity::class.java)
        startActivity(intent)
        finish()

    }

    fun updateClicked(view: View){
        var email = auth.currentUser?.email.toString()
        var newname = editnameText.text.toString()
        var newsurname = editsurnameText.text.toString()
        var newphone = editphoneText.text.toString()
        var newaddress = editadressText.text.toString()

        val infoMap = hashMapOf<String,Any>()

        infoMap.put("Name",newname)
        infoMap.put("Surname",newsurname)
        infoMap.put("Address",newaddress)
        infoMap.put("Phone",newphone)
        infoMap.put("email",email)

        db.collection("Users").document("$email").set(infoMap)
        Toast.makeText(applicationContext,"Usen information saved",Toast.LENGTH_LONG).show()

        val intent = Intent(applicationContext,HomePage::class.java)
        startActivity(intent)
        finish()

    }
}