package it.unibo.gamevault.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.unibo.gamevault.R
import it.unibo.gamevault.ui.adapter.VaultGamesAdapter
import it.unibo.gamevault.ui.model.VaultGamesModel
import it.unibo.gamevault.ui.viewModel.VaultViewModel

class VaultActivity : AppCompatActivity() {

    private val vaultViewModel: VaultViewModel by viewModels()
    private lateinit var searchView: SearchView
    private lateinit var btnClose: Button
    private lateinit var vaultGamesAdapter: VaultGamesAdapter
    private var originalVaultGames: List<VaultGamesModel> = emptyList() //Need this to save the vault games before show the filter list

    //Use this to recreate the HomeActivity when the user go back with the phone button
    override fun onBackPressed() {
        val intent = Intent(this@VaultActivity, HomePageActivity::class.java)
        startActivity(intent)
        super.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vault)

        val txtNoGame: TextView = findViewById(R.id.txtNoGame)
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        searchView = findViewById(R.id.search_bar)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                filterGames(newText)
                return true
            }
        })

        btnClose = findViewById(R.id.btnClose)
        btnClose.setOnClickListener{
            val intent = Intent(this@VaultActivity, HomePageActivity::class.java)
            startActivity(intent)
        }

        vaultViewModel.combinedGamesVault.observe(this) { games ->
            if (games.isEmpty()) {
                txtNoGame.visibility = TextView.VISIBLE
            } else {
                txtNoGame.visibility = TextView.INVISIBLE
                originalVaultGames = games.map { game ->
                    VaultGamesModel(
                        imgLink = game.imgLink ?: "",
                        gameName = game.gameName ?: "No game",
                        yourRating = game.yourRating ?: 0.0,
                        startDate = game.startDate ?: "None",
                        endDate = game.endDate ?: "None"
                    )
                }
                vaultGamesAdapter = VaultGamesAdapter(originalVaultGames, vaultViewModel)
                recyclerView.adapter = vaultGamesAdapter
                vaultGamesAdapter.slideDeletion(recyclerView)
            }
        }
    }

    //Change the item in the recycler, leave only the game with the name in the search bar
    private fun filterGames(query: String?) {
        val filteredGames = if (query.isNullOrBlank()) {
            originalVaultGames
        } else {
            originalVaultGames.filter { game ->
                game.gameName.contains(query, ignoreCase = true)
            }
        }
        vaultGamesAdapter.updateData(filteredGames)
    }
}