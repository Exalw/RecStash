package com.example.recstash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MaterialTheme {
                RecipeApp()
            }
        }
    }
}

@Composable
fun RecipeApp(
    viewModel: RecipeViewModel = viewModel()
) {
    var screen by remember { mutableStateOf(Screen.List) }
    val recipes by viewModel.recipes.collectAsState()

    when (screen) {
        Screen.List -> RecipeListScreen(
            recipes = recipes,
            onAddRecipe = { screen = Screen.Add },
            onRecipeClick = { }
        )

        Screen.Add -> AddRecipeScreen(
            onSave = { name, description ->
                viewModel.addRecipe(name, description)
                screen = Screen.List
            },
            onCancel = {
                screen = Screen.List
            }
        )
    }
}

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
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Recipes",
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

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

@Composable
fun RecipeListItem(
    recipe: RecipeEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = recipe.name,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = recipe.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun AddRecipeScreen(
    onSave: (String, String) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "Add Recipe",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Recipe name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row {
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        onSave(name, description)
                    }
                }
            ) {
                Text("Save")
            }

            Spacer(modifier = Modifier.width(12.dp))

            OutlinedButton(onClick = onCancel) {
                Text("Cancel")
            }
        }
    }
}

enum class Screen {
    List,
    Add
}

// PREVIEW

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

@Preview(showBackground = true)
@Composable
fun AddRecipeScreenPreview() {
    MaterialTheme {
        AddRecipeScreen(
            onSave = { _, _ -> },
            onCancel = {}
        )
    }
}