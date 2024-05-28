package it.unibo.gamevault.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.unibo.gamevault.R
import it.unibo.gamevault.ui.adapter.VaultGamesAdapter
import it.unibo.gamevault.ui.model.VaultGamesModel
import it.unibo.gamevault.ui.viewModel.VaultViewModel

class VaultActivity : AppCompatActivity() {

    private val vaultViewModel: VaultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vault)

        val txtNoGame: TextView = findViewById(R.id.txtNoGame)
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        vaultViewModel.combinedGamesVault.observe(this) { games ->
            if (games.isEmpty()) {
                txtNoGame.visibility = TextView.VISIBLE
            } else {
                txtNoGame.visibility = TextView.INVISIBLE
                val vaultGames = games.map { game ->
                    VaultGamesModel(
                        imgLink = game.imgLink ?: "",
                        gameName = game.gameName ?: "No game",
                        yourRating = game.yourRating ?: 0.0,
                        startDate = game.startDate ?: "0/0/0000",
                        endDate = game.endDate ?: "0/0/0000"
                    )
                }
                val vaultGamesAdapter = VaultGamesAdapter(vaultGames)
                recyclerView.adapter = vaultGamesAdapter
            }
        }
    }
}