package com.sun.cocktaildb.repository

import com.sun.cocktaildb.model.Category
import com.sun.cocktaildb.model.Cocktail

interface CocktailRepository {
    fun getCategories(): List<Category>

    fun getPopularCocktails(): List<Cocktail>
}
