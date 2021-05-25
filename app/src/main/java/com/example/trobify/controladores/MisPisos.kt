package com.example.trobify.controladores

import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trobify.R
import com.example.trobify.adapters.AdaptadorFichaInmueble
import com.example.trobify.adapters.AdaptadorInmuebleBusqueda
import com.example.trobify.models.DataInmueble
import com.example.trobify.models.DataInmueble2
import com.example.trobify.models.Database
import com.example.trobify.models.Inmueble
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlin.collections.ArrayList

class  MisPisos : AppCompatActivity() , AdaptadorInmuebleBusqueda.OnItemClickListener {
    //lateinit var database : Database
    val db = Firebase.firestore
    lateinit var caja : RecyclerView
    lateinit var userId : String
    lateinit var publicados : TextView
    lateinit var noPublicados : TextView



    override fun onItemClicked(dataInmueble : DataInmueble2) {

        val goFicha = Intent(this, AdaptadorFichaInmueble::class.java)
        goFicha.putExtra("user", userId)
        goFicha.putExtra("inmueble", Inmueble.Builder().build().adaptadorInm(dataInmueble))
        goFicha.putExtra("desdeMisPisos", true)
        startActivity(goFicha)


    }

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_pisos)
        caja = findViewById(R.id.recyclerMisPisos)
        publicados = findViewById(R.id.publicados)
        noPublicados = findViewById(R.id.nopublicados)

        userId = intent.extras!!.get("user") as String

        val buttonAtras = findViewById<Button>(R.id.buttonAtrasMisPisos)
        buttonAtras.setOnClickListener {
            finish()
        }
        publicados.setOnClickListener {
            mostrarPublicados(publicados,noPublicados)
        }
        noPublicados.setOnClickListener {
            mostrarNoPublicados(publicados,noPublicados)
        }
        mostrarPublicados(publicados,noPublicados)
    }
    private fun mostrarNoPublicados(publicados : TextView, noPublicados : TextView){
        noPublicados.setBackgroundColor(Color.BLUE)
        noPublicados.setTextColor(Color.WHITE)
        publicados.setTextColor(Color.BLACK)
        publicados.setBackgroundColor(Color.GRAY)
        var pisos = Database.getPisosNoPostUser(userId)
        if (pisos != null && pisos.isNotEmpty()) {
            mostrar(pisos)
        }else mostrar(arrayListOf())
    }
    private fun mostrarPublicados(publicados : TextView, noPublicados : TextView){
        publicados.setBackgroundColor(Color.BLUE)
        publicados.setTextColor(Color.WHITE)
        noPublicados.setTextColor(Color.BLACK)
        noPublicados.setBackgroundColor(Color.GRAY)
        var pisos = Database.getPisosUser(userId)
        if (pisos != null && pisos.isNotEmpty()) {
            mostrar(pisos)
        }else mostrar(arrayListOf())
    }

    private fun mostrar(pisosFav : ArrayList<DataInmueble2>){
        caja.setHasFixedSize(true)
        val layoutmanager = LinearLayoutManager(baseContext)
        caja.layoutManager = layoutmanager
        caja.adapter = AdaptadorInmuebleBusqueda(pisosFav, arrayListOf(""),this)
    }






}
