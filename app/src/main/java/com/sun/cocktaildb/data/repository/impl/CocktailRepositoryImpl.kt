package com.sun.cocktaildb.data.repository.impl

import com.sun.cocktaildb.data.repository.CocktailRepository
import com.sun.cocktaildb.domain.model.Category
import com.sun.cocktaildb.domain.model.Cocktail

class CocktailRepositoryImpl : CocktailRepository {
    
    override suspend fun getCategories(): List<Category> {
        return listOf(
            Category("1", "Sour", "Sour cocktails"),
            Category("2", "Argi", "Argi cocktails"),
            Category("3", "Mahatan", "Mahatan cocktails")
        )
    }
    
    override suspend fun getPopularCocktails(): List<Cocktail> {
        return listOf(
            Cocktail("1", "Long Island", "Here is the description for this cocktail.", "url1"),
            Cocktail("2", "Margarita", "Classic margarita cocktail", "url2"),
            Cocktail("3", "Mojito", "Refreshing mojito cocktail", "url3")
        )
    }
} 