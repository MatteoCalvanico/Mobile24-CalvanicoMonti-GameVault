package it.unibo.gamevault.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import it.unibo.gamevault.R

class HomePageActivity : AppCompatActivity() {

    private lateinit var btnVault: ImageButton
    private lateinit var btnSetting: Button
    private lateinit var searchBarIcon: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        btnVault = findViewById(R.id.btnVault)
        btnSetting = findViewById(R.id.btnSetting)
        searchBarIcon = findViewById(R.id.search_bar)

        // Click on vault imageButton
        btnVault.setOnClickListener{
            val intent = Intent(this, VaultActivity::class.java)
            startActivity(intent)
        }

        //Click on settings button
        btnSetting.setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        //Click on search icon
        searchBarIcon.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
    }
}
