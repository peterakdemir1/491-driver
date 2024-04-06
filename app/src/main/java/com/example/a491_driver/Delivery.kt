package com.example.a491_driver

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
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

    @SerializedName("returnId")
    val returnId: String?
) : java.io.Serializable {
}