package com.sun.cocktaildb.screen.authenticate.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

import android.widget.Toast
import com.sun.cocktaildb.R
import com.sun.cocktaildb.screen.MainActivity
import com.sun.cocktaildb.utils.base.BaseActivity

class LoginActivity : BaseActivity(), LoginContract.View {
    
    private lateinit var presenter: LoginPresenter
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    
    override fun getLayoutResourceId(): Int = R.layout.activity_logn

    override fun initView() {
        presenter = LoginPresenter()
        presenter.setView(this)
        
        initializeViews()
        setupClickListeners()
    }
    
    private fun initializeViews() {
        emailEditText = findViewById(R.id.et_username)
        passwordEditText = findViewById(R.id.et_password)
        loginButton = findViewById(R.id.btn_login)
        // Note: ProgressBar is not in the current layout, we'll handle loading state differently
    }
    
    private fun setupClickListeners() {
        loginButton.setOnClickListener {
            presenter.login()
        }
    }
    
    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }
    
    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        presenter.setView(null)
    }

    override fun showLoading() {
        loginButton.isEnabled = false
        loginButton.text = "Logging in..."
    }

    override fun hideLoading() {
        loginButton.isEnabled = true
        loginButton.text = getString(R.string.login)
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showSuccess() {
        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
    }

    override fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun getEmail(): String {
        return emailEditText.text.toString().trim()
    }

    override fun getPassword(): String {
        return passwordEditText.text.toString()
    }

    override fun clearInputs() {
        emailEditText.text.clear()
        passwordEditText.text.clear()
    }
}
