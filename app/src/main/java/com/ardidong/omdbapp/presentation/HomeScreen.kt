package com.ardidong.omdbapp.presentation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ardidong.omdbapp.presentation.theme.OMDBAppTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
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