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

data class QuizzItem(
        val id: String,
        val title: String,
        val question: String,
        val correct: Int,
        val answer1: String,
        val answer2: String,
        val answer3: String
)

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

        currentQuest = QuizzItem("Test", "QUIZZ test", "How's the daddy ?",
                2, "Jojo", "Me", "Epita lambda student.")
        val txt1: TextView = findViewById(R.id.TitleText)
        val txt2: TextView = findViewById(R.id.QuestionText)
        val txt3: TextView = findViewById(R.id.ScoreText)
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
        tabLab.add(txt3)

        val dataPeople = arrayListOf<PeopleQuizz>()

        val jsonConv = GsonConverterFactory.create(GsonBuilder().create())

        val retrofit = Retrofit.Builder()
                .baseUrl("https://ghibliapi.herokuapp.com/people/")
                .addConverterFactory(jsonConv)
                .build()

        // val service = retrofit.create(PeopleQuizz::class.java)

        val callback = object : Callback<List<PeopleQuizz>> {
            override fun onFailure(call: Call<List<PeopleQuizz>>?, t: Throwable?) {
                // Code here what happens if calling the WebService fails
                //button.text = "FAIL1"
            }

            override fun onResponse(call: Call<List<PeopleQuizz>>?, response: Response<List<PeopleQuizz>>?) {
                // Code here what happens when WebService responds
                // button.text = "Fail2"
                if (response != null) {
                    // button.text = "Fail3"
                    if (response.code() == 200) {
                        val responseData = response.body()
                        if (responseData != null) {
                            // data.addAll(responseData)
                            // data.shuffle()
                        }
                    }
                }
            }
        }

        // start Game
        StartQuestions()
    }

    fun StartQuestions() {
        // Change Question
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
        // Change Question
        if (count > max) {
            this.tabLab[0].text = "Finish !"
            this.tabLab[1].text = ""
            this.tabLab[2].text = "Score :" + score + "/" + max
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
