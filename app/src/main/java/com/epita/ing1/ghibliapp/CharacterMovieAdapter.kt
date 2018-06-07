package com.epita.ing1.ghibliapp

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharacterMovieAdapter(val context: Context,
                            val data: List<String>) :
        RecyclerView.Adapter<CharacterMovieAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.text_view_character_movie)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rowView = LayoutInflater
                .from(context)
                .inflate(R.layout.character_movie_list, parent, false)
        val viewHolder = ViewHolder(rowView)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tmp = data[position].split('/')
        val id = tmp.last()
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder()
                .baseUrl("https://ghibliapi.herokuapp.com/")
                .addConverterFactory(jsonConverter)
                .build()
        val service: WSInterface = retrofit.create(WSInterface::class.java)

        val callback = object : Callback<List<MovieDetails>> {
            override fun onFailure(call: Call<List<MovieDetails>>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<List<MovieDetails>>?, response: Response<List<MovieDetails>>?) {
                if (response != null) {
                    if (response.code() == 200) {
                        // We got our data !
                        val responseData = response.body()
                        if (responseData != null) {
                            for (i in responseData)
                                if (i.id == id)
                                    holder.nameTextView.text = i.title
                        }
                    }
                }
            }
        }
        service.getFilmDetails().enqueue(callback)
    }
}