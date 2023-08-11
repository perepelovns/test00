package com.example.startwars

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    private val characters: MutableList<Character> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newCharacters: List<Character>?) {
        characters.clear()
        if (newCharacters != null) {
            characters.addAll(newCharacters)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return CharacterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        holder.bind(character)
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    inner class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val genderTextView: TextView = itemView.findViewById(R.id.genderTextView)
        private val starshipsTextView: TextView = itemView.findViewById(R.id.starshipsTextView)

        @SuppressLint("SetTextI18n")
        fun bind(character: Character) {
            nameTextView.text = character.name
            genderTextView.text = character.gender
            starshipsTextView.text = "Starships: ${character.starships.size}"
            Log.d("CharacterAdapter", "Binding character: ${character.name}, Gender: ${character.gender}, Starships: ${character.starships.size}")
        }
    }
}
