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
        holder.message.setText(listMessage[position].message)

        if (listMessage[position].type_message.equals("2")) {
            //holder.profilePicture.setVisibility(View.VISIBLE)
            holder.message.setVisibility(View.VISIBLE)
            //Glide.with(c).load(listMessage[position].getprofilePictureMessage()).into(holder.getMessagePicture())
        } else if (listMessage[position].type_message.equals("1")) {
            //holder.getMessagePicture()?.setVisibility(View.GONE)
            holder.message.setVisibility(View.VISIBLE)
        }
        if (listMessage[position].getprofilePictureMessage()?.isEmpty()!!) {
            //holder.profilePicture.setImageResource(R.mipmap.ic_launcher)
        } else {
            Glide.with(c).load(listMessage[position].getprofilePictureMessage())
                //.into(holder.profilePicture)
        }
        val codigoHora : Long = listMessage[position].time!!
        val d = Date(codigoHora)
        val sdf = SimpleDateFormat("hh:mm:ss a") //a pm o am
        holder.time.setText(sdf.format(d))
    }

    override fun getItemCount() : Int {
        return listMessage.size
    }

}