package com.example.recstash.data

class RecipeRepository(
    private val recipeDao: RecipeDao
) {
    val recipes = recipeDao.getAllRecipes()

    suspend fun addRecipe(recipe: RecipeEntity) {
        recipeDao.insertRecipe(recipe)
    }

    suspend fun updateRecipe(recipe: RecipeEntity) {
        recipeDao.updateRecipe(recipe)
    }

    suspend fun deleteRecipe(recipe: RecipeEntity) {
        recipeDao.deleteRecipe(recipe)
    }
}