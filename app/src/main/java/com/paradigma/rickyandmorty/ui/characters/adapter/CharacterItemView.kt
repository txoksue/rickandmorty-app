package com.paradigma.rickyandmorty.ui.characters.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.paradigma.rickyandmorty.R
import com.paradigma.rickyandmorty.common.extensions.loadImage
import com.paradigma.rickyandmorty.databinding.CharacterItemViewBinding
import com.paradigma.rickyandmorty.domain.Character

class CharacterItemView(private var binding: CharacterItemViewBinding, private var onClickCharacter: (Character) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    lateinit var context: Context
    lateinit var status: String
    lateinit var gender: String

    fun bind(character: Character) {

        with(binding) {

            context = this.root.context

            textViewCharacterName.text = if (character.name.isNotEmpty()) character.name else context.getString(R.string.no_name)
            imageCharacter.loadImage(character.image)

            gender = if (character.gender.isNotEmpty()) character.gender else context.getString(R.string.no_gender)
            status = if (character.status.isNotEmpty()) character.status else context.getString(R.string.no_status)

            setGenderInfo(gender)
            setStatusInfo(status)

            root.setOnClickListener {
                imageCharacter.transitionName = "transitionImageView"
                onClickCharacter(character)
            }
        }
    }

    private fun setGenderInfo(gender: String) {
        val icon = if (gender != context.getString(R.string.unknown)) {
            R.drawable.ic_gender
        } else R.drawable.ic_unknown

        with(binding) {
            componentCharacterGender.setImage(icon)
            componentCharacterGender.setText(gender)
        }

    }

    private fun setStatusInfo(status: String) {
        val icon = when (status) {
            context.getString(R.string.alive) -> {
                R.drawable.ic_alive
            }
            context.getString(R.string.dead) -> {
                R.drawable.ic_dead
            }
            else -> {
                R.drawable.ic_unknown
            }
        }

        with(binding) {
            componentCharacterStatus.setImage(icon)
            componentCharacterStatus.setText(status)
        }
    }


}