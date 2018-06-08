package com.epita.ing1.ghibliapp


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.character_recycler_view.*
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
class CharacterListFragment : Fragment(), TextWatcher {

    private val mdata = ArrayList<Character>()
    private var adapter: CharacterAdapter? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.character_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val baseURL = "https://ghibliapi.herokuapp.com"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(jsonConverter)
                .build()
        val service: CharacterInterface = retrofit.create(CharacterInterface::class.java)

        val myItemClickListener = View.OnClickListener {
            // we retrieve the row position from its tag
            val position = it.tag as Int
            val clickedItem = mdata[position]
            val myFragment = CharacterFragment.newInstance(clickedItem)
            val fragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.main_container, myFragment).addToBackStack("Character")
            fragmentTransaction.commit()
        }

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
                            adapter = CharacterAdapter(context!!, mdata, myItemClickListener)
                            character_recycler.adapter = adapter
                        }
                    }
                }
            }
        }
        service.listCharacter().enqueue(callback)
        character_recycler_searchView.addTextChangedListener(this)
    }

    /**
     * SEARCH BAR FILTER TEXT CHAMGES WATCHER
     */
    override fun afterTextChanged(s: Editable?) {
        filter(s.toString())
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    private fun filter(text: String) {
        val filteredItems = ArrayList<Character>()

        for (character in mdata) {
            if (character.name.toLowerCase().contains(text.toLowerCase())) {
                filteredItems.add(character)
            }
        }
        adapter!!.filterList(filteredItems)
    }
}
