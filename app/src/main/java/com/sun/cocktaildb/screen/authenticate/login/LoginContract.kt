package com.sun.cocktaildb.screen.authenticate.login

interface LoginContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showError(message: String)
        fun showSuccess()
        fun navigateToMain()
        fun getEmail(): String
        fun getPassword(): String
        fun clearInputs()
    }

    interface Presenter : com.sun.cocktaildb.utils.base.BasePresenter<View> {
        fun login()
        fun validateInputs(): Boolean
    }
} 