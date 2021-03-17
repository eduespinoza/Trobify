package com.example.trobify

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainTrobify : AppCompatActivity() {
    //var test = InmuebleTest("Piso en la playa", 700, 69, arrayOf(R.drawable.piso4, R.drawable.piso5))
    //var test4 = InmuebleTest("Piso en la playa", 700, 69, arrayOf(R.drawable.piso4, R.drawable.piso5))
    //var test5 = InmuebleTest("Piso en la playa", 700, 69, arrayOf(R.drawable.piso4, R.drawable.piso5))
    //var test2 = InmuebleTest("Piso en el campo", 700, 69, arrayOf(R.drawable.piso5, R.drawable.piso5))
    //var test3 = InmuebleTest("Piso en la montaña", 700, 69, arrayOf(R.drawable.piso4, R.drawable.piso5))
    //private val adaptadorInmueble by lazy {
       // AdaptadorInmuebleBusqueda(arrayListOf(test,test2, test3,test4,test5))
    //}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trobify_main)
        crearInmueble()
    }
    fun crearInmueble(){
        var recycler = findViewById<RecyclerView>(R.id.recycler)
        recycler.setHasFixedSize(true)
        val layoutmanager = LinearLayoutManager(baseContext)
        recycler.layoutManager = layoutmanager
        //recycler.adapter = adaptadorInmueble
    }
}