package com.example.trobify

import android.R.id
import android.R.layout
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*


class MessagesAdapter(private val c : Context) :
    RecyclerView.Adapter<MessageHolder>() {
    private val listMessage : MutableList<MessageReception> = ArrayList()
    fun addMessage(m : MessageReception) {
        listMessage.add(m)
        notifyItemInserted(listMessage.size)
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : MessageHolder {
        val v : View =
            LayoutInflater.from(c).inflate(R.layout.message_card, parent, false)
        return MessageHolder(v)
    }

    override fun onBindViewHolder(holder :  MessageHolder, position : Int) {
        holder.name.setText(listMessage[position].name)
        holder.textMessage.setText(listMessage[position].textMessage)

        if (listMessage[position].type_message.equals("2")) {
            holder.getPictureInMessage().visibility = View.VISIBLE
            holder.textMessage.visibility = View.VISIBLE
            Glide.with(c).load(listMessage[position].urlPicture).into(holder.getPictureInMessage())
        } else if (listMessage[position].type_message.equals("1")) {
            holder.getPictureInMessage().visibility = View.GONE
            holder.textMessage.visibility = View.VISIBLE
        }
        if (listMessage[position].profilePicture?.isEmpty()!!) {
            holder.profilePictureInMessage.setImageResource(R.mipmap.ic_launcher)
        } else {
            Glide.with(c).load(listMessage[position].profilePicture)
                .into(holder.profilePictureInMessage)
        }
        val timeCode : Long = listMessage[position].time!!
        val d = Date(timeCode)
        val dateF = SimpleDateFormat("hh:mm:ss a")
        holder.time.setText(dateF.format(d))
    }

    override fun getItemCount() : Int {
        return listMessage.size
    }

}