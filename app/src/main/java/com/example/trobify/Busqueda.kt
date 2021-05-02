package com.example.trobify

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.here.sdk.core.CountryCode
import com.here.sdk.core.GeoCoordinates
import com.here.sdk.core.LanguageCode
import com.here.sdk.core.errors.InstantiationErrorException
import com.here.sdk.search.*

class Busqueda {
    val db = Firebase.firestore
    var query : String = ""
    var pisos = arrayListOf<DataInmueble>()
    lateinit var motorDeBusqueda : SearchEngine
    lateinit var opcionesDeBusqueda : SearchOptions
    //var sugerencias = mutableMapOf<String,GeoCoordinates>()
    var sugerencias = arrayListOf<Place>()
    init {
        prepararBuscadorHere()
    }
    fun buscar(busqueda : String) : ArrayList<DataInmueble>{
        this.query = busqueda
        println(" wachooooo")
        var resultado = arrayListOf<DataInmueble>()
        pisos.forEach {
            if(it.direccion?.titulo?.toUpperCase()?.contains(busqueda)!!)
                resultado.add(it)
        }
        return resultado
    }
    fun buscarConIntencion(busqueda : String, intencion : String) : ArrayList<DataInmueble>{
        println("Con intencion wachooooo")
        println(intencion)
        this.query = busqueda
        var resultado = arrayListOf<DataInmueble>()
        pisos.forEach {
            if(it.direccion?.titulo?.toUpperCase()?.contains(busqueda)!!
                && it.intencion.equals(intencion))resultado.add(it)
        }
        return resultado
    }
    private val searchCallback =
        SearchCallback { searchError, mutableList ->
            if (searchError != null) {
                return@SearchCallback
            }
            var res = ""
            sugerencias.clear()
            for (geocodingResult in mutableList!!) {
                println(geocodingResult.title)
                sugerencias.add(geocodingResult)
                //val address = geocodingResult.title
                //val geoCoordinates = geocodingResult.place?.geoCoordinates
                //if (geoCoordinates != null) {
                //sugerencias[address] = geoCoordinates
                //}
                //res += "$address\n"
            }
            //Log.d("SuggestionCallBack","$res")
        }
    fun obtenerSugerencias(query : String, geoCoordinates : GeoCoordinates){
        println("${query} con espacion")
        motorDeBusqueda.search(TextQuery("${query} ",geoCoordinates),opcionesDeBusqueda,searchCallback)
    }
    fun obtenerSugerencias(query : String){
        //coordenadas de valencia por defecto, esto es para que tenga un rango
        //de busqueda definido
        var coordenadas = GeoCoordinates(39.46895, -0.37686)
        //var address = AddressQuery(query,coordenadas, arrayListOf(CountryCode.ESP))

        motorDeBusqueda.suggest(TextQuery(query,coordenadas,
            arrayListOf(CountryCode.ESP)),opcionesDeBusqueda,sugerenciaCallback)
    }
    private val sugerenciaCallback =
        SuggestCallback { searchError, list ->
            if (searchError != null) {
                Log.d("Geocoding", "Error: $searchError")
                return@SuggestCallback
            }
            var res = ""
            sugerencias.clear()
            for (geocodingResult in list!!) {
                println(geocodingResult.title)
                geocodingResult.place?.let { sugerencias.add(it) }
                //val address = geocodingResult.title
                //val geoCoordinates = geocodingResult.place?.geoCoordinates
                //if (geoCoordinates != null) {
                    //sugerencias[address] = geoCoordinates
                //}
                //res += "$address\n"
            }
            //Log.d("SuggestionCallBack","$res")
        }
    fun prepararBuscadorHere(){
        try {
            motorDeBusqueda = SearchEngine()
        } catch (e: InstantiationErrorException) {
            throw RuntimeException("Initialization of SearchEngine failed: " + e.error.name)
        }
        var numMaxSugerencias = 6
        opcionesDeBusqueda = SearchOptions(LanguageCode.ES_ES,numMaxSugerencias)
        var t = db.collection("inmueblesv3").get()
        t.addOnCompleteListener {
            for (res in it.result){
                var inmueble = res.toObject(DataInmueble::class.java)
                pisos.add(inmueble)
            }
        }
    }
    fun obtenerResultados(resultado : (ArrayList<DataInmueble>) -> Unit){
        var inmueblesEncontrados = arrayListOf<DataInmueble>()

        /*var inmueblesEncontrados = arrayListOf<DataInmueble>()
        db.collection("inmueblesv3").
        whereEqualTo("direccion",this.query).get()
            .addOnCompleteListener{ task->
                 if(task.isSuccessful){
                     for(resultadoBusqueda in task.result){
                         var inmueble = resultadoBusqueda.toObject(DataInmueble::class.java)
                         inmueblesEncontrados.add(inmueble)
                     }
                     Log.d("QUERY","$inmueblesEncontrados")
                     resultado(inmueblesEncontrados)
                 }
            }*/
    }
    fun getInmueblesIntencion(intencion : String) : ArrayList<DataInmueble> {
        var resultado = arrayListOf<DataInmueble>()
        pisos.forEach {
            if(it.intencion?.equals(intencion)!!)resultado.add(it)
        }
        return resultado
    }
}