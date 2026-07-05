package com.example.recstash.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.recstash.data.RecipeEntity

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
            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
                    .heightIn(min = 72.dp),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                if (recipe.imagePath != null) {
                    val context = LocalContext.current
                    AsyncImage(
                        model = imageModelFromPath(context, recipe.imagePath),
                        contentDescription = recipe.name,
                        modifier = Modifier
                            .size(72.dp)
                            .padding(end = 12.dp)
                    )
                }

                Text(
                    text = recipe.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }