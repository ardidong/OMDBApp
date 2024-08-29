package com.ardidong.omdbapp.presentation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ardidong.omdbapp.presentation.theme.OMDBAppTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    homeViewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        homeViewModel.search("the")
    }
    Scaffold(
        topBar = {}
    ) { innerPadding ->
        innerPadding
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    OMDBAppTheme {
        HomeScreen()
    }
}