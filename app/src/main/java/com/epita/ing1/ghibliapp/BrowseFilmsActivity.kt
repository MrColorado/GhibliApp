package com.epita.ing1.ghibliapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_browse_films.*
import kotlinx.android.synthetic.main.activity_browse_films.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BrowseFilmsActivity : AppCompatActivity() {

    val API_BASE_URL = "https://ghibliapi.herokuapp.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse_films)

        // init ListView (basically optimizations and orientation)
        moviesList.setHasFixedSize(true)
        moviesList.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
        )

        // Init data (movies list) to give to Adapter
        val data = ArrayList<Movie>()
        // Use GSON library to create our JSON parser
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        // Create a Retrofit client object targeting the provided URL
        // and add a JSON converter (because we are expecting json responses)
        val retrofit = Retrofit.Builder().baseUrl(API_BASE_URL).addConverterFactory(jsonConverter).build()
        // Use the client to create a service:
        // an object implementing the interface to the WebService
        val service: WSInterface = retrofit.create(WSInterface::class.java)
        val callback = object : Callback<List<Movie>> {
            override fun onFailure(call: Call<List<Movie>>?, t: Throwable?) {
                Log.d("Retrofit", "GET Request error")
            }

            override fun onResponse(call: Call<List<Movie>>?, response: Response<List<Movie>>?) {
                if (response != null) {
                    if (response.code() == 200) {
                        val responseData = response.body()
                        if (responseData != null) {
                            for (movie in responseData) {
                                data.add(Movie(movie.title))
                            }
                        }
                    }
                }
            }
        }
        service.getFilms().enqueue(callback)

        // Google removed ItemClickListener in RecyclerView so we have to do it ourselves
        val myItemClickListener = View.OnClickListener {
            clickedView ->
            // we retrieve the row position from its tag
            val position = clickedView.tag as Int
            val clickedItem = data[position]
            // do stuff
            Toast.makeText(
                    this,
                    "Clicked " + clickedItem.title,
                    Toast.LENGTH_SHORT)
                    .show()
        }

        //moviesList.emptyView = emptyListTextView
        moviesList.adapter = MovieListRecyclerAdapter(this , data, myItemClickListener)
    }
}
