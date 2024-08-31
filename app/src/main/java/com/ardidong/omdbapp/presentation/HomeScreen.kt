package com.ardidong.omdbapp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.ardidong.omdbapp.R
import com.ardidong.omdbapp.presentation.component.ErrorComponent
import com.ardidong.omdbapp.presentation.component.MediaCard
import com.ardidong.omdbapp.presentation.component.MediaCardLoading
import com.ardidong.omdbapp.presentation.component.SearchTextField
import com.ardidong.omdbapp.presentation.theme.OMDBAppTheme
import retrofit2.HttpException
import java.net.SocketTimeoutException

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        homeViewModel.search("")
    }

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
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 8.dp)
        ) {
            SearchTextField(
                value = state.titleFilter,
                onValueChanged = onSearch
            )

            LazyColumn(
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(mediaList.itemCount) { index ->
                    mediaList[index]?.let { media ->
                        MediaCard(
                            modifier = Modifier.fillMaxWidth(),
                            media = media
                        )
                    }
                }

                mediaList.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item { MediaCardLoading(modifier = Modifier.fillMaxWidth()) }
                        }

                        loadState.append is LoadState.Loading -> {
                            item { MediaCardLoading(modifier = Modifier.fillMaxWidth()) }
                        }

                        loadState.append is LoadState.Error -> {
                            item {
                                HomeScreenErrorCard(error = (loadState.append as LoadState.Error).error) {
                                    retry()
                                }
                            }
                        }

                        loadState.refresh is LoadState.Error -> {
                            item {
                                HomeScreenErrorCard(error = (loadState.refresh as LoadState.Error).error) {
                                    retry()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreenErrorCard(
    modifier: Modifier = Modifier,
    error: Throwable,
    onRetry: (() -> Unit)? = null
) {
    var message = error.message
    var canRetry = false

    when(error) {
        is HttpException, is SocketTimeoutException -> {
            message = stringResource(id = R.string.error_connection_message)
            canRetry = true
        }
    }

    ErrorComponent(
        modifier = modifier,
        errorText = message.orEmpty(),
        onRetry = if (canRetry) onRetry else null
    )
}

@Preview
@Composable
private fun HomeScreenPreview() {
    OMDBAppTheme {
        HomeScreen()
    }
}