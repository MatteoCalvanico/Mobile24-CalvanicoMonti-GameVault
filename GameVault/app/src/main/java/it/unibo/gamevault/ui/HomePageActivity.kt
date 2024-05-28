package it.unibo.gamevault.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import it.unibo.gamevault.R
import it.unibo.gamevault.data.local.Repository.AppRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomePageActivity : AppCompatActivity() {

    private lateinit var btnVault: ImageButton
    private lateinit var btnSetting: Button
    private lateinit var searchBarIcon: ImageButton

    private lateinit var imgPfp: ImageView
    private lateinit var psLogo: ImageButton
    private lateinit var steamLogo: ImageButton
    private lateinit var xboxLogo: ImageButton

    private lateinit var favoritePoster1: ImageButton
    private lateinit var favoritePoster2: ImageButton
    private lateinit var favoritePoster3: ImageButton
    private lateinit var favoritePoster4: ImageButton

    private lateinit var repository: AppRepository
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        btnVault = findViewById(R.id.btnVault)
        btnSetting = findViewById(R.id.btnSetting)
        searchBarIcon = findViewById(R.id.search_bar)

        imgPfp = findViewById(R.id.imgPfp)
        psLogo = findViewById(R.id.psLogo)
        steamLogo = findViewById(R.id.steamLogo)
        xboxLogo = findViewById(R.id.xboxLogo)

        favoritePoster1 = findViewById(R.id.favoritePoster1)
        favoritePoster2 = findViewById(R.id.favoritePoster2)
        favoritePoster3 = findViewById(R.id.favoritePoster3)
        favoritePoster4 = findViewById(R.id.favoritePoster4)

        repository = (application as it.unibo.gamevault.Application).repository


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

        //Load user data
        loadUserData()
    }

    private fun loadUserData() {
        CoroutineScope(Dispatchers.IO).launch {
            val user = repository.getUserById(0)
            val gameOne = user?.favouriteOne?.let { repository.getGameBySlug(it) }
            val gameTwo = user?.favouriteTwo?.let { repository.getGameBySlug(it) }
            val gameThree = user?.favouriteThree?.let { repository.getGameBySlug(it) }
            val gameFour = user?.favouriteFour?.let { repository.getGameBySlug(it) }

            withContext(Dispatchers.Main) {
                if (user != null) {
                    //Load pfp from local storage
                    user.profileImage?.let {
                        imageUri = Uri.parse(it)
                        Glide.with(this@HomePageActivity)
                            .load(imageUri)
                            .into(imgPfp)
                    }

                    //Link
                    user.PSNLink?.let {
                        psLogo.setOnClickListener {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.toString()))
                            startActivity(intent)
                        }
                    }
                    user.steamLink?.let {
                        steamLogo.setOnClickListener {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.toString()))
                            startActivity(intent)
                        }
                    }
                    user.XboxLink?.let {
                        xboxLogo.setOnClickListener {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.toString()))
                            startActivity(intent)
                        }
                    }

                    //Games
                    gameOne?.imgLink?.let { Glide.with(this@HomePageActivity).load(it).into(favoritePoster1) }
                    gameTwo?.imgLink?.let { Glide.with(this@HomePageActivity).load(it).into(favoritePoster2) }
                    gameThree?.imgLink?.let { Glide.with(this@HomePageActivity).load(it).into(favoritePoster3) }
                    gameFour?.imgLink?.let { Glide.with(this@HomePageActivity).load(it).into(favoritePoster4) }

                } else { //If there isn't the user (first time the user open the app)
                    Toast.makeText(this@HomePageActivity, "Welcome, go to the settings to create your profile", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}