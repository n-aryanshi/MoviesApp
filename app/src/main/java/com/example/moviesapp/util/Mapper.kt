package com.example.moviesapp.util

import com.example.moviesapp.model.remote.Details
import com.example.moviesapp.view.Shows

object Mapper {
    fun detailsToShows(details: Details): Shows {
        return Shows(
            id = details.imdb_id,
            title = details.title,
            year = details.year,
            type = details.type,
            poster = details.poster,
            genre_names = details.genre_names?:emptyList()
        )
    }
}