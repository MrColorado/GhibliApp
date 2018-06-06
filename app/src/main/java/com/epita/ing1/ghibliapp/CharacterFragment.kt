package com.epita.ing1.ghibliapp


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.recycler_view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class CharacterFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mdata : MutableList<Character> = arrayListOf()
        val movies = listOf("Star Wars")

        mdata.add(Character("1", "Han Solo", "Male", "Green", movies, "Humain", "none"))
        mdata.add(Character("1", "Darth Vader", "Male", "Green", movies, "Humain", "none"))

        character_recycler.setHasFixedSize(true)
        character_recycler.layoutManager = LinearLayoutManager(
                activity,
                LinearLayoutManager.VERTICAL,
                false)
        character_recycler.adapter = CharacterAdapter(this.context!!, mdata)
    }
}
