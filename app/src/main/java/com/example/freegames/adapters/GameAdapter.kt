package com.example.freegames.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.freegames.data.Game
import com.example.freegames.databinding.ItemGameBinding
import com.squareup.picasso.Picasso

class GameAdapter(
    var items: List<Game>,
    val onClickListener: (String) -> Unit
) : RecyclerView.Adapter<GameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
            val binding= ItemGameBinding.inflate(layoutInflater,parent,false)
        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val item = items[position]
        holder.render(item,onClickListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun updateItems(items:List<Game>){
        this.items = items
        notifyDataSetChanged()
    }
}
class GameViewHolder(val binding: ItemGameBinding) : RecyclerView.ViewHolder(binding.root) {

    fun render(game: Game, onClickListener: (String) -> Unit) {

        binding.gameImageView.setOnClickListener { onClickListener(game.id) }
        binding.gameNameTextView.text= game.title
        Picasso.get().load(game.thumbnail).into(binding.gameImageView)

    }


}