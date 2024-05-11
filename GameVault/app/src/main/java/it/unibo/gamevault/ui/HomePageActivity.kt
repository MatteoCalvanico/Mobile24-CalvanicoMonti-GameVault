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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        btnVault = findViewById(R.id.btnVault)
        btnSetting = findViewById(R.id.btnSetting)

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
    }
}
