package it.unibo.gamevault.ui

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import it.unibo.gamevault.R

class SettingsActivity : AppCompatActivity() {
    private lateinit var btnCancel: Button
    private lateinit var btnSave: Button
    private lateinit var btnSelectImg: Button
    private lateinit var imagePreview: ImageView
    private lateinit var btnRawg: Button

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
                galleryLauncher.launch("image/*")
            }
        }

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