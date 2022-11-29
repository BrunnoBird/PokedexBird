package com.bgaprojects.pokebird.data.model.pokemon

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PokemonStats(
    @SerializedName("base_stat")
    val base_stat: Int,
    @SerializedName("stat")
    val stat: PokemonStateName
) : Serializable
