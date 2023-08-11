package com.example.startwars

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface StarWarsApiService {

    @GET("people/")
    fun searchCharacters(
        @Query("search") searchQuery: String
    ): Call<List<Character>>

    @GET("starships/")
    fun searchStarships(
        @Query("search") searchQuery: String
    ): Call<List<Starship>>
}


data class Character(
    val name: String,
    val gender: String,
    val starships: List<String>
)

data class Starship(
    val name: String,
    val model: String,
    val manufacturer: String,
    val passengers: String
)

