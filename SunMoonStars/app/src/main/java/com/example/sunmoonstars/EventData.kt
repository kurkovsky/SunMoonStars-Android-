package com.example.sunmoonstars

data class EventData(
    val items: List<Event>
)

data class Event(
    val startTime: String,
    val endTime: String,
    val location: String,
    val title: String,
    val activityAbstract: String,
    val link: String,
    val date: String,
    val pricePer: String,
    val price: String
)

