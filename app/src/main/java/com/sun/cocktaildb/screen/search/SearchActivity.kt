package com.sun.cocktaildb.screen.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sun.cocktaildb.data.model.Cocktail
import com.sun.cocktaildb.data.repository.impl.CocktailRepositoryImpl
import com.sun.cocktaildb.databinding.ActivitySearchBinding
import com.sun.cocktaildb.screen.search.adapter.SearchAdapter
import com.sun.cocktaildb.screen.search.adapter.HistoryAdapter
import com.sun.cocktaildb.R

class SearchActivity : AppCompatActivity(), SearchView {
    
    private lateinit var binding: ActivitySearchBinding
    private lateinit var presenter: SearchPresenter
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var historyAdapter: HistoryAdapter
    private var isBottomNavigationSetup = false
    private var isFinishingActivity = false

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SearchActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Reset setup flags for new activity instance
        isBottomNavigationSetup = false
        isFinishingActivity = false

        setupPresenter()
        setupRecyclerView()
        setupHistoryRecyclerView()
        // Remove setupBottomNavigation from onCreate to avoid timing issues
        setupSearchField()
        setupClickListeners()
    }

    private fun setupPresenter() {
        presenter = SearchPresenter(CocktailRepositoryImpl())
        presenter.setView(this)
    }

    private fun setupRecyclerView() {
        searchAdapter = SearchAdapter { cocktail ->
            presenter.onCocktailClicked(cocktail)
        }
        
        binding.rvSearchResults.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = searchAdapter
        }
    }

    private fun setupHistoryRecyclerView() {
        historyAdapter = HistoryAdapter(
            onHistoryClicked = { historyItem ->
                binding.etSearch.setText(historyItem)
                presenter.searchCocktails(historyItem)
            },
            onHistoryRemoved = { historyItem ->
                presenter.removeFromHistory(historyItem)
            }
        )
        
        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = historyAdapter
        }
    }

    private fun setupBottomNavigation() {
        // Prevent multiple setup attempts
        if (isBottomNavigationSetup) {
            return
        }
        
        // Add null safety check and ensure view is properly initialized
        binding.bottomNavigation?.let { bottomNav ->
            // Simple approach: just wait a bit and then setup
            bottomNav.post {
                if (!isFinishing && !isDestroyed && !isBottomNavigationSetup) {
                    setupBottomNavigationInternal(bottomNav)
                    isBottomNavigationSetup = true
                }
            }
        }
    }
    
    private fun setupBottomNavigationInternal(bottomNav: BottomNavigationView) {
        // Check if activity is finishing or destroyed
        if (isFinishing || isDestroyed) {
            return
        }
        
        bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    // Simple navigation to home
                    if (!isFinishingActivity && !isFinishing && !isDestroyed) {
                        isFinishingActivity = true
                        finish()
                    }
                    true
                }
                R.id.navigation_favorites -> {
                    // TODO: Navigate to favorites
                    true
                }
                R.id.navigation_search -> {
                    // Already on search screen
                    true
                }
                R.id.navigation_profile -> {
                    // TODO: Navigate to profile
                    true
                }
                else -> false
            }
        }
        
        // Set search as selected - simple approach
        try {
            bottomNav.selectedItemId = R.id.navigation_search
        } catch (e: Exception) {
            // Log the error but don't crash
            e.printStackTrace()
        }
    }

    private fun setupSearchField() {
        // Remove auto-search on text change
        // Search only happens when Search button is clicked
    }

    private fun setupClickListeners() {
        binding.btnSearch.setOnClickListener {
            val query = binding.etSearch.text?.toString() ?: ""
            presenter.searchCocktails(query)
        }
    }

    override fun onResume() {
        super.onResume()
        // Setup bottom navigation here to ensure view is fully initialized
        setupBottomNavigation()
        presenter.onStart()
    }

    override fun onPause() {
        super.onPause()
        presenter.onStop()
    }
    
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            // Try to setup bottom navigation again when window gains focus
            setupBottomNavigation()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Reset setup flags
        isBottomNavigationSetup = false
        isFinishingActivity = false
    }

    // SearchView implementations
    override fun showSearchResults(cocktails: List<Cocktail>) {
        binding.apply {
            historySection.visibility = View.GONE // Hide entire history section when showing search results
            rvSearchResults.visibility = View.VISIBLE
            llNoResults.visibility = View.GONE
            searchAdapter.updateCocktails(cocktails)
        }
    }

    override fun showNoResults() {
        binding.apply {
            historySection.visibility = View.GONE // Hide entire history section when showing no results
            rvSearchResults.visibility = View.GONE
            llNoResults.visibility = View.VISIBLE
        }
    }

    override fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    override fun clearSearchResults() {
        binding.apply {
            historySection.visibility = View.VISIBLE // Show entire history section when clearing results (back to initial state)
            rvSearchResults.visibility = View.GONE
            llNoResults.visibility = View.GONE
            searchAdapter.updateCocktails(emptyList())
        }
    }

    override fun onCocktailClicked(cocktail: Cocktail) {
        Toast.makeText(this, "Selected: ${cocktail.name}", Toast.LENGTH_SHORT).show()
        // TODO: Navigate to cocktail detail screen
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showHistory(historyItems: List<String>) {
        binding.historySection.visibility = View.VISIBLE
        historyAdapter.updateHistory(historyItems)
    }

    override fun hideHistory() {
        binding.historySection.visibility = View.GONE
    }

    override fun removeFromHistory(historyItem: String) {
        // History removal is handled by presenter
        // No need to show Toast
    }
}
