package com.sun.cocktaildb.repository.impl

import com.sun.cocktaildb.model.Category
import com.sun.cocktaildb.model.Cocktail
import com.sun.cocktaildb.repository.CocktailRepository

class CocktailRepositoryImpl : CocktailRepository {
    override fun getCategories(): List<Category> {
        return listOf(
            Category("1", "Sour", "https://example.com/img/sour.png"),
            Category("2", "Argi", "https://example.com/img/argi.png"),
            Category("3", "Manhattan", "https://example.com/img/manhattan.png"),
            Category("4", "Martini", "https://example.com/img/martini.png"),
            Category("5", "Tiki", "https://example.com/img/tiki.png"),
            Category("6", "Highball", "https://example.com/img/highball.png"),
        )
    }

    override fun getPopularCocktails(): List<Cocktail> {
        return listOf(
            Cocktail("1", "Long Island", "Here is the description for this cocktail.", "https://example.com/img/long_island.png"),
            Cocktail("2", "Margarita", "Classic margarita cocktail", "https://example.com/img/margarita.png"),
            Cocktail("3", "Mojito", "Refreshing mojito cocktail", "https://example.com/img/mojito.png"),
            Cocktail("4", "Old Fashioned", "Whiskey, sugar, bitters", "https://example.com/img/old_fashioned.png"),
            Cocktail("5", "Negroni", "Gin, vermouth, Campari", "https://example.com/img/negroni.png"),
            Cocktail("6", "Daiquiri", "Rum, lime, sugar", "https://example.com/img/daiquiri.png"),
            Cocktail("7", "Cosmopolitan", "Vodka, triple sec, cranberry", "https://example.com/img/cosmo.png"),
            Cocktail("8", "Pina Colada", "Rum, coconut, pineapple", "https://example.com/img/pinacolada.png"),
        )
    }
}
