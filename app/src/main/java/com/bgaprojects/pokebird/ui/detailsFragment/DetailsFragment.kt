package com.bgaprojects.pokebird.ui.detailsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bgaprojects.pokebird.R
import com.bgaprojects.pokebird.databinding.FragmentDetailsBinding
import com.bgaprojects.pokebird.ui.base.BaseFragment
import com.bgaprojects.pokebird.util.extractImagePokemon
import com.bgaprojects.pokebird.util.parseTypeToColor
import com.bumptech.glide.Glide
import java.util.*

class DetailsFragment : BaseFragment<FragmentDetailsBinding, DetailsViewModel>() {
    override val viewModel: DetailsViewModel by viewModels()

    private val args: DetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pokemon = args.pokemon

        pokemon.apply {
            binding.tvNamePokemonDetails.text = name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            }
            binding.tvIdPokemonDetails.text = "#$id"
            binding.tvWeigthPokemonDetails.text = "$weight Kg"
            binding.tvHeigthPokemonDetails.text = "$height M"
            binding.tvElementMainPokemonDetails.text = types[0].type.name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            }
            if (pokemon.types.size >= 2) {
                binding.tvElementSubPokemonDetails.text =
                    (pokemon.types[1].type.name.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                    })
            }

            context?.let {
                Glide.with(it).load(extractImagePokemon(pokemon)).into(binding.ivPokemonDetails)
                binding.root.setBackgroundColor(
                    ContextCompat.getColor(it, parseTypeToColor(pokemon.types[0].type))
                )
            }
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailsBinding = FragmentDetailsBinding.inflate(inflater, container, false)
}