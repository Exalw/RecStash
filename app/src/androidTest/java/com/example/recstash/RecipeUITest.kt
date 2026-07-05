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

        composeTestRule.onNodeWithText("Save").performClick()

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
        composeTestRule.onNodeWithText("Save").performClick()

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
        composeTestRule.onNodeWithText("Save").performClick()

        composeTestRule.onNodeWithText(oldTitle).performClick()
        composeTestRule.onNodeWithText("Edit").performClick()

        composeTestRule.onNodeWithTag("edit_name").performTextClearance()
        composeTestRule.onNodeWithTag("edit_name").performTextInput(newTitle)

        composeTestRule.onNodeWithText("Save").performClick()

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
        composeTestRule.onNodeWithText("Save").performClick()

        composeTestRule.onNodeWithText(oldTitle).performClick()
        composeTestRule.onNodeWithText("Edit").performClick()

        composeTestRule.onNodeWithTag("edit_name").performTextClearance()
        composeTestRule.onNodeWithTag("edit_name").performTextInput(newTitle)

        composeTestRule.onNodeWithText("Cancel").performClick()

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
        composeTestRule.onNodeWithText("Save").performClick()

        composeTestRule.onNodeWithText(title).performClick()
        composeTestRule.onNodeWithText("Edit").performClick()

        composeTestRule.onNodeWithTag("edit_description").performTextClearance()
        composeTestRule.onNodeWithTag("edit_description").performTextInput(newDescription)

        composeTestRule.onNodeWithText("Save").performClick()

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
        composeTestRule.onNodeWithText("Save").performClick()

        composeTestRule.onNodeWithText(title).performClick()
        composeTestRule.onNodeWithText("Edit").performClick()

        composeTestRule.onNodeWithTag("edit_description").performTextClearance()
        composeTestRule.onNodeWithTag("edit_description").performTextInput(newDescription)

        composeTestRule.onNodeWithText("Cancel").performClick()

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
        composeTestRule.onNodeWithText("Save").performClick()

        composeTestRule.onNodeWithText(title).performClick()
        composeTestRule.onNodeWithText("Edit").performClick()

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
        composeTestRule.onNodeWithText("Save").performClick()

        composeTestRule.onNodeWithText(title).performClick()
        composeTestRule.onNodeWithText("Edit").performClick()

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
        composeTestRule.onNodeWithText("Save").performClick()

        composeTestRule.onNodeWithText(title).performClick()
        composeTestRule.onNodeWithText("Edit").performClick()

        composeTestRule.onNodeWithTag("delete_button").performClick()
        composeTestRule.onNodeWithTag("delete_first_yes_button").performClick()
        composeTestRule.onNodeWithTag("delete_final_confirm_button").performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(title).assertDoesNotExist()
        composeTestRule.onNodeWithText("Recipes").assertExists()
    }
}