package com.example.snapchatclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth

class SnapsActivity : AppCompatActivity() {
    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snaps)
    }

    override fun onCreateOptionsMenu(menu: Menu?):Boolean{
        val inflater = menuInflater;
        inflater.inflate(R.menu.snaps,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item?.itemId == R.id.createSnap){
            val intent = Intent(this,CreateSnapActivity::class.java)
            startActivity(intent)
        }else if(item?.itemId == R.id.logout){
            mAuth.signOut()
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        mAuth.signOut()
        super.onBackPressed()
    }

}