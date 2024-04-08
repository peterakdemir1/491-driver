package com.example.a491_driver

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

const val DELIVERY_EXTRA1 = "DELIVERY_EXTRA1"
class TakeDeliveryActivity : AppCompatActivity() {
    private lateinit var itemImage: ImageView
    private lateinit var itemTitle: TextView
    private lateinit var itemLocationOne: TextView
    private lateinit var itemLocationTwo: TextView
    private lateinit var itemPayout: TextView
    lateinit var sharedpreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_delivery)

        itemImage = findViewById(R.id.itemImage)
        itemTitle = findViewById(R.id.itemTitle)
        itemLocationOne = findViewById(R.id.locationOneText)
        itemLocationTwo = findViewById(R.id.locationTwoText)
        itemPayout = findViewById(R.id.payoutText)


        val delivery = intent.getSerializableExtra(DELIVERY_EXTRA) as Delivery


        itemTitle.text = delivery.delivery_title
        val locationOne = "Location 1: " + delivery.pickup_location
        itemLocationOne.text = locationOne
        val locationTwo = "Location 2: " + delivery.deliver_location
        itemLocationTwo.text = locationTwo
        val payout = "Payout: $" + delivery.payment
        itemPayout.text = payout


        Glide.with(this)
            .load(delivery.img_url)
            .into(itemImage)


        val acceptBtn = findViewById<Button>(R.id.acceptButton)
        val denyBtn = findViewById<Button>(R.id.denyButton)

        acceptBtn.setOnClickListener {
            // Insert API Stuff to say item is accepted
            // updating the item's "accepted" value to true
            delivery.key?.let { it1 -> ItemFetcher().updateAccepted(it1) }
            GlobalScope.launch(Dispatchers.Main) {
                attachDriver(delivery)
            }
        }

        denyBtn.setOnClickListener {
            val intent = Intent(this, WaitingActivity::class.java)
            startActivity(intent)
            finish()
        }

    }


    suspend fun attachDriver(delivery: Delivery){
        val apiService = RetrofitClient.instance.create(APIService::class.java)
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        try {
            // attach driver
            val driver_id = DriverId(sharedpreferences.getInt(getString(R.string.driver_id_key), -1))
            if (delivery.rental_id != null) {
                val rental_id = delivery.rental_id
                apiService.attachDriverToRental(rental_id, driver_id)
            } else if (delivery.return_id != null) {
                val return_id = delivery.return_id
                apiService.attachDriverToReturn(return_id, driver_id)
            }

            // go to pick up activity
            val editor = sharedpreferences.edit()
            editor.putString("picking up", "item")
            editor.apply()
            val intent = Intent(this, PickUpActivity::class.java)
            intent.putExtra(DELIVERY_EXTRA1, delivery)
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            Log.e("TakeDeliveryActivity", "Could not attach driver to delivery")
            Log.e("TakeDeliveryActivity", "Error:  ${e.message}")
            Toast.makeText(this, "Couldn't Accept", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, WaitingActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

class DriverId(val driver_id: Int)