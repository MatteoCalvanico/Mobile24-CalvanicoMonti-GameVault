package it.unibo.gamevault.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import it.unibo.gamevault.R

class VaultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vault_recycler)
    }
}