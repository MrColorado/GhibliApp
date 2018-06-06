package com.epita.ing1.ghibliapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_characters.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharacterActivity : AppCompatActivity() {

    val data : MutableList<Character> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters)

        character_list.setOnItemClickListener() {
            adapterView, clickedView, position, id ->
            Toast.makeText(this, "Clicked$position", Toast.LENGTH_SHORT).show()
        }

        val baseURL = "https://ghibliapi.herokuapp.com/people"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(jsonConverter)
                .build()
        val service: CharacterInterface = retrofit.create(CharacterInterface::class.java)

        val callback = object : Callback<List<Character>> {
            override fun onFailure(call: Call<List<Character>>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<List<Character>>?, response: Response<List<Character>>?) {
                if (response != null) {
                    if (response.code() == 200) {
                        // We got our data !
                        val responseData = response.body()
                        if (responseData != null) {
                            data.addAll(responseData)
                            val adapter = CharacterAdapter(data, this@CharacterActivity)
                            character_list.adapter = adapter
                        }
                    }
                }
            }
        }
        service.listCharacter().enqueue(callback)
    }
}
