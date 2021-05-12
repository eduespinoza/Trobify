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
    }
    private fun obtenerResultados():ArrayList<DataInmueble2>{
        if(filtrosSize == 1){
            println("results : $list")
            return Database.getInmueblesByIds(list)
        }
        var resultIds = arrayListOf<String>()
        try {
            resultIds = list.groupingBy{it}.eachCount().filter{it.value == filtrosSize}.keys.toList() as ArrayList<String>
            println(resultIds)
        }catch (exception : Exception){
            return arrayListOf()
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
        result = result.groupingBy{it}.eachCount().filter{it.value == 2}.keys.toList() as ArrayList<String>
        return result
    }
    private fun obtenerIdsSuperficie(min : Int, max : Int): ArrayList<String>{
        filtrosSize++
        var idsMin = Database.getInmueblesBySuperficieMin(min)
        var idsMax =  Database.getInmueblesBySuperficieMax(max)
        var result = arrayListOf<String>()
        result.addAll(idsMin)
        result.addAll(idsMax)
        result = result.groupingBy{it}.eachCount().filter{it.value == 2}.keys.toList() as ArrayList<String>
        return result
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
}