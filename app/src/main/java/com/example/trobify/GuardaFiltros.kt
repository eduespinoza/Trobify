package com.example.trobify

open class GuardaFiltros : FiltrosBusqueda() {
    object filtrosGuardados{
        var tipoInmueble:String = ""
        var numHabitaciones:Int = 0
        var numBaños:Int = 0
        var extras = mutableMapOf<String, Boolean>()
        var estado = arrayListOf<String>()
        var precioMin:Int = 0
        var precioMax:Int = 0
        var superficieMin:Int = 0
        var superficieMax:Int = 0
        var tipoVivienda = arrayListOf<String>()
    }
}