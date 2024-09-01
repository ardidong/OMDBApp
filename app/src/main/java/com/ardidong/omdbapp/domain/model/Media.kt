package com.ardidong.omdbapp.domain.model

import com.ardidong.omdbapp.domain.MediaType

data class Media(
	val type: MediaType,
	val year: String,
	val imdbID: String,
	val poster: String,
	val title: String
)

