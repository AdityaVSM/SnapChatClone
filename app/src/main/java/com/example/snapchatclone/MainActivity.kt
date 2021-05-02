package com.example.snapchatclone

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.util.*

class MainActivity : AppCompatActivity() {
    val mAuth = FirebaseAuth.getInstance()
    var RC_SIGN_IN:Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.setTitle("Login");
        //getSupportActionBar()?.setBackgroundDrawable(android.R.drawable.bottom_bar)
        setContentView(R.layout.activity_main)


        if(mAuth.currentUser != null){
            login()
        }
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build())


        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(), RC_SIGN_IN)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser
                val uid = user.uid
                val name = user.displayName.toString()
                val email = user.email.toString()
                Log.i("name:",name)
                Log.i("uid:",uid)
                Log.i("email:",email)
                FirebaseDatabase.getInstance().getReference().child("users").child(uid.toString()).child("email").setValue(email)
                FirebaseDatabase.getInstance().getReference().child("users").child(uid.toString()).child("userName").setValue(name)
                // Successfully signed in
                //val user = Firebase.auth.currentUser
                login()
                // ...
            } else {
                Toast.makeText(this,"Login failed. Try again", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }



    fun login(){
        //Move to next activity
        val intent = Intent(this, SnapsActivity::class.java)
        startActivity(intent)
    }
}