package com.example.trobify.models

import android.content.ContentValues.TAG
import android.content.ContextWrapper
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.grpc.Context
import java.io.Serializable


class User : Serializable{

    private var name : String? = null
    private var surname : String? = null
    private var email : String? = null
    private var id : String? = null
    private var pass : String? = null
    private var phone : Int? = null
    private var proilePic : String? = null
    private var favorites : ArrayList<String> = arrayListOf()


    constructor(name : String?, surname : String?, email : String?, id : String?,
                pass : String?){
        this.name = name
        this.surname = surname
        this.email = email
        this.pass = pass
        this.id = id
        this.proilePic = "default"

    }

    constructor(name : String?, surname : String?, email : String?, id : String?,
                pass : String?, favorites : ArrayList<String>){
        this.name = name
        this.surname = surname
        this.email = email
        this.pass = pass
        this.id = id
        this.proilePic = "default"
        this.favorites = favorites

    }

    constructor(){}

    fun getName() : String? {
        return  this.name
    }

    fun getPhone() : Int?{
        return this.phone
    }

    fun addFav(idInmueble : String){
        favorites.add(idInmueble)

    }
    fun getId() : String?{
        return this.id
    }

    fun getFav() : ArrayList<String>{
        return this.favorites
    }

    fun setUserProfilePic(url :String?) {
        proilePic = url
    }

    fun getUserProfilePic() : String?{
        return this.proilePic
    }

    private fun updateUI(user: FirebaseUser?) {}
}
