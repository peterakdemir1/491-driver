package com.example.a491_driver

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide

const val DELIVERY_EXTRA2 = "ITEM_EXTRA2"
class PickUpActivity: AppCompatActivity() {
    private lateinit var itemImage: ImageView
    private lateinit var itemTitle: TextView
    private lateinit var itemLocationOne: TextView
    lateinit var sharedpreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pickup)

        itemImage = findViewById(R.id.itemImage)
        itemTitle = findViewById(R.id.itemText)
        itemLocationOne = findViewById(R.id.locationText)

        // For when API is enabled
        val delivery = intent.getSerializableExtra(DELIVERY_EXTRA) as Delivery

        itemTitle.text = delivery.delivery_title
        val locationOne = "Location: " + delivery.pickup_location
        itemLocationOne.text = locationOne
//
        Glide.with(this)
            .load(delivery.imageUrl)
//            .load(ContextCompat.getDrawable(this, R.drawable.drill_test))
            .into(itemImage)


        val googleMapsBtn = findViewById<Button>(R.id.directionsButton)

        googleMapsBtn.setOnClickListener {
            val location = delivery.pickup_location
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q=$location")
            )
            startActivity(intent)
        }

        val confirmPickUpBtn = findViewById<Button>(R.id.confirmPickupButton)
        confirmPickUpBtn.setOnClickListener {

            // Insert API stuff to confirm pickup

            sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
            val editor = sharedpreferences.edit()
            editor.remove("picking up")
            editor.putString("delivering", "item")
            editor.apply()

            val intent = Intent(this, DeliveryActivity::class.java)
            intent.putExtra(DELIVERY_EXTRA2, delivery)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity() // Finish all activities in the task associated with this activity
    }
}