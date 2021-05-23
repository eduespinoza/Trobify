package com.example.trobify.controladores

import android.content.ContentValues
import android.content.Intent
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
            val goMain = Intent(this, MainTrobify::class.java)
            goMain.putExtra("user", userId)
            startActivity(goMain)

            finish()
        }
        Database.getFavsUser(userId)?.let { mostrar(it) }
        //cargarFavDe(userId)
        /*val noFav = findViewById<TextView>(R.id.noFavText)
        noFav.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40F)
        noFav.text = "No hay favoritos"*/
    }

    private fun mostrar(pisosFav : ArrayList<DataInmueble2>){
        caja.setHasFixedSize(true)
        val layoutmanager = LinearLayoutManager(baseContext)
        caja.layoutManager = layoutmanager
        caja.adapter = AdaptadorInmuebleBusqueda(pisosFav, arrayListOf(""),this)
    }
}


