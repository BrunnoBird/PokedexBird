package com.bgaprojects.pokebird.ui.homeFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bgaprojects.pokebird.data.model.pokemon.PokemonListResultModel
import com.bgaprojects.pokebird.data.model.pokemon.PokemonResultModel
import com.bgaprojects.pokebird.repository.PokemonRepository
import com.bgaprojects.pokebird.ui.state.ResourceState
import com.bgaprojects.pokebird.util.extractIdPokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class homeViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private var _list =
        MutableStateFlow<ResourceState<List<PokemonResultModel>>>(ResourceState.Loading())
    val list: StateFlow<ResourceState<List<PokemonResultModel>>> = _list

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
        try {
            val response = repository.list()
            _pokemonResult.value = handleResponsePokemonResult(response)

            try {
                _pokemonResult.value.data?.results.let { it ->
                    var pokemonsList: MutableList<PokemonResultModel> = mutableListOf()

                    it?.map { pokemonResultModel ->
                        val number = extractIdPokemon(pokemonResultModel)

                        val pokemonApiResponse = repository.listInfo(number)
                        val pokemons = handleResponsePokemonApiResult(pokemonApiResponse)
                        pokemons?.let {
                            it.data?.let {
                                pokemonsList.add(it)
                            }
                        }
                    }
                    _list.value = ResourceState.Success(pokemonsList)
                }
            } catch (t: Throwable) {

            } catch (t: Throwable) {
            }

        } catch (t: Throwable) {
            when (t) {
                is IOException -> _list.value =
                    ResourceState.Error("Erro de conexão com a internet")
                else -> _list.value =
                    ResourceState.Error("Falha na conversão de dados")
            }
        }
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
        val listFiltered = list.value.data?.filter {
            it.name.contains(name.toString().lowercase()) ?: false
        }

        listFiltered?.let {
            return it
        }
        return listOf()
    }
}