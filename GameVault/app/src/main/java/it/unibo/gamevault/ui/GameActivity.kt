package it.unibo.gamevault.ui

import android.content.res.ColorStateList
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import it.unibo.gamevault.R
import it.unibo.gamevault.ui.model.Game

class GameActivity : AppCompatActivity() {

    private lateinit var btnAdd: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        btnAdd = findViewById(R.id.btnSave)
        val addDialog = AddGameDialog()

        //Resolve version problem
        val game = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("game", Game::class.java)
        } else {
            intent.getParcelableExtra("game")
        }

        Log.d("GameActivity", "Received game: $game")

        game?.let {
            val gameTitle = findViewById<TextView>(R.id.gameTitle)
            gameTitle.isSelected = true //Need this to make marquee work
            gameTitle.text = game.name

            val gamePoster = findViewById<ImageView>(R.id.gamePoster)
            Glide.with(gamePoster)
                .load(game.backgroundImage)
                .placeholder(R.drawable.poster_placeholder)
                .into(gamePoster)

            val gamePlatform = findViewById<TextView>(R.id.gamePlatform)
            var allPlatform = ""
            for(p in game.platforms!!) {
                allPlatform += p.getPlatformFormat()
            }
            gamePlatform.text = allPlatform

            val gameRelease = findViewById<TextView>(R.id.gameRelease)
            gameRelease.text = (if(game.tba == true) { "TBA" } else { game.released })

            val gameAbout = findViewById<com.colormoon.readmoretextview.ReadMoreTextView>(R.id.gameAbout)
            gameAbout.text = game.getDescriptionFormat()
            gameAbout.setCollapsedText("...Read more")
            gameAbout.setCollapsedTextColor(R.color.white)
            gameAbout.setExpandedText("  Read Less")
            gameAbout.setExpandedTextColor(R.color.white)
            gameAbout.setTrimLines(4)

            val gameRate = findViewById<TextView>(R.id.gameRate)
            gameRate.text = game.metacritic?.toString() ?: "N/A"
            gameRate.backgroundTintList = when {
                game.metacritic == null -> ColorStateList.valueOf(getColor(R.color.lightGray))
                game.metacritic >= 70 -> ColorStateList.valueOf(getColor(R.color.positiveRating))
                game.metacritic >= 50 -> ColorStateList.valueOf(getColor(R.color.midRating))
                else-> ColorStateList.valueOf(getColor(R.color.negativeRating))
            }
        }

        btnAdd.setOnClickListener{
            addDialog.show(supportFragmentManager, "Show add information")
        }
    }
}