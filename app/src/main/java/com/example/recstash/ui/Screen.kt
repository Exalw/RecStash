package com.example.recstash.ui

import com.example.recstash.data.RecipeEntity

sealed class Screen {
    object List : Screen()
    object Add : Screen()
    data class Detail(val recipe: RecipeEntity) : Screen()
    data class Edit(val recipe: RecipeEntity) : Screen()
}