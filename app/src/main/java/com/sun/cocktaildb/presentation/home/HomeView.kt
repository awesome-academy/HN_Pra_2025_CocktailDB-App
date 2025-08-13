package com.sun.cocktaildb.presentation.home

import com.sun.cocktaildb.domain.model.Category
import com.sun.cocktaildb.domain.model.Cocktail
import com.sun.cocktaildb.presentation.base.BaseView

interface HomeView : BaseView {
    fun showCategories(categories: List<Category>)
    fun showPopularCocktails(cocktails: List<Cocktail>)
    fun onCategoryClicked(category: Category)
    fun onCocktailClicked(cocktail: Cocktail)
    fun onBottomNavigationItemSelected(itemId: Int)
} 