package com.example.snapchatclone

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    var RC_SIGN_IN:Int = 1
    lateinit var createAccButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.setTitle("Login");
        //getSupportActionBar()?.setBackgroundDrawable(android.R.drawable.bottom_bar)
        setContentView(R.layout.activity_main)
        createAccButton = findViewById(R.id.createAccButton)

        createAccButton.setOnClickListener {
            createAcc()
        }
//        if(AuthUI.getInstance() != null){
//            login()
//        }
    }
    fun createAcc(){
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
                val uid = user?.uid.toString()
                Log.i("uid",uid)
                val name = user?.displayName.toString()
                Log.i("username",name)
                val email = user?.email.toString()
                if (name == "null" || uid == "null" || email == "null"){
                    Toast.makeText(this,"Login failed. Create account and Try again", Toast.LENGTH_LONG).show()
                    createAcc()
                }else {
                    FirebaseDatabase.getInstance().getReference().child("users").child(uid.toString()).child("email").setValue(email)
                    FirebaseDatabase.getInstance().getReference().child("users").child(uid.toString()).child("userName").setValue(name)
                    login()
                }
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