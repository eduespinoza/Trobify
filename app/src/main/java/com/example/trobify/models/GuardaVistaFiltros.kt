package com.example.trobify.models

import com.example.trobify.controladores.FiltrosBusqueda

open class GuardaVistaFiltros : FiltrosBusqueda() {
    object vistaGuardada{
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
    }
}