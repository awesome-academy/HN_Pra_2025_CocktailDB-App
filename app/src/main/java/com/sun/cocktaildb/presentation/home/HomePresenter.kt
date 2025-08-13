package com.sun.cocktaildb.presentation.home

import android.os.Handler
import android.os.Looper
import com.sun.cocktaildb.domain.model.Category
import com.sun.cocktaildb.domain.model.Cocktail
import com.sun.cocktaildb.domain.usecase.GetCategoriesUseCase
import com.sun.cocktaildb.domain.usecase.GetPopularCocktailsUseCase
import com.sun.cocktaildb.presentation.base.BasePresenter
import java.util.concurrent.Executors

class HomePresenter(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getPopularCocktailsUseCase: GetPopularCocktailsUseCase
) : BasePresenter<HomeView> {
    
    private var view: HomeView? = null
    private val executor = Executors.newSingleThreadExecutor()
    private val mainHandler = Handler(Looper.getMainLooper())
    
    override fun setView(view: HomeView?) {
        this.view = view
    }
    
    override fun onStart() {
        loadCategories()
        loadPopularCocktails()
    }
    
    override fun onStop() {
        // Cleanup if needed
    }
    
    private fun loadCategories() {
        view?.showLoading()
        executor.execute {
            try {
                val categories = getCategoriesUseCase()
                mainHandler.post {
                    view?.showCategories(categories)
                }
            } catch (e: Exception) {
                mainHandler.post {
                    view?.showError(e.message ?: "Error loading categories")
                }
            }
        }
    }
    
    private fun loadPopularCocktails() {
        executor.execute {
            try {
                val cocktails = getPopularCocktailsUseCase()
                mainHandler.post {
                    view?.showPopularCocktails(cocktails)
                    view?.hideLoading()
                }
            } catch (e: Exception) {
                mainHandler.post {
                    view?.showError(e.message ?: "Error loading popular cocktails")
                    view?.hideLoading()
                }
            }
        }
    }
    
    fun onCategoryClicked(category: Category) {
        view?.onCategoryClicked(category)
    }
    
    fun onCocktailClicked(cocktail: Cocktail) {
        view?.onCocktailClicked(cocktail)
    }
    
    fun onBottomNavigationItemSelected(itemId: Int) {
        view?.onBottomNavigationItemSelected(itemId)
    }
} 