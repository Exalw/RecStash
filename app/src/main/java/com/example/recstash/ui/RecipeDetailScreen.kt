package com.example.recstash.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.recstash.data.RecipeEntity

@Composable
fun RecipeDetailScreen(
    recipe: RecipeEntity,
    onBack: () -> Unit,
    onEdit: () -> Unit
) {
    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .safeDrawingPadding()
                    .padding(16.dp)
            ) {
                OutlinedButton(onClick = onBack) {
                    Text("Back")
                }

                Spacer(modifier = Modifier.width(12.dp))

                Button(onClick = onEdit) {
                    Text("Edit")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .safeDrawingPadding()
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = recipe.name,
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (recipe.imagePath != null) {
                val context = LocalContext.current
                AsyncImage(
                    model = imageModelFromPath(context, recipe.imagePath),
                    contentDescription = recipe.name,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 420.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            if (recipe.description.isNotBlank()) {
                Text(
                    text = recipe.description,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(24.dp))
            }

            if (recipe.ingredients.isNotBlank()) {
                Text(
                    text = "Ingredients",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = recipe.ingredients,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(24.dp))
            }

            if (recipe.instructions.isNotBlank()) {
                Text(
                    text = "Instructions",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                val instructionSteps = recipe.instructions
                    .lines()
                    .map { it.trim() }
                    .filter { it.isNotBlank() }

                instructionSteps.forEachIndexed { index, step ->
                    Text(
                        text = "${index + 1}. $step",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeDetailScreenPreview() {
    MaterialTheme {
        RecipeDetailScreen(
            recipe = RecipeEntity(
                name = "Pasta",
                description = "A simple tomato pasta with garlic and basil.",
                instructions = "Cook Tomato\nBoil Tomato\nEat Plastic",
                ingredients = "1 Tomato\n1 another Tomato",
                imagePath = null
            ),
            onBack = {},
            onEdit = {}
        )
    }
}