package com.example.levelupmovil.data.model

import com.example.levelupmovil.R


enum class Category(val displayName: String, val imageRes: Int, val dbValue: String) {

    PC_GAMERS("Pc Gamer", R.drawable.pc_gamer_example, "Pc gamers"),
    BOARD_GAMES("Juegos de Mesa", R.drawable.table_top_game_example, "Juegos de mesa"),
    ACCESORIES("Accesorios", R.drawable.accesories_example, "Accesorios"),
    CONSOLES("Consolas", R.drawable.console_example, "Consolas"),
    CHAIRS("Sillas Gamer", R.drawable.game_chair_example, "Sillas gamers"),
    MOUSES("Mouses", R.drawable.mouse_example, "Mouses"),
    MOUSE_PADS("Mouse Pads", R.drawable.mousepad_example, "Mouse pads"),
    SHIRTS("Poleras", R.drawable.polera_example, "Poleras"),
    HOODIES("Polerones", R.drawable.poleron_example, "Polerones"),


    OTHERS("Otros", R.drawable.accesories_example, "Others");


    companion object {
        fun fromDbValue(value: String?): Category {
            return entries.find { it.dbValue.equals(value, ignoreCase = true) } ?: OTHERS
        }
    }
}