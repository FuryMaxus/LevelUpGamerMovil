package com.example.levelupmovil.ui.components

import android.view.RoundedCorner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.levelupmovil.model.Category


@Composable
fun CategoryButton(
    category: Category,
    onClick: (Category) -> Unit,
    isSelected: Boolean
){

    val backgroundColor = if (isSelected) Color(0xFF1E90FF) else Color(0xFF39FF14)
    val textColor = Color.Black

    Surface(
        modifier = Modifier
        .width(100.dp)
        .height(100.dp)
        .clickable { onClick(category) },
        color = backgroundColor,
        shadowElevation = if (isSelected) 8.dp else 2.dp,
        shape = RoundedCornerShape(12.dp))
    {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = category.imageRes),
                contentDescription = category.displayName,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = category.displayName,
                style = MaterialTheme.typography.labelMedium,
                color = textColor
            )
        }
    }

}