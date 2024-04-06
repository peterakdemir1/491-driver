package com.example.a491_driver

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ItemFetcher(passedItems: MutableList<Delivery>, passedAdapter: ItemAdapter) {
    val items = passedItems
    val itemAdapter = passedAdapter

    fun getCurrentDeliveries() {
        val databaseReference = FirebaseDatabase.getInstance().getReference("messages")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Iterate through each child
                    val list = snapshot.children.toList()

                    val newItem = list[list.size - 1]
                    Log.i("RT Firebase Contents", newItem.toString())

                    val key = newItem.key.toString()
                    val destinationAddress = newItem.child("destination_address").getValue(String::class.java)
                    val imageUrl = newItem.child("image_url").getValue(String::class.java)
                    val itemName = newItem.child("item_name").getValue(String::class.java)
                    val sourceAddress = newItem.child("source_address").getValue(String::class.java)
                    val tipAmount = newItem.child("tip_amount").getValue(String::class.java)
                    val rentalID = newItem.child("rental_id").getValue(String::class.java)
                    val returnID = newItem.child("return_id").getValue(String::class.java)

                    Log.i("New RT Firebase Item Key", key)
                    Log.i(
                        "New RT Firebase Item",
                        "item: ${itemName}\nsource: ${sourceAddress}\ndestination: ${destinationAddress}\nimage url: ${imageUrl}\ntip: ${tipAmount}"
                    )

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

                    items.add(delivery)
                    itemAdapter.notifyDataSetChanged()

                    val itemReference = databaseReference.child(key)
//                    delButton.setOnClickListener {
//                        itemReference.removeValue().addOnCompleteListener { task ->
//                            if (task.isSuccessful) {
//                                Log.d("Delete Item", "Item successfully deleted.")
//                                // Handle successful deletion, e.g., update UI or notify user
//                            } else {
//                                Log.d("Delete Item", "Failed to delete item.")
//                                // Handle failure, e.g., show error message
//                            }
//                        }
//                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("DatabaseError", error.toException())
            }
//        })
//    }
        })
    }
}