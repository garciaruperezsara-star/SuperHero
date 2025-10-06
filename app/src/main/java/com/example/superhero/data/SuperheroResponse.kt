package com.example.superhero.data

data class SuperheroResponse(val results: List<Superhero>)
data class Superhero(
    val id: String,
    val name: String,
    val image: Image,
    val powerstats: Powerstats,
    val biography: Biography
    //val appearance: Appearance,
    //val work: Work,
    //val connections: Connections todo implement this
)

data class Biography (
    val fullName: String,
    val alterEgos: List<String>,
    val aliases: List<String>,
    val placeOfBirth: String,
    val firstAppearance: String,
    val publisher: String,
    val alignment: String
    )

data class Powerstats (
    val intelligence : String,
    val strength: String,
    val speed: String,
    val durability: String,
    val power: String,
    val combat: String
)
data class Image(val url: String)