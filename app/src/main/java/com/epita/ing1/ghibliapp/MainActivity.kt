package com.epita.ing1.ghibliapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.main_container, CharacterFragment())

        fragmentTransaction.commit()
    }

    /*
    val baseURL = "https://ghibliapi.herokuapp.com/people"
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
                        data.addAll(responseData)
                        val adapter = CharacterAdapter(data, this@CharacterActivity)
                        character_list.adapter = adapter
                    }
                }
            }
        }
    }
    service.listCharacter().enqueue(callback)
    */
}
