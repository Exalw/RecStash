package com.example.recstash.ui

import android.content.Context
import java.io.File

fun imageModelFromPath(context: Context, imagePath: String): Any {
    return if (imagePath.startsWith("/")) {
        File(imagePath)
    } else {
        val resourceId = context.resources.getIdentifier(
            imagePath,
            "drawable",
            context.packageName
        )

        if (resourceId != 0) {
            resourceId
        } else {
            File(imagePath)
        }
    }
}
