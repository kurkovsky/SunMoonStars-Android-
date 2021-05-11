package com.example.sunmoonstars

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), EventsAdapter.OnItemClickListener {

    private val BASE_URL = "https://www.sunmoonandstars.org/_functions/"
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Sun, Moon & Stars Activity & Event Calendar"

        

        val eventList = ArrayList<Event>()

        val adapter = EventsAdapter(eventList, this)
        recview.adapter = adapter

        // use a linear layout manager
        recview.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val eventGetAPI = retrofit.create(EventService::class.java)

        eventGetAPI.getEvents().enqueue(object : Callback<EventData> {

            override fun onFailure(call: Call<EventData>, t: Throwable) {
                Log.d(TAG, "onFailure : $t")
            }

            override fun onResponse(call: Call<EventData>, response: Response<EventData>) {
                Log.d(TAG, "onResponse: $response")

                val body = response.body()

                if (body == null){
                    Log.w(TAG, "Valid response was not received")
                    return
                }

                Log.d(TAG, ": ${body.items.get(0).title}")
                Log.d(TAG, ": ${body.items.get(0).location}")
                Log.d(TAG, ": ${body.items.get(0).endTime}")
                Log.d(TAG, ": ${body.items.get(0).startTime}")
                Log.d(TAG, ": ${body.items.get(0).activityAbstract}")

                // Update the adapter with the new data
                eventList.addAll(body.items)
                adapter.notifyDataSetChanged()
            }

        })
    }
    //Sends you to the actual website given the position and link
    override fun onItemClick(position: Int, link: String) {
        //Toast.makeText(this, "Position $position clicked", Toast.LENGTH_SHORT).show()
        if (link.length<=1) {
            Toast.makeText(this, "No sign up link found.", Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(link)
            startActivity(intent)
        }
    }
}