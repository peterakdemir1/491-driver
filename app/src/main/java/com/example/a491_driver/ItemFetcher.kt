package com.example.a491_driver

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ItemFetcher() {
    fun getCurrentDeliveries(): Flow<List<Delivery>> = callbackFlow {
        val databaseReference = FirebaseDatabase.getInstance().getReference("messages")
        val listener = databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val list = snapshot.children.toList()
                    val newList = mutableListOf<Delivery>()

                    for (item in list) {
                        val key = item.key.toString()
                        val destinationAddress = item.child("destination_address").getValue(String::class.java)
                        val imageUrl = item.child("image_url").getValue(String::class.java)
                        val itemName = item.child("item_name").getValue(String::class.java)
                        val sourceAddress = item.child("source_address").getValue(String::class.java)
                        val tipAmount = item.child("tip_amount").getValue(String::class.java)
                        val rentalID = item.child("rental_id").getValue(Int::class.java).toString()
                        val returnID = item.child("return_id").getValue(Int::class.java).toString()

                        val delivery = Delivery(
                            key = key,
                            name = itemName,
                            source = sourceAddress,
                            destination = destinationAddress,
                            imageUrl = imageUrl,
                            tip = tipAmount,
                            rentalId = rentalID,
                            returnId = returnID
                        )

                        newList.add(delivery)
                    }
                    trySend(newList).isSuccess
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("DatabaseError", error.toException())
            }
        })
        awaitClose {
            databaseReference.removeEventListener(listener)
        }
    }

    suspend fun removeDelivery(key: String) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("messages")
        val itemReference = databaseReference.child(key)
        itemReference.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Delete Item", "Item successfully deleted.")
                // Handle successful deletion, e.g., update UI or notify user
            } else {
                Log.d("Delete Item", "Failed to delete item.")
                // Handle failure, e.g., show error message
            }
        }


    }
}