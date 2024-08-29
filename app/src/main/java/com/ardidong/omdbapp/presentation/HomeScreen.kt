package com.ardidong.omdbapp.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.ardidong.omdbapp.presentation.component.SearchTextField
import com.ardidong.omdbapp.presentation.theme.OMDBAppTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val state =  homeViewModel.state.collectAsState()
    HomeScreenContent(
        modifier = modifier,
        state = state.value,
        onSearch = { title ->
            homeViewModel.search(title)
        }
    )
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    state: HomeScreenState,
    onSearch: (String) -> Unit
) {
    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        val mediaList = state.mediaList.collectAsLazyPagingItems()
        Column(
            modifier = Modifier.padding(innerPadding).padding(horizontal = 8.dp)
        ) {
            SearchTextField(
                value = state.titleFilter,
                onValueChanged = onSearch
            )

            LazyColumn {
                items(mediaList.itemCount) { index ->
                    mediaList[index]?.let { media ->
                        Text(text = media.title)
                        Text(text = media.year)
                        Text(text = media.poster)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    OMDBAppTheme {
        HomeScreen()
    }
}