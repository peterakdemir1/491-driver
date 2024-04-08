package com.example.a491_driver

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


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

        val delivery = intent.getSerializableExtra(DELIVERY_EXTRA2) as Delivery

        itemTitle.text = delivery.delivery_title
        val locationTwo = "Location: " + delivery.pickup_location
        itemLocationTwo.text = locationTwo


        Glide.with(this)
            .load(delivery.img_url)
//            .load(ContextCompat.getDrawable(this, R.drawable.drill_test))
            .into(itemImage)



        val googleMapsBtn = findViewById<Button>(R.id.directionsButton)

        googleMapsBtn.setOnClickListener {
            val location = delivery.deliver_location // replace this with item.pickupLocation
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q=$location")
            )
            startActivity(intent)
        }

        val confirmDeliveryBtn = findViewById<Button>(R.id.confirmDeliveryButton)
        confirmDeliveryBtn.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                markDelivered(delivery)
            }
        }


    }
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity() // Finish all activities in the task associated with this activity
    }

    suspend fun markDelivered(delivery: Delivery){
        val apiService = RetrofitClient.instance.create(APIService::class.java)

        try {
            if (delivery.rental_id != null) {
                apiService.markRentalDelivered(delivery.rental_id)
            } else if (delivery.return_id != null) {
                apiService.markReturnDelivered(delivery.return_id)
            }
            try {
                apiService.makeListingAvailable(delivery.listing_id.toString())

                sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
                val editor = sharedpreferences.edit()
                editor.remove("delivering")
                editor.apply()

                val intent = Intent(this, WaitingActivity::class.java)
                startActivity(intent)
                finish()
            } catch (e: Exception) {
                Log.e("DeliveryActivity", "Could not mark item as available")
                Log.e("DeliveryActivity", "Error: ${e.message}")
            }

        } catch (e: Exception) {
            Log.e("DeliveryActivity", "Could not mark item as delivered")
            Log.e("DeliveryActivity", "Error: ${e.message}")
        }
    }

}