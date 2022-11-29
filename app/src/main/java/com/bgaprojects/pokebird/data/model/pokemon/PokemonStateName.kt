package com.bgaprojects.pokebird.data.model.pokemon

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PokemonStateName(
    @SerializedName("name")
    val name: String
) : Serializable
