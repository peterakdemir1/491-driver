package com.example.a491_driver
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
interface APIService {
    @POST("drivers/")
    suspend fun createDriver(@Body driver: Driver): ReturnDriver

    @POST("check-driver-credentials/")
    suspend fun checkDriverCredentials(@Body driver: DriverCredentials): ReturnMessage
}