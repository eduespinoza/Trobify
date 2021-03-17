package com.example.trobify

import android.widget.ImageView
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView

class Message {
    private lateinit var message : String
    private lateinit var name : String
    private lateinit var profilePicture : String
    private lateinit var type_message : String
    private lateinit var time : String

    constructor(){}
    constructor(message : String, name : String, profilePicture : String, type_message : String, time : String){
        this.message = message
        this.name = name
        this.profilePicture = profilePicture
        this.type_message = type_message
        this.time = time
    }


    fun getMessage() : String {
        return message
    }

    fun setMessage(message : String) {
        this.message = message
    }

    fun getName() : String {
        return name
    }

    fun setName(name : String) {
        this.name = name
    }

    fun getprofilePictureMessage() : String{
        return profilePicture
    }

    fun setprofilePictureMessage(profilePicture : String) {
        this.profilePicture = profilePicture
    }

    fun get_type_message() : String {
        return type_message
    }

    fun set_type_message(pictureMessage : String) {
        this.type_message = type_message
    }

    fun getTime() : String {
        return time
    }

    fun setTime(time : String) {
        this.time = time
    }
}