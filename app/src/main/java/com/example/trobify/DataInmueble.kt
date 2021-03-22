package com.example.trobify

data class DataInmueble (var caracteristicas : String? = null, var caractertisicasList : MutableList<Boolean>? = mutableListOf(true,false),var certificadoEnergetico: String?="",
                         var descripcion : String?="",var direccion : String?="",var estado : String?="",var id : String?="",var intencion : String?="",var numBanos : Int?=-1,
                         var numHabitaciones : Int?=-1,var precio : Int?=-1,var propietario : User?=null, var superficie : Int?=-1,var tipoInmueble : String?="",var titulo : String?="")
