package com.epita.ing1.ghibliapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WSInterface {
    @GET("films")
    fun getFilms() : Call<List<Movie>>

    @GET("films")
    fun getFilmDetails() : Call<List<MovieDetails>>
}