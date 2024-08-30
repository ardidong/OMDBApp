package com.ardidong.omdbapp.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ardidong.omdbapp.domain.Media

@Composable
fun MediaCard(
    modifier: Modifier = Modifier,
    media: Media
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .width(75.dp)
                    .height(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                model = media.poster,
                contentDescription = ""
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.height(100.dp)
            ) {
                Text(
                    text = media.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.weight(1.0f))
                Text(text = media.year, style = MaterialTheme.typography.bodyMedium)
            }
        }

    }
}
