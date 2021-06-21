package com.example.trobify.controladores

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trobify.R
import com.example.trobify.adapters.AdaptadorFichaInmueble
import com.example.trobify.adapters.AdaptadorInmuebleBusqueda
import com.example.trobify.models.DataInmueble2
import com.example.trobify.database.Database
import com.example.trobify.models.Inmueble
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.collections.ArrayList

class  ListaFavoritos () : AppCompatActivity() , AdaptadorInmuebleBusqueda.OnItemClickListener {

    val db = Firebase.firestore
    lateinit var caja : RecyclerView
    lateinit var userId : String

    override fun onItemClicked(dataInmueble : DataInmueble2) {
        val goFicha = Intent(this, AdaptadorFichaInmueble::class.java)
        goFicha.putExtra("inmueble", Inmueble.Builder().build().adaptadorInm(dataInmueble))
        goFicha.putExtra("user", userId)
        goFicha.putExtra("desdeMisPisos", false)
        startActivity(goFicha)
    }

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lista_favoritos)
        caja = findViewById(R.id.recyclerFavoritos)

        userId = intent.extras!!.get("user") as String

        val buttonAtras = findViewById<Button>(R.id.buttonAtrasFavoritos)
        buttonAtras.setOnClickListener {

            finish()
        }
        Database.obtenerFavoritosUsuario(userId)?.let { mostrar(it) }
    }

    private fun mostrar(pisosFav : ArrayList<DataInmueble2>){
        caja.setHasFixedSize(true)
        val layoutmanager = LinearLayoutManager(baseContext)
        caja.layoutManager = layoutmanager
        caja.adapter = AdaptadorInmuebleBusqueda(pisosFav, arrayListOf(""),this)
    }
}


