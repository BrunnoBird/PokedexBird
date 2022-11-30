package com.bgaprojects.pokebird.ui.detailsFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bgaprojects.pokebird.R
import com.bgaprojects.pokebird.data.model.pokemon.PokemonResultModel
import com.bgaprojects.pokebird.databinding.FragmentDetailsBinding
import com.bgaprojects.pokebird.ui.base.BaseFragment
import com.bgaprojects.pokebird.util.extractImagePokemon
import com.bgaprojects.pokebird.util.parseTypeToColor
import com.bumptech.glide.Glide
import java.util.*

class DetailsFragment : BaseFragment<FragmentDetailsBinding, DetailsViewModel>() {
    override val viewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUiBinding()
        setupToolbar()
    }

    private fun setupToolbar() {
        binding.tbFragmentDetails.ivBackButtonDetails.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupUiBinding() {
        val pokemonDetails = args.pokemon
        pokemonDetails.apply {
            binding.tvIdPokemonDetails.text = "#$id"
            binding.tvWeigthPokemonDetails.text = "$weight Kg"
            binding.tvHeigthPokemonDetails.text = "$height M"
            binding.tvNamePokemonDetails.text = name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            }

            uiBindingPokemonTypeElements(pokemonDetails)
            uiBindingPokemonStats()
            uiBindingPokemonImage(pokemonDetails)
        }
    }

    private fun uiBindingPokemonImage(pokemon: PokemonResultModel) {
        context?.let {
            Glide.with(it).load(extractImagePokemon(pokemon)).into(binding.ivPokemonDetails)
            binding.root.setBackgroundColor(
                ContextCompat.getColor(it, parseTypeToColor(pokemon.types[0].type))
            )
        }
    }

    private fun PokemonResultModel.uiBindingPokemonStats() {
        stats.let {
            it.forEach { stat ->
                when (stat.stat.name) {
                    "defense" -> {
                        binding.tvDefenceDetails.text = stat.base_stat.toString()
                        binding.progressBarDefenceDetails.progress = stat.base_stat
                    }
                    "attack" -> {
                        binding.tvAttackDetails.text = stat.base_stat.toString()
                        binding.progressBarAttackDetails.progress = stat.base_stat
                    }
                    "hp" -> {
                        binding.tvHpDetails.text = stat.base_stat.toString()
                        binding.progressBarHpDetails.progress = stat.base_stat
                    }
                    "speed" -> {
                        binding.tvSpeedDetails.text = stat.base_stat.toString()
                        binding.progressBarSpeedDetails.progress = stat.base_stat
                    }
                }
            }
        }
    }

    private fun PokemonResultModel.uiBindingPokemonTypeElements(pokemon: PokemonResultModel) {
        binding.tvElementMainPokemonDetails.text = types[0].type.name.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }

        if (pokemon.types.size == 2) {
            binding.tvElementSubPokemonDetails.text =
                (pokemon.types[1].type.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                })
        } else {
            binding.tvElementSubPokemonDetails.visibility = View.GONE
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailsBinding = FragmentDetailsBinding.inflate(inflater, container, false)
}