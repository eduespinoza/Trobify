package com.example.trobify

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MessagesAdapter : RecyclerView.Adapter<HolderMessage> {

    private var listMessage : MutableList<Message> = arrayListOf(Message())
    private lateinit var context : Context

    constructor(context : Context){ this.context = context }

    private fun addMessage(message : Message){
        listMessage.add(message)

    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : HolderMessage {
        val v : View = LayoutInflater.from(context).inflate(R.layout.messages_card_view,parent,false )
        return HolderMessage(v)
    }

    override fun getItemCount() : Int {
        return listMessage.size
    }

    override fun onBindViewHolder(holder : HolderMessage, position : Int) {
        holder.getName()?.setText(listMessage.get(position).getName())
        holder.getMessage()?.setText(listMessage.get(position).getMessage())
        holder.getTime()?.setText(listMessage.get(position).getTime())
    }

}