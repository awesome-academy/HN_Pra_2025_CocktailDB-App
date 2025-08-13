package com.sun.cocktaildb.domain.usecase

import com.sun.cocktaildb.data.repository.CocktailRepository
import com.sun.cocktaildb.domain.model.Category

class GetCategoriesUseCase(
    private val repository: CocktailRepository
) {
    operator fun invoke(): List<Category> {
        return repository.getCategories()
    }
} 