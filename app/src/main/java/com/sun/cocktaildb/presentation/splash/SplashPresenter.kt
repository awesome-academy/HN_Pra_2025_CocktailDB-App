package com.sun.cocktaildb.presentation.splash

import android.os.Handler
import android.os.Looper
import com.sun.cocktaildb.presentation.base.BasePresenter

class SplashPresenter : BasePresenter<SplashView> {
    private var view: SplashView? = null
    private val handler = Handler(Looper.getMainLooper())

    override fun setView(view: SplashView?) {
        this.view = view
    }

    override fun onStart() {
        startSplashTimer()
    }

    override fun onStop() {
        // Cleanup if needed
        handler.removeCallbacksAndMessages(null)
    }

    private fun startSplashTimer() {
        handler.postDelayed({
            checkUserStatus()
        }, 2000) // 2 seconds delay
    }

    private fun checkUserStatus() {
        // Check if user is logged in
        // For now, always navigate to home
        view?.navigateToHome()
    }
}
