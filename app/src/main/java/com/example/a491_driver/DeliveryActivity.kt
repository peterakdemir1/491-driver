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


class DeliveryActivity: AppCompatActivity() {
    private lateinit var itemImage: ImageView
    private lateinit var itemTitle: TextView
    private lateinit var itemLocationTwo: TextView
    lateinit var sharedpreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deliver)

        itemImage = findViewById(R.id.itemImage)
        itemTitle = findViewById(R.id.itemText)
        itemLocationTwo = findViewById(R.id.locationText)

        // For when API is enabled
        val delivery = intent.getSerializableExtra(DELIVERY_EXTRA) as Delivery

        itemTitle.text = delivery.name
        val locationTwo = "Location: " + delivery.destination
        itemLocationTwo.text = locationTwo
//
        Glide.with(this)
            .load(delivery.imageUrl)
//            .load(ContextCompat.getDrawable(this, R.drawable.drill_test))
            .into(itemImage)



        val googleMapsBtn = findViewById<Button>(R.id.directionsButton)

        googleMapsBtn.setOnClickListener {
//            val location = "154 Summit Street, Newark, NJ 07102" // replace this with item.pickupLocation
            val location = delivery.destination
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q=$location")
            )
            startActivity(intent)
        }

        val confirmDeliveryBtn = findViewById<Button>(R.id.confirmDeliveryButton)
        confirmDeliveryBtn.setOnClickListener {

            // Insert API stuff to confirm delivery

            sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
            val editor = sharedpreferences.edit()
            editor.remove("delivering")
            editor.apply()

            // - api stuff is simply calling ItemFetcher.removeDelivery(delivery.key)
            delivery.key?.let { it1 -> ItemFetcher().removeDelivery(it1) }

            // then using retrofit to update delivered/returned in the database based on type
            if (delivery.type == "rental") {
                // TODO: Update DB rentals "delivered" flag to true using delivery.rentalId
            } else if (delivery.type == "return") {
                // TODO: Update DB rentals AND returns "returned" flag to true based on delivery.rentalId
            }

            val intent = Intent(this, WaitingActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity() // Finish all activities in the task associated with this activity
    }
}