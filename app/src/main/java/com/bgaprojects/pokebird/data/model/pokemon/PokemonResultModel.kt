package com.bgaprojects.pokebird.data.model.pokemon

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PokemonResultModel(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("types")
    val types: List<Type>,
    @SerializedName("weight")
    var weight: Int,
    @SerializedName("height")
    var height: Int,
    @SerializedName("stats")
    val stats: List<PokemonStats>,
) : Serializable