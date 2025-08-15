package com.sun.cocktaildb.screen.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.sun.cocktaildb.R
import com.sun.cocktaildb.screen.MainActivity
import com.sun.cocktaildb.screen.authenticate.login.LoginActivity
import com.sun.cocktaildb.utils.base.BaseActivity

class SplashScreenActivity : BaseActivity() {
    override fun getLayoutResourceId(): Int = R.layout.activity_splashscreen

    override fun initView() {
        Handler(Looper.getMainLooper()).postDelayed({
            nextActivity()
        }, 1000)
    }

    private fun nextActivity() {
        Intent(this, LoginActivity::class.java).apply {
            startActivity(this)
        }
        finish()
    }
}
