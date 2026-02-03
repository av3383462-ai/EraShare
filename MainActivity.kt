package com.example.gestureshare

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    
    private val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.NEARBY_WIFI_DEVICES
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // UI Layout set karna
        val textView = TextView(this).apply {
            text = "Era Share: Initializing..."
            textSize = 20f
            gravity = android.view.Gravity.CENTER
        }
        setContentView(textView)

        // Permissions mangna
        if (allPermissionsGranted()) {
            startAppLogic(textView)
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, 100)
        }
    }

    private fun startAppLogic(view: TextView) {
        try {
            // Yahan hum check kar rahe hain ki model file hai ya nahi
            val assets = assets.list("")
            if (assets?.contains("hand_landmarker.task") == true) {
                view.text = "Hand Landmarker Found!\nShow ✊ to Send | ✋ to Receive"
            } else {
                view.text = "Error: hand_landmarker.task not found in assets!"
            }
        } catch (e: Exception) {
            view.text = "Crash Prevented: ${e.message}"
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
}
