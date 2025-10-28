package com.example.levelupmovil.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.levelupmovil.model.Category

@Composable
fun CategorySlider(
    selectedCategory: Category?,
    onCategoryClick: (Category?) -> Unit
){
    LazyRow(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(Category.entries.toTypedArray()) { category ->
            val isSelected = selectedCategory == category
            CategoryButton(
                category = category,
                onClick = onCategoryClick,
                isSelected = isSelected
            )
        }
    }
}