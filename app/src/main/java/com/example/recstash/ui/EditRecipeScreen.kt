package com.example.recstash.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.recstash.data.RecipeEntity
import com.example.recstash.data.copyImageToAppStorage

@Composable
fun EditRecipeScreen(
    recipe: RecipeEntity,
    onSave: (RecipeEntity) -> Unit,
    onCancel: () -> Unit,
    onDelete: (RecipeEntity) -> Unit
) {
    var name by remember { mutableStateOf(recipe.name) }
    var description by remember { mutableStateOf(recipe.description) }
    var imagePath by remember { mutableStateOf(recipe.imagePath) }

    var showDeleteConfirm by remember { mutableStateOf(false) }
    var showVerySureConfirm by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            imagePath = copyImageToAppStorage(context, uri)
        }
    }

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .safeDrawingPadding()
                    .padding(16.dp)
            ) {
                OutlinedButton(
                    modifier = Modifier.testTag("cancel_button"),
                    onClick = onCancel
                ) {
                    Text("Cancel")
                }

                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    modifier = Modifier.testTag("save_button"),
                    onClick = {
                        if (name.isNotBlank()) {
                            onSave(
                                recipe.copy(
                                    name = name,
                                    description = description,
                                    imagePath = imagePath
                                )
                            )
                        }
                    }
                ) {
                    Text("Save")
                }

                Spacer(modifier = Modifier.width(12.dp))

                OutlinedButton(
                    modifier = Modifier.testTag("delete_button"),
                    onClick = { showDeleteConfirm = true }
                ) {
                    Text("Delete")
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
                text = "Edit Recipe",
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Recipe name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("edit_name")
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("edit_description"),
                minLines = 3
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (imagePath != null) {
                val context = LocalContext.current
                AsyncImage(
                    model = imageModelFromPath(context, imagePath!!),
                    contentDescription = name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 300.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            OutlinedButton(
                onClick = {
                    imagePicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            ) {
                Text("Change Image")
            }
        }
    }

    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            title = { Text("Delete recipe?") },
            text = { Text("Are you sure?") },
            confirmButton = {
                Button(
                    modifier = Modifier.testTag("delete_first_yes_button"),
                    onClick = {
                        showDeleteConfirm = false
                        showVerySureConfirm = true
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                OutlinedButton(
                    modifier = Modifier.testTag("delete_first_no_button"),
                    onClick = { showDeleteConfirm = false }
                ) {
                    Text("No")
                }
            }
        )
    }

    if (showVerySureConfirm) {
        AlertDialog(
            onDismissRequest = { showVerySureConfirm = false },
            title = { Text("Really delete recipe?") },
            text = { Text("Are you very sure? This cannot be undone.") },
            confirmButton = {
                Button(
                    modifier = Modifier.testTag("delete_final_confirm_button"),
                    onClick = {
                        showVerySureConfirm = false
                        onDelete(recipe)
                    }
                ) {
                    Text("Delete it")
                }
            },
            dismissButton = {
                OutlinedButton(
                    modifier = Modifier.testTag("delete_final_cancel_button"),
                    onClick = { showVerySureConfirm = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EditRecipeScreenPreview() {
    MaterialTheme {
        EditRecipeScreen(
            recipe = RecipeEntity(
                name = "Pasta",
                description = "A simple tomato pasta with garlic and basil.",
                imagePath = null
            ),
            onSave = {},
            onCancel = {},
            onDelete = {}
        )
    }
}