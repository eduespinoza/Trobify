package com.example.trobify.models

import com.example.trobify.controladores.FiltrosBusqueda

class GestionFiltros {
    lateinit var filtros : FiltrosBusqueda.filtros
    var list = arrayListOf<String>()
    var filtrosSize = 0

    private fun gestionDeFiltros(){
        if (!filtros.tipoInmueble?.equals("Cualquiera")!!){
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
            if (value == true)result.add(key)
        }
        return result
    }
    private fun obtenerResultados():ArrayList<DataInmueble2>{
        var resultIds = arrayListOf<String>()
        list.groupBy{it}.forEach{mapa ->
            if(mapa.value.size == filtrosSize)
                resultIds.add(mapa.key)
        }
        return Database.getInmueblesByIds(resultIds)
    }
    fun aplicar(filtrosSeleccionados : FiltrosBusqueda.filtros):ArrayList<DataInmueble2>{
        filtros = filtrosSeleccionados
        gestionDeFiltros()
        return obtenerResultados()
    }
    private fun obtenerIdsPrecio(min : Int, max : Int): ArrayList<String>{
        filtrosSize++
        var idsMin = Database.getInmueblesByPrecioMayorIgual(min)
        var idsMax =  Database.getInmueblesByPrecioMenorIgual(max)
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
        var idsMin = Database.getInmueblesBySuperficieMin(min)
        var idsMax =  Database.getInmueblesBySuperficieMax(max)
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
        var ids = Database.getInmueblesByTipoInmueble(opcion)
        return ids
    }
    private fun obtenerIdsTipoVivienda(opciones : ArrayList<String>) : ArrayList<String>{
        filtrosSize++
        var result = arrayListOf<String>()
        for (op in opciones){
            result.addAll(Database.getInmueblesByTipoVivienda(op))
        }
        return result
    }
    private fun obtenerIdsNumBaños(banos : Int) : ArrayList<String>{
        filtrosSize++
        var result = Database.getInmueblesByBaños(banos)
        return result
    }
    private fun obtenerIdsNumHabs(habs : Int) : ArrayList<String>{
        filtrosSize++
        var result = Database.getInmueblesByHabs(habs)
        return result
    }
    private fun obtenerIdsEstado(estados : ArrayList<String>) : ArrayList<String>{
        filtrosSize++
        var result = Database.getInmueblesByEstado(estados)
        return result
    }
    private fun obtenerIdsExtras(extras : ArrayList<String>) : ArrayList<String>{
        filtrosSize++
        var result = Database.getInmueblesByExtras(extras)
        return result
    }
}