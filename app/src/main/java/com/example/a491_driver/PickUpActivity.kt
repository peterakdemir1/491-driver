package com.example.a491_driver

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide

const val ITEM_EXTRA2 = "ITEM_EXTRA2"
class PickUpActivity: AppCompatActivity() {
    private lateinit var itemImage: ImageView
    private lateinit var itemTitle: TextView
    private lateinit var itemLocationOne: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pickup)

        itemImage = findViewById(R.id.itemImage)
        itemTitle = findViewById(R.id.itemText)
        itemLocationOne = findViewById(R.id.locationText)

        // For when API is enabled
//        val item = intent.getSerializableExtra(ITEM_EXTRA) as Item
//
//        itemTitle.text = item.itemTitle
//        val locationOne = "Location: " + item.pickupLocation
//        itemLocationOne.text = locationOne
//
        Glide.with(this)
//            .load(item.itemImageUrl)
            .load(ContextCompat.getDrawable(this, R.drawable.drill_test))
            .into(itemImage)


        val googleMapsBtn = findViewById<Button>(R.id.directionsButton)

        googleMapsBtn.setOnClickListener {
            val location = "154 Summit Street, Newark, NJ 07102" // replace this with item.pickupLocation
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q=$location")
            )
            startActivity(intent)
        }

        val confirmPickUpBtn = findViewById<Button>(R.id.confirmPickupButton)
        confirmPickUpBtn.setOnClickListener {

            // Insert API stuff to confirm pickup

            val intent = Intent(this, DeliveryActivity::class.java)
//            intent.putExtra(ITEM_EXTRA2, item)
            startActivity(intent)
            finish()
        }


    }
}