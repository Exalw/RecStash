package com.example.recstash.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [RecipeEntity::class],
    version = 3
)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getDatabase(context: Context): RecipeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipe_database"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                            DefaultRecipes.recipes.forEach { recipe ->
                                db.execSQL(
                                    """
                                INSERT INTO recipes 
                                (name, description, ingredients, instructions, imagePath)
                                VALUES (?, ?, ?, ?, ?)
                                """.trimIndent(),
                                    arrayOf(
                                        recipe.name,
                                        recipe.description,
                                        recipe.ingredients,
                                        recipe.instructions,
                                        recipe.imagePath
                                    )
                                )
                            }
                        }
                    })
                    .fallbackToDestructiveMigration(false)
                    .build()


                INSTANCE = instance
                instance
            }
        }
    }
}