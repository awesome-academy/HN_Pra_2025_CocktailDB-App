package com.sun.cocktaildb.domain.usecase

import com.sun.cocktaildb.data.repository.CocktailRepository
import com.sun.cocktaildb.domain.model.Cocktail

class GetPopularCocktailsUseCase(
    private val repository: CocktailRepository
) {
    operator fun invoke(): List<Cocktail> {
        return repository.getPopularCocktails()
    }
} 