package com.epita.ing1.ghibliapp


import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.character.*


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

        return inflater.inflate(R.layout.character, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val character = arguments!!.getSerializable("character") as Character
        if (character.gender == "Female")
            image_view_gender.setImageResource(R.drawable.female)
        text_view_description_movie.text = "Lien vers les descriptions des film du personage: "
        text_view_character_name.text = character.name
        text_view_character_age.text = "Age: " + character.age
        text_view_eye_color.text = "Eyes color: " + character.eye_color
        text_view_hair_color.text = "Hair color: " + character.hair_color

        val myItemClickListener = View.OnClickListener {
            // we retrieve the row position from its tag
            val position = it.tag as Int
            val clickedItem = character.films[position]
            val implicitIntent = Intent(Intent.ACTION_VIEW)
            implicitIntent.data = Uri.parse( clickedItem)
            startActivity(implicitIntent)
        }

        recycler_view_character_movies.setHasFixedSize(true)
        recycler_view_character_movies.layoutManager = LinearLayoutManager(
                activity,
                LinearLayoutManager.VERTICAL,
                false)
        recycler_view_character_movies.adapter = CharacterMovieAdapter(context!!, character.films, myItemClickListener)

    }

    companion object {
        fun newInstance(character: Character): CharacterFragment {
            val args = Bundle()
            args.putSerializable("character", character)
            val fragment = CharacterFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
