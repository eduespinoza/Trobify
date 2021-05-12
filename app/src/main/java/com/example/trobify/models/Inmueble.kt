package com.example.trobify.models

import java.io.Serializable
import java.time.LocalDateTime
import kotlin.collections.ArrayList


class Inmueble : Serializable{
    var id : String? = null
    var propietario : String? = null
    var numHabitaciones : Int? = null
    var numBanos : Int? = null
    var superficie : Int? = null
    var direccion : String? = null
    var direccionO : Direccion? = null
    var tipoVivienda : String? = null
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
    var direccionSitio : Sitio? = null

    constructor()

    constructor(
        id : String?,
        propietario : String?,
        numHabitaciones : Int?,
        numBanos : Int?,
        superficie : Int?,
        direccion : String?,
        direccionO : Direccion?,
        tipoVivienda : String?,
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

        fechaSubida : LocalDateTime?,
        direccionSitio : Sitio?
    ) {

        this.id = id
        this.propietario = propietario
        this.numHabitaciones = numHabitaciones
        this.numBanos = numBanos
        this.superficie = superficie
        this.tipoInmueble = tipoInmueble
        this.tipoVivienda = tipoVivienda
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

        this.fechaSubida = fechaSubida
        this.direccionSitio = direccionSitio

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



    fun getIdd() : String?{
        return id
    }



    //FUNCION NECESARIA - para poder coger bien los datos de la bd
    fun adaptarInmuble(dataInmueble : DataInmueble) : Inmueble {
        return Inmueble(dataInmueble.id,dataInmueble.propietario.toString(),dataInmueble.numHabitaciones,dataInmueble.numBanos
        ,dataInmueble.superficie,
            dataInmueble.direccion?.titulo,direccionO,dataInmueble.tipoVivienda,dataInmueble.tipoInmueble,dataInmueble.intencion,dataInmueble.precio,dataInmueble.fotos,
        dataInmueble.fotosOrd,dataInmueble.certificadoEnergetico,dataInmueble.descripcion,dataInmueble.estado,dataInmueble.parking
        ,dataInmueble.ascensor,dataInmueble.amueblado,dataInmueble.calefaccion,dataInmueble.jardin,dataInmueble.piscina,dataInmueble.terraza,
            dataInmueble.trastero,LocalDateTime.parse(dataInmueble.fechaSubida), dataInmueble.direccion)
    }
    fun adaptadorInm(data : DataInmueble2) : Inmueble{
        var inmueble = Inmueble()
        inmueble.id = data.id
        inmueble.propietario = data.propietario
        inmueble.descripcion = data.descripcion
        inmueble.tipoInmueble = data.tipoInmueble
        inmueble.tipoVivienda = data.tipoVivienda
        inmueble.numBanos = data.numBanos
        inmueble.numHabitaciones = data.numHabitaciones
        inmueble.precio = data.precio
        inmueble.superficie = data.superficie
        inmueble.intencion = data.intencion
        inmueble.fotos = data.fotos
        inmueble.estado = data.estado
        return inmueble
    }





}