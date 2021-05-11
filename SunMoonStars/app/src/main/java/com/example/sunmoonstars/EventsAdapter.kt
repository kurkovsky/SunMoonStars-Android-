package com.example.sunmoonstars

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class EventsAdapter(
    private val events: ArrayList<Event>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<EventsAdapter.MyViewHolder>(){

    private val TAG = "EventsAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Inflate a layout from our XML (row_item.XML) and return the holder
        //Log.d(TAG, "onCreateViewHolder: ${count++}")

        // create a new view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val currentItem = events[position]
        holder.name.text = currentItem.title
        holder.location.text = currentItem.location
        holder.activityAbstract.text = currentItem.activityAbstract
        //holder.starttime.text = currentItem.startTime.substring(11,16)
        holder.starttime.text = getTheTime(currentItem.startTime.substring(11,16))
        //holder.endtime.text = currentItem.endTime.substring(11,16)
        holder.endtime.text = getTheTime(currentItem.endTime.substring(11,16))
        holder.link.text = currentItem.link
        holder.date.text = currentItem.date.substring(0,10)
        holder.price.text = currentItem.price
        holder.priceper.text = currentItem.pricePer

    }

    inner class MyViewHolder (itemView: View): RecyclerView.ViewHolder (itemView),
    View.OnClickListener{
        // This class will represent a single row in our recyclerView list
        // This class also allows caching views and reuse them
        // Each MyViewHolder object keeps a reference to 3 view items in our row_item.xml file
        val name = itemView.tv_name
        val location = itemView.tv_location
        val starttime = itemView.tv_starttime
        val endtime = itemView.tv_endtime
        val activityAbstract = itemView.tv_description
        val link = itemView.tv_link
        val date = itemView.tv_date
        val price = itemView.tv_price
        val priceper = itemView.tv_priceper

        init {
            itemView.setOnClickListener(this)
        }
        //function to pass values to the interface
        override fun onClick(v: View?) {
            val position = adapterPosition
            val link = events[position].link
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position,link)

            }
        }
    }

    // function to convert 24 hour time to 12 hour time
    fun getTheTime(twofourTime: String): String {
        //substrings that will seperate the hours, colon, and mins
        val hours: String = twofourTime.substring(0,2)
        val mins: String = twofourTime.substring(3,5)
        var hoursInt = hours.toInt()

        //subtract 4 from the time given because wix is trash and somehows messes up the conversion
        if(hoursInt in 4..23){
            hoursInt = hoursInt - 4;
        }else if(hoursInt == 0){
            hoursInt = 20;
        }else if(hoursInt == 1){
            hoursInt = 21;
        }else if(hoursInt == 2){
            hoursInt = 22;
        }else if(hoursInt == 3){
            hoursInt = 23;
        }
        //put the 24 hours format back into the string
        var convertedtime: String = "$hoursInt:$mins";
        //using simpledateformat, convert the 24 hours format into 12 format
        try {
            val _24HourSDF = SimpleDateFormat("HH:mm")
            val _12HourSDF = SimpleDateFormat("hh:mm a")
            val _24HourDt = _24HourSDF.parse(convertedtime)
            return _12HourSDF.format(_24HourDt)
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }

    }

    override fun getItemCount(): Int {
        return events.size
    }
    //interface to pass items to main activity
    interface OnItemClickListener {
        fun onItemClick(position: Int, link: String)
    }
}