package com.example.startwars

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.widget.Button
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var apiService: StarWarsApiService
    private lateinit var searchResultsRecyclerView: RecyclerView
    private lateinit var searchEditText: EditText
    private lateinit var characterAdapter: CharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        searchResultsRecyclerView = rootView.findViewById(R.id.searchResultsRecyclerView)
        searchEditText = rootView.findViewById(R.id.searchEditText)

        // Инициализация Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://swapi.dev/api/people/1/") // Укажите ваш базовый URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Создание экземпляра API-сервиса
        apiService = retrofit.create(StarWarsApiService::class.java)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        characterAdapter = CharacterAdapter() // Создайте свой адаптер
        searchResultsRecyclerView.adapter = characterAdapter
        val searchButton = view.findViewById<Button>(R.id.searchButton)
        val favoriteButton = view.findViewById<Button>(R.id.favoriteButton)

        // Добавьте менеджер компоновки
        searchResultsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Обработка нажатия на кнопку поиска
        searchButton.setOnClickListener {
            Log.d("SearchButton", "Button clicked")
            val searchQuery = searchEditText.text.toString()
            if (searchQuery.length >= 2) {
                Log.d("SearchButton", "Search query: $searchQuery") // Log
                searchCharacters(searchQuery)
            }
        }

        favoriteButton.setOnClickListener {
            findNavController().navigate(R.id.favoriteFragment)
        }
    }

    private fun searchCharacters(query: String) {
        Log.d("SearchCharacters", "Performing search for query: $query")
        val call = apiService.searchCharacters(query)

        call.enqueue(object : Callback<List<Character>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<List<Character>>, response: Response<List<Character>>) {
                Log.d("SearchCharacters", "Response received")
                if (response.isSuccessful) {
                    val characters = response.body()
                    if (characters != null) {
                        Log.d("SearchCharacters", "Number of characters received: ${characters.size}") // Log
                        characterAdapter.setData(characters)
                        characterAdapter.notifyDataSetChanged()
                    } else {
                        Log.d("SearchCharacters", "No characters received") // Log
                    }
                }
            }

            override fun onFailure(call: Call<List<Character>>, t: Throwable) {
                Log.e("SearchCharacters", "Error: ${t.message}")
            }
        })
    }
}