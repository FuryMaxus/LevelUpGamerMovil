package com.example.levelupmovil.repository

import androidx.room.TypeConverter
import com.example.levelupmovil.data.model.Category
import com.example.levelupmovil.data.model.ProductCondition

class Converters {

    @TypeConverter
    fun fromCategory(category: Category): String {
        return category.dbValue
    }

    @TypeConverter
    fun toCategory(value: String): Category {
        return Category.fromDbValue(value)
    }

    @TypeConverter
    fun fromCondition(condition: ProductCondition): String {
        return condition.dbValue
    }

    @TypeConverter
    fun toCondition(value: String): ProductCondition {
        return ProductCondition.fromDbValue(value)
    }
}
