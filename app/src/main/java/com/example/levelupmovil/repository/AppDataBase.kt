package com.example.levelupmovil.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.levelupmovil.data.model.Product


@Database(entities = [Product::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDataBase: RoomDatabase() {
    abstract fun productDao(): ProductDao
}