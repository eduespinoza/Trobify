package com.example.trobify

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.core.view.children
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.internal.NavigationMenu
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class MainTrobify : AppCompatActivity() {
    var test = Inmueble("la playa", 700, 69, arrayOf(R.drawable.piso4, R.drawable.piso5))
    var test4 = Inmueble("la playa", 700, 69, arrayOf(R.drawable.piso4, R.drawable.piso5))
    var test5 = Inmueble("la playa", 700, 69, arrayOf(R.drawable.piso4, R.drawable.piso5))
    var test2 = Inmueble("el campo", 700, 69, arrayOf(R.drawable.piso5, R.drawable.piso5))
    var test3 = Inmueble("la montaña", 700, 69, arrayOf(R.drawable.piso4, R.drawable.piso5))
    private val adaptadorInmueble by lazy {
        AdaptadorInmuebleBusqueda(arrayListOf(test,test2, test3,test4,test5))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trobify_main)
        actionBar?.hide()
        crearInmueble()
        setListeners()
    }

    fun crearInmueble(){
        var recycler = findViewById<RecyclerView>(R.id.recycler)
        recycler.setHasFixedSize(true)
        val layoutmanager = LinearLayoutManager(baseContext)
        recycler.layoutManager = layoutmanager
        recycler.adapter = adaptadorInmueble
    }
    fun setListeners(){
        var botonLateral : Button = findViewById(R.id.botonLateral)
        var menuLateral : DrawerLayout = findViewById(R.id.drawer_layout)
        var filtrar : TextView = findViewById(R.id.filtrar)

        //listener abrir menu lateral
        botonLateral.setOnClickListener {
            menuLateral.openDrawer(GravityCompat.START)
            menuLateral.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }
        filtrar.setOnClickListener {
            val irAFiltrar = Intent(this, FiltrosBusqueda::class.java)
            startActivity(irAFiltrar)
        }
    }
}