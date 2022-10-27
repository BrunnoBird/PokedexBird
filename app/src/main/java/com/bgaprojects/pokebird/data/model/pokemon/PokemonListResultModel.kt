package com.bgaprojects.pokebird.data.model.pokemon

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PokemonListResultModel(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("previous")
    val previous: Any,
    @SerializedName("results")
    val results: List<PokemonResult>
) : Serializable