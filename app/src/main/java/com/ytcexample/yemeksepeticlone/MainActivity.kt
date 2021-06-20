package com.ytcexample.yemeksepeticlone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*


 class MainActivity : AppCompatActivity() {

     private lateinit var auth: FirebaseAuth
     private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(applicationContext,HomePage::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun signinClicked(view: View) {
        val email = emailText.text.toString()
        val password = passwordText.text.toString()
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(applicationContext,"Welcome: ${auth.currentUser?.email.toString()}",Toast.LENGTH_LONG).show()
                val intent = Intent(applicationContext,HomePage::class.java)
                intent.putExtra("email",email.toString())
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener { exception -> Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()
        }
    }

    fun signupClicked(view: View) {


                val intent = Intent(applicationContext,SignUpActivity::class.java)

                startActivity(intent)

    }



}