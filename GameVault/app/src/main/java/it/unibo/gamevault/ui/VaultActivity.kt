package it.unibo.gamevault.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.unibo.gamevault.R
import it.unibo.gamevault.ui.adapter.VaultGamesAdapter
import it.unibo.gamevault.ui.model.VaultGamesModel
import java.time.LocalDate
import java.util.Date

class VaultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vault_recycler)

        val txtNoGame: TextView = findViewById(R.id.txtNoGame)

        /**
         * Prendere i dati dei giochi salvati dal database e salvarli in lista
         */
        val vaultGames = listOf<VaultGamesModel>(VaultGamesModel("null", "Prova", 4.0, "00/00/2020", "00/00/2020")) //TODO: gestire il take dei dati dal db

        val vaultGamesAdapter = VaultGamesAdapter(vaultGames)

        //If the vault is empty (no games added) change the textView visibility
        if (vaultGames.isEmpty()){
            txtNoGame.visibility = TextView.VISIBLE
        } else {
            txtNoGame.visibility = TextView.INVISIBLE

            val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = vaultGamesAdapter
        }
    }
}