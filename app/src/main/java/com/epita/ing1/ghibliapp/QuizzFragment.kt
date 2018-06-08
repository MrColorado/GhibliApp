package com.epita.ing1.ghibliapp

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_quizz.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuizzFragment : Fragment() {

    var tabAn: MutableList<Button> = mutableListOf()
    var tabLab: MutableList<TextView> = mutableListOf()
    var score: Int = 0
    var count: Int = 1
    var max: Int = 10
    var quests: MutableList<QuizzItem> = mutableListOf()
    var movies_urls: Array<String> = Array(30) {_ -> ""}
    var movies_str: Array<String> = Array(30) {_ -> ""}
    var movie_i = 0
    var movie_c = 0
    var currentQuest: QuizzItem? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_quizz, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        NextQuestButton.setOnClickListener {
            // Increm
            count++
            // Change Question
            if (count > max) {
                this.tabLab[1].text = "C'est Fini ! Super !"
                this.tabLab[0].text = "Score Final: $score/$max"
                this.tabLab[2].text = ""
                this.tabAn[0].visibility = View.INVISIBLE
                this.tabAn[1].visibility = View.INVISIBLE
                this.tabAn[2].visibility = View.INVISIBLE
                this.tabAn[3].visibility = View.INVISIBLE
            } else {
                currentQuest = quests[count - 1]
                // Question Labels
                this.tabLab[0].text = currentQuest!!.title
                this.tabLab[1].text = currentQuest!!.question
                this.tabLab[2].text = "$count/$max"
                // Update Buttons values
                this.tabAn[0].text = movies_str[movie_c]
                this.tabAn[1].text = movies_str[movie_c + 1]
                this.tabAn[2].text = movies_str[movie_c + 2]
                movie_c += 3
                // Buttons Show
                this.tabAn[0].getBackground().setColorFilter(0xFFCCCCCC.toInt(), PorterDuff.Mode.MULTIPLY)
                this.tabAn[1].getBackground().setColorFilter(0xFFCCCCCC.toInt(), PorterDuff.Mode.MULTIPLY)
                this.tabAn[2].getBackground().setColorFilter(0xFFCCCCCC.toInt(), PorterDuff.Mode.MULTIPLY)
                this.tabAn[0].visibility = View.VISIBLE
                this.tabAn[1].visibility = View.VISIBLE
                this.tabAn[2].visibility = View.VISIBLE
                this.tabAn[3].visibility = View.INVISIBLE
            }
        }

        ButtonAn1.setOnClickListener {
            this.tabLab[1].text = "La bonne réponse était :"
            if (this.currentQuest!!.correct == 1) {
                this.tabLab[1].text = "Bien joué !!!"
                this.score += 1
                this.tabAn[0].getBackground().setColorFilter(0xFF00FF00.toInt(), PorterDuff.Mode.MULTIPLY)
                this.tabAn[1].visibility = View.INVISIBLE
                this.tabAn[2].visibility = View.INVISIBLE
            } else if (this.currentQuest!!.correct == 2) {
                this.tabAn[1].getBackground().setColorFilter(0xFFFF0000.toInt(), PorterDuff.Mode.MULTIPLY)
                this.tabAn[2].visibility = View.INVISIBLE
                this.tabAn[0].visibility = View.INVISIBLE
            } else {
                this.tabAn[2].getBackground().setColorFilter(0xFFFF0000.toInt(), PorterDuff.Mode.MULTIPLY)
                this.tabAn[0].visibility = View.INVISIBLE
                this.tabAn[1].visibility = View.INVISIBLE
            }
            this.tabAn[3].visibility = View.VISIBLE
        }

        ButtonAn2.setOnClickListener {
            this.tabLab[1].text = "La bonne réponse était :"
            if (this.currentQuest!!.correct == 2) {
                this.tabLab[1].text = "Bien joué !!!"
                this.score += 1
                this.tabAn[1].getBackground().setColorFilter(0xFF00FF00.toInt(), PorterDuff.Mode.MULTIPLY)
                this.tabAn[0].visibility = View.INVISIBLE
                this.tabAn[2].visibility = View.INVISIBLE
            } else if (this.currentQuest!!.correct == 1) {
                this.tabAn[0].getBackground().setColorFilter(0xFFFF0000.toInt(), PorterDuff.Mode.MULTIPLY)
                this.tabAn[1].visibility = View.INVISIBLE
                this.tabAn[2].visibility = View.INVISIBLE
            } else {
                this.tabAn[2].getBackground().setColorFilter(0xFFFF0000.toInt(), PorterDuff.Mode.MULTIPLY)
                this.tabAn[1].visibility = View.INVISIBLE
                this.tabAn[0].visibility = View.INVISIBLE
            }
            this.tabAn[3].visibility = View.VISIBLE
        }

        ButtonAn3.setOnClickListener {
            this.tabLab[1].text = "La bonne réponse était :"
            if (this.currentQuest!!.correct == 3) {
                this.tabLab[1].text = "Bien joué !!!"
                this.score += 1
                this.tabAn[2].getBackground().setColorFilter(0xFF00FF00.toInt(), PorterDuff.Mode.MULTIPLY)
                this.tabAn[0].visibility = View.INVISIBLE
                this.tabAn[1].visibility = View.INVISIBLE
            } else if (this.currentQuest!!.correct == 1) {
                this.tabAn[0].getBackground().setColorFilter(0xFFFF0000.toInt(), PorterDuff.Mode.MULTIPLY)
                this.tabAn[1].visibility = View.INVISIBLE
                this.tabAn[2].visibility = View.INVISIBLE
            } else {
                this.tabAn[1].getBackground().setColorFilter(0xFFFF0000.toInt(), PorterDuff.Mode.MULTIPLY)
                this.tabAn[0].visibility = View.INVISIBLE
                this.tabAn[2].visibility = View.INVISIBLE
            }
            this.tabAn[3].visibility = View.VISIBLE
        }
        currentQuest = QuizzItem("QUIZZ test", "How's the daddy ?",
                2, "Jojo", "Me", "Epita lambda student.")
        val txt1: TextView = getView()!!.findViewById(R.id.TitleText)
        val txt2: TextView = getView()!!.findViewById(R.id.QuestionText)
        val txt3: TextView = getView()!!.findViewById(R.id.QuestionsStatus)
        val an1: Button = getView()!!.findViewById(R.id.ButtonAn1)
        val an2: Button = getView()!!.findViewById(R.id.ButtonAn2)
        val an3: Button = getView()!!.findViewById(R.id.ButtonAn3)
        val next: Button = getView()!!.findViewById(R.id.NextQuestButton)
        an1.visibility = View.INVISIBLE
        an2.visibility = View.INVISIBLE
        an3.visibility = View.INVISIBLE
        next.visibility = View.INVISIBLE
        tabAn.add(an1)
        tabAn.add(an2)
        tabAn.add(an3)
        tabAn.add(next)
        tabLab.add(txt1)
        tabLab.add(txt2)
        tabLab.add(txt3)
        // Get People, Vehicle, ;...
        val sourcePeople: MutableList<Character> = mutableListOf()
        val baseURL = "https://ghibliapi.herokuapp.com"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(jsonConverter)
                .build()
        val service: CharacterInterface = retrofit.create(CharacterInterface::class.java)

        val callbackMovie = object : Callback<MovieQuizz> {
            override fun onFailure(call: Call<MovieQuizz>?, t: Throwable?) {
                movie_i += 1
                if (movie_i == 30) {
                    StartQuestions()
                }
            }

            override fun onResponse(call: Call<MovieQuizz>?, response: Response<MovieQuizz>?) {
                if (response != null) {
                    if (response.code() == 200) {
                        val responseData = response.body()
                        if (responseData != null) {
                            var index = 0
                            for (k in movies_urls) {
                                if (k == responseData.id) {
                                    movies_urls[index] = ""
                                    break
                                }
                                index += 1
                            }
                            movies_str[index] = responseData.title
                            movie_i += 1
                            if (movie_i == 30) {
                                StartQuestions()
                            }
                        }
                    }
                }
            }
        }

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
                                            } else if (ann1 == c) {
                                                cor = false
                                            } else if (ann2 == c) {
                                                cor = false
                                            } else if (ann3 == c) {
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
                                            break
                                        } else {
                                            break
                                        }
                                    }
                                }
                                if (k == 1) {
                                    ann1 = sourcePeople[i].films[0]
                                } else if (k == 2) {
                                    ann2 = sourcePeople[i].films[0]
                                } else {
                                    ann3 = sourcePeople[i].films[0]
                                }
                                ann1 = ann1.substring(38, ann1.length)
                                ann2 = ann2.substring(38, ann2.length)
                                ann3 = ann3.substring(38, ann3.length)
                                movies_urls[movie_c] = ann1
                                movies_urls[movie_c + 1] = ann2
                                movies_urls[movie_c + 2] = ann3
                                movie_c += 3
                                val serviceM1: WSInterface = retrofit.create(WSInterface::class.java)
                                serviceM1.getFilmById(ann1).enqueue(callbackMovie)
                                serviceM1.getFilmById(ann2).enqueue(callbackMovie)
                                serviceM1.getFilmById(ann3).enqueue(callbackMovie)
                                quests.add(QuizzItem(sourcePeople[i].name, "A quel film appartient ce personnage ?",
                                        k, ann1, ann2, ann3))
                            }
                        }
                    }
                }
            }
        }
        service.listCharacter().enqueue(callback)
    }

    @SuppressLint("SetTextI18n")
    fun StartQuestions() {
        // Change Question
        movie_c = 0
        currentQuest = quests[count - 1]
        // Update Question Labels
        this.tabLab[0].text = currentQuest!!.title
        this.tabLab[1].text = currentQuest!!.question
        this.tabLab[2].text = "$count/$max"
        // Update Buttons values
        this.tabAn[0].text = movies_str[movie_c]
        this.tabAn[1].text = movies_str[movie_c + 1]
        this.tabAn[2].text = movies_str[movie_c + 2]
        movie_c += 3
        // Buttons Show
        this.tabAn[0].getBackground().setColorFilter(0xFFCCCCCC.toInt(), PorterDuff.Mode.MULTIPLY)
        this.tabAn[1].getBackground().setColorFilter(0xFFCCCCCC.toInt(), PorterDuff.Mode.MULTIPLY)
        this.tabAn[2].getBackground().setColorFilter(0xFFCCCCCC.toInt(), PorterDuff.Mode.MULTIPLY)
        this.tabAn[0].visibility = View.VISIBLE
        this.tabAn[1].visibility = View.VISIBLE
        this.tabAn[2].visibility = View.VISIBLE
        this.tabAn[3].visibility = View.INVISIBLE
    }
}
