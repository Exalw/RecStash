package com.example.recstash

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.recstash.data.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecipeDaoTest {

    private lateinit var database: RecipeDatabase
    private lateinit var dao: RecipeDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(
            context,
            RecipeDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        dao = database.recipeDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertRecipe_recipeIsReturned() = runBlocking {
        val recipe = RecipeEntity(
            name = "Pasta",
            description = "Tomato pasta",
            imagePath = null
        )

        dao.insertRecipe(recipe)

        val recipes = dao.getAllRecipes().first()

        assertEquals(1, recipes.size)
        assertEquals("Pasta", recipes[0].name)
        assertEquals("Tomato pasta", recipes[0].description)
        assertEquals(null, recipes[0].imagePath)
    }

    @Test
    fun recipesAreReturnedNewestFirst() = runBlocking {
        dao.insertRecipe(
            RecipeEntity(
                name = "Old Recipe",
                description = "First"
            )
        )

        dao.insertRecipe(
            RecipeEntity(
                name = "New Recipe",
                description = "Second"
            )
        )

        val recipes = dao.getAllRecipes().first()

        assertEquals("New Recipe", recipes[0].name)
        assertEquals("Old Recipe", recipes[1].name)
    }

    @Test
    fun insertRecipe_withImagePath_imagePathIsReturned() = runBlocking {
        dao.insertRecipe(
            RecipeEntity(
                name = "Pizza",
                description = "With image",
                imagePath = "/fake/path/pizza.jpg"
            )
        )

        val recipes = dao.getAllRecipes().first()

        assertEquals(1, recipes.size)
        assertEquals("/fake/path/pizza.jpg", recipes[0].imagePath)
    }

    @Test
    fun emptyDatabase_returnsEmptyList() = runBlocking {
        val recipes = dao.getAllRecipes().first()

        assertEquals(0, recipes.size)
    }

    @Test
    fun insertMultipleRecipes_allRecipesAreReturned() = runBlocking {
        dao.insertRecipe(RecipeEntity(name = "Pasta", description = "Tomato"))
        dao.insertRecipe(RecipeEntity(name = "Soup", description = "Pumpkin"))
        dao.insertRecipe(RecipeEntity(name = "Cake", description = "Chocolate"))

        val recipes = dao.getAllRecipes().first()

        assertEquals(3, recipes.size)
    }

    @Test
    fun insertedRecipe_getsGeneratedId() = runBlocking {
        dao.insertRecipe(
            RecipeEntity(
                name = "Generated ID Test",
                description = "Test"
            )
        )

        val recipes = dao.getAllRecipes().first()

        assert(recipes[0].id > 0)
    }

    @Test
    fun deleteRecipe_recipeIsRemoved() = runBlocking {
        dao.insertRecipe(RecipeEntity(name = "Delete me", description = "Temporary"))

        val insertedRecipe = dao.getAllRecipes().first()[0]

        dao.deleteRecipe(insertedRecipe)

        val recipesAfterDelete = dao.getAllRecipes().first()

        assertEquals(0, recipesAfterDelete.size)
    }

    @Test
    fun updateRecipe_recipeIsChanged() = runBlocking {
        dao.insertRecipe(RecipeEntity(name = "Old name", description = "Old description"))

        val insertedRecipe = dao.getAllRecipes().first()[0]

        dao.updateRecipe(
            insertedRecipe.copy(
                name = "New name",
                description = "New description"
            )
        )

        val updatedRecipe = dao.getAllRecipes().first()[0]

        assertEquals("New name", updatedRecipe.name)
        assertEquals("New description", updatedRecipe.description)
    }
}
