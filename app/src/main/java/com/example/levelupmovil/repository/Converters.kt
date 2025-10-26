package com.example.levelupmovil.repository

import androidx.room.TypeConverter
import com.example.levelupmovil.model.Category
import com.example.levelupmovil.model.ProductCondition

class Converters {

    @TypeConverter
    fun fromCategory(category: Category): String = category.name

    @TypeConverter
    fun toCategory(value: String): Category =
        Category.entries.first { it.name == value }

    @TypeConverter
    fun fromCondition(condition: ProductCondition): String = condition.name

    @TypeConverter
    fun toCondition(value: String): ProductCondition = ProductCondition.valueOf(value)
}