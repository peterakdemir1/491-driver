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


class DeliveryActivity: AppCompatActivity() {
    private lateinit var itemImage: ImageView
    private lateinit var itemTitle: TextView
    private lateinit var itemLocationTwo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deliver)

        itemImage = findViewById(R.id.itemImage)
        itemTitle = findViewById(R.id.itemText)
        itemLocationTwo = findViewById(R.id.locationText)

        // For when API is enabled
//        val item = intent.getSerializableExtra(ITEM_EXTRA) as Item
//
//        itemTitle.text = item.itemTitle
//        val locationTwo = "Location: " + item.pickupLocation
//        itemLocationTwo.text = locationOne
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

        val confirmPickUpBtn = findViewById<Button>(R.id.confirmDeliveryButton)
        confirmPickUpBtn.setOnClickListener {

            // Insert API stuff to confirm delivery

            val intent = Intent(this, WaitingActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}