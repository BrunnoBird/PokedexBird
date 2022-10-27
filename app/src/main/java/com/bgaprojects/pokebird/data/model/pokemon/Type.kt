package com.bgaprojects.pokebird.data.model.pokemon

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Type(
    @SerializedName("type")
    val type: TypeX
): Serializable
