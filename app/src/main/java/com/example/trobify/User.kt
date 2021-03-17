package com.example.trobify

import android.content.ContentValues.TAG
import android.content.ContextWrapper
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.grpc.Context


class User {

    private var name : String? = null
    private var surname : String? = null
    private var email : String? = null
    private var id : String? = null
    private var pass : String? = null
    private var phone : Int? = null
    private var favourite : MutableList<String> = mutableListOf()  //cambiar a inmueble luego


    constructor(name : String?, surname : String?, email : String?, id : String?,
                pass : String?){
        this.name = name
        this.surname = surname
        this.email = email
        this.pass = pass
        this.id = id

        //añadir a base de datos
    }

    fun getName() : String? {
        return  this.name
    }

    fun getPhone() : Int?{
        return this.phone
    }

    fun addInmuebleToFav(){
        favourite.add("Inmueble") //cambiar a inmueble luego

    }




    private fun updateUI(user: FirebaseUser?) {

    }


}