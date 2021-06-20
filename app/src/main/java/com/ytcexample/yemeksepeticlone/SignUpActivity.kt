package com.ytcexample.yemeksepeticlone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.emailText
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
    }


    fun signup1Clicked(view: View) {
        val email = signupemailText.text.toString()
        val password = signuppasswordText.text.toString()
        val name = signupnameText.text.toString()
        val surname = signupsurnameText.text.toString()
        val address = signupadressText.text.toString()
        val phone = signupphoneText.text.toString()

        val infoMap = hashMapOf<String,Any>()
        infoMap.put("email",email)
        infoMap.put("Name",name)
        infoMap.put("Surname",surname)
        infoMap.put("Address",address)
        infoMap.put("Phone",phone)
        val cartMap = hashMapOf<String,Any>()
        cartMap.put("fee",0)
        val itemMap = hashMapOf<String,Any>()
        itemMap.put("many",0)

        val currMap = hashMapOf<String,Any>()
        currMap.put("res","blank")



        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if (task.isSuccessful) {

                db.collection("Users").document("$email").set(infoMap)
                db.collection("Carts").document("$email").collection("List").document("Adana").set(itemMap)
                db.collection("Carts").document("$email").collection("List").document("Et Döner").set(itemMap)
                db.collection("Carts").document("$email").collection("List").document("Et Şiş").set(itemMap)
                db.collection("Carts").document("$email").collection("List").document("Köfte").set(itemMap)
                db.collection("Carts").document("$email").collection("List").document("Lahmacun").set(itemMap)
                db.collection("Carts").document("$email").collection("List").document("Tavuk Şiş").set(itemMap)
                db.collection("Carts").document("$email").collection("List").document("Urfa").set(itemMap)
                db.collection("Carts").document("$email").collection("fee").document("fee").set(cartMap)
                db.collection("Carts").document("$email").collection("currRestaurant").document("res").set(cartMap)





                auth.signOut()
                val intent = Intent(applicationContext,MainActivity::class.java)
                Toast.makeText(applicationContext,"Login now",Toast.LENGTH_LONG).show()
                startActivity(intent)
                finish()
            }

        }.addOnFailureListener { exception ->
            if(exception != null){
                Toast.makeText(applicationContext,exception.localizedMessage.toString(), Toast.LENGTH_LONG)
            }
        }
    }
}