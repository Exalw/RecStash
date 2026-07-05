package com.example.recstash.data

import android.content.Context
import android.net.Uri
import java.io.File
import java.util.UUID

fun copyImageToAppStorage(
    context: Context,
    sourceUri: Uri
): String {
    val imagesDir = File(context.filesDir, "recipe_images")
    imagesDir.mkdirs()

    val imageFile = File(imagesDir, "${UUID.randomUUID()}.jpg")

    context.contentResolver.openInputStream(sourceUri).use { input ->
        requireNotNull(input) { "Could not open image input stream" }

        imageFile.outputStream().use { output ->
            input.copyTo(output)
        }
    }

    return imageFile.absolutePath
}