package com.example.trobify.models

import com.example.trobify.controladores.FiltrosBusqueda

class SnapshotFiltros {
    object foto{
        var fotoFiltros = SnapshotFiltros()
    }

    var tipoInmueble:String = "Cualquiera"
    var numHabitaciones:Int = 0
    var numBaños:Int = 0
    var extras = mutableMapOf<String, Boolean>()
    var estado = arrayListOf<String>()
    var precioMin:Int = 0
    var precioMax:Int = 0
    var superficieMin:Int = 0
    var superficieMax:Int = 0
    var tipoVivienda = arrayListOf<String>()

    var elementosSeleccionadosTipoEdif = booleanArrayOf(false,/*Apartamento*/ false,/*Ático*/ false,/*Dúplex*/ false,/*Loft*/ false,/*Planta baja*/ false/*Estudio*/)
    var elementosSeleccionadosTipoPorDefecto = booleanArrayOf(false,/*Apartamento*/ false,/*Ático*/ false,/*Dúplex*/ false,/*Loft*/ false,/*Planta baja*/ false,/*Estudio*/ false, /*Casa*/ false,/*Chalet*/ false,/*Adosado*/ false/*Finca rústica*/)

    constructor(){}

    constructor(tipoInmueble:String, numHabitaciones:Int, numBaños:Int, extras:MutableMap<String,Boolean>,
                estado:ArrayList<String>, precioMin:Int, precioMax:Int, superficieMin:Int, superficieMax:Int, tipoVivienda:ArrayList<String>,
                elementosSeleccionadosTipoEdif:BooleanArray, elementosSeleccionadosTipoPorDefecto:BooleanArray){
        this.tipoInmueble = tipoInmueble
        this.numHabitaciones = numHabitaciones
        this.numBaños = numBaños
        this.extras = extras
        this.estado = estado
        this.precioMin = precioMin
        this.precioMax = precioMax
        this.superficieMin = superficieMin
        this.superficieMax = superficieMax
        this.tipoVivienda = tipoVivienda

        this.elementosSeleccionadosTipoEdif = elementosSeleccionadosTipoEdif
        this.elementosSeleccionadosTipoPorDefecto = elementosSeleccionadosTipoPorDefecto
    }

    fun restoreData(){
        FiltrosBusqueda.filtros.tipoInmueble = this.tipoInmueble
        FiltrosBusqueda.filtros.numHabitaciones = this.numHabitaciones
        FiltrosBusqueda.filtros.numBaños = this.numBaños
        FiltrosBusqueda.filtros.extras = this.extras
        FiltrosBusqueda.filtros.estado = this.estado
        FiltrosBusqueda.filtros.precioMin = this.precioMin
        FiltrosBusqueda.filtros.precioMax = this.precioMax
        FiltrosBusqueda.filtros.superficieMin = this.superficieMin
        FiltrosBusqueda.filtros.superficieMax = this.superficieMax
        FiltrosBusqueda.filtros.tipoVivienda = this.tipoVivienda

        FiltrosBusqueda.filtros.elementosSeleccionadosTipoEdif = this.elementosSeleccionadosTipoEdif
        FiltrosBusqueda.filtros.elementosSeleccionadosTipoPorDefecto = this.elementosSeleccionadosTipoPorDefecto
    }
}