package com.sun.cocktaildb.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

object ImageLoader {
	
	private val executor = Executors.newFixedThreadPool(3)
	private val mainHandler = Handler(Looper.getMainLooper())
	
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
		
		// Tag to prevent stale-image race on fast-recycling views
		imageView.tag = imageUrl
		
		executor.execute {
			val bitmap = downloadBitmap(imageUrl)
			mainHandler.post {
				if (imageView.tag == imageUrl) {
					if (bitmap != null) {
						imageView.setImageBitmap(bitmap)
					} else {
						imageView.setImageResource(placeholderResId)
					}
				}
			}
		}
	}
	
	private fun downloadBitmap(urlString: String): Bitmap? {
		return try {
			val url = URL(urlString)
			val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
			connection.doInput = true
			connection.connectTimeout = 15000
			connection.readTimeout = 15000
			connection.instanceFollowRedirects = true
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
}

