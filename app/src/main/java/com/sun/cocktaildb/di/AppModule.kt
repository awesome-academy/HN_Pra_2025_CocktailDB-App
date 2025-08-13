package com.sun.cocktaildb.di

import com.sun.cocktaildb.data.repository.CocktailRepository
import com.sun.cocktaildb.data.repository.impl.CocktailRepositoryImpl
import com.sun.cocktaildb.domain.usecase.GetCategoriesUseCase
import com.sun.cocktaildb.domain.usecase.GetPopularCocktailsUseCase
import com.sun.cocktaildb.presentation.home.HomePresenter

object AppModule {
    
    // Repository
    private val cocktailRepository: CocktailRepository by lazy {
        CocktailRepositoryImpl()
    }
    
    // Use Cases
    private val getCategoriesUseCase: GetCategoriesUseCase by lazy {
        GetCategoriesUseCase(cocktailRepository)
    }
    
    private val getPopularCocktailsUseCase: GetPopularCocktailsUseCase by lazy {
        GetPopularCocktailsUseCase(cocktailRepository)
    }
    
    // Presenters
    fun provideHomePresenter(): HomePresenter {
        return HomePresenter(getCategoriesUseCase, getPopularCocktailsUseCase)
    }
    
    fun provideSplashPresenter(): com.sun.cocktaildb.presentation.splash.SplashPresenter {
        return com.sun.cocktaildb.presentation.splash.SplashPresenter()
    }
} 