package com.example.a491_driver

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
/*
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
    @SerializedName("type")
    val type: String?
) : java.io.Serializable {
}*/

class Delivery (
    val key: String?,
    val listing_id: Int?,
    val rental_id: Int?,
    val return_id: Int?,
    val pickup_location: String?,
    val deliver_location: String?,
    val delivery_title: String?,
    val payment: String?,
    val img_url: String?,
    val type: String?
) : java.io.Serializable {
}