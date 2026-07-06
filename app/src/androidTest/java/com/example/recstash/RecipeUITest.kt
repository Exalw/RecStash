package com.example.recstash

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import com.example.recstash.ui.RecipeApp
import org.junit.Rule
import org.junit.Test

class RecipeUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun addRecipeScreen_savesRecipeToList() {
        composeTestRule.setContent {
            MaterialTheme {
                RecipeApp()
            }
        }

        composeTestRule.onNodeWithText("+").performClick()

        composeTestRule.onNodeWithText("Recipe name").performTextInput("Gnocchi")
        composeTestRule.onNodeWithTag("add_description").performTextInput("Potato pasta")

        composeTestRule.onNodeWithTag("save_button").performClick()

        composeTestRule.onNodeWithText("Gnocchi").assertExists()
        composeTestRule.onNodeWithText("Potato pasta").assertDoesNotExist()
    }

    @Test
    fun emptyRecipeName_staysOnAddScreen() {
        composeTestRule.setContent {
            MaterialTheme {
                RecipeApp()
            }
        }

        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithTag("add_description").performTextInput("No title")
        composeTestRule.onNodeWithTag("save_button").performClick()

        composeTestRule.onNodeWithText("Add Recipe").assertExists()
    }

    @Test
    fun editRecipeTitle_save_changesTitle() {
        composeTestRule.setContent {
            MaterialTheme {
                RecipeApp()
            }
        }

        val oldTitle = "Gnocchi Old Title"
        val newTitle = "Gnocchi New Title"

        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("Recipe name").performTextInput(oldTitle)
        composeTestRule.onNodeWithTag("add_description").performTextInput("Potato pasta")
        composeTestRule.onNodeWithTag("save_button").performClick()

        composeTestRule.onNodeWithText(oldTitle).performClick()
        composeTestRule.onNodeWithTag("edit_button").performClick()

        composeTestRule.onNodeWithTag("edit_name").performTextClearance()
        composeTestRule.onNodeWithTag("edit_name").performTextInput(newTitle)

        composeTestRule.onNodeWithTag("save_button").performClick()

        composeTestRule.onNodeWithText(newTitle).assertExists()
        composeTestRule.onNodeWithText(oldTitle).assertDoesNotExist()
    }

    @Test
    fun editRecipeTitle_cancel_keepsOldTitle() {
        composeTestRule.setContent {
            MaterialTheme {
                RecipeApp()
            }
        }

        val oldTitle = "Cancel Old Title"
        val newTitle = "Cancel New Title"

        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("Recipe name").performTextInput(oldTitle)
        composeTestRule.onNodeWithTag("add_description").performTextInput("Original description")
        composeTestRule.onNodeWithTag("save_button").performClick()

        composeTestRule.onNodeWithText(oldTitle).performClick()
        composeTestRule.onNodeWithTag("edit_button").performClick()

        composeTestRule.onNodeWithTag("edit_name").performTextClearance()
        composeTestRule.onNodeWithTag("edit_name").performTextInput(newTitle)

        composeTestRule.onNodeWithTag("cancel_button").performClick()

        composeTestRule.onNodeWithText(oldTitle).assertExists()
        composeTestRule.onNodeWithText(newTitle).assertDoesNotExist()
    }

    @Test
    fun editRecipeDescription_save_changesDescription() {
        composeTestRule.setContent {
            MaterialTheme {
                RecipeApp()
            }
        }

        val title = "Description Edit Recipe"
        val oldDescription = "Old description"
        val newDescription = "New better description"

        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("Recipe name").performTextInput(title)
        composeTestRule.onNodeWithTag("add_description").performTextInput(oldDescription)
        composeTestRule.onNodeWithTag("save_button").performClick()

        composeTestRule.onNodeWithText(title).performClick()
        composeTestRule.onNodeWithTag("edit_button").performClick()

        composeTestRule.onNodeWithTag("edit_description").performTextClearance()
        composeTestRule.onNodeWithTag("edit_description").performTextInput(newDescription)

        composeTestRule.onNodeWithTag("save_button").performClick()

        composeTestRule.onNodeWithText(newDescription).assertExists()
        composeTestRule.onNodeWithText(oldDescription).assertDoesNotExist()
    }

    @Test
    fun editRecipeDescription_cancel_keepsOldDescription() {
        composeTestRule.setContent {
            MaterialTheme {
                RecipeApp()
            }
        }

        val title = "Description Cancel Recipe"
        val oldDescription = "Original untouched description"
        val newDescription = "Changed but cancelled description"

        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("Recipe name").performTextInput(title)
        composeTestRule.onNodeWithTag("add_description").performTextInput(oldDescription)
        composeTestRule.onNodeWithTag("save_button").performClick()

        composeTestRule.onNodeWithText(title).performClick()
        composeTestRule.onNodeWithTag("edit_button").performClick()

        composeTestRule.onNodeWithTag("edit_description").performTextClearance()
        composeTestRule.onNodeWithTag("edit_description").performTextInput(newDescription)

        composeTestRule.onNodeWithTag("cancel_button").performClick()

        composeTestRule.onNodeWithText(oldDescription).assertExists()
        composeTestRule.onNodeWithText(newDescription).assertDoesNotExist()
    }

    @Test
    fun deleteRecipe_firstDialogNo_keepsRecipe() {
        composeTestRule.setContent {
            MaterialTheme {
                RecipeApp()
            }
        }

        val title = "Delete No Recipe"

        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("Recipe name").performTextInput(title)
        composeTestRule.onNodeWithTag("add_description").performTextInput("Should stay")
        composeTestRule.onNodeWithTag("save_button").performClick()

        composeTestRule.onNodeWithText(title).performClick()
        composeTestRule.onNodeWithTag("edit_button").performClick()

        composeTestRule.onNodeWithTag("delete_button").performClick()
        composeTestRule.onNodeWithTag("delete_first_no_button").performClick()

        composeTestRule.onNodeWithText(title).assertExists()
    }

    @Test
    fun deleteRecipe_secondDialogCancel_keepsRecipe() {
        composeTestRule.setContent {
            MaterialTheme {
                RecipeApp()
            }
        }

        val title = "Delete Cancel Recipe"

        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("Recipe name").performTextInput(title)
        composeTestRule.onNodeWithTag("add_description").performTextInput("Should stay")
        composeTestRule.onNodeWithTag("save_button").performClick()

        composeTestRule.onNodeWithText(title).performClick()
        composeTestRule.onNodeWithTag("edit_button").performClick()

        composeTestRule.onNodeWithTag("delete_button").performClick()
        composeTestRule.onNodeWithTag("delete_first_yes_button").performClick()
        composeTestRule.onNodeWithTag("delete_final_cancel_button").performClick()

        composeTestRule.onNodeWithText(title).assertExists()
    }

    @Test
    fun deleteRecipe_confirmTwice_removesRecipeFromList() {
        composeTestRule.setContent {
            MaterialTheme {
                RecipeApp()
            }
        }

        val title = "Delete Confirm Recipe"

        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("Recipe name").performTextInput(title)
        composeTestRule.onNodeWithTag("add_description").performTextInput("Should disappear")
        composeTestRule.onNodeWithTag("save_button").performClick()

        composeTestRule.onNodeWithText(title).performClick()
        composeTestRule.onNodeWithTag("edit_button").performClick()

        composeTestRule.onNodeWithTag("delete_button").performClick()
        composeTestRule.onNodeWithTag("delete_first_yes_button").performClick()
        composeTestRule.onNodeWithTag("delete_final_confirm_button").performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(title).assertDoesNotExist()
        composeTestRule.onNodeWithText("Recipes").assertExists()
    }

    @Test
    fun editRecipeIngredients_save_changesIngredients() {
        composeTestRule.setContent {
            MaterialTheme {
                RecipeApp()
            }
        }

        val title = "Ingredients Edit Recipe"
        val oldIngredients = "Potatoes\nFlour\nSalt"
        val newIngredients = "Tomatoes\nGarlic\nBasil"

        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("Recipe name").performTextInput(title)
        composeTestRule.onNodeWithTag("add_ingredients").performTextInput(oldIngredients)
        composeTestRule.onNodeWithTag("save_button").performClick()

        composeTestRule.onNodeWithText(title).performClick()
        composeTestRule.onNodeWithTag("edit_button").performClick()

        composeTestRule.onNodeWithTag("edit_ingredients").performTextClearance()
        composeTestRule.onNodeWithTag("edit_ingredients").performTextInput(newIngredients)

        composeTestRule.onNodeWithTag("save_button").performClick()

        composeTestRule.onNodeWithText("Tomatoes\nGarlic\nBasil").assertExists()
        composeTestRule.onNodeWithText("Potatoes").assertDoesNotExist()
    }

    @Test
    fun editRecipeIngredients_cancel_keepsOldIngredients() {
        composeTestRule.setContent {
            MaterialTheme {
                RecipeApp()
            }
        }

        val title = "Ingredients Cancel Recipe"
        val oldIngredients = "Old ingredient one\nOld ingredient two"
        val newIngredients = "New ingredient one\nNew ingredient two"

        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("Recipe name").performTextInput(title)
        composeTestRule.onNodeWithTag("add_ingredients").performTextInput(oldIngredients)
        composeTestRule.onNodeWithTag("save_button").performClick()

        composeTestRule.onNodeWithText(title).performClick()
        composeTestRule.onNodeWithTag("edit_button").performClick()

        composeTestRule.onNodeWithTag("edit_ingredients").performTextClearance()
        composeTestRule.onNodeWithTag("edit_ingredients").performTextInput(newIngredients)

        composeTestRule.onNodeWithTag("cancel_button").performClick()

        composeTestRule.onNodeWithText("Old ingredient one", substring = true).assertExists()
        composeTestRule.onNodeWithText("Old ingredient two", substring = true).assertExists()
        composeTestRule.onNodeWithText("New ingredient one", substring = true).assertDoesNotExist()
    }

    @Test
    fun editRecipeInstructions_save_changesInstructions() {
        composeTestRule.setContent {
            MaterialTheme {
                RecipeApp()
            }
        }

        val title = "Instructions Edit Recipe"
        val oldInstructions = "Boil potatoes\nMash potatoes\nAdd flour"
        val newInstructions = "Cut tomatoes\nCook sauce\nServe hot"

        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("Recipe name").performTextInput(title)
        composeTestRule.onNodeWithTag("add_instructions").performTextInput(oldInstructions)
        composeTestRule.onNodeWithTag("save_button").performClick()

        composeTestRule.onNodeWithText(title).performClick()
        composeTestRule.onNodeWithTag("edit_button").performClick()

        composeTestRule.onNodeWithTag("edit_instructions").performTextClearance()
        composeTestRule.onNodeWithTag("edit_instructions").performTextInput(newInstructions)

        composeTestRule.onNodeWithTag("save_button").performClick()

        composeTestRule.onNodeWithText("1. Cut tomatoes").assertExists()
        composeTestRule.onNodeWithText("2. Cook sauce").assertExists()
        composeTestRule.onNodeWithText("3. Serve hot").assertExists()
        composeTestRule.onNodeWithText("1. Boil potatoes").assertDoesNotExist()
    }

    @Test
    fun editRecipeInstructions_cancel_keepsOldInstructions() {
        composeTestRule.setContent {
            MaterialTheme {
                RecipeApp()
            }
        }

        val title = "Instructions Cancel Recipe"
        val oldInstructions = "Old step one\nOld step two"
        val newInstructions = "New step one\nNew step two"

        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("Recipe name").performTextInput(title)
        composeTestRule.onNodeWithTag("add_instructions").performTextInput(oldInstructions)
        composeTestRule.onNodeWithTag("save_button").performClick()

        composeTestRule.onNodeWithText(title).performClick()
        composeTestRule.onNodeWithTag("edit_button").performClick()

        composeTestRule.onNodeWithTag("edit_instructions").performTextClearance()
        composeTestRule.onNodeWithTag("edit_instructions").performTextInput(newInstructions)

        composeTestRule.onNodeWithTag("cancel_button").performClick()

        composeTestRule.onNodeWithText("1. Old step one").assertExists()
        composeTestRule.onNodeWithText("2. Old step two").assertExists()
        composeTestRule.onNodeWithText("1. New step one").assertDoesNotExist()
    }
}