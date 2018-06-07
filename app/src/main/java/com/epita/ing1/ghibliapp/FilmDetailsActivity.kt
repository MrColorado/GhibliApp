package com.epita.ing1.ghibliapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_film_details.*

class FilmDetailsActivity : AppCompatActivity(), View.OnClickListener {

    /**
     * Google Search button onClickListener
     */
    override fun onClick(clickedView: View?) {
        val implicitIntent = Intent(Intent.ACTION_VIEW)
        implicitIntent.data = Uri.parse(clickedView!!.tag as String)
        startActivity(implicitIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_details)

        // Init data (movies list) to give to Adapter
        // TODO intent get (Movie object)
        val originIntent = intent

        val titleAndYear = "%s (%d)"
                .format(originIntent.getStringExtra("title"),
                        originIntent.getIntExtra("year", -1))

        film_details_movieTitle.text = titleAndYear
        film_details_movieDirector
                .text = "%s %s".format(film_details_movieDirector.text,
                originIntent.getStringExtra("director"))
        film_details_movieDescription
                .text = originIntent.getStringExtra("description")
        film_details_googleSearchButton
                .tag = "https://www.google.com/search?&q=${film_details_movieTitle.text}"
        film_details_googleSearchButton.setOnClickListener(this@FilmDetailsActivity)

        film_details_iconView.setImageResource(R.drawable.movie_icon)
    }
}
