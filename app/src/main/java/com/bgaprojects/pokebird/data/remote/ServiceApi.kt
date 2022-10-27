package com.bgaprojects.pokebird.data.remote

import com.bgaprojects.pokebird.data.model.pokemon.PokemonListResultModel
import com.bgaprojects.pokebird.data.model.pokemon.PokemonResultModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
    ): Response<PokemonListResultModel>

    @GET("pokemon/{id}")
    suspend fun getPokemonInfo(
        @Path("id") id: Long
    ): Response<PokemonResultModel>
}