package com.example.a491_driver

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide

//const val ITEM_EXTRA = "ITEM_EXTRA"
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

        // For when API is enabled
        val delivery = intent.getSerializableExtra(DELIVERY_EXTRA) as Delivery
//
        itemTitle.text = delivery.name
        val locationOne = "Location 1: " + delivery.source
        itemLocationOne.text = locationOne
        val locationTwo = "Location 2: " + delivery.destination
        itemLocationTwo.text = locationTwo
        val payout = "Payout: $" + delivery.tip
        itemPayout.text = payout


        Glide.with(this)
            .load(delivery.imageUrl)
//            .load(ContextCompat.getDrawable(this, R.drawable.drill_test))
            .into(itemImage)


        val acceptBtn = findViewById<Button>(R.id.acceptButton)
        val denyBtn = findViewById<Button>(R.id.denyButton)

        acceptBtn.setOnClickListener {

            // Insert API Stuff to say item is accepted

            sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
            val editor = sharedpreferences.edit()
            editor.putString("picking up", "item")
            editor.apply()
            val intent = Intent(this, PickUpActivity::class.java)
            intent.putExtra(DELIVERY_EXTRA, delivery)
            startActivity(intent)
            finish()
        }

        denyBtn.setOnClickListener {

            // Insert API stuff to say item was denied

            val intent = Intent(this, WaitingActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}