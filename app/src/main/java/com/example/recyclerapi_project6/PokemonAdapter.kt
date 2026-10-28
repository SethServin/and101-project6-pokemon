package com.example.recyclerapi_project6

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PokemonAdapter(
    private val context: Context,
    private val pokemonList: MutableList<Pokemon>
) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivPokemon: ImageView = view.findViewById(R.id.ivPokemon)
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvId: TextView = view.findViewById(R.id.tvId)
        val tvType: TextView = view.findViewById(R.id.tvType)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pokemon, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val pokemon = pokemonList[position]

        holder.tvName.text =
            pokemon.name.replaceFirstChar { it.uppercase() }

        holder.tvId.text = "ID: ${pokemon.id}"
        holder.tvType.text = "Type: ${pokemon.type}"

        Glide.with(context)
            .load(pokemon.imageUrl)
            .into(holder.ivPokemon)

        holder.itemView.setOnClickListener {
            Toast.makeText(
                context,
                pokemon.name,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }
}
