package com.epita.ing1.ghibliapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import com.google.gson.GsonBuilder

// 4 Questions People
// 3 Questions about species
// 3 Questions about location

data class PeopleQuizz(
        val id: String,
        val name: String,
        val gender: String,
        val age: String,
        val eye_color: String,
        val hair_color: String,
        val films: MutableList<String>,
        val species: String,
        val url: String)

class QuizzActivity : AppCompatActivity() {

    val quests: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quizz)

        val dataPeople = arrayListOf<PeopleQuizz>()

        val jsonConv = GsonConverterFactory.create(GsonBuilder().create())

        val retrofit = Retrofit.Builder()
                .baseUrl()
                .addConverterFactory(jsonConv)
                .build()

        val service = retrofit.create(PeopleQuizz::class.java)

        val callback = object  : Callback<List<PeopleQuizz>> {

        }
    }
}
