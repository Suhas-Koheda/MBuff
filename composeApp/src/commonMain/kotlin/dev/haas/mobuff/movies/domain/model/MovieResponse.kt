package dev.haas.mobuff.movies.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    val page:Int,
    val results:List<Movie>,
    val totalPages:Int?=null,
    val totalResults:Int?=null
)