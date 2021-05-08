package com.example.trobify

import android.widget.Spinner
import com.example.trobify.models.TipoInmueble

open class GuardaFiltros : FiltrosBusqueda() {
    object filtrosGuardados{
        var tipoInmueble:String? = TipoInmueble.Cualquiera.toString()
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

    object guardaSeleccion{
        var desTipoInmueble = 0
        var desNumHabitaciones = 0
        var desNumBaños = 0
        var desPrecioMin = 0
        var desPrecioMax = 0
        var desSurfaceMin = 0
        var desSurfaceMax = 0

        var boxObraNueva = false
        var boxMuyBien = false
        var boxBien = false
        var boxReformado = false
        var boxAReformar = false
        var boxCasiNuevo = false

        var boxParking = false
        var boxAscensor = false
        var boxAmueblado = false
        var boxCalefaccion = false
        var boxJardin = false
        var boxPiscina = false
        var boxTerraza = false
        var boxTrastero = false

        var elementosSeleccionadosTipoEdif = booleanArrayOf(false,/*Apartamento*/ false,/*Ático*/ false,/*Dúplex*/ false,/*Loft*/ false,/*Planta baja*/ false/*Estudio*/)
        var elementosSeleccionadosTipoPorDefecto = booleanArrayOf(false,/*Apartamento*/ false,/*Ático*/ false,/*Dúplex*/ false,/*Loft*/ false,/*Planta baja*/ false,/*Estudio*/ false, /*Casa*/ false,/*Chalet*/ false,/*Adosado*/ false/*Finca rústica*/)
    }
}