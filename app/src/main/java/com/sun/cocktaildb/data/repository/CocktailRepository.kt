package com.sun.cocktaildb.data.repository

import com.sun.cocktaildb.domain.model.Category
import com.sun.cocktaildb.domain.model.Cocktail

interface CocktailRepository {
    fun getCategories(): List<Category>
    fun getPopularCocktails(): List<Cocktail>
} 