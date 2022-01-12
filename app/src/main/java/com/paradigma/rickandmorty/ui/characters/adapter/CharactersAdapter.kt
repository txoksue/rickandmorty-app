package com.paradigma.rickandmorty.ui.characters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paradigma.rickandmorty.databinding.CharacterItemViewBinding
import com.paradigma.rickandmorty.databinding.LoaderItemViewBinding
import com.paradigma.rickandmorty.domain.Character

class CharactersAdapter(private val characterList: ArrayList<Character?>, private val onClickCharacter: (Character) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val CHARACTER_ITEM = 0
        const val LOADING_ITEM = 1
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            CHARACTER_ITEM -> {
                val binding = CharacterItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CharacterItemView(binding, onClickCharacter)
            }
            else -> {
                val binding = LoaderItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LoaderItemView(binding)
            }
        }

    }

    override fun getItemCount(): Int = characterList.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CharacterItemView -> {
                characterList[position]?.let { character ->
                    holder.bind(character)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {

        return when (characterList[position]) {
            null -> LOADING_ITEM
            else -> CHARACTER_ITEM
        }

    }

    fun setItems(newCharacterList: List<Character?>) {
        characterList.clear()
        characterList.addAll(newCharacterList)
        notifyDataSetChanged()
    }

}