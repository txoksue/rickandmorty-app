package com.paradigma.rickyandmorty.ui.characters.adapter

import androidx.recyclerview.widget.RecyclerView
import com.paradigma.rickyandmorty.common.extensions.loadImage
import com.paradigma.rickyandmorty.databinding.CharacterItemViewBinding
import com.paradigma.rickyandmorty.domain.Character

class CharacterItemView(private var binding: CharacterItemViewBinding, private var onClickCharacter: (Character) -> Unit) : RecyclerView.ViewHolder(binding.root) {

    fun bind(character: Character) {

        with(binding) {

            textViewCharacterName.text = character.name
            imageCharacter.loadImage(character.image)

            root.setOnClickListener {
                imageCharacter.transitionName = "transitionImageView"
                onClickCharacter(character)
            }
        }
    }


}