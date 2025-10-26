package com.example.moviesapp.repository

import android.app.ProgressDialog.show
import com.example.moviesapp.network.ApiService
import com.example.moviesapp.util.Constants
import com.example.moviesapp.util.Mapper
import com.example.moviesapp.util.Result
import com.example.moviesapp.view.Shows
import jakarta.inject.Inject
import kotlinx.coroutines.delay

class ShowsRepo @Inject constructor(
    private val apiService : ApiService
) {

//    suspend fun getMovies(): List<Shows>{
//        val response = apiService.getTitles(types = "movie")
//        if(!response.isSuccessful){
//            return Result.Error("Failed to fetch Movies : ${response.message()}")
//        }
//
//        val titles = response.body()?.titles ?: emptyList()
//        val movies = titles.map{title->
//            Shows(
//                title = title.title,
//                year = title.year,
//                type = title.type,
//                poster = title.poster,
//                genre = emptyList()
//            )
//        }
//
//
//    }
    //fetching shows
    suspend fun getShows(types: String?): Result<List<Shows>>{
        return try{
            //fethc title first
            val titleResponse = apiService.getTitles(types = types ) //response
            if(!titleResponse.isSuccessful) {
                return Result.Error("Failed to fetch titles: ${titleResponse.message()}")
            }

            val titlesList = titleResponse.body()?.titles ?: emptyList() //whole list
            if (titlesList.isEmpty()) {
                return Result.Success(emptyList()) // No data but successful
            }

            val showsList = mutableListOf<Shows>()

            //ftech detail of each title
            for (title in titlesList) {
                try {
                    val detailsResponse = apiService.getTitleDetails(title.id, Constants.API_KEY)
                    if (detailsResponse.isSuccessful) {
                        detailsResponse.body()?.let { details ->
                            if (details.type.equals(types, ignoreCase = true)) { // Filter by requested type
                                val show = Shows(
                                    id = details.imdb_id,
                                    title = details.title,
                                    year = details.year,
                                    type = details.type,
                                    poster = details.poster ?: "",
                                    genre_names = details.genre_names ?: emptyList()

                                )
                                showsList.add(show)
                            }
                        }
                    } else {
                        // You can log or skip failed ones
                        println("Failed to fetch details for ID ${title.id}: ${detailsResponse.message()}")
                    }
                } catch (e: Exception) {
                    println("Error fetching details for ID ${title.id}: ${e.message}")
                }
            }

            // 3️⃣ Return the final result
            Result.Success(showsList)

        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknown error occurred while fetching shows")
        }
    }



}