package dev.haas.mobuff.movies.data.repository

import dev.haas.mobuff.movies.domain.model.MovieResponse
import dev.haas.mobuff.movies.data.interfaces.TMDBClient
import dev.haas.mobuff.movies.data.interfaces.TMDBClient.Companion.LISTURL
import dev.haas.mobuff.movies.data.interfaces.TMDBClient.Companion.SEARCHURL
import dev.haas.mobuff.movies.data.interfaces.TMDBClient.Companion.httpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter

class TMDBRepository private constructor() : TMDBClient {
    private val responseCache = mutableMapOf<String, MovieResponse>()

    override suspend fun getMovie(query: String, language: String, page: Int): MovieResponse {
        val cacheKey = "$query-$language-$page"
        responseCache[cacheKey]?.let { return it }
        return httpClient.get(if (query.isNotEmpty()) SEARCHURL else LISTURL) {
            parameter("query", query)
            parameter("with_original_language", language)
            parameter("page", page)
            headers {
                append("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlYWJmNWM0MWFiNjdmZDFkOTg0N2U0MTc4MjU4YzMzZiIsIm5iZiI6MTc0NjIwMjY0NS42NjcsInN1YiI6IjY4MTRmMDE1OGY0OTIxYzZmNWEyMGJlNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.9fLYV0VKq85WBXGYMG5pkfD-53J4EOBJn_sCkTHdG1A")
                append("accept", "application/json")
            }
        }.body<MovieResponse>().also {
            responseCache[cacheKey] = it
        }
    }

    companion object {
        val instance by lazy { TMDBRepository() }
    }
}