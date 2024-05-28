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
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import it.unibo.gamevault.R
import it.unibo.gamevault.data.local.Repository.AppRepository
import it.unibo.gamevault.data.local.entity.UserLocalModel
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

    private lateinit var repository: AppRepository
    private var userInfo: UserLocalModel? = null

    //TODO: Remember to save the uri in the app's database
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

        repository = (application as it.unibo.gamevault.Application).repository
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
                galleryLauncher.launch("image/*") //TODO: Usare Gidle
            }
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
                    Glide.with(this@SettingsActivity)
                        .load(user.profileImage?.let { uri -> Uri.parse(uri) })
                        .into(imagePreview)
                }else {
                    Toast.makeText(this@SettingsActivity, "No profile found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun saveUserInfo() {
        val user = UserLocalModel(
            0,
            galleryUri?.toString() ?: userInfo?.profileImage.orEmpty(),
            editPSN.text.toString().ifEmpty { userInfo?.PSNLink.orEmpty() },
            editSteam.text.toString().ifEmpty { userInfo?.steamLink.orEmpty() },
            editXbox.text.toString().ifEmpty { userInfo?.XboxLink.orEmpty() },
            editRawg.text.toString().ifEmpty { userInfo?.APIKey.orEmpty() },
            null,
            null,
            null,
            null //TODO: gestione preferiti
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