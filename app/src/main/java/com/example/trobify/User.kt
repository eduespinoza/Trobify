package com.example.trobify

import android.widget.ImageView


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



}