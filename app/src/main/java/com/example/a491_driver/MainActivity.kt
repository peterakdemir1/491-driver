package com.example.a491_driver

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

const val SHARED_PREFS = "SHARED_PREFS"
class MainActivity : AppCompatActivity() {

    lateinit var sharedpreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_driving)

        // Check if user is logged in, send to login if not
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

        /*val editor = sharedpreferences.edit()
        editor.remove("username")
        editor.remove("picking up")
        editor.remove("delivering")
        editor.apply()*/

        val username = sharedpreferences.getString("username", null)
        val userId = sharedpreferences.getInt(getString(R.string.driver_id_key), -1)
        val pickUp = sharedpreferences.getString("picking up", null)
        val delivering = sharedpreferences.getString("delivering", null)
        val itemTitle = sharedpreferences.getString("itemTitle", null)
        val itemSource = sharedpreferences.getString("itemSource", null)
        val itemDestination = sharedpreferences.getString("itemDestination", null)
        val itemTip = sharedpreferences.getString("itemTip", null)
        val itemImage = sharedpreferences.getString("itemImage", null)
        val itemRentalId = sharedpreferences.getInt("itemRentalId", -1)
        val itemReturnId = sharedpreferences.getInt("itemReturnId", -1)
        val itemListingId = sharedpreferences.getInt("itemListingId", -1)
        val key = sharedpreferences.getString("key", null)

        if (username == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else if (pickUp != null) {
            startActivity(Intent(this, PickUpActivity::class.java))
        } else if (delivering != null) {
            startActivity(Intent(this, DeliveryActivity::class.java))
        }

        val usernameView = findViewById<TextView>(R.id.username)
        usernameView.text = username

        val startDrivingBtn = findViewById<Button>(R.id.deliverButton)
        startDrivingBtn.setOnClickListener {
            val intent = Intent(this, WaitingActivity::class.java)
            startActivity(intent)
            finish()
        }

        val logoutBtn = findViewById<ImageButton>(R.id.logoutButton)
        logoutBtn.setOnClickListener {
            // remove username from shared preferences, send to login screen
            val editor = sharedpreferences.edit()
            editor.clear()
            editor.apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}