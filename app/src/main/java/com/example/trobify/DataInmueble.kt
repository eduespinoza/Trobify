package com.example.trobify

import java.time.LocalDateTime

data class DataInmueble (var id : String? = null,
                         var propietario : User? = null,
                         var numHabitaciones : Int? = null,
                         var numBanos : Int? = null,
                         var superficie : Int? = null,
                         var direccion : String? = null,
                         var tipoInmueble : String? = null,
                         var intencion : String? = null,
                         var precio : Int? = null,
                         var fotos : ArrayList<Int> = arrayListOf(),
                         var fotosOrd : ArrayList<String> = arrayListOf(),
                         var certificadoEnergetico : String? = null,
                         var descripcion : String? = null,
                         var estado : String? = null,
                         var parking : Boolean? = null,
                         var ascensor : Boolean? = null,
                         var amueblado : Boolean? = null,
                         var calefaccion : Boolean? = null,
                         var jardin : Boolean? = null,
                         var piscina : Boolean? = null,
                         var terraza : Boolean? = null,
                         var trastero : Boolean? = null,
                         var fechaSubida : String? = null)
