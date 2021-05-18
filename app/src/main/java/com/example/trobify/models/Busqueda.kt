package com.example.trobify.models

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.here.sdk.core.CountryCode
import com.here.sdk.core.GeoCircle
import com.here.sdk.core.GeoCoordinates
import com.here.sdk.core.LanguageCode
import com.here.sdk.core.errors.InstantiationErrorException
import com.here.sdk.search.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Busqueda {
    //val inmuebles = Database().inmuebles
    var db = Firebase.firestore
    var query : String = ""
    //lateinit var inmuebles : ArrayList<DataInmueble2>
    lateinit var motorDeBusqueda : SearchEngine
    lateinit var opcionesDeBusqueda : SearchOptions
    var esPrimera = true
    //var sugerencias = mutableMapOf<String,GeoCoordinates>()
    var sugerencias = arrayListOf<Place>()
    var suggeries = arrayListOf<Sitio>()
    var sitio : Place? = null
    init {
        prepararBuscadorHere()
    }
    fun buscar(busqueda : String) : ArrayList<DataInmueble2>{
        println("vale ahora busco manin")
        var result = Database.getInmueblesBusqueda(busqueda, null)
        println(result)
        return result
    }
    //ESTO LO HACE FIREBASE
    fun buscarConIntencion(busqueda : String, intencion : String) : ArrayList<DataInmueble2>{
        return Database.getInmueblesBusqueda(busqueda, intencion)
    }
    private val searchCallback =
        SearchCallback { searchError, mutableList ->
            if (searchError != null) {
                return@SearchCallback
            }
            var res = ""
            sugerencias.clear()
            for (geo in mutableList!!) {
                println(geo.title)
                sugerencias.add(geo)
                suggeries.add(Sitio(titulo = geo.title, id = geo.id,
                    coordenadas = mutableMapOf("latitud" to geo.geoCoordinates?.latitude!!,
                        "longitud" to geo.geoCoordinates?.longitude!!)))
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
                println("${geocodingResult.title} este resultado manin")
            }

        }
    fun direccionFromCoord(coord:GeoCoordinates){
            var result = "aqui so yo"
            var searchOptions = SearchOptions(LanguageCode.ES_ES,1)
            motorDeBusqueda.search(coord,searchOptions,direc)

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
        //database = Database()
        //inmuebles = Database.inmuebles
        /*
        //BAD SMELL
        var t = db.collection("inmueblesv3").get()
        t.addOnCompleteListener {
            for (res in it.result){
                var inmueble = res.toObject(DataInmueble2::class.java)
                inmuebles.add(inmueble)
            }
        }
        //BAD SMELL

         */
    }
    fun obtenerResultados(resultado : (ArrayList<DataInmueble2>) -> Unit){
        var inmueblesEncontrados = arrayListOf<DataInmueble2>()

        /*var inmueblesEncontrados = arrayListOf<DataInmueble2>()
        db.collection("inmueblesv3").
        whereEqualTo("direccion",this.query).get()
            .addOnCompleteListener{ task->
                 if(task.isSuccessful){
                     for(resultadoBusqueda in task.result){
                         var inmueble = resultadoBusqueda.toObject(DataInmueble2::class.java)
                         inmueblesEncontrados.add(inmueble)
                     }
                     Log.d("QUERY","$inmueblesEncontrados")
                     resultado(inmueblesEncontrados)
                 }
            }*/
    }
}