package com.epita.ing1.ghibliapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_film_details.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FilmDetailsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FilmDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FilmDetailsFragment : Fragment(), View.OnClickListener {

    /**
     * Google Search button onClickListener
     */
    override fun onClick(clickedView: View?) {
        val implicitIntent = Intent(Intent.ACTION_VIEW)
        implicitIntent.data = Uri.parse(clickedView!!.tag as String)
        startActivity(implicitIntent)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_film_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Init data (movies list) to give to Adapter
        // TODO intent get (Movie object)
        val dataBundle = arguments!!

        val titleAndYear = "%s (%d)"
                .format(dataBundle.getString("title"),
                        dataBundle.getInt("year"))

        film_details_movieTitle.text = titleAndYear
        film_details_movieDirector
                .text = "%s %s".format(film_details_movieDirector.text,
                dataBundle.getString("director"))
        film_details_movieDescription
                .text = dataBundle.getString("description")
        film_details_googleSearchButton
                .tag = "https://www.google.com/search?&q=${film_details_movieTitle.text}"
        film_details_googleSearchButton.setOnClickListener(this@FilmDetailsFragment)

        film_details_iconView.setImageResource(R.drawable.movie_icon)
    }
}
