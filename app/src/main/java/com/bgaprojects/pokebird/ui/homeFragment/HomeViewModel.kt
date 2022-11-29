package com.bgaprojects.pokebird.ui.homeFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bgaprojects.pokebird.data.model.pokemon.PokemonListResultModel
import com.bgaprojects.pokebird.data.model.pokemon.PokemonResultModel
import com.bgaprojects.pokebird.repository.PokemonRepository
import com.bgaprojects.pokebird.ui.state.ResourceState
import com.bgaprojects.pokebird.util.extractIdPokemonForFetchDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private var _pokemonsListState =
        MutableStateFlow<ResourceState<List<PokemonResultModel>>>(ResourceState.Loading())
    val pokemonsListState: StateFlow<ResourceState<List<PokemonResultModel>>> = _pokemonsListState

    private val _pokemonResult =
        MutableStateFlow<ResourceState<PokemonListResultModel
                >>(ResourceState.Loading())

    init {
        fetch()
    }

    private fun fetch() = viewModelScope.launch {
        safeFetch()
    }

    private suspend fun safeFetch() {
        val pokemonsListDetails: MutableList<PokemonResultModel> = mutableListOf()

        try {
            fetchListPokemonsWithoutDetails()
            fetchListPokemonsDetails(pokemonsListDetails)
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _pokemonsListState.value =
                    ResourceState.Error("Erro de conexão com a internet")
                else -> _pokemonsListState.value =
                    ResourceState.Error("Falha na conversão de dados")
            }
        }
    }

    private suspend fun fetchListPokemonsDetails(pokemonsListDetails: MutableList<PokemonResultModel>) {
        _pokemonResult.value.data?.results.let { stateListPokemonWithoutDetails ->
            stateListPokemonWithoutDetails?.map { pokemonResultModel ->

                val idPokemon = extractIdPokemonForFetchDetails(pokemonResultModel)
                val pokemonDetailsResponse = repository.getPokemonsDetails(idPokemon)
                val pokemonsListState =
                    handleResponsePokemonApiResult(pokemonDetailsResponse)

                pokemonsListState.also {
                    it.data?.let { pokemonDetailed ->
                        pokemonsListDetails.add(
                            pokemonDetailed
                        )
                    }
                }
            }
            _pokemonsListState.value = ResourceState.Success(pokemonsListDetails)
        }
    }

    private suspend fun fetchListPokemonsWithoutDetails() {
        val pokemonsListResponse = repository.listPokemons()
        _pokemonResult.value = handleResponsePokemonResult(pokemonsListResponse)
    }

    private fun handleResponsePokemonApiResult(pokemonApiResponse: Response<PokemonResultModel>): ResourceState<PokemonResultModel> {
        if (pokemonApiResponse.isSuccessful) {
            pokemonApiResponse.body()?.let { values ->
                return ResourceState.Success(values)
            }
        }
        return ResourceState.Error(pokemonApiResponse.message())
    }

    private fun handleResponsePokemonResult(response: Response<PokemonListResultModel>): ResourceState<PokemonListResultModel> {
        if (response.isSuccessful) {
            response.body()?.let { values ->
                return ResourceState.Success(values)
            }
        }
        return ResourceState.Error(response.message())
    }

    fun filterByName(name: CharSequence): List<PokemonResultModel> {
        val listFiltered = pokemonsListState.value.data?.filter {
            it.name.contains(name.toString().lowercase()) ?: false
        }

        listFiltered?.let {
            return it
        }
        return listOf()
    }
}