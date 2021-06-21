package com.example.trobify.models

import com.example.trobify.controladores.FiltrosBusqueda
import com.example.trobify.database.Database

class GestionFiltros {
    lateinit var filtros : FiltrosBusqueda.filtros
    var list = arrayListOf<String>()
    var filtrosSize = 0

    lateinit var filtrosModelo : FiltrosModelo
    var lista = arrayListOf<String>()

    private fun gestionDeFiltrosModelo(){
        list = arrayListOf()
        if (!filtrosModelo.tipoInmueble.equals("Cualquiera")){
            list.addAll(obtenerIdsTipoInmueble(filtrosModelo.tipoInmueble!!))
        }
        if (filtrosModelo.tipoVivienda.isNotEmpty()){
            list.addAll(obtenerIdsTipoVivienda(filtrosModelo.tipoVivienda))
        }
        if (filtrosModelo.precioMax != 0){
            list.addAll(obtenerIdsPrecio(filtrosModelo.precioMin,filtrosModelo.precioMax))
        }
        if (filtrosModelo.superficieMax != 0){
            list.addAll(obtenerIdsSuperficie(filtrosModelo.superficieMin,filtrosModelo.superficieMax))
        }
        if (filtrosModelo.numBaños != 0){
            list.addAll(obtenerIdsNumBaños(filtrosModelo.numBaños))
        }
        if (filtrosModelo.numHabitaciones != 0){
            list.addAll(obtenerIdsNumHabs(filtrosModelo.numHabitaciones))
        }
        if (filtrosModelo.estado.isNotEmpty()){
            list.addAll(obtenerIdsEstado(filtrosModelo.estado))
        }
        if (getExtras(filtrosModelo.extras).isNotEmpty()){
            list.addAll(obtenerIdsExtras(getExtras(filtrosModelo.extras)))
        }
    }

    private fun gestionDeFiltros(){
        list = arrayListOf()
        if (!filtros.tipoInmueble.equals("Cualquiera")){
            list.addAll(obtenerIdsTipoInmueble(filtros.tipoInmueble!!))
        }
        if (filtros.tipoVivienda.isNotEmpty()){
            list.addAll(obtenerIdsTipoVivienda(filtros.tipoVivienda))
        }
        if (filtros.precioMax != 0){
            list.addAll(obtenerIdsPrecio(filtros.precioMin,filtros.precioMax))
        }
        if (filtros.superficieMax != 0){
            list.addAll(obtenerIdsSuperficie(filtros.superficieMin,filtros.superficieMax))
        }
        if (filtros.numBaños != 0){
            list.addAll(obtenerIdsNumBaños(filtros.numBaños))
        }
        if (filtros.numHabitaciones != 0){
            list.addAll(obtenerIdsNumHabs(filtros.numHabitaciones))
        }
        if (filtros.estado.isNotEmpty()){
            list.addAll(obtenerIdsEstado(filtros.estado))
        }
        if (getExtras(filtros.extras).isNotEmpty()){
            list.addAll(obtenerIdsExtras(getExtras(filtros.extras)))
        }
    }
    private fun getExtras(map : MutableMap<String,Boolean>):ArrayList<String>{
        var result = arrayListOf<String>()
        map.forEach {key,value ->
            if (value)result.add(key)
        }
        return result
    }
    private fun obtenerResultados():ArrayList<DataInmueble2>{
        var resultIds = arrayListOf<String>()
        list.groupBy{it}.forEach{mapa ->
            if(mapa.value.size == filtrosSize)
                resultIds.add(mapa.key)
        }
        return Database.obtenerInmueblesSegunIds(resultIds)
    }
    fun aplicar (filtros : FiltrosModelo):ArrayList<DataInmueble2>{
        filtrosModelo = filtros
        gestionDeFiltrosModelo()
        return obtenerResultados()
    }

    fun aplicar(filtrosSeleccionados : FiltrosBusqueda.filtros):ArrayList<DataInmueble2>{
        filtros = filtrosSeleccionados
        gestionDeFiltros()
        return obtenerResultados()
    }

    private fun obtenerIdsPrecio(min : Int, max : Int): ArrayList<String>{
        filtrosSize++
        var idsMin = Database.obtenerInmueblesSegunPrecioMayorIgual(min)
        var idsMax =  Database.obtenerInmueblesSegunPrecioMenorIgual(max)
        var result = arrayListOf<String>()
        result.addAll(idsMin)
        result.addAll(idsMax)
        idsMax.clear()
        result.groupBy{it}.forEach{mapa ->
            if(mapa.value.size == 2)
                idsMax.add(mapa.key)
        }
        return idsMax
    }
    private fun obtenerIdsSuperficie(min : Int, max : Int): ArrayList<String>{
        filtrosSize++
        var idsMin = Database.obtenerInmueblesSegunSuperficieMin(min)
        var idsMax =  Database.obtenerInmueblesSegunSuperficieMax(max)
        var result = arrayListOf<String>()
        result.addAll(idsMin)
        result.addAll(idsMax)
        idsMax.clear()
        result.groupBy{it}.forEach{mapa ->
            if(mapa.value.size == 2)
                idsMax.add(mapa.key)
        }
        return idsMax
    }
    private fun obtenerIdsTipoInmueble(opcion : String) : ArrayList<String>{
        filtrosSize++
        var ids = Database.obtenerInmbueblesSegunTipoInmueble(opcion)
        return ids
    }
    private fun obtenerIdsTipoVivienda(opciones : ArrayList<String>) : ArrayList<String>{
        filtrosSize++
        var result = arrayListOf<String>()
        for (op in opciones){
            result.addAll(Database.obtenerInmueblesSegunTipoVivienda(op))
        }
        return result
    }
    private fun obtenerIdsNumBaños(banos : Int) : ArrayList<String>{
        filtrosSize++
        var result = Database.obtenerInmueblesSegunBanos(banos)
        return result
    }
    private fun obtenerIdsNumHabs(habs : Int) : ArrayList<String>{
        filtrosSize++
        var result = Database.obtenerInmueblesSegunHabitaciones(habs)
        return result
    }
    private fun obtenerIdsEstado(estados : ArrayList<String>) : ArrayList<String>{
        filtrosSize++
        var result = Database.obtenerInmueblesSegunEstado(estados)
        return result
    }
    private fun obtenerIdsExtras(extras : ArrayList<String>) : ArrayList<String>{
        filtrosSize++
        var result = Database.obtenerInmueblesSegunExtras(extras)
        return result
    }
}