package com.pompouslypickled.venison.api.Hit

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "stored_recipe_table")
class StoredRecipes (
    @PrimaryKey
    val stored_bookName: String,
    val stored_bookCalories: Double,
    val stored_bookImage: String,
    val stored_bookTotal_time: Double,
    val stored_bookYield: Double,
    val stored_bookUrl: String,
    val stored_bookIngredients: String,
    val stored_bookUri: String,
    val stored_isInMyRecipies: Boolean
)