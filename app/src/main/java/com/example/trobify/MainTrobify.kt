package com.example.trobify

import android.app.SearchManager
import android.content.Intent
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainTrobify : AppCompatActivity() {
    //private lateinit var database: DatabaseReference
    var test = Inmueble("la playa", 700, 69, arrayOf(R.drawable.piso4, R.drawable.piso5))
    var test4 = Inmueble("la playa", 700, 69, arrayOf(R.drawable.piso4, R.drawable.piso5))
    var test5 = Inmueble("la playa", 700, 69, arrayOf(R.drawable.piso4, R.drawable.piso5))
    var test2 = Inmueble("el campo", 700, 69, arrayOf(R.drawable.piso5, R.drawable.piso5))
    var test3 = Inmueble("la montaña", 700, 69, arrayOf(R.drawable.piso4, R.drawable.piso5))

    lateinit var listaConResultados : RecyclerView
    val sugerencias = Busqueda()

    override fun onCreate(savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trobify_main)
        listaConResultados = findViewById(R.id.recycler)
        actionBar?.hide()
        prepararPrimerosResultados()
        prepararBuscador()
        setListeners()
    }
    private val adaptadorInmueble by lazy {
        //AdaptadorInmuebleBusqueda()
    }

    fun prepararPrimerosResultados(){
        listaConResultados.setHasFixedSize(true)
        val layoutmanager = LinearLayoutManager(baseContext)
        listaConResultados.layoutManager = layoutmanager
        var data = cargarInmueblesDesdeBd {
                var adapter = AdaptadorInmuebleBusqueda(it)
                listaConResultados.adapter = adapter
        }
    }
    fun prepararBuscador(){
        var buscador : SearchView = findViewById(R.id.buscarView)
        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val to = intArrayOf(R.id.item_label)
        var adaptadorCursor = SimpleCursorAdapter(this.baseContext,R.layout.sugerencia_item,
            null,from,to,CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER)
        buscador.suggestionsAdapter = adaptadorCursor
        buscador.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                mostrarResultados(query)
                Log.d("busc",query)
                return false
            }
            override fun onQueryTextChange(query : String?) : Boolean {
                if (query != null) {
                    sugerencias.obtenerSugerencias(query)
                    var cursor = MatrixCursor(arrayOf(BaseColumns._ID,SearchManager.SUGGEST_COLUMN_TEXT_1))
                    sugerencias.sugerencias.forEachIndexed{
                        indice, item ->
                        cursor.addRow(arrayOf(indice,item))
                    }
                    adaptadorCursor.changeCursor(cursor)
                }
                return false
            }
        })
    }
    fun setListeners(){
        var botonLateral : Button = findViewById(R.id.botonLateral)
        var menuLateral : DrawerLayout = findViewById(R.id.drawer_layout)
        var filtrar : TextView = findViewById(R.id.filtrar)
        var mapa : TextView = findViewById(R.id.verMapa)
        //listener abrir menu lateral
        botonLateral.setOnClickListener {
            menuLateral.openDrawer(GravityCompat.START)
        }
        filtrar.setOnClickListener {
            val irAFiltrar = Intent(this, FiltrosBusqueda::class.java)
            startActivity(irAFiltrar)
        }
        mapa.setOnClickListener {
            //abrir mapa
            Log.d("drawe","HOOOOLA")
        }

        /*nav.setOnClickListener {
            Log.d("drawe","HOOOOLA")
        }*/
    }


    fun mostrarResultados(busqueda : String){
        var nuevaBusqueda = Busqueda()
        nuevaBusqueda.buscar(busqueda.toUpperCase())
        var data = nuevaBusqueda.obtenerResultados{
            listaConResultados.adapter = AdaptadorInmuebleBusqueda(it)
        }
    }

    fun cargarInmueblesDesdeBd(myCallback : (ArrayList<DataInmueble>) -> Unit){
        val db = Firebase.firestore
        var pisosTochos = arrayListOf<DataInmueble>()
        db.collection("inmuebles").whereEqualTo("direccion","LABAILA")
            .get().addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    for(ficha in task.result){
                    var pisito = ficha.toObject(DataInmueble::class.java)
                    pisosTochos.add(pisito)
                    }
                    myCallback(pisosTochos)
                }
            }
    }
    //fun para generar id's de inmuebles aleatorios *** se podria meter en clase inmueble
    // no se comprueba que el id se repita con otro ya puesto
    fun generateRandomId() : String {
        val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val pathId = (1..5)
            .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("");
        return pathId
    }

    //fun para generar pisos en bd
    /*fun generatePisos() {
        var calles = arrayListOf<String>("RUSSAFA", "JOSÉ GROLLO", "JOSÉ MARIA FUSTER", "JOSE MARIA HARO (MAGISTRAT)","BADAJOZ", "JACINT", "LABAILA", "JACOMART", "PERIODISTA JOSÉ OMBUENA", "JOAQUIM DUALDE", "JOAQUIM NAVARRO")
        for (callejones : String in calles){
            subirInmueblesBD(Inmueble(generateRandomId(),null,kotlin.random.Random.nextInt(1,4),20,
                callejones,null,null,null,null,null,null,null))
            subirInmueblesBD(Inmueble(generateRandomId(),null,kotlin.random.Random.nextInt(1,4),20,
                callejones,null,null,null,null,null,null,null))
            subirInmueblesBD(Inmueble(generateRandomId(),null,kotlin.random.Random.nextInt(1,4),20,
                callejones,null,null,null,null,null,null,null))
        }
    }
    private fun subirInmueblesBD(inmueble : Inmueble){
        val database = Firebase.firestore
        inmueble.id?.let { database.collection("inmuebles").document(it).set(inmueble) }
    }
    */
}
