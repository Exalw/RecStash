package com.example.recstash.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    private val database = RecipeDatabase.getDatabase(application)
    private val repository = RecipeRepository(database.recipeDao())

    val recipes = repository.recipes.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addRecipe(name: String, description: String) {
        viewModelScope.launch {
            repository.addRecipe(name, description)
        }
    }
}