package com.bgaprojects.pokebird.ui.HomeFragment

import android.opengl.Visibility
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bgaprojects.pokebird.databinding.FragmentDetailsBinding
import com.bgaprojects.pokebird.databinding.FragmentHomeBinding
import com.bgaprojects.pokebird.ui.adapter.PokemonAdapter
import com.bgaprojects.pokebird.ui.base.BaseFragment
import com.bgaprojects.pokebird.ui.detailsFragment.DetailsFragment
import com.bgaprojects.pokebird.ui.state.ResourceState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val viewModel: HomeViewModel by viewModels()
    private val pokemonAdapter by lazy { PokemonAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        setupRecyclerView()
        collectObserver()
        clickAdapter()
        listeners()
    }

    private fun listeners() = with(binding) {
        tietSearchPokemon.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                return
            }

            override fun onTextChanged(name: CharSequence?, p1: Int, p2: Int, p3: Int) {
                name?.let {
                    val filteredPokemon = viewModel.filterByName(name)
                    pokemonAdapter.pokemonsList = filteredPokemon
                    pokemonAdapter.notifyDataSetChanged()
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                return
            }

        })
    }

    private fun clickAdapter() {
        pokemonAdapter.setOnClickListener { pokemonModel ->
//            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(pokemonModel)
//            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() = with(binding) {
        rvPokemons.apply {
            adapter = pokemonAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun collectObserver() = lifecycleScope.launch {
        binding.loadingLottie.visibility = View.VISIBLE
        viewModel.list.collect { resources ->
            when (resources) {
                is ResourceState.Success -> {
                    resources.data?.let { values ->
                        pokemonAdapter.pokemonsList = values
                    }
                    pokemonAdapter.notifyDataSetChanged()
                    binding.loadingLottie.visibility = View.GONE
                }
                is ResourceState.Error -> {
                    resources.message?.let { message ->
                        binding.loadingLottie.visibility = View.GONE
                    }
                }
                is ResourceState.Loading -> {
                    binding.loadingLottie.visibility = View.VISIBLE
                }

                else -> {}
            }
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

}