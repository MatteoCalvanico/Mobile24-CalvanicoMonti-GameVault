package it.unibo.gamevault.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import it.unibo.gamevault.Manifest
import it.unibo.gamevault.databinding.ActivitySettingBinding
import it.unibo.gamevault.R

class SettingsActivity : AppCompatActivity() {
    private lateinit var btnCancel: Button
    private lateinit var btnSave: Button
    private lateinit var btnSelectImg: Button
    private lateinit var imagePreview: ImageView

    //TODO: Remember to save the uri in the app's database
    private var galleryUri: Uri? = null
    private var REQUEST_READ_MEDIA_IMAGES = 100

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

        //Click on select image
        btnSelectImg.setOnClickListener{
            galleryLauncher.launch("image/*")
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
}