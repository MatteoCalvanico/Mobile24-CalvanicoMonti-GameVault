package it.unibo.gamevault.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import it.unibo.gamevault.R
import it.unibo.gamevault.data.local.entity.GamesVaultLocalModel
import it.unibo.gamevault.ui.model.VaultGamesModel
import it.unibo.gamevault.ui.viewModel.VaultViewModel
import java.lang.StringBuilder

class VaultGamesAdapter(private var dataSet: List<VaultGamesModel>, private val viewModel: VaultViewModel) : RecyclerView.Adapter<VaultGamesAdapter.ViewHolder>() {

    //Provide a reference to the type of views that you are using (custom ViewHolder)
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val gameName: TextView = view.findViewById(R.id.txtGameName)
        val yourRating: RatingBar = view.findViewById(R.id.yourRating)
        val dateStart: TextView = view.findViewById(R.id.txtDateStart)
        val dateEnd: TextView = view.findViewById(R.id.txtDateEnd)
    }

    //Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.vault_item, viewGroup, false)
        return ViewHolder(view)
    }

    //Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the contents of the view with that element
        val game = dataSet[position]

        viewHolder.gameName.text = game.gameName
        viewHolder.yourRating.rating = (game.yourRating ?: 0.0).toFloat() //If the user didn't rate the game we put 0 star by default

        val dateStr = StringBuilder().append("Start: ").append(game.startDate ?: "No data")
        viewHolder.dateStart.text = dateStr.toString()

        val endDateStr = StringBuilder().append("End: ").append(game.endDate ?: "No data")
        viewHolder.dateEnd.text = endDateStr.toString()

        // We use Glide to load the right img
        Glide.with(viewHolder.itemView)
            .load(game.imgLink) // Now using imageUrl from VaultGamesModel
            .placeholder(R.drawable.poster_placeholder)
            .into(viewHolder.itemView.findViewById(R.id.vaultGamePoster) as ImageView)
    }

    //With ItemTouchHelper we made possible de slide for delete the games
    fun slideDeletion(recyclerView: RecyclerView) {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = false //We don't need this

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val gamesVaultToDelete = dataSet[position]

                //Delete the game in the DB
                viewModel.delete(GamesVaultLocalModel(gamesVaultToDelete.gameName))

                //Update adapter's
                (dataSet as MutableList).removeAt(position)
                notifyItemRemoved(position)
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    //Change the dataSet base on the search
    fun updateData(newDataSet: List<VaultGamesModel>) {
        dataSet = newDataSet
        notifyDataSetChanged()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}