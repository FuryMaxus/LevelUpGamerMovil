package com.example.levelupmovil.model


import android.media.Image
import com.example.levelupmovil.R
enum class Category(val displayName: String, val imageRes: Int) {
    PC_GAMERS("Pc Gamer", R.drawable.pc_gamer_example),
    BOARD_GAMES("Juegos de Mesa",R.drawable.table_top_game_example),
    ACCESORIES("Accesorios",R.drawable.accesories_example),
    CONSOLES("Consolas",R.drawable.console_example),
    CHAIRS("Sillas Gamer",R.drawable.game_chair_example),
    MOUSES("Mouses",R.drawable.mouse_example),
    MOUSE_PADS("Mouse Pads",R.drawable.mousepad_example),
    SHIRTS("Poleras",R.drawable.polera_example),
    HOODIES("Polerones",R.drawable.poleron_example)
}

