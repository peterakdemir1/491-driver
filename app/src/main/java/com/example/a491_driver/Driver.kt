package com.example.a491_driver

import com.google.gson.annotations.SerializedName

class Driver (
    val first_name: String,
    val last_name: String,
    val username: String,
    val password: String,
    val phone_number: String,
    val license_id: String
) {
}
/*
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

@Serializable
class Delivery (
    @SerializedName("key")
    val key: String? = null,

    @SerializedName("name")
    val name: String?,

    @SerializedName("source")
    val source: String?,

    @SerializedName("destination")
    val destination: String?,

    @SerializedName("imageUrl")
    val imageUrl: String?,

    @SerializedName("tip")
    val tip: String?,

    @SerializedName("rentalId")
    val rentalId: String?,

    @SerializedName("type")
    val type: String?
) : java.io.Serializable {
}*/