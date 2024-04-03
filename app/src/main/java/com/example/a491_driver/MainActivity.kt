package com.example.a491_driver

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageButton

const val SHARED_PREFS = "SHARED_PREFS"
class MainActivity : AppCompatActivity() {

    lateinit var sharedpreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_driving)

        val API_KEY = "2K3xFgG1BbcwRzw2gQSuFVPitxuh75dz"


//        val mapWebView: WebView = findViewById(R.id.mapWebView)
//        mapWebView.settings.javaScriptEnabled = true
//        mapWebView.loadUrl("file:///android_asset/map.html")

        // Check if user is logged in, send to login if not
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        val username = sharedpreferences.getString("username", null)
        val userId = sharedpreferences.getInt("user id", -1)
        if (username == null) {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        val startDrivingBtn = findViewById<Button>(R.id.deliverButton)
        startDrivingBtn.setOnClickListener {
            val intent = Intent(this, WaitingActivity::class.java)
            startActivity(intent)
        }

        val logoutBtn = findViewById<ImageButton>(R.id.logoutButton)
        logoutBtn.setOnClickListener {
            // remove username from shared preferences, send to login screen
            val editor = sharedpreferences.edit()
            editor.clear()
            editor.apply()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}