package it.unibo.gamevault.ui

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteConstraintException
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.webkit.URLUtil.isNetworkUrl
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import it.unibo.gamevault.Application
import it.unibo.gamevault.R
import it.unibo.gamevault.data.local.Repository.AppRepository
import it.unibo.gamevault.data.local.entity.GameLocalModel
import it.unibo.gamevault.data.local.entity.UserLocalModel
import it.unibo.gamevault.ui.fragment.AddFavoriteDialog
import it.unibo.gamevault.ui.viewModel.SharedViewModel
import it.unibo.gamevault.ui.viewModel.SharedViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsActivity : AppCompatActivity() {
    private lateinit var btnCancel: Button
    private lateinit var btnSave: Button
    private lateinit var btnSelectImg: Button
    private lateinit var imagePreview: ImageView
    private lateinit var btnRawg: Button
    private lateinit var editPSN: EditText
    private lateinit var editSteam: EditText
    private lateinit var editXbox: EditText
    private lateinit var editRawg: EditText
    private lateinit var favorite1: ImageButton
    private lateinit var favorite2: ImageButton
    private lateinit var favorite3: ImageButton
    private lateinit var favorite4: ImageButton

    private lateinit var repository: AppRepository
    private var userInfo: UserLocalModel? = null

    private var favoriteGame1: GameLocalModel? = null
    private var favoriteGame2: GameLocalModel? = null
    private var favoriteGame3: GameLocalModel? = null
    private var favoriteGame4: GameLocalModel? = null
    private val sharedViewModel: SharedViewModel by viewModels {
        val repository = (application as Application).repository
        SharedViewModelFactory(repository)
    }

    private var imageUri: Uri? = null
    private var galleryUri: Uri? = null
    private var PERMISSION_REQUEST_CODE = 101

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        galleryUri = it
        try {
            imagePreview.setImageURI(galleryUri)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        btnCancel = findViewById(R.id.btnCancel)
        btnSave = findViewById(R.id.btnSave)
        btnSelectImg = findViewById(R.id.btnSelectImage)
        imagePreview = findViewById(R.id.previewImage)
        btnRawg = findViewById(R.id.btnRawg)
        editPSN = findViewById(R.id.editPSN)
        editSteam = findViewById(R.id.editSteam)
        editXbox = findViewById(R.id.editXbox)
        editRawg = findViewById(R.id.editRawg)
        favorite1 = findViewById(R.id.favoritePoster1)
        favorite2 = findViewById(R.id.favoritePoster2)
        favorite3 = findViewById(R.id.favoritePoster3)
        favorite4 = findViewById(R.id.favoritePoster4)

        repository = (application as Application).repository
        loadUserData()

        //Click on "go to Rawg"
        btnRawg.setOnClickListener {
            val url = Uri.parse("https://rawg.io/apidocs")
            val intent = Intent(Intent.ACTION_VIEW, url)

            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                // Handle case where no suitable browser is found
                Toast.makeText(this, "No browser found to open the URL", Toast.LENGTH_SHORT).show()
            }
        }

        //Click on select image
        btnSelectImg.setOnClickListener {
            if (checkAndRequestGalleryPermission()) {
                galleryLauncher.launch("image/*") //TODO: Gestire l'errore per immagini troppo grandi
            }
        }

        //Click to add favorite 1
        favorite1.setOnClickListener {
            showFavoriteDialogAndObserveResult(favorite1)
        }

        //Click to add favorite 2
        favorite2.setOnClickListener {
            showFavoriteDialogAndObserveResult(favorite2)
        }

        //Click to add favorite 3
        favorite3.setOnClickListener {
            showFavoriteDialogAndObserveResult(favorite3)
        }

        //Click to add favorite 4
        favorite4.setOnClickListener {
            showFavoriteDialogAndObserveResult(favorite4)
        }

        //Click on save settings button
        btnSave.setOnClickListener{
            saveUserInfo()
        }

        //Click on cancel setting button (go back to the home)
        btnCancel.setOnClickListener{
            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
        }
    }

    //Load the old profile info
    private fun loadUserData() {
        CoroutineScope(Dispatchers.IO).launch {
            val user = repository.getUserById(0)
            userInfo = user

            withContext(Dispatchers.Main){
                if (user != null) {
                    Toast.makeText(this@SettingsActivity, "Profile found, info loaded", Toast.LENGTH_SHORT).show()
                    editPSN.setText(user.PSNLink)
                    editSteam.setText(user.steamLink)
                    editXbox.setText(user.XboxLink)
                    editRawg.setText(user.APIKey)

                    //Load favorites images
                    withContext(Dispatchers.Main) {
                        user.favouriteOne?.let { gameSlug ->
                            loadFavoriteGameImage(gameSlug, favorite1)
                        }
                        user.favouriteTwo?.let { gameSlug ->
                            loadFavoriteGameImage(gameSlug, favorite2)
                        }
                        user.favouriteThree?.let { gameSlug ->
                            loadFavoriteGameImage(gameSlug, favorite3)
                        }
                        user.favouriteFour?.let { gameSlug ->
                            loadFavoriteGameImage(gameSlug, favorite4)
                        }
                    }

                    //Load pfp
                    user.profileImage?.let {
                        imageUri = Uri.parse(it)
                        Glide.with(this@SettingsActivity).load(imageUri).into(imagePreview)
                    }
                }else {
                    Toast.makeText(this@SettingsActivity, "No profile found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //Save the new profile info
    private fun saveUserInfo() {
        val psnLink = editPSN.text.toString()
        val steamLink = editSteam.text.toString()
        val xboxLink = editXbox.text.toString()

        val isPsnValid = isNetworkUrl(psnLink)
        val isSteamValid = isNetworkUrl(steamLink)
        val isXboxValid = isNetworkUrl(xboxLink)

        // Check with url isn't valid and make a Toast
        if (!isPsnValid || !isSteamValid || !isXboxValid) {
            val invalidLinks = mutableListOf<String>()
            if (!isPsnValid) invalidLinks.add("PSN")
            if (!isSteamValid) invalidLinks.add("Steam")
            if (!isXboxValid) invalidLinks.add("Xbox")

            val errorMessage = "This url are invalid: ${invalidLinks.joinToString(", ")}. Set default one."
            Toast.makeText(this@SettingsActivity, errorMessage, Toast.LENGTH_SHORT).show()
        }

        val user = UserLocalModel(
            0,
            galleryUri?.toString() ?: userInfo?.profileImage,
            if (isPsnValid) psnLink else "https://www.playstation.com/".ifEmpty { userInfo?.PSNLink },
            if (isSteamValid) steamLink else "https://store.steampowered.com/".ifEmpty { userInfo?.steamLink },
            if (isXboxValid) xboxLink else "https://www.xbox.com/".ifEmpty { userInfo?.XboxLink },
            editRawg.text.toString().ifEmpty { userInfo?.APIKey },
            favoriteGame1?.slug,
            favoriteGame2?.slug,
            favoriteGame3?.slug,
            favoriteGame4?.slug
        )

        try {
            CoroutineScope(Dispatchers.IO).launch {
                if (userInfo == null) {
                    repository.insertUser(user)
                } else {
                    repository.updateUser(user)
                }
            }
            Toast.makeText(this@SettingsActivity, "Info saved", Toast.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException) {
            Toast.makeText(this@SettingsActivity, "SQL Error", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this@SettingsActivity, "Unexpected Error", Toast.LENGTH_SHORT).show()
        }
    }

    //Show AddFavoriteDialog and observe the result
    private fun showFavoriteDialogAndObserveResult(favoriteImageButton: ImageButton) {
        val favoriteDialog = AddFavoriteDialog()
        favoriteDialog.show(supportFragmentManager, "Show add favorite")

        //Take the game chosen thanks to the SharedViewModel
        sharedViewModel.gameResult.observe(this) { game ->

            if (game != null) {

                //Update the var
                when (favoriteImageButton) {
                    favorite1 -> favoriteGame1 = game
                    favorite2 -> favoriteGame2 = game
                    favorite3 -> favoriteGame3 = game
                    favorite4 -> favoriteGame4 = game
                }

                //Update poster
                Glide.with(this)
                    .load(game.imgLink)
                    .placeholder(R.drawable.poster_placeholder)
                    .into(favoriteImageButton)

                sharedViewModel.gameResult.removeObservers(this) //Remove the observer to avoid multiple updates
            } else { //Error or no game chosen
                sharedViewModel.error.observe(this) { error ->
                    Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                    sharedViewModel.error.removeObservers(this) //Remove error observer (same as before)
                }
            }
        }
    }

    //Search the game to obtain the image
    private fun loadFavoriteGameImage(gameSlug: String, imageView: ImageView) {
        CoroutineScope(Dispatchers.IO).launch {
            val game = repository.getGameBySlug(gameSlug) //Obtain game

            withContext(Dispatchers.Main) {
                game?.imgLink?.let { imgLink ->  //Load poster
                    Glide.with(this@SettingsActivity)
                        .load(imgLink)
                        .placeholder(R.drawable.poster_placeholder)
                        .into(imageView)
                }
            }
        }
    }


    // Check and Request Permission function
    private fun checkAndRequestGalleryPermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
            //Permission already granted
            true
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_MEDIA_IMAGES), PERMISSION_REQUEST_CODE)
            false
        }
    }

    // Permission denied dialog -- we go back to the home or to the app's settings
    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permission Denied")
            .setMessage("You have denied the permission, grant it in the app settings to select an image.")
            .setPositiveButton("Go to Settings") { _, _ ->
                // Open the app's settings page
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { _, _ ->
                // Go to the home page
                val intent = Intent(this, HomePageActivity::class.java)
                startActivity(intent)
            }
            .show()
    }

    // Handle Permission Result -- If the user doesn't accept show dialog
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    galleryLauncher.launch("image/*")
                } else {
                    showPermissionDeniedDialog()
                }
            }
        }
    }
}