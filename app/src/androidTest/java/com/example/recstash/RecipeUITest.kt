package com.example.recstash

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
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
        composeTestRule.onNodeWithText("Description").performTextInput("Potato pasta")

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
        composeTestRule.onNodeWithText("Description").performTextInput("No title")
        composeTestRule.onNodeWithText("Save").performClick()

        composeTestRule.onNodeWithText("Add Recipe").assertExists()
    }
}