package com.example.trobify

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.here.sdk.core.CountryCode
import com.here.sdk.core.GeoCoordinates
import com.here.sdk.core.LanguageCode
import com.here.sdk.core.errors.InstantiationErrorException
import com.here.sdk.search.SearchEngine
import com.here.sdk.search.SearchOptions
import com.here.sdk.search.SuggestCallback
import com.here.sdk.search.TextQuery

class Busqueda {
    val db = Firebase.firestore
    var query : String = ""
    lateinit var motorDeBusqueda : SearchEngine
    lateinit var opcionesDeBusqueda : SearchOptions
    var sugerencias = arrayListOf<String>()
    init {
        prepararBuscadorHere()
    }
    fun buscar(busqueda : String){
        this.query = busqueda
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
                val address = geocodingResult.title
                sugerencias.add(address)
                res += "$address\n"
            }
            Log.d("SuggestionCallBack","$res")
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
    fun obtenerResultados(resultado : (ArrayList<DataInmueble>) -> Unit){
        var inmueblesEncontrados = arrayListOf<DataInmueble>()
        db.collection("inmueblesv2").
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
            }
    }
}