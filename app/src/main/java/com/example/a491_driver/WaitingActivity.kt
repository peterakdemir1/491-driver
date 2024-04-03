package com.example.a491_driver

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class WaitingActivity : AppCompatActivity() {
    lateinit var sharedpreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waiting)

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        val logoutBtn = findViewById<ImageButton>(R.id.logoutButton)
        logoutBtn.setOnClickListener {
            // remove username from shared preferences, send to login screen
            val editor = sharedpreferences.edit()
            editor.clear()
            editor.apply()
            startActivity(Intent(this, LoginActivity::class.java))
        }

        // remove once api trigger when a delivery is ready is implemented
        val testBtn = findViewById<Button>(R.id.testButton)
        testBtn.setOnClickListener {
            startActivity(Intent(this, TakeDeliveryActivity::class.java))
        }
    }
}