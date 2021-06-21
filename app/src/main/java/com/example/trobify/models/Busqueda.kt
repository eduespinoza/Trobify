package com.example.trobify.models

import com.example.trobify.database.Database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.here.sdk.core.GeoCoordinates
import com.here.sdk.core.LanguageCode
import com.here.sdk.core.errors.InstantiationErrorException
import com.here.sdk.search.*

class Busqueda {
    var db = Firebase.firestore
    lateinit var motorDeBusqueda : SearchEngine
    lateinit var opcionesDeBusqueda : SearchOptions
    var sugerencias = arrayListOf<Place>()
    var suggeries = arrayListOf<Sitio>()
    var sitio : Place? = null
    init {
        prepararBuscadorHere()
    }
    fun buscar(busqueda : String) : ArrayList<DataInmueble2>{
        var result = Database.obtenerInmueblesSegunBusqueda(busqueda, null)
        return result
    }
    fun buscarConIntencion(busqueda : String, intencion : String) : ArrayList<DataInmueble2>{
        return Database.obtenerInmueblesSegunBusqueda(busqueda, intencion)
    }
    private val searchCallback =
        SearchCallback { searchError, mutableList ->
            if (searchError != null) {
                return@SearchCallback
            }
            sugerencias.clear()
            for (geo in mutableList!!) {
                println(geo.title)
                sugerencias.add(geo)
                suggeries.add(Sitio(titulo = geo.title, id = geo.id,
                    coordenadas = mutableMapOf("latitud" to geo.geoCoordinates?.latitude!!,
                        "longitud" to geo.geoCoordinates?.longitude!!)))
            }
        }
    fun obtenerSugerencias(query : String, geoCoordinates : GeoCoordinates){
        println("${query} con espacion")
        motorDeBusqueda.search(TextQuery(query,geoCoordinates),opcionesDeBusqueda,searchCallback)
    }
    val direc =
        SearchCallback { searchError, mutableList ->
            if (searchError != null) {
                return@SearchCallback
            }
            for (geocodingResult in mutableList!!) {
                sitio = geocodingResult
                var place = Sitio(geocodingResult.title,
                    mutableMapOf("latitud" to (geocodingResult.geoCoordinates?.latitude!!)
                        ,"longitud" to (geocodingResult.geoCoordinates?.longitude!!)
                    ),geocodingResult.id)
                place.id?.let { db.collection("testingPlacesv2").document(it).set(place) }
            }

        }
    fun direccionFromCoord(coord:GeoCoordinates){
            var searchOptions = SearchOptions(LanguageCode.ES_ES,1)
            motorDeBusqueda.search(coord,searchOptions,direc)

    }
    fun prepararBuscadorHere(){
        try {
            motorDeBusqueda = SearchEngine()
        } catch (e: InstantiationErrorException) {
            throw RuntimeException("Initialization of SearchEngine failed: " + e.error.name)
        }
        var numMaxSugerencias = 6
        opcionesDeBusqueda = SearchOptions(LanguageCode.ES_ES,numMaxSugerencias)
    }

}