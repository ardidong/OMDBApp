package com.ardidong.omdbapp.domain

data class Media(
	val type: MediaType,
	val year: String,
	val imdbID: String,
	val poster: String,
	val title: String
)

