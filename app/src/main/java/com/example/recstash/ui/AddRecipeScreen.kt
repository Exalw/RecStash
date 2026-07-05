package com.example.recstash.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recstash.data.copyImageToAppStorage

@Composable
fun AddRecipeScreen(
    onSave: (String, String, String, String, String?) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("") }

    val context = LocalContext.current
    var imagePath by remember { mutableStateOf<String?>(null) }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            imagePath = copyImageToAppStorage(context, uri)
        }
    }

    Column(
        modifier = Modifier
            .safeDrawingPadding()
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
            label = { Text("Short description") },
            modifier = Modifier.fillMaxWidth()
                .testTag("add_description"),
            minLines = 2
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = ingredients,
            onValueChange = { ingredients = it },
            label = { Text("Ingredients") },
            modifier = Modifier.fillMaxWidth()
                .testTag("add_ingredients"),
            minLines = 4
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = instructions,
            onValueChange = { instructions = it },
            label = { Text("Instructions") },
            modifier = Modifier.fillMaxWidth()
                .testTag("add_instructions"),
            minLines = 4
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row {
            OutlinedButton(
                onClick = {
                    imagePicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            ) {
                Text("Choose Image")
            }

            if (imagePath != null) {
                Text("Image selected")
            }

            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        onSave(name, description, ingredients, instructions, imagePath)
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

@Preview(showBackground = true)
@Composable
    fun AddRecipeScreenPreview() {
    MaterialTheme {
        AddRecipeScreen(
            onSave = { _, _, _, _, _ -> },
            onCancel = {}
        )
    }
}