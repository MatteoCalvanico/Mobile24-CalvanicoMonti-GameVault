package it.unibo.gamevault.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import it.unibo.gamevault.R
import it.unibo.gamevault.ui.model.VaultGamesModel
import java.lang.StringBuilder

class VaultGamesAdapter(private val dataSet: List<VaultGamesModel>) : RecyclerView.Adapter<VaultGamesAdapter.ViewHolder>() {

    // Provide a reference to the type of views that you are using (custom ViewHolder)
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val gamePoster: ImageButton;
        val gameName: TextView;
        val yourRating: RatingBar;
        val dateStart: TextView;
        val dateEnd: TextView;
        init {
            // Define click listener for the ViewHolder's View
            gamePoster = view.findViewById(R.id.vaultGamePoster);
            gameName = view.findViewById(R.id.txtGameName);
            yourRating = view.findViewById(R.id.yourRating);
            dateStart = view.findViewById(R.id.txtDateStart);
            dateEnd = view.findViewById(R.id.txtDateEnd);
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.vault_item, viewGroup, false)
        return ViewHolder(view) }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the contents of the view with that element
        val game = dataSet[position]

        viewHolder.gameName.text = game.gameName
        viewHolder.yourRating.rating = (game.yourRating ?: 0.0).toFloat() //If hte user didn't rate the game we put 0 start by default

        var dateStr = StringBuilder().append("Start: ").append(game.startDate)
        viewHolder.dateStart.text = dateStr.toString()
        dateStr.clear()

        dateStr = StringBuilder().append("End: ").append(game.endDate)
        viewHolder.dateEnd.text = dateStr.toString()
        dateStr.clear()

        // TODO: Use Glide to load the poster game in the view !!!
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}