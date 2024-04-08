package com.example.a491_driver

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class WaitingActivity : AppCompatActivity() {
    lateinit var sharedpreferences: SharedPreferences
    val deliveries = mutableListOf<Delivery>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waiting)

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

        val waitingText = findViewById<TextView>(R.id.waitingText)
        val availableText = findViewById<TextView>(R.id.availableText)
        val logoutBtn = findViewById<ImageButton>(R.id.logoutButton)
        logoutBtn.setOnClickListener {
            // remove username from shared preferences, send to login screen
            val editor = sharedpreferences.edit()
            editor.clear()
            editor.apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // init recycler view, set up fetcher
        val recycler = findViewById<RecyclerView>(R.id.recycleView)
        recycler.layoutManager = GridLayoutManager(this, 1)
        val itemAdapter = ItemAdapter(deliveries, this)
        recycler.adapter = itemAdapter

        val fetcher = ItemFetcher()
        lifecycleScope.launch {
            fetcher.getCurrentDeliveries().collect {
                deliveries.clear()
                deliveries.addAll(it)
                itemAdapter.notifyDataSetChanged()
                if (deliveries.size > 0) {
                    waitingText.visibility = TextView.INVISIBLE
                    availableText.visibility = TextView.VISIBLE
                    recycler.visibility = RecyclerView.VISIBLE
                } else {
                    waitingText.visibility = TextView.VISIBLE
                    availableText.visibility = TextView.INVISIBLE
                    recycler.visibility = RecyclerView.INVISIBLE
                }
            }
        }

        // remove once api trigger when a delivery is ready is implemented
//        val testBtn = findViewById<Button>(R.id.testButton)
//        testBtn.setOnClickListener {
//            startActivity(Intent(this, TakeDeliveryActivity::class.java))
//            finish()
//        }
    }
}