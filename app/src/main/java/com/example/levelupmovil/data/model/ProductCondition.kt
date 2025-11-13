package com.example.levelupmovil.data.model

enum class ProductCondition(val displayName: String, val dbValue: String) {

    NEW("Nuevo", "new"),
    OPEN("Open Box", "open"),
    SECOND_HAND("Segunda Mano", "second"),

    UNKNOWN("Desconocido", "");

    companion object {
        fun fromDbValue(value: String?): ProductCondition {
            return entries.find { it.dbValue.equals(value, ignoreCase = true) } ?: UNKNOWN
        }
    }
}