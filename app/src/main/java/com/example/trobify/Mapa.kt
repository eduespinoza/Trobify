package com.example.trobify

import android.Manifest
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.MatrixCursor
import android.location.LocationManager
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.widget.CursorAdapter
import android.widget.SearchView
import android.widget.SimpleCursorAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.here.sdk.core.Anchor2D
import com.here.sdk.core.GeoCoordinates
import com.here.sdk.core.Metadata
import com.here.sdk.core.Point2D
import com.here.sdk.gestures.TapListener
import com.here.sdk.mapviewlite.*
import com.here.sdk.mapviewlite.MapScene.LoadSceneCallback


class Mapa : AppCompatActivity(){
    lateinit var mapa : MapViewLite
    var latitudDefault = 39.48204
    var longitudDefault = -0.33876
    lateinit var coordenadas : GeoCoordinates
    var buscador = Busqueda()
    lateinit var buscadorMapa : SearchView
    lateinit var user : String
    var esPrimera = true
    var resMarker = MapMarker(GeoCoordinates(latitudDefault,longitudDefault))
    private val db = Firebase.firestore
    var inmuebles = arrayListOf<DataInmueble>()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vista_mapa)
        mapa = findViewById(R.id.map_view)
        mapa.onCreate(savedInstanceState)
        user = (intent.extras!!.get("user") as String?).toString()
        prepararBuscador()
        getCoordenadas()
        loadMapScene()
        loadMarkers()
        setTapGestureHandler()

    }
    private fun prepararBuscador(){
        buscadorMapa = findViewById(R.id.buscadorMapa)
        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val to = intArrayOf(R.id.item_label)
        var adaptadorCursor = SimpleCursorAdapter(this.baseContext,R.layout.sugerencia_item,
            null,from,to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER)
        buscadorMapa.suggestionsAdapter = adaptadorCursor
        buscadorMapa.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                muestraResultados(query)
                Log.d("busc",query)
                return false
            }
            override fun onQueryTextChange(query : String?) : Boolean {
                if (query != null) {
                    buscador.obtenerSugerencias("$query ",coordenadas)
                    var cursor = MatrixCursor(arrayOf(BaseColumns._ID,SearchManager.SUGGEST_COLUMN_TEXT_1))
                    buscador.sugerencias.forEachIndexed{
                        indice, item ->
                        cursor.addRow(arrayOf(indice,item.title))
                    }
                    adaptadorCursor.changeCursor(cursor)
                }
                return false
            }
        })
        buscadorMapa.setOnSuggestionListener(object: SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {return false}
            override fun onSuggestionClick(position: Int): Boolean {
                val cursor = buscadorMapa.suggestionsAdapter.getItem(position) as MatrixCursor
                val selection = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
                buscadorMapa.setQuery(selection, false)
                // Do something with selection
                return true
            }
        })
    }
    private fun muestraResultados(query:String){
        buscador.obtenerSugerencias(query,coordenadas)
        var localCoordinates = GeoCoordinates(longitudDefault,latitudDefault)
        buscador.sugerencias.forEach{
            if(it.title.equals(query)){
                localCoordinates = it.geoCoordinates!!
                return@forEach
                /*var sitio = Sitio(it.title,
                    mutableMapOf("latitud" to it.geoCoordinates!!.latitude,
                      "longitud" to it.geoCoordinates!!.longitude),it.id)
                sitio.id?.let { it1 -> db.collection("testingPlacesv2").document(it1).set(sitio) }*/
            }
            else{
                localCoordinates = buscador.sugerencias[0].geoCoordinates!!
                return@forEach
            }
        }
        mapa.camera.target = localCoordinates
        mapa.camera.zoomLevel = 15.0
        if(esPrimera)markerDeResultado(localCoordinates)
        resMarker.coordinates = localCoordinates
    }
    private fun markerDeResultado(coordinates : GeoCoordinates){
        var mapImage = MapImageFactory.fromResource(this.resources,R.drawable.res_marker)
        var markerStyle = MapMarkerImageStyle()
        markerStyle.anchorPoint = Anchor2D(1.0, 1.0)
        resMarker.addImage(mapImage,markerStyle)
        mapa.mapScene.addMapMarker(resMarker)
        esPrimera = false
    }
    fun getCoordenadas(){
        var location = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var hasGps = location.isProviderEnabled(LocationManager.GPS_PROVIDER)
        var hasNetwork = location.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (hasGps || hasNetwork) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                println("no granted")
                ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION),1)
            }
        }
        location.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0F
        ) { }
        var locationGPS = location.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        var locationNet = location.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        if(locationGPS != null){
            longitudDefault = locationGPS.longitude
            latitudDefault = locationGPS.latitude
            coordenadas = GeoCoordinates(latitudDefault,longitudDefault)
        }
        if(locationNet != null){
            longitudDefault = locationNet.longitude
            latitudDefault = locationNet.latitude
            coordenadas = GeoCoordinates(latitudDefault,longitudDefault)
        }
    }

    private fun loadMarkers(){
        db.collection("inmueblesv4").get().addOnCompleteListener {
            if(it.isSuccessful){
                for(result in it.result){
                    val inmueble = result.toObject(DataInmueble::class.java)
                    inmuebles.add(inmueble)
                    println("Inmueble " + inmueble.id)
                    val place = inmueble.direccion
                    val mapImage = MapImageFactory.fromResource(this.resources,R.drawable.map_marker)
                    val mapMarker = place?.coordenadas?.get("latitud")?.let { it1 ->
                        place.coordenadas!!["longitud"]?.let { it2 ->
                            GeoCoordinates(
                                it1,
                                it2
                            )
                        }
                    }?.let { it2 -> MapMarker(it2) }
                    val markerStyle = MapMarkerImageStyle()
                        markerStyle.anchorPoint = Anchor2D(.5, .5)
                    mapMarker?.addImage(mapImage,markerStyle)
                    val metadata = Metadata()
                    inmueble.id?.let { it1 -> metadata.setString("id", it1) }
                    if (mapMarker != null) {
                        mapMarker.metadata = metadata
                        println("${mapMarker.metadata!!.getString("id")}")
                        mapa.mapScene.addMapMarker(mapMarker)
                    }
                }
            }
        }
    }
    private fun setTapGestureHandler() {
        mapa.gestures.tapListener = object : TapListener {
            override fun onTap(touchPoint : Point2D) {
                pickMapMarker(touchPoint)
            }
        }
    }

    private fun pickMapMarker(touchPoint : Point2D) {
        val radiusInPixel = 2.0
        mapa.pickMapItems(touchPoint, radiusInPixel,
            PickMapItemsCallback { pickMapItemsResult ->
                var marker = pickMapItemsResult?.topmostMarker
                if (marker != null) {
                    println("${marker.metadata?.getString("id")}")
                    marker.metadata?.getString("id")?.let { mostrarFichaInmueble(it) }
                }
            })
    }
    private fun mostrarFichaInmueble(id :String){
        val goFicha = Intent(this, AdaptadorFichaInmueble::class.java)
        goFicha.putExtra("user", user)
        inmuebles.forEach stop@{
            if(it.id == id){
                goFicha.putExtra("inmueble", Inmueble().adaptarInmuble(it))
                return@stop
            }
        }
        goFicha.putExtra("desdeMisPisos", false)
        startActivity(goFicha)
    }
    private fun loadMapScene() {
        // Load a scene from the SDK to render the map with a map style.
        mapa.getMapScene().loadScene(MapStyle.NORMAL_DAY,
            LoadSceneCallback { errorCode ->
                if (errorCode == null) {
                    mapa.getCamera().setTarget(GeoCoordinates(latitudDefault,longitudDefault))
                    mapa.getCamera().setZoomLevel(14.0)
                } else {
                    Log.d("Error", "onLoadScene failed: $errorCode")
                }
            })
    }
    override fun onPause() {
        super.onPause()
        mapa.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapa.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapa.onDestroy()
    }

    override fun onBackPressed() {
        finish()
    }
}