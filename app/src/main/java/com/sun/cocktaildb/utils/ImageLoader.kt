package com.sun.cocktaildb.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

object ImageLoader {
    
    /**
     * Load image from URL into ImageView using Android native APIs
     */
    fun loadImage(imageView: ImageView, imageUrl: String, placeholderResId: Int) {
        if (imageUrl.isEmpty()) {
            imageView.setImageResource(placeholderResId)
            return
        }
        
        // Set placeholder first
        imageView.setImageResource(placeholderResId)
        
        // Load image asynchronously
        ImageLoaderTask(imageView, placeholderResId).execute(imageUrl)
    }
    
    /**
     * AsyncTask to load images from network
     */
    private class ImageLoaderTask(
        private val imageView: ImageView,
        private val placeholderResId: Int
    ) : AsyncTask<String, Void, Bitmap?>() {
        
        override fun doInBackground(vararg urls: String): Bitmap? {
            val urlString = urls[0]
            return try {
                val url = URL(urlString)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connectTimeout = 15000
                connection.readTimeout = 15000
                connection.connect()
                
                val input: InputStream = connection.inputStream
                val bitmap = BitmapFactory.decodeStream(input)
                input.close()
                connection.disconnect()
                bitmap
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
        
        override fun onPostExecute(result: Bitmap?) {
            if (result != null) {
                imageView.setImageBitmap(result)
            } else {
                imageView.setImageResource(placeholderResId)
            }
        }
    }
}

