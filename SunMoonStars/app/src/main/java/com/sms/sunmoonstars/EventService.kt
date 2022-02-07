package com.sms.sunmoonstars

import retrofit2.Call
import retrofit2.http.GET

interface EventService {

    // Requesting a random User info based on specified  nationality, https://randomuser.me/api/?nat=us
    // Adding the nationality parameter to your request
    // get(".") indicates that your url is the same as the base url
    @GET("activities")
    fun getEvents() : Call<EventData>


}