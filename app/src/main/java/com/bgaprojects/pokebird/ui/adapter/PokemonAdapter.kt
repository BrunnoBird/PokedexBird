package com.bgaprojects.pokebird.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bgaprojects.pokebird.data.model.pokemon.PokemonResultModel
import com.bgaprojects.pokebird.databinding.ItemPokemonBinding
import com.bgaprojects.pokebird.util.extractImagePokemon
import com.bgaprojects.pokebird.util.parseTypeToColor
import com.bumptech.glide.Glide
import java.util.*

class PokemonAdapter(
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {
    var pokemonsList: List<PokemonResultModel> = listOf()

    inner class PokemonViewHolder(itemView: ItemPokemonBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        private val tvNamePokemon: TextView
        private val clPokemonItem: ConstraintLayout
        private val cvPokemonItem: CardView
        private val tvNumberPokemon: TextView
        private val ivPokemonCard: ImageView
        private val tvTypesPokemonPrimary: TextView
        private val tvTypesPokemonSecondary: TextView

        init {
            clPokemonItem = itemView.clPokemonItem
            tvNamePokemon = itemView.tvNamePokemon
            cvPokemonItem = itemView.cvPokemonItem
            tvNumberPokemon = itemView.tvNumberPokemon
            ivPokemonCard = itemView.ivPokemonCard
            tvTypesPokemonPrimary = itemView.tvTypesPokemonPrimary
            tvTypesPokemonSecondary = itemView.tvTypesPokemonSecondary
        }

        fun bind(pokemon: PokemonResultModel) {
            cvPokemonItem.setOnClickListener {
                onItemClickListener?.let {
                    it(pokemon)
                }
            }
            cvPokemonItem.setBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    parseTypeToColor(pokemon.types[0].type)
                )
            )
            tvNamePokemon.text = pokemon.name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            }
            tvNumberPokemon.text = "#${pokemon.id}"
            tvTypesPokemonPrimary.text = pokemon.types[0].type.name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            }
            if (pokemon.types.size >= 2) {
                tvTypesPokemonSecondary.text = (pokemon.types[1].type.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                })
            }

            Glide.with(itemView.context).load(extractImagePokemon(pokemon)).into(ivPokemonCard)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(
            ItemPokemonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(pokemonsList[position])
    }

    override fun getItemCount(): Int = pokemonsList.size

    //Click Event External
    private var onItemClickListener: ((PokemonResultModel) -> Unit)? = null

    fun setOnClickListener(listener: (PokemonResultModel) -> Unit) {
        onItemClickListener = listener
    }
}