package com.example.recstash.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recstash.data.RecipeEntity

@Composable
    fun RecipeListScreen(
        recipes: List<RecipeEntity>,
        onAddRecipe: () -> Unit,
        onRecipeClick: (RecipeEntity) -> Unit
    ) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = onAddRecipe) {
                    Text("+")
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
            ) {

                Text(
                    text = "Recipes",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                LazyColumn {
                    items(recipes) { recipe ->
                        RecipeListItem(
                            recipe = recipe,
                            onClick = { onRecipeClick(recipe) }
                        )
                    }
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun RecipeListScreenPreview() {
        MaterialTheme {
            RecipeListScreen(
                recipes = listOf(
                    RecipeEntity(name = "Pasta", description = "Simple tomato pasta"),
                    RecipeEntity(name = "Chili", description = "Beans, tomato, spices")
                ),
                onAddRecipe = {},
                onRecipeClick = {}
            )
        }
    }