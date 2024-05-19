package it.unibo.gamevault.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import it.unibo.gamevault.R
import it.unibo.gamevault.ui.model.Game

class SearchGamesAdapter(private var dataSet: List<Game>) : RecyclerView.Adapter<SearchGamesAdapter.GameViewHolder>() {

    class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gameName: TextView = itemView.findViewById(R.id.txtGameName)
        val gameRelease: TextView = itemView.findViewById(R.id.txtGameYear)
        val gamePoster: ImageView = itemView.findViewById(R.id.searchGamePoster)
        val gamePlatform: TextView = itemView.findViewById(R.id.txtGamePlatform)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
        return GameViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val currentGame = dataSet[position]

        holder.gameName.text = currentGame.name
        holder.gameRelease.text = (if(currentGame.tba == true) { "TBA" } else { currentGame.released })

        var allPlatform = ""
        for(p in currentGame.platforms!!) {
            allPlatform += p.getPlatformFormat() //The API return the platforms in this form: platform=(nameOfPlatform)
        }
        holder.gamePlatform.text = if (allPlatform.isEmpty()) { "No platform found" } else { allPlatform }

        Glide.with(holder.itemView)
            .load(currentGame.backgroundImage)
            .placeholder(R.drawable.poster_placeholder)
            .into(holder.gamePoster)
    }

    override fun getItemCount(): Int = dataSet.size

    fun updateSearchResults(newResults: List<Game>) {
        dataSet = newResults
        notifyDataSetChanged()
    }
}
