package dev.haas.mobuff.movies.model

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val backdrop_path:String?,
    val id:Int?,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String?,
    val title: String,
    val video: Boolean,
){
    val rating:Float?=null
    val posterPathUrl:String
        get()=if(!poster_path.isNullOrEmpty())"https://image.tmdb.org/t/p/w500$poster_path" else "https://media.istockphoto.com/id/1407160246/vector/danger-triangle-icon.jpg?s=612x612&w=0&k=20&c=BS5mwULONmoEG9qPnpAxjb6zhVzHYBNOYsc7S5vdzYI="
    val backDropPathUrl:String
        get() = if(!backdrop_path.isNullOrEmpty())"https://image.tmdb.org/t/p/w500$backdrop_path" else ""
}