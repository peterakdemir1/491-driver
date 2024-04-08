package com.example.a491_driver

class Driver (
    val first_name: String,
    val last_name: String,
    val username: String,
    val password: String,
    val phone_number: String,
    val license_id: String
) {
}

class Delivery (
    val listing_id: Int,
    val rental_id: Int?,
    val return_id: Int?,
    val pickup_location: String,
    val deliver_location: String,
    val delivery_title: String,
    val payment: String,
    val img_url: String
) : java.io.Serializable {
}