package com.example.trobify

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainTrobify : AppCompatActivity() {
<<<<<<< HEAD
    var test = Inmueble("Piso en la playa", 700, 69.0, arrayOf(R.drawable.piso4, R.drawable.piso5))
    var test4 = Inmueble("Piso en la playa", 700, 69.0, arrayOf(R.drawable.piso4, R.drawable.piso5))
    var test5 = Inmueble("Piso en la playa", 700, 69.0, arrayOf(R.drawable.piso4, R.drawable.piso5))
    var test2 = Inmueble("Piso en el campo", 700, 69.0, arrayOf(R.drawable.piso5, R.drawable.piso5))
    var test3 = Inmueble("Piso en la montaña", 700, 69.0, arrayOf(R.drawable.piso4, R.drawable.piso5))
    private val adaptadorInmueble by lazy {
        AdaptadorInmuebleBusqueda(arrayListOf(test,test2, test3,test4,test5))
    }
=======
    //var test = InmuebleTest("Piso en la playa", 700, 69, arrayOf(R.drawable.piso4, R.drawable.piso5))
    //var test4 = InmuebleTest("Piso en la playa", 700, 69, arrayOf(R.drawable.piso4, R.drawable.piso5))
    //var test5 = InmuebleTest("Piso en la playa", 700, 69, arrayOf(R.drawable.piso4, R.drawable.piso5))
    //var test2 = InmuebleTest("Piso en el campo", 700, 69, arrayOf(R.drawable.piso5, R.drawable.piso5))
    //var test3 = InmuebleTest("Piso en la montaña", 700, 69, arrayOf(R.drawable.piso4, R.drawable.piso5))
    //private val adaptadorInmueble by lazy {
       // AdaptadorInmuebleBusqueda(arrayListOf(test,test2, test3,test4,test5))
    //}
>>>>>>> parent of 85181af (Revert "Chat V2")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trobify_main)
        crearInmueble()
        setListeners()
    }

    fun crearInmueble(){
        var recycler = findViewById<RecyclerView>(R.id.recycler)
        recycler.setHasFixedSize(true)
        val layoutmanager = LinearLayoutManager(baseContext)
        recycler.layoutManager = layoutmanager
        //recycler.adapter = adaptadorInmueble
    }
    fun setListeners(){
        var buscarTrobify = findViewById<TextView>(R.id.buscarTrobify)
        buscarTrobify.setOnClickListener {
            var searchview = findViewById<SearchView>(R.id.buscarView)
            searchview.isIconified = true;
        }
    }
}