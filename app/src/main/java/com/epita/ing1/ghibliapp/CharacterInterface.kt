package com.epita.ing1.ghibliapp

import retrofit2.Call
import retrofit2.http.GET

interface CharacterInterface {
    @GET("/people")
    fun listCharacter() : Call<List<Character>>
}