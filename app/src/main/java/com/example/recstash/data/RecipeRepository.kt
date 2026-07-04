package com.example.recstash

class RecipeRepository(
    private val recipeDao: RecipeDao
) {
    val recipes = recipeDao.getAllRecipes()

    suspend fun addRecipe(name: String, description: String) {
        recipeDao.insertRecipe(
            RecipeEntity(
                name = name,
                description = description
            )
        )
    }
}