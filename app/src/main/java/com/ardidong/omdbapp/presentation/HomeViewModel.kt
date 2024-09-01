package com.ardidong.omdbapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ardidong.omdbapp.domain.MediaRepository
import com.ardidong.omdbapp.domain.model.Media
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MediaRepository
) : ViewModel() {
    private var _state = MutableStateFlow(HomeScreenState.EMPTY)
    val state: StateFlow<HomeScreenState> get() = _state

    fun search(title: String) = viewModelScope.launch {
        val searchTitle = title.trim().replace(" ", "+")

        val searchTerm = searchTitle.ifBlank {
            if (!repository.hasLocalData()) {
                "love"
            } else {
                searchTitle
            }
        }

        updateState { it.copy(mediaList = emptyFlow(), titleFilter = searchTerm) }
        val mediaList = repository.searchMedia(searchTerm)
            .cachedIn(viewModelScope)
        updateState { it.copy(mediaList = mediaList) }
    }

    private fun updateState(update: (HomeScreenState) -> HomeScreenState) {
        _state.value = update(_state.value)
    }
}

data class HomeScreenState(
    val mediaList: Flow<PagingData<Media>>,
    val titleFilter: String
){
    companion object {
        val EMPTY = HomeScreenState(
            mediaList = emptyFlow(),
            titleFilter = ""
        )
    }
}
