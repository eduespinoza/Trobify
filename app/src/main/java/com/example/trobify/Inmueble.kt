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


class Inmueble(
    private var id : String,
    private var propietario : User,
    private var numHabitaciones : Int,
    private var numBanos : Int,  //en m2
    private var superficie : Double,
    private var direccion : String,  // piso, aprtamento, atico, duplex, estudio...

    private var tipoInmueble : String,
    private var intencion : String,
    private var precio : Int,

    private var fotos : MutableList<String>,   //cambiar tipo a img
    private var certificadoEnergetico : String,   //Desde la A hasta la G
    private var estado : String,

    private var descripcion : String,

    //EXTRAS 0 si 1 no
    private var amueblado : Int,
    private var parking : Int,
    private var ascensor : Int,
    private var calefaccion : Int,
    private var jardin : Int,
    private var pisicina : Int,
    private var terraza : Int
) {


    private fun updateUI(user: FirebaseUser?) {

    }


}