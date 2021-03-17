package com.example.trobify

import  android.R.id
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView


class MessageHolder : RecyclerView.ViewHolder{
    var name : TextView
    var message : TextView
    var time : TextView
    //var profilePicture : CircleImageView
    //private var messagePicture : ImageView


    constructor(itemView : View) : super(itemView){
        name = itemView.findViewById<View>(R.id.profileName) as TextView
        message = itemView.findViewById<View>(R.id.txtMessage) as TextView
        time = itemView.findViewById<View>(R.id.horaMensaje as TextView
        //profilePicture = itemView.findViewById<View>(R.id.fotoPerfilMensaje) as CircleImageView
        //messagePicture = itemView.findViewById<View>(R.id.mensajeFoto) as ImageView
    }


    //fun getMessagePicture() : ImageView? {
        //return messagePicture
    //}

}


