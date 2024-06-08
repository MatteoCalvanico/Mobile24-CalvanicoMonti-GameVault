package it.unibo.gamevault.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ncorti.slidetoact.SlideToActView
import it.unibo.gamevault.R
import it.unibo.gamevault.data.local.Repository.AppRepository
import it.unibo.gamevault.data.local.entity.GameLocalModel
import it.unibo.gamevault.data.local.entity.UserLocalModel
import it.unibo.gamevault.ui.viewModel.HomePageViewModel
import it.unibo.gamevault.ui.viewModel.HomeViewModelFactory
class HomePageActivity : AppCompatActivity() {

    private lateinit var btnSwipeVault: SlideToActView
    private lateinit var btnSetting: Button
    private lateinit var searchBarIcon: ImageButton

    private lateinit var imgPfp: ImageView
    private lateinit var psLogo: ImageButton
    private lateinit var steamLogo: ImageButton
    private lateinit var xboxLogo: ImageButton

    private lateinit var meanRating: TextView
    private lateinit var totGame: TextView
    private lateinit var endGame: TextView

    private lateinit var favoritePoster1: ImageButton
    private lateinit var favoritePoster2: ImageButton
    private lateinit var favoritePoster3: ImageButton
    private lateinit var favoritePoster4: ImageButton

    private lateinit var repository: AppRepository
    private var imageUri: Uri? = null

    private lateinit var viewModel: HomePageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage_redux)

        enableEdgeToEdge()

        repository = (application as it.unibo.gamevault.Application).repository
        viewModel = ViewModelProvider(this, HomeViewModelFactory(repository))[HomePageViewModel::class.java]

        btnSwipeVault = findViewById(R.id.btnSwipeVault)
        btnSetting = findViewById(R.id.btnSetting)
        searchBarIcon = findViewById(R.id.search_bar)

        imgPfp = findViewById(R.id.imgPfp)
        psLogo = findViewById(R.id.psLogo)
        steamLogo = findViewById(R.id.steamLogo)
        xboxLogo = findViewById(R.id.xboxLogo)

        meanRating = findViewById(R.id.meanRateTxt)
        totGame = findViewById(R.id.totGameTxt)
        endGame = findViewById(R.id.endGameTxt)

        favoritePoster1 = findViewById(R.id.favoritePoster1)
        favoritePoster2 = findViewById(R.id.favoritePoster2)
        favoritePoster3 = findViewById(R.id.favoritePoster3)
        favoritePoster4 = findViewById(R.id.favoritePoster4)

        //Swipe on vault SlideToActView
        btnSwipeVault.onSlideCompleteListener = object : SlideToActView.OnSlideCompleteListener {
            override fun onSlideComplete(view: SlideToActView) {
                val intent = Intent(this@HomePageActivity, VaultActivity::class.java)
                startActivity(intent)
            }
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

        //Observe LiveData for UI updates
        viewModel.userData.observe(this) { user ->
            updateUserData(user)
        }
        viewModel.favoriteGames.observe(this) { games ->
            updateFav(games)
        }
        viewModel.meanRating.observe(this) {rating ->
            meanRating.text = String.format("%.1f", rating)
        }
        viewModel.totalGameCount.observe(this) {games ->
            totGame.text = games.toString()
        }
        viewModel.gameEnded.observe(this) {games ->
            endGame.text = games.toString()
        }
    }

    private fun updateUserData(user: UserLocalModel?){
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
                    openLink(user.PSNLink)
                }
            }
            user.steamLink?.let {
                steamLogo.setOnClickListener {
                    openLink(user.steamLink)
                }
            }
            user.XboxLink?.let {
                xboxLogo.setOnClickListener {
                    openLink(user.XboxLink)
                }
            }
        } else { //If there isn't the user (first time the user open the app)
            Toast.makeText(this@HomePageActivity, "Welcome, go to the settings to create your profile", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateFav( games: List<GameLocalModel>){
        games.getOrNull(0)?.imgLink?.let { Glide.with(this@HomePageActivity).load(it).into(favoritePoster1) }
        games.getOrNull(1)?.imgLink?.let { Glide.with(this@HomePageActivity).load(it).into(favoritePoster2) }
        games.getOrNull(2)?.imgLink?.let { Glide.with(this@HomePageActivity).load(it).into(favoritePoster3) }
        games.getOrNull(3)?.imgLink?.let { Glide.with(this@HomePageActivity).load(it).into(favoritePoster4) }
    }

    private fun openLink(link: String?){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse((link)))
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // Handle case where no suitable browser is found
            Toast.makeText(this@HomePageActivity, "No browser found to open the URL", Toast.LENGTH_SHORT).show()
        }
    }
}