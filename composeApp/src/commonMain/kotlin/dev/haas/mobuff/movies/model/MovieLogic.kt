package dev.haas.mobuff.movies.model

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object MovieLogic {
    private val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
    }

    private const val SEARCHURL="https://api.themoviedb.org/3/search/movie?include_adult=false"
    private const val LISTURL="https://api.themoviedb.org/3/discover/movie"

    suspend fun searchMovie(query: String="", language: String="te", page: Int=1): MovieResponse {
        val movie = httpClient.get(if (query.isNotEmpty()) SEARCHURL else LISTURL ) {
            parameter("query", query)
            parameter("with_original_language", language)
            parameter("page", page)
            headers {
                append("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlYWJmNWM0MWFiNjdmZDFkOTg0N2U0MTc4MjU4YzMzZiIsIm5iZiI6MTc0NjIwMjY0NS42NjcsInN1YiI6IjY4MTRmMDE1OGY0OTIxYzZmNWEyMGJlNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.9fLYV0VKq85WBXGYMG5pkfD-53J4EOBJn_sCkTHdG1A")
                append("accept", "application/json")
            }
        }.body<MovieResponse>()
        return movie
    }
}