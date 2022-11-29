package com.bgaprojects.pokebird.util

import android.util.Log
import com.bgaprojects.pokebird.R
import com.bgaprojects.pokebird.data.model.pokemon.PokemonResult
import com.bgaprojects.pokebird.data.model.pokemon.PokemonResultModel
import com.bgaprojects.pokebird.data.model.pokemon.TypeX
import java.util.*

fun extractIdPokemonForFetchDetails(pokemonResult: PokemonResult): Long {
    val number = pokemonResult.url.replace("https://pokeapi.co/api/v2/pokemon/", "")
        .replace("/", "").toLong()
    return number
}

fun parseTypeToColor(type: TypeX): Int {
    return when (type.name.lowercase(Locale.ROOT)) {
        "normal" -> R.color.type_normal
        "fire" -> R.color.type_fire
        "water" -> R.color.type_water
        "electric" -> R.color.type_eletric
        "grass" -> R.color.type_grass
        "ice" -> R.color.type_ice
        "fighting" -> R.color.type_fight
        "poison" -> R.color.type_poison
        "ground" -> R.color.type_ground
        "flying" -> R.color.type_flying
        "psychic" -> R.color.type_psychic
        "bug" -> R.color.type_bug
        "rock" -> R.color.type_rock
        "ghost" -> R.color.type_ghost
        "dragon" -> R.color.type_dragon
        "dark" -> R.color.type_dark
        "steel" -> R.color.type_steel
        "fairy" -> R.color.type_fairy
        else -> R.color.black
    }
}

fun extractImagePokemon(pokemonResult: PokemonResultModel): String {
    val formattedNumber: String? = pokemonResult.id.toString().padStart(3, '0');
    Log.d("TESTE", formattedNumber.toString())
    return "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/$formattedNumber.png"
}