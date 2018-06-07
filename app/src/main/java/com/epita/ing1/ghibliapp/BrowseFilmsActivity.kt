package com.epita.ing1.ghibliapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_browse_films.*
import kotlinx.android.synthetic.main.activity_browse_films.view.*

class BrowseFilmsActivity : AppCompatActivity() {

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
        for (i in 0..30) {
            data.add(Movie("My neighbour Totoro $i"))
        }

        // Google removed ItemClickListener in RecyclerView so we have to do it ourselves
        val myItemClickListener = View.OnClickListener {
            it ->
            // we retrieve the row position from its tag
            val position = it.tag as Int
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
