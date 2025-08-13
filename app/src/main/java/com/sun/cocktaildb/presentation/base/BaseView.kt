package com.sun.cocktaildb.presentation.base

interface BaseView {
    fun showLoading()

    fun hideLoading()

    fun showError(message: String)
}
