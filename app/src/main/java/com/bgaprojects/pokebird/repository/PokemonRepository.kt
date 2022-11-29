package com.bgaprojects.pokebird.repository

import com.bgaprojects.pokebird.data.model.pokemon.PokemonListResultModel
import com.bgaprojects.pokebird.data.model.pokemon.PokemonResultModel
import com.bgaprojects.pokebird.data.remote.ServiceApi
import retrofit2.Response
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val api: ServiceApi
) {
    suspend fun list(): Response<PokemonListResultModel> = api.getPokemonList(10)
    suspend fun listInfo(pokemonId: Long): Response<PokemonResultModel> =
        api.getPokemonInfo(pokemonId)
}