package com.sun.cocktaildb.presentation.base

interface BasePresenter<T : BaseView> {
    fun onStart()
    fun onStop()
    fun setView(view: T?)
} 