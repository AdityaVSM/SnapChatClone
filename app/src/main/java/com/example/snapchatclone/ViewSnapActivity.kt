package com.example.snapchatclone

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL


class ViewSnapActivity : AppCompatActivity() {
    var messageTextView: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_snap)

        messageTextView = findViewById(R.id.messageTextView)
        var snapsImageView: ImageView = findViewById(R.id.snapImageView)

        messageTextView?.text = intent.getStringExtra("message")

        var url = intent.getStringExtra("imageUrl")

        val task = ImageDownoader()
        val myImage: Bitmap
        try {
            myImage = task.execute(intent.getStringExtra("imageUrl")).get()
            snapsImageView?.setImageBitmap(myImage)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    inner class ImageDownoader : AsyncTask<String, Void, Bitmap>() {
        override fun doInBackground(vararg urls: String?): Bitmap? {
            try {
                val url = URL(urls[0])
                val connection = url.openConnection() as HttpURLConnection
                connection.connect()
                val `is` = connection.inputStream
                return BitmapFactory.decodeStream(`is`)
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }
    }
}
