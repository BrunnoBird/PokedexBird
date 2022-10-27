package com.bgaprojects.pokebird.repository

import com.bgaprojects.pokebird.data.remote.ServiceApi
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val api: ServiceApi
) {
    suspend fun list() = api.getPokemonList(10)
    suspend fun listInfo(pokemonId: Long) = api.getPokemonInfo(pokemonId)
}