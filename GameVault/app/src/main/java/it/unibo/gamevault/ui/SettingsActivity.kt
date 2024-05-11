package it.unibo.gamevault.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import it.unibo.gamevault.R

class SettingsActivity : AppCompatActivity() {

    private lateinit var btnCancel: Button
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        btnCancel = findViewById(R.id.btnCancel)
        btnSave = findViewById(R.id.btnSave)

        //Click on save settings button
        btnSave.setOnClickListener{
            //TODO
        }

        //Click on cancel setting button (go back to the home)
        btnCancel.setOnClickListener{
            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
        }
    }
}