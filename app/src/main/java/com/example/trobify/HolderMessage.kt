package com.example.trobify

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView


class HolderMessage : RecyclerView.ViewHolder {
    private var name : TextView
    private var message : TextView
    private var time : TextView
    private var profilePictureMessage : CircleImageView
    private var pictureMessage : ImageView

    constructor(itemView : View) :super(itemView){
        name = itemView.findViewById<View>(R.id.profileNameMessage) as TextView
        message = itemView.findViewById<View>(R.id.sendedMessage) as TextView
        time = itemView.findViewById<View>(R.id.messageTime) as TextView
        profilePictureMessage = itemView.findViewById<View>(R.id.profilePictureMessage) as CircleImageView
        pictureMessage = itemView.findViewById<View>(R.id.messageImage) as ImageView
    }

    fun getName() : TextView? {
        return name
    }

    fun setName(name : TextView) {
        this.name = name
    }

    fun getMessage() : TextView? {
        return message
    }

    fun setMessage(message : TextView) {
        this.message = message
    }

    fun getTime() : TextView? {
        return time
    }

    fun setTime(time : TextView) {
        this.time = time
    }

    fun getprofilePictureMessage() : CircleImageView? {
        return profilePictureMessage
    }

    fun setprofilePictureMessage(profilePictureMessage : CircleImageView) {
        this.profilePictureMessage = profilePictureMessage
    }

    fun getpictureMessage() : ImageView? {
        return pictureMessage
    }

    fun setpictureMessage(pictureMessage : ImageView) {
        this.pictureMessage = pictureMessage
    }

}