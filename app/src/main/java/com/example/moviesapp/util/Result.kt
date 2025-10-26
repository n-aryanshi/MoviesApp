package com.example.moviesapp.util

sealed class Result<out T> {
    data object Loading: Result<Nothing>()
    data object Idle : Result<Nothing>()
    data class Success<out T> (val data: T): Result<T>()
    data class Error(val msg: String): Result<Nothing>()
}