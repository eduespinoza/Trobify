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

    private var name : String = ""
    private var surname : String = ""
    private var email : String = ""
    private var id : String = ""
    private var pass : String = ""
    private var telefono : Int = -1
    private var listaFavoritos : MutableList<String> = mutableListOf()


    class User(var nombre : String, var apellidos : String, var correo : String, var id : String,
    var pass : String, var telefono : Int,var listaFavoritos : MutableList<String>){}



    private fun updateUI(user: FirebaseUser?) {

    }


}