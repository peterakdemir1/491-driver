package com.example.a491_driver
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
interface APIService {
    @POST("drivers/")
    suspend fun createDriver(@Body driver: Driver): ReturnDriver

    @POST("check-driver-credentials/")
    suspend fun checkDriverCredentials(@Body driver: DriverCredentials): ReturnMessage

    @PATCH("rentals/attach-driver/{pk}/")
    suspend fun attachDriverToRental(@Path("pk") rental_id: Int, @Body driver: DriverId)

    @PATCH("returns/attach-driver/{pk}/")
    suspend fun attachDriverToReturn(@Path("pk") return_id: Int, @Body driver_id: DriverId)

    @PATCH("rentals/mark-delivered/{pk}/")
    suspend fun markRentalDelivered(@Path("pk") rental_id: Int)

    @PATCH("returns/mark-delivered/{pk}/")
    suspend fun markReturnDelivered(@Path("pk") return_id: Int)

    @POST("listings/make-available/{pk}/")
    suspend fun makeListingAvailable(@Path("pk") listing_id: String?)
}