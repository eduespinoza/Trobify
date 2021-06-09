package com.example.trobify.models

class FiltrosModelo (
    var tipoInmueble:String = "Cualquiera",
    var numHabitaciones:Int = 0,
    var numBaños:Int = 0,
    var extras : MutableMap<String, Boolean> = mutableMapOf(),
    var estado : ArrayList<String> = arrayListOf(),
    var precioMin:Int = 0,
    var precioMax:Int = 0,
    var superficieMin:Int = 0,
    var superficieMax:Int = 0,
    var tipoVivienda : ArrayList<String> = arrayListOf()
    )