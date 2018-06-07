package com.epita.ing1.ghibliapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import com.google.gson.GsonBuilder

// 4 Questions People
// 3 Questions about species
// 3 Questions about location

/*
data class PeopleQuizz(
        val id: String,
        val name: String,
        val gender: String,
        val age: String,
        val eye_color: String,
        val hair_color: String,
        val films: MutableList<String>,
        val species: String,
        val url: String)
*/


class QuizzActivity : AppCompatActivity() {

    var tabAn: MutableList<Button> = mutableListOf()

    var tabLab: MutableList<TextView> = mutableListOf()

    var score: Int = 0

    var count: Int = 1

    var max: Int = 10

    var quests: MutableList<QuizzItem> = mutableListOf()

    var currentQuest: QuizzItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quizz)

        currentQuest = QuizzItem("QUIZZ test", "How's the daddy ?",
                2, "Jojo", "Me", "Epita lambda student.")
        val txt1: TextView = findViewById(R.id.TitleText)
        val txt2: TextView = findViewById(R.id.QuestionText)
        val an1: Button = findViewById(R.id.ButtonAn1)
        val an2: Button = findViewById(R.id.ButtonAn2)
        val an3: Button = findViewById(R.id.ButtonAn3)
        val next: Button = findViewById(R.id.NextQuestButton)
        an1.setVisibility(View.VISIBLE)
        an2.setVisibility(View.VISIBLE)
        an3.setVisibility(View.VISIBLE)
        tabAn.add(an1)
        tabAn.add(an2)
        tabAn.add(an3)
        tabAn.add(next)
        tabLab.add(txt1)
        tabLab.add(txt2)

        // Get People, Vehicle, ;...
        val sourcePeople: MutableList<Character> = mutableListOf()
        val baseURL = "https://ghibliapi.herokuapp.com"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(jsonConverter)
                .build()
        val service: CharacterInterface = retrofit.create(CharacterInterface::class.java)

        val callback = object : Callback<List<Character>> {
            override fun onFailure(call: Call<List<Character>>?, t: Throwable?) {}

            override fun onResponse(call: Call<List<Character>>?, response: Response<List<Character>>?) {
                if (response != null) {
                    if (response.code() == 200) {
                        val responseData = response.body()
                        if (responseData != null) {
                            sourcePeople.addAll(responseData)
                            val copyPeople = sourcePeople
                            sourcePeople.shuffle()
                            for (i in 1..10) {
                                val k = (i % 3) + 1
                                var ann1 = ""
                                var ann2 = ""
                                var ann3 = ""
                                copyPeople.shuffle()
                                for (j in copyPeople) {
                                    var cor = true
                                    for (l in sourcePeople[i].films) {
                                        for (c in j.films) {
                                            if (l == c) {
                                                cor = false
                                            }
                                        }
                                    }
                                    if (cor) {
                                        if (ann1 == "") {
                                            ann1 = j.films[0]
                                        } else if (ann2 == "") {
                                            ann2 = j.films[0]
                                        } else if (ann3 == "") {
                                            ann3 = j.films[0]
                                        } else {
                                            break
                                        }
                                    }
                                }
                                if (k == 1) {
                                    ann1 = sourcePeople[i].films[0]
                                } else if (k == 2) {
                                    ann2 = sourcePeople[i].films[0]
                                } else if (k == 3) {
                                    ann3 = sourcePeople[i].films[0]
                                }
                                quests.add(QuizzItem(sourcePeople[i].name, "Which movie for this character ?",
                                        k, ann1, ann2, ann3))
                            }
                            StartQuestions()
                        }
                    }
                }
            }
        }
        service.listCharacter().enqueue(callback)
    }

    fun StartQuestions() {
        // Change Question
        quests.shuffle()
        currentQuest = quests[count - 1]
        // Update Question Labels
        this.tabLab[0].text = currentQuest!!.title
        this.tabLab[1].text = currentQuest!!.question
        // Update Buttons values
        this.tabAn[0].text = currentQuest!!.answer1
        this.tabAn[1].text = currentQuest!!.answer2
        this.tabAn[2].text = currentQuest!!.answer3
        // Buttons Show
        this.tabAn[0].visibility = View.VISIBLE
        this.tabAn[1].visibility = View.VISIBLE
        this.tabAn[2].visibility = View.VISIBLE
        this.tabAn[3].visibility = View.INVISIBLE
    }

    fun nextQuestion(clickedView: View) {
        // Increm
        count++
        currentQuest = quests[count - 1]
        // Change Question
        if (count > max) {
            this.tabLab[0].text = "Finish !"
            this.tabLab[1].text = "Score :" + score + "/" + max
            this.tabAn[0].visibility = View.INVISIBLE
            this.tabAn[1].visibility = View.INVISIBLE
            this.tabAn[2].visibility = View.INVISIBLE
            this.tabAn[3].visibility = View.INVISIBLE
        } else {
            // Update Question Labels
            this.tabLab[0].text = currentQuest!!.title
            this.tabLab[1].text = currentQuest!!.question
            // Update Buttons values
            this.tabAn[0].text = currentQuest!!.answer1
            this.tabAn[1].text = currentQuest!!.answer2
            this.tabAn[2].text = currentQuest!!.answer3
            // Buttons Show
            this.tabAn[0].visibility = View.VISIBLE
            this.tabAn[1].visibility = View.VISIBLE
            this.tabAn[2].visibility = View.VISIBLE
            this.tabAn[3].visibility = View.INVISIBLE
        }
    }

    fun onClickAn1(clickedView: View) {
        this.tabLab[1].text = "Answer is :"
        if (this.currentQuest!!.correct == 1) {
            this.tabLab[1].text = " Well done !!!"
            this.score += 1
            this.tabAn[1].visibility = View.INVISIBLE
            this.tabAn[2].visibility = View.INVISIBLE
        } else if (this.currentQuest!!.correct == 2) {
            this.tabAn[2].visibility = View.INVISIBLE
            this.tabAn[0].visibility = View.INVISIBLE
        } else {
            this.tabAn[0].visibility = View.INVISIBLE
            this.tabAn[1].visibility = View.INVISIBLE
        }
        this.tabAn[3].visibility = View.VISIBLE
    }

    fun onClickAn2(clickedView: View) {
        this.tabLab[1].text = "Answer is :"
        if (this.currentQuest!!.correct == 2) {
            this.tabLab[1].text = " Well done !!!"
            this.score += 1
            this.tabAn[0].visibility = View.INVISIBLE
            this.tabAn[2].visibility = View.INVISIBLE
        } else if (this.currentQuest!!.correct == 1) {
            this.tabAn[1].visibility = View.INVISIBLE
            this.tabAn[2].visibility = View.INVISIBLE
        } else {
            this.tabAn[1].visibility = View.INVISIBLE
            this.tabAn[0].visibility = View.INVISIBLE
        }
        this.tabAn[3].visibility = View.VISIBLE
    }

    fun onClickAn3(clickedView: View) {
        this.tabLab[1].text = "Answer is :"
        if (this.currentQuest!!.correct == 3) {
            this.tabLab[1].text = " Well done !!!"
            this.score += 1
            this.tabAn[0].visibility = View.INVISIBLE
            this.tabAn[1].visibility = View.INVISIBLE
        } else if (this.currentQuest!!.correct == 1) {
            this.tabAn[1].visibility = View.INVISIBLE
            this.tabAn[2].visibility = View.INVISIBLE
        } else {
            this.tabAn[0].visibility = View.INVISIBLE
            this.tabAn[2].visibility = View.INVISIBLE
        }
        this.tabAn[3].visibility = View.VISIBLE
    }

    fun fillQuizz(clickedView: View) {

    }
}
