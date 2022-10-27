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
    @SerializedName("defense")
    val defense: Int? = null,
    @SerializedName("hp")
    val hp: Int? = null,
    @SerializedName("weigth")
    var weight: Int,
    @SerializedName("heigth")
    var height: Int,
): Serializable