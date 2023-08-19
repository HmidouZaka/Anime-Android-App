package com.projectbyzakaria.views.ui.bottoms

import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable

import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.FileProvider

import com.google.android.material.bottomsheet.BottomSheetDialog
import com.projectbyzakaria.views.BuildConfig
import com.projectbyzakaria.views.databinding.ButtomShetBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class BottomSheat(context: Context, val drawable: Drawable?, val url: String) :
    BottomSheetDialog(context) {
    lateinit var binding: ButtomShetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ButtomShetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.share.setOnClickListener {
            sendImage()
        }
        binding.addBackround.setOnClickListener {
            addImageToBackground()
        }
        binding.downLoad.setOnClickListener {
            downLoadImage()
        }
    }

    fun downloadImage(imageUrl: String, imageName: String) {
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File(storageDir, imageName)
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadUri = Uri.parse(imageUrl)
        val request = DownloadManager.Request(downloadUri)
            .setTitle("Downloading $imageName")
            .setDescription("Downloading image")
            .setDestinationUri(Uri.fromFile(imageFile))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)
        downloadManager.enqueue(request)
        hide()
    }

    fun downLoadImage() {
        downloadImage(url, "my_image.jpg")
    }

    fun sendImage() {
        var intentSend = Intent(Intent.ACTION_SEND)
        intentSend.type = "text/plain"
        intentSend.putExtra(Intent.EXTRA_SUBJECT, "Check out the link of image")
        intentSend.putExtra(Intent.EXTRA_TEXT, url)
        context.startActivity(Intent.createChooser(intentSend, "Send Image Url"))
    }

    fun addImageToBackground() {
        if (drawable != null) {
            val bitmap = (drawable as BitmapDrawable).bitmap
            val wallaper = WallpaperManager.getInstance(context)
            object : Thread() {
                override fun run() {
                    super.run()
                    wallaper.setBitmap(bitmap)
                }
            }.start()
            Toast.makeText(context, "Wallpaper set successfully", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Failed to set wallpaper", Toast.LENGTH_SHORT).show()
        }
        hide()
    }


}