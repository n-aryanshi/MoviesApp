package com.example.moviesapp.model.remote

data class Title(
    val id: Int,
    val imdb_id: String,
    val title: String,
    val tmdb_id: Int,
    val tmdb_type: String,
    val type: String,
    val year: Int
)