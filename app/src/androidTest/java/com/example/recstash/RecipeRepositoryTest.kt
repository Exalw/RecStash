package com.example.recstash

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.recstash.data.RecipeDao
import com.example.recstash.data.RecipeDatabase
import com.example.recstash.data.RecipeRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class RecipeRepositoryTest {

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
    fun repository_addRecipe_recipeAppearsInList() = runBlocking {
        val repository = RecipeRepository(dao)

        repository.addRecipe(
            name = "Ramen",
            description = "Noodles",
            imagePath = "/fake/path/ramen.jpg"
        )

        val recipes = repository.recipes.first()

        assertEquals(1, recipes.size)
        assertEquals("Ramen", recipes[0].name)
        assertEquals("Noodles", recipes[0].description)
        assertEquals("/fake/path/ramen.jpg", recipes[0].imagePath)
    }
}