package com.sun.cocktaildb.screen.splashscreen

import android.content.Intent
import com.sun.cocktaildb.databinding.ActivitySplashscreenBinding
import com.sun.cocktaildb.presentation.base.BaseActivity
import com.sun.cocktaildb.presentation.splash.SplashPresenter
import com.sun.cocktaildb.presentation.splash.SplashView

class SplashScreenActivity : BaseActivity(), SplashView {
    private lateinit var binding: ActivitySplashscreenBinding
    private lateinit var presenter: com.sun.cocktaildb.presentation.splash.SplashPresenter

    override fun initView() {
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupPresenter()
    }

    private fun setupPresenter() {
        presenter = SplashPresenter()
        presenter.setView(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.onStart()
    }

    override fun onPause() {
        super.onPause()
        presenter.onStop()
    }

    // SplashView implementations
    override fun showLoading() {
        // Loading indicator
    }

    override fun hideLoading() {
        // Loading indicator
    }

    override fun showError(message: String) {
        // Error handling
    }

    override fun navigateToHome() {
        val intent = Intent(this, com.sun.cocktaildb.screen.homescreen.HomeScreenActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun navigateToLogin() {
        // Login navigation
        // For now, navigate to home
        navigateToHome()
    }
}
