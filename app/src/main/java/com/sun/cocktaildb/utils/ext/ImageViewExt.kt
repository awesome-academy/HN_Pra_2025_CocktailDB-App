package com.sun.cocktaildb.utils.ext

import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.sun.cocktaildb.R
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

inline fun ImageView.loadImageUrl(
    context: Context,
    url: String,
    @DrawableRes placeholder: Int = R.drawable.placeholder,
    @DrawableRes error: Int = R.drawable.image_load_failed,
    crossinline onSuccess: () -> Unit = {},
    crossinline onError: (error: String) -> Unit = {},
) {
    this.setImageResource(placeholder)
    val imageLoadExecutor = Executors.newSingleThreadExecutor()

    val imageLoadingRunnable =
        Runnable {
            try {
                val connection = URL(url).openConnection() as HttpURLConnection
                val inputStream = connection.inputStream
                val bitmap = BitmapFactory.decodeStream(inputStream)
                (context as? Activity)?.runOnUiThread {
                    this.setImageBitmap(bitmap)
                }
            } catch (e: Exception) {
                onError(e.toString())
            }
        }

    imageLoadExecutor.execute {
        imageLoadingRunnable.run()
    }
}
