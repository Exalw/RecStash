package com.example.recstash

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performClick
import com.example.recstash.data.RecipeEntity
import com.example.recstash.ui.EditRecipeScreen
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class EditRecipeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val recipe = RecipeEntity(
        id = 1,
        name = "Pasta",
        description = "Old description",
        imagePath = null
    )

    @Test
    fun editScreen_showsExistingRecipeData() {
        composeTestRule.setContent {
            MaterialTheme {
                EditRecipeScreen(
                    recipe = recipe,
                    onSave = {},
                    onCancel = {},
                    onDelete = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Edit Recipe").assertIsDisplayed()
        composeTestRule.onNodeWithText("Pasta").assertIsDisplayed()
        composeTestRule.onNodeWithText("Old description").assertIsDisplayed()
        composeTestRule.onNodeWithTag("save_button").performClick()
        composeTestRule.onNodeWithTag("cancel_button").performClick()
        composeTestRule.onNodeWithText("Delete").assertIsDisplayed()
    }

    @Test
    fun editScreen_saveReturnsUpdatedRecipe() {
        var savedRecipe: RecipeEntity? = null

        composeTestRule.setContent {
            MaterialTheme {
                EditRecipeScreen(
                    recipe = recipe,
                    onSave = { savedRecipe = it },
                    onCancel = {},
                    onDelete = {}
                )
            }
        }

        composeTestRule.onNodeWithTag("edit_name").performTextClearance()
        composeTestRule.onNodeWithTag("edit_name").performTextInput("Updated Pasta")

        composeTestRule.onNodeWithTag("edit_description").performTextClearance()
        composeTestRule.onNodeWithTag("edit_description").performTextInput("Updated description")

        composeTestRule.onNodeWithTag("save_button").performClick()

        assertEquals("Updated Pasta", savedRecipe?.name)
        assertEquals("Updated description", savedRecipe?.description)
        assertEquals(1L, savedRecipe?.id)
    }

    @Test
    fun editScreen_emptyNameDoesNotSave() {
        var saveWasCalled = false

        composeTestRule.setContent {
            MaterialTheme {
                EditRecipeScreen(
                    recipe = recipe,
                    onSave = { saveWasCalled = true },
                    onCancel = {},
                    onDelete = {}
                )
            }
        }

        composeTestRule.onNodeWithTag("edit_name").performTextClearance()
        composeTestRule.onNodeWithTag("save_button").performClick()

        assertFalse(saveWasCalled)
    }

    @Test
    fun editScreen_cancelCallsOnCancel() {
        var cancelWasCalled = false

        composeTestRule.setContent {
            MaterialTheme {
                EditRecipeScreen(
                    recipe = recipe,
                    onSave = {},
                    onCancel = { cancelWasCalled = true },
                    onDelete = {}
                )
            }
        }

        composeTestRule.onNodeWithTag("cancel_button").performClick()

        assertTrue(cancelWasCalled)
    }

    @Test
    fun editScreen_deleteNeedsTwoConfirmations() {
        var deletedRecipe: RecipeEntity? = null

        composeTestRule.setContent {
            MaterialTheme {
                EditRecipeScreen(
                    recipe = recipe,
                    onSave = {},
                    onCancel = {},
                    onDelete = { deletedRecipe = it }
                )
            }
        }

        composeTestRule.onNodeWithTag("delete_button").performClick()
        composeTestRule.onNodeWithText("Delete recipe?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Yes").performClick()

        composeTestRule.onNodeWithText("Really delete recipe?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Delete it").performClick()

        assertEquals(recipe, deletedRecipe)
    }

    @Test
    fun editScreen_deleteCancelDoesNotDelete() {
        var deleteWasCalled = false

        composeTestRule.setContent {
            MaterialTheme {
                EditRecipeScreen(
                    recipe = recipe,
                    onSave = {},
                    onCancel = {},
                    onDelete = { deleteWasCalled = true }
                )
            }
        }

        composeTestRule.onNodeWithTag("delete_button").performClick()
        composeTestRule.onNodeWithText("No").performClick()

        assertFalse(deleteWasCalled)
    }
}