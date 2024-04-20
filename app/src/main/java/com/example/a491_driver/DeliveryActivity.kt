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
    private lateinit var itemLocationOne: TextView
    private lateinit var itemLocationTwo: TextView
    lateinit var sharedpreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deliver)

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        val editor = sharedpreferences.edit()

        itemImage = findViewById(R.id.itemImage)
        itemTitle = findViewById(R.id.itemText)
        itemLocationOne = findViewById(R.id.sourceText)
        itemLocationTwo = findViewById(R.id.locationText)

//        val delivery = intent.getSerializableExtra(DELIVERY_EXTRA2) as Delivery

        val locationOne = "Retrieved from: " + sharedpreferences.getString("itemSource", null)
        itemLocationOne.text = locationOne
        itemTitle.text = sharedpreferences.getString("itemTitle", null)
        val locationTwo = "Delivering to: " + sharedpreferences.getString("itemDestination", null)
        itemLocationTwo.text = locationTwo


        Glide.with(this)
            .load(sharedpreferences.getString("itemImage", null))
//            .load(ContextCompat.getDrawable(this, R.drawable.drill_test))
            .into(itemImage)



        val googleMapsBtn = findViewById<Button>(R.id.directionsButton)

        googleMapsBtn.setOnClickListener {
            val location = sharedpreferences.getString("itemDestination", null)
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q=$location")
            )
            startActivity(intent)
        }

        val confirmDeliveryBtn = findViewById<Button>(R.id.confirmDeliveryButton)
        confirmDeliveryBtn.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                markDelivered()
            }
        }


    }
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity() // Finish all activities in the task associated with this activity
    }

    suspend fun markDelivered(){
        val apiService = RetrofitClient.instance.create(APIService::class.java)

        val itemRentalId = sharedpreferences.getInt("itemRentalId", -1)
        val itemReturnId = sharedpreferences.getInt("itemReturnId", -1)
        val itemListingId = sharedpreferences.getInt("itemListingId", -1)
        try {
            if (itemRentalId != -1) {
                apiService.markRentalDelivered(itemRentalId)
            } else if (itemReturnId != -1) {
                apiService.markReturnDelivered(itemReturnId)
            }
            try {
                apiService.makeListingAvailable(itemListingId.toString())
                sharedpreferences.getString("key", null)?.let { ItemFetcher().removeDelivery(it) }

                sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
                val editor = sharedpreferences.edit()

                editor.remove("delivering")
                editor.putString("itemTitle", null)
                editor.putString("itemSource", null)
                editor.putString("itemDestination", null)
                editor.putString("itemTip", null)
                editor.putString("itemImage", null)
                editor.putInt("itemRentalId", -1)
                editor.putInt("itemReturnId", -1)
                editor.putInt("itemListingId", -1)
                editor.putString("key", null)
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