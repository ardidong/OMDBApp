package com.ardidong.omdbapp.data

import com.google.gson.annotations.SerializedName

data class SearchMediaResponse(

	@SerializedName("Response")
	val response: String? = null,

	@SerializedName("Error")
	val error: String?,

	@SerializedName("totalResults")
	val totalResults: String? = null,

	@SerializedName("Search")
	val search: List<SearchItem?>? = null
) {
	data class SearchItem(

		@SerializedName("Type")
		val type: String? = null,

		@SerializedName("Year")
		val year: String? = null,

		@SerializedName("imdbID")
		val imdbID: String? = null,

		@SerializedName("Poster")
		val poster: String? = null,

		@SerializedName("Title")
		val title: String? = null
	)
}