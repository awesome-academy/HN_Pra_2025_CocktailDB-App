package com.sun.cocktaildb.utils.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.graphics.drawable.toDrawable
import com.sun.cocktaildb.R

class LoadingDialog(
    val mContext: Context,
) {
    private var dialog: Dialog? = null
    private var run: Runnable? = null
    private var handler = android.os.Handler(Looper.getMainLooper())

    init {
        dialog = Dialog(mContext)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setContentView(R.layout.dialog_loading)
        val window: Window? = dialog?.window
        window?.let {
            it.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
            )
            it.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
            val layoutParams = window.attributes
            layoutParams.gravity = Gravity.CENTER

            // Hide system UI (status bar and navigation bar)
            layoutParams.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES

            it.attributes = layoutParams

            // Set flags to hide system UI
            it.addFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN or
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            )

            dialog?.setCancelable(false)

            run =
                Runnable {
                    try {
                        if (dialog != null && dialog?.isShowing == true) {
                            dialog?.hide()
                        }
                    } catch (e: Exception) {
                        // //LogVnp.Shape1(Shape1);
                    }
                }
        }
    }

    fun show() {
        dialog?.show()

        // Ensure system UI stays hidden after dialog shows
        dialog?.window?.let { window ->
            window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            )
        }

        run?.let { handler.postDelayed(it, 90000) }
    }

    fun hide() {
        dialog?.dismiss()

        try {
            run?.let { handler.removeCallbacks(it) }
        } catch (e: Exception) {
        }
    }
}
