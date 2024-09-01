package com.ardidong.omdbapp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.ardidong.omdbapp.presentation.util.ConnectionState
import com.ardidong.omdbapp.presentation.util.rememberConnectionState
import kotlinx.coroutines.launch
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
    val connectionState by rememberConnectionState()
    val isConnected by remember {
        derivedStateOf { connectionState == ConnectionState.Available }
    }

    HomeScreenContent(
        modifier = modifier,
        state = state.value,
        isConnected = isConnected,
        onSearch = { title ->
            homeViewModel.search(title)
        }
    )
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    state: HomeScreenState,
    onSearch: (String) -> Unit,
    isConnected: Boolean
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var initialConnectionProcessed by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = isConnected ) {
        if (!initialConnectionProcessed) {
            initialConnectionProcessed = true
            if (isConnected) return@LaunchedEffect
        }

        scope.launch {
            if (isConnected) {
                snackbarHostState.currentSnackbarData?.dismiss()
                snackbarHostState.showSnackbar(
                    message = "Back Online",
                    duration = SnackbarDuration.Short
                )
            } else {
                snackbarHostState.currentSnackbarData?.dismiss()
                snackbarHostState.showSnackbar(
                    message = "No Connection",
                    duration = SnackbarDuration.Indefinite
                )
            }
        }
    }


    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) {
                Snackbar(
                    snackbarData = it,
                    containerColor = if (!isConnected)
                        MaterialTheme.colorScheme.error
                    else
                        MaterialTheme.colorScheme.primaryContainer
                )
            }
        }
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