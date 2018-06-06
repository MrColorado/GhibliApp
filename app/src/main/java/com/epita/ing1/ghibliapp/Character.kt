package com.epita.ing1.ghibliapp

data class Character(val id: String,
                     val name: String,
                     val gender: String,
                     val eye_color: String,
                     val films: List<String>,
                     val species: String,
                     val url: String) {

}