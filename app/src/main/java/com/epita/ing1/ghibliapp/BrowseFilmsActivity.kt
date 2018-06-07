package com.epita.ing1.ghibliapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_browse_films.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BrowseFilmsActivity : AppCompatActivity(), View.OnClickListener {

    var data: ArrayList<Movie> = ArrayList()

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

        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder().baseUrl("https://ghibliapi.herokuapp.com/").addConverterFactory(jsonConverter).build()
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
                                data.add(Movie(
                                        movie.id,
                                        movie.title,
                                        movie.description,
                                        movie.director,
                                        movie.producer,
                                        movie.release_date,
                                        movie.rt_score))
                            }
                            moviesList.adapter = MovieListRecyclerAdapter(this@BrowseFilmsActivity, data, this@BrowseFilmsActivity)
                        }
                    }
                }
            }
        }
        service.getFilms().enqueue(callback)

    }

    // Google removed ItemClickListener in RecyclerView so we have to do it ourselves
    override fun onClick(clickedView: View?) {
        // we retrieve the row position from its tag
        val position = clickedView!!.tag as Int
        val clickedItem = data[position]
        // do stuff
        Toast.makeText(
                this,
                "Clicked " + clickedItem.title,
                Toast.LENGTH_SHORT)
                .show()
        val explicitIntent = Intent(
                this@BrowseFilmsActivity,
                FilmDetailsActivity::class.java
        )
        explicitIntent.putExtra("title", clickedItem.title)
        explicitIntent.putExtra("year", clickedItem.release_date)
        explicitIntent.putExtra("director", clickedItem.director)
        explicitIntent.putExtra("description", clickedItem.description)

        startActivity(explicitIntent)
    }
}
