package com.example.recstash.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.recstash.data.RecipeEntity


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    recipe: RecipeEntity,
    onBack: () -> Unit,
    onEdit: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = recipe.name,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.W500
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = onEdit,
                        modifier = Modifier.testTag("edit_button")
                    ) {
                        Text("Edit")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            if (recipe.imagePath != null) {
                val context = LocalContext.current

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.large
                ) {
                    AsyncImage(
                        model = imageModelFromPath(context, recipe.imagePath),
                        contentDescription = recipe.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 420.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            if (recipe.description.isNotBlank()) {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = recipe.description,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            if (recipe.ingredients.isNotBlank()) {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Ingredients",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.W500
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = recipe.ingredients,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            if (recipe.instructions.isNotBlank()) {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Instructions",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.W500
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        val instructionSteps = recipe.instructions
                            .lines()
                            .map { it.trim() }
                            .filter { it.isNotBlank() }

                        instructionSteps.forEachIndexed { index, step ->
                            Row {
                                AssistChip(
                                    onClick = {},
                                    label = {
                                        Text("${index + 1}")
                                    }
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Text(
                                    text = step,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
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