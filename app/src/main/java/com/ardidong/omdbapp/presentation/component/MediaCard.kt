package com.ardidong.omdbapp.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.ardidong.omdbapp.domain.model.Media
import com.ardidong.omdbapp.presentation.shimmerEffect

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
            SubcomposeAsyncImage(
                modifier = Modifier
                    .width(75.dp)
                    .height(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(media.poster)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                loading = {
                    Box(
                        modifier =  Modifier
                            .width(75.dp)
                            .height(100.dp)
                            .shimmerEffect()
                    )
                }
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


@Composable
fun MediaCardLoading(
    modifier: Modifier = Modifier
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
            Box(
                modifier = Modifier
                    .width(75.dp)
                    .height(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier
                    .height(100.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .shimmerEffect()
                )

                Spacer(modifier = Modifier.weight(1.0f))

                Box(
                    modifier = Modifier
                        .width(50.dp)
                        .height(20.dp)
                        .shimmerEffect()
                )
            }
        }
    }
}

@Preview(widthDp = 500)
@Composable
private fun MediaCardLoadingPreview() {
    MediaCardLoading()
}