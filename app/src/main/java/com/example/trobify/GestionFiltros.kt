package com.example.trobify

class GestionFiltros(val database : Database) {
    lateinit var filtros : FiltrosBusqueda.filtros
    var list = arrayListOf<String>()
    var filtrosSize = 0

    private fun gestionDeFiltros(){
        if (filtros.tipoInmueble != "Cualquiera"){
            list.addAll(obtenerIdsTipoInmueble(filtros.tipoInmueble))
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
    private fun obtenerResultados():ArrayList<DataInmueble>{
        if(filtrosSize == 1){return database.getInmueblesByIds(list)}
        var resultIds = arrayListOf<String>()
        try {
            resultIds = list.groupingBy{it}.eachCount().filter{it.value == filtrosSize}.keys.toList() as ArrayList<String>
            println(resultIds)
        }catch (exception : Exception){
            return arrayListOf()
        }
        return database.getInmueblesByIds(resultIds)
    }
    fun aplicar(fil : FiltrosBusqueda.filtros):ArrayList<DataInmueble>{
        filtros = fil
        gestionDeFiltros()
        return obtenerResultados()
    }
    private fun obtenerIdsPrecio(min : Int, max : Int): ArrayList<String>{
        filtrosSize++
        var idsMin = database.getInmueblesByPrecioMayorIgual(min)
        var idsMax =  database.getInmueblesByPrecioMenorIgual(max)
        var result = arrayListOf<String>()
        result.addAll(idsMin)
        result.addAll(idsMax)
        result = result.groupingBy{it}.eachCount().filter{it.value == 2}.keys.toList() as ArrayList<String>
        return result
    }
    private fun obtenerIdsSuperficie(min : Int, max : Int): ArrayList<String>{
        filtrosSize++
        var idsMin = database.getInmueblesBySuperficieMin(min)
        var idsMax =  database.getInmueblesBySuperficieMax(max)
        var result = arrayListOf<String>()
        result.addAll(idsMin)
        result.addAll(idsMax)
        result = result.groupingBy{it}.eachCount().filter{it.value == 2}.keys.toList() as ArrayList<String>
        return result
    }
    private fun obtenerIdsTipoInmueble(opcion : String) : ArrayList<String>{
        filtrosSize++
        var ids = database.getInmueblesByTipoInmueble(opcion)
        return ids
    }
    private fun obtenerIdsTipoVivienda(opciones : ArrayList<String>) : ArrayList<String>{
        filtrosSize++
        var result = arrayListOf<String>()
        for (op in opciones){
            result.addAll(database.getInmueblesByTipoVivienda(op))
        }
        return result
    }
}