package com.epita.ing1.ghibliapp


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.epita.ing1.ghibliapp.R.layout.character_list
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.recycler_view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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
        val baseURL = "https://ghibliapi.herokuapp.com"
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
                            mdata.addAll(responseData)
                            character_recycler.setHasFixedSize(true)
                            character_recycler.layoutManager = LinearLayoutManager(
                                    activity,
                                    LinearLayoutManager.VERTICAL,
                                    false)
                            character_recycler.adapter = CharacterAdapter(context!!, mdata)
                        }
                    }
                }
            }
        }
        service.listCharacter().enqueue(callback)
    }
}
