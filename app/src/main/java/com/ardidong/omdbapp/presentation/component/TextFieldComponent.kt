package com.ardidong.omdbapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ardidong.omdbapp.presentation.theme.OMDBAppTheme

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
) {
    Row(
        modifier = modifier
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        Icon(imageVector = Icons.Filled.Search, contentDescription = "search_icon")
        Spacer(modifier = Modifier.width(4.dp))

        TextField(
            modifier = Modifier.fillMaxWidth().background(Color.Transparent),
            value = value,
            onValueChange = onValueChanged,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp),
        )

        if (value.isNotBlank()) {
            Spacer(modifier = Modifier.width(4.dp))
            IconButton(onClick = { onValueChanged("") }) {
                Icon(imageVector = Icons.Filled.Clear, contentDescription = "clear_icon")
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Preview(name = "PIXEL_4", heightDp = 720)
@Composable
private fun IconTextPreview() {
    OMDBAppTheme {
        Scaffold {
            it
            SearchTextField(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChanged = {}
            )
        }
    }
}