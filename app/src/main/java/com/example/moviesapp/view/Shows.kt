package com.example.moviesapp.view

data class Shows (
    val id: String,
    val title: String,
    val year: Int,
    val type: String,
    val poster: String,
    val genre_names: List<String> = emptyList()
)