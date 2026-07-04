package com.example.recstash.ui

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recstash.data.RecipeViewModel

@Composable
fun RecipeApp(
    viewModel: RecipeViewModel = viewModel()
) {
    var screen by remember { mutableStateOf<Screen>(Screen.List) }
    val recipes by viewModel.recipes.collectAsState()

    when (val currentScreen = screen) {
        Screen.List -> RecipeListScreen(
            recipes = recipes,
            onAddRecipe = { screen = Screen.Add },
            onRecipeClick = { recipe ->
                screen = Screen.Detail(recipe)
            }
        )

        Screen.Add -> AddRecipeScreen(
            onSave = { name, description, imagePath ->
                viewModel.addRecipe(name, description, imagePath)
                screen = Screen.List
            },
            onCancel = {
                screen = Screen.List
            }
        )

        is Screen.Detail -> RecipeDetailScreen(
            recipe = currentScreen.recipe,
            onBack = {
                screen = Screen.List
            },
            onEdit = {
                screen = Screen.Edit(currentScreen.recipe)
            }
        )

        is Screen.Edit -> EditRecipeScreen(
            recipe = currentScreen.recipe,
            onSave = { updatedRecipe ->
                viewModel.updateRecipe(updatedRecipe)
                screen = Screen.Detail(updatedRecipe)
            },
            onCancel = {
                screen = Screen.Detail(currentScreen.recipe)
            },
            onDelete = { recipe ->
                viewModel.deleteRecipe(recipe)
                screen = Screen.List
            }
        )
    }
}