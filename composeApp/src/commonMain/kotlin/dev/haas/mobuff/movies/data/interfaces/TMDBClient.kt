package dev.haas.mobuff.movies.data.interfaces

import dev.haas.mobuff.movies.domain.model.MovieResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

interface TMDBClient{
     companion object{
          val SEARCHURL="https://api.themoviedb.org/3/search/movie?include_adult=false"
          val LISTURL="https://api.themoviedb.org/3/discover/movie"
          val httpClient = HttpClient(CIO) {
               install(ContentNegotiation) {
                    json(Json {
                         ignoreUnknownKeys = true
                         prettyPrint = true
                         isLenient = true
                    })
               }
               install(HttpRequestRetry){
                    retryOnServerErrors(maxRetries = 3)
               }
          }
     }
     suspend fun getMovie(query: String="", language: String="te", page: Int=1):MovieResponse
}