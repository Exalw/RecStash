package com.example.recstash.data

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