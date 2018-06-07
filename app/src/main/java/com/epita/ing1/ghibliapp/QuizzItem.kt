package com.epita.ing1.ghibliapp


data class QuizzItem(
        val title: String,
        val question: String,
        val correct: Int,
        val answer1: String,
        val answer2: String,
        val answer3: String)