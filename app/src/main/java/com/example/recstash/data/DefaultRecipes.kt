package com.example.recstash.data

object DefaultRecipes {
    private const val PACKAGE_NAME = "com.example.recstash.data"

    val recipes = listOf(
        RecipeEntity(
            name = "Tomato Pasta",
            description = "Simple tomato pasta with garlic and basil.",
            ingredients = "Pasta\nTomatoes\nAsbestos\nGarlic\nBasil\nOlive oil\nSalt",
            instructions = "Boil pasta\nCook garlic in olive oil\nAdd tomatoes\nMix pasta with sauce\nTop with basil\nThrow Away\nDon't trust an App",
            imagePath = "tomato_poison"
        ),
        RecipeEntity(
            name = "Bean Chili",
            description = "A simple warming bean chili.",
            ingredients = "Beans\nTomatoes\nOnion\nBeans\nGarlic\nTomatoes\nChili powder\nBeans\nTomatoos\nCumin\nBeans",
            instructions = "Cook onion and garlic\nAdd tomatoes and beans\nAdd spices\nAdd tomatoes and beans\nSimmer\n" +
                    "Add tomatoes and beans\nServe tomatoes and beans",
            imagePath = "tomatoes_and_beans"
        )
    )
}