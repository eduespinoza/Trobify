package com.example.trobify

import android.R.id
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView


class MessageHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    var name : TextView
    var textMessage : TextView
    var time : TextView
    var profilePictureInMessage : CircleImageView
    private var pictureInMessage : ImageView

    fun getPictureInMessage() : ImageView {
        return pictureInMessage
    }

    fun setPictureInMessage(pictureInMessage : ImageView) {
        this.pictureInMessage = pictureInMessage
    }

    init {
        name = itemView.findViewById<View>(R.id.nameInMessage) as TextView
        textMessage = itemView.findViewById<View>(R.id.messageInMessage) as TextView
        time = itemView.findViewById<View>(R.id.timeInMessage) as TextView
        profilePictureInMessage = itemView.findViewById<View>(R.id.profilePictureInMessage) as CircleImageView
        pictureInMessage = itemView.findViewById<View>(R.id.pictureInMessage) as ImageView
    }
}


