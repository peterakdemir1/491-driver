package com.example.a491_driver

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ItemFetcher() {
    private lateinit var delivery: Delivery
    fun getCurrentDeliveries(): Flow<List<Delivery>> = callbackFlow {
        val databaseReference = FirebaseDatabase.getInstance().getReference("messages")
        val listener = databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val list = snapshot.children.toList()
                    val newList = mutableListOf<Delivery>()

                    for (item in list) {
                        if (item.child("accepted").getValue(Boolean::class.java) == false) {
                            val key = item.key.toString()
                            val destinationAddress = item.child("destination_address").getValue(String::class.java)
                            val imageUrl = item.child("image_url").getValue(String::class.java)
                            val itemName = item.child("item_name").getValue(String::class.java)
                            val sourceAddress = item.child("source_address").getValue(String::class.java)
                            val tipAmount = item.child("tip_amount").getValue(String::class.java)
                            val rentalID = item.child("rental_id").getValue(Int::class.java).toString()
                            val deliveryType = item.child("type").getValue(String::class.java)
                            val listingID = item.child("listing_id").getValue(Int::class.java)


                            if (deliveryType == "return") {
                                delivery = Delivery(
                                    key = key,
                                    delivery_title = itemName,
                                    pickup_location = sourceAddress,
                                    deliver_location = destinationAddress,
                                    img_url = imageUrl,
                                    payment = tipAmount,
                                    rental_id = null,
                                    type = deliveryType,
                                    return_id = rentalID.toInt(),
                                    listing_id = 27 //listingID.toInt()
                                )
                            } else {
                                delivery = Delivery(
                                    key = key,
                                    delivery_title = itemName,
                                    pickup_location = sourceAddress,
                                    deliver_location = destinationAddress,
                                    img_url = imageUrl,
                                    payment = tipAmount,
                                    rental_id = rentalID.toInt(),
                                    type = deliveryType,
                                    return_id = null,
                                    listing_id = 27 //listingID.toInt()
                                )
                            }

                            newList.add(delivery)
                        }
                    }
                    trySend(newList).isSuccess
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("DatabaseError", error.toException())
            }
        })
        awaitClose {
            databaseReference.removeEventListener(listener)
        }
    }

    fun removeDelivery(key: String) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("messages")
        val itemReference = databaseReference.child(key)
        itemReference.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Delete Item", "Item successfully deleted.")
            } else {
                Log.d("Delete Item", "Failed to delete item.")
            }
        }
    }

    fun updateAccepted(key: String) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("messages")
        val itemReference = databaseReference.child(key)
        itemReference.child("accepted").setValue(true).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Update Item", "Item successfully updated.")
            } else {
                Log.d("Update Item", "Failed to update item.")
            }
        }
    }
}