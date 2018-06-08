package com.epita.ing1.ghibliapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_browse_films_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [BrowseFilmsListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [BrowseFilmsListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class BrowseFilmsListFragment : Fragment(), View.OnClickListener, TextWatcher {

    private var data: ArrayList<Movie> = ArrayList()
    private var adapter: MovieListRecyclerAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // init ListView (basically optimizations and orientation)
        moviesList.setHasFixedSize(true)
        moviesList.layoutManager = LinearLayoutManager(
                activity,
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
                            adapter = MovieListRecyclerAdapter(context!!, data, this@BrowseFilmsListFragment)
                            moviesList.adapter = adapter
                        }
                    }
                }
            }
        }
        service.getFilms().enqueue(callback)

        // Search Bar
        browse_films_fragment_search_bar.addTextChangedListener(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_browse_films_list, container, false)
    }

    // Google removed ItemClickListener in RecyclerView so we have to do it ourselves
    override fun onClick(clickedView: View?) {
        // we retrieve the row position from its tag
        val position = clickedView!!.tag as Int
        val clickedItem = data[position]

        val dataBundle = Bundle()
        dataBundle.putString("title", clickedItem.title)
        dataBundle.putInt("year", clickedItem.release_date)
        dataBundle.putString("director", clickedItem.director)
        dataBundle.putString("description", clickedItem.description)

        val filmDetailsFragment = FilmDetailsFragment()
        filmDetailsFragment.arguments = dataBundle

        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.main_container, filmDetailsFragment)

        fragmentTransaction.commit()
    }

    /**
     * Search Bar textChangedListener
     */
    override fun afterTextChanged(editable: Editable?) {
        filter(editable.toString())
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // do nothing
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // do nothing
    }

    private fun filter(text: String) {
        //new array list that will hold the filtered data
        val filteredMovies = ArrayList<Movie>()

        //looping through existing elements
        for (movie in data) {
            //if the existing elements contains the search input
            if (movie.title.toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filteredMovies.add(movie)
            }
        }
        //calling a method of the adapter class and passing the filtered list
        this.adapter!!.filterList(filteredMovies)
    }
}
