package com.pompouslypickled.venison.api.Hit

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "recipe_book_table")
data class RecipeBook (
    @PrimaryKey
    val bookName: String,
    val bookCalories: Double,
    val bookImage: String,
    val bookTotal_time: Double,
    val bookYield: Double,
    val bookUrl: String,
    val bookIngredients: String,
    val bookUri: String,
    var isInMyRecipies: Boolean,
    var listOfIngredients: String
)