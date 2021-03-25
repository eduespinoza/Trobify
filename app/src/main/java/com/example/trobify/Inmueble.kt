package com.example.trobify

import android.content.ContentValues.TAG
import android.content.ContextWrapper
import android.content.Intent
import android.util.Log
import android.widget.CheckBox
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.Exclude
import com.google.firebase.ktx.Firebase
import io.grpc.Context
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList


class Inmueble : Serializable{
    var id : String? = null
    var propietario : User? = null
    var numHabitaciones : Int? = null
    var numBanos : Int? = null
    var superficie : Int? = null
    var direccion : String? = null
    var direccionO : Direccion? = null
    var tipoInmueble : String? = null
    var intencion : String? = null //vender o alquilar
    var precio : Int? = null

    var fotos : ArrayList<Int> = arrayListOf()
    var fotosOrd : ArrayList<String> = arrayListOf()
    var certificadoEnergetico : String? = null  //Desde la A hasta la G, hay que hacer que solo deje poner las letras posibles
    var descripcion : String? = null

    var fechaSubida : LocalDateTime? = null
    var estado : String? = null

    var parking : Boolean? = null
    var ascensor : Boolean? = null
    var amueblado : Boolean? = null
    var calefaccion : Boolean? = null
    var jardin : Boolean? = null
    var piscina : Boolean? = null
    var terraza : Boolean? = null
    var trastero : Boolean? = null

    var caracteristicas : String? = null

    constructor()

    constructor(
        id : String?,
        propietario : User?,
        numHabitaciones : Int?,
        numBanos : Int?,
        superficie : Int?,
        direccion : String?,
        direccionO : Direccion?,
        tipoInmueble : String?,
        intencion : String?,
        precio : Int?,

        fotos : ArrayList<Int>,
        fotosOrd : ArrayList<String>,
        certificadoEnergetico : String?,
        descripcion : String?,

        estado : String?,

        parking : Boolean?,
        ascensor : Boolean?,
        amueblado : Boolean?,
        calefaccion : Boolean?,
        jardin : Boolean?,
        piscina : Boolean?,
        terraza : Boolean?,
        trastero : Boolean?,

        fechaSubida : LocalDateTime?
    ) {

        this.id = id
        this.propietario = propietario
        this.numHabitaciones = numHabitaciones
        this.numBanos = numBanos
        this.superficie = superficie
        this.tipoInmueble = tipoInmueble
        this.intencion = intencion
        this.precio = precio
        this.fotos = fotos
        this.fotosOrd = fotosOrd
        this.certificadoEnergetico = certificadoEnergetico
        this.descripcion = descripcion
        this.estado = estado
        this.parking = parking
        this.ascensor = ascensor
        this.amueblado = amueblado
        this.calefaccion = calefaccion
        this.jardin = jardin
        this.piscina = piscina
        this.terraza = terraza
        this.trastero = trastero
        this.caracteristicas = caracteristicasToString()

        this.direccion = direccion
        this.direccionO = direccionO

        if (direccionO != null) {
            this.direccion = direccionO.direccionToString()
        }

        this.fechaSubida = fechaSubida
    }

    fun caracteristicasToString( ) : String {
        var res = "El piso dispone de las siguientes caracteristicas: \n"

        if(parking == true){ res += "-Parking\n" }
        if(ascensor == true){ res += "-Ascensor\n" }
        if(amueblado == true){ res += "-Amueblado\n" }
        if(calefaccion == true){ res += "-Calefaccion\n" }
        if(jardin == true){ res += "-Jardin\n" }
        if(piscina == true){ res += "-Piscina\n" }
        if(terraza == true){ res += "-Terraza\n" }
        if(trastero == true){ res += "-Trastero\n" }

        return res
    }

    fun getfotos() : ArrayList<Int>{
        return fotos
    }
    fun getfotosord() : ArrayList<String>{
        return fotosOrd

    }

    //FUNCION NECESARIA - para poder coger bien los datos de la bd
    fun adaptarInmuble(dataInmueble : DataInmueble) : Inmueble{
        return Inmueble(dataInmueble.id,dataInmueble.propietario,dataInmueble.numHabitaciones,dataInmueble.numBanos
        ,dataInmueble.superficie,dataInmueble.direccion,direccionO,dataInmueble.tipoInmueble,dataInmueble.intencion,dataInmueble.precio,dataInmueble.fotos,
        dataInmueble.fotosOrd,dataInmueble.certificadoEnergetico,dataInmueble.descripcion,dataInmueble.estado,dataInmueble.parking
        ,dataInmueble.ascensor,dataInmueble.amueblado,dataInmueble.calefaccion,dataInmueble.jardin,dataInmueble.piscina,dataInmueble.terraza,
            dataInmueble.trastero,LocalDateTime.parse(dataInmueble.fechaSubida))
    }




}