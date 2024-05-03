package it.unibo.gamevault.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import it.unibo.gamevault.R

class VaultGamesAdapter : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vault_item)
    }
}