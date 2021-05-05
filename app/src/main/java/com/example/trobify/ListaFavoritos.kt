package com.example.trobify

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class  ListaFavoritos () : AppCompatActivity() , AdaptadorInmuebleBusqueda.OnItemClickListener {

    val db = Firebase.firestore
    lateinit var caja : RecyclerView
    lateinit var userId : String




    override fun onItemClicked(dataInmueble : DataInmueble) {
        val goFicha = Intent(this, AdaptadorFichaInmueble::class.java)
        goFicha.putExtra("inmueble", Inmueble().adaptarInmuble(dataInmueble))
        goFicha.putExtra("user", userId)
        goFicha.putExtra("desdeMapa", false)
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
        }
        cargarFavDe(userId)

        /*val noFav = findViewById<TextView>(R.id.noFavText)
        noFav.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40F)
        noFav.text = "No hay favoritos"*/


    }

    fun cargarFavDe(userId : String){


        val sfDocRef = db.collection("users").document(userId.toString())

        db.runTransaction { transaction ->
            val snapshot = transaction.get(sfDocRef)
            var favoritosStringArray = snapshot.get("favorites")
            cargarInmuebles(favoritosStringArray as ArrayList<String>)

        }.addOnSuccessListener { result ->
            Log.d(ContentValues.TAG, "Transaction success: $result")
        }.addOnFailureListener { e ->
            Log.w(ContentValues.TAG, "Transaction failure.", e)
        }

    }


    fun cargarInmuebles(listaIdFavoritos : ArrayList<String>) {
        var arrayDePisos  = arrayListOf<DataInmueble>()

        for (i in 0..listaIdFavoritos.size) {
            var pisoId = listaIdFavoritos[i]
            val sfDocRef = db.collection("inmueblesv3").document(pisoId)
            sfDocRef.get().addOnSuccessListener { document ->
                val piso = document.toObject<DataInmueble>()
                if (piso != null) {
                    arrayDePisos.add(piso)
                }
                if (arrayDePisos.size == listaIdFavoritos.size) {
                    mostrar(arrayDePisos)
                }
            }
        }
    }

    private fun mostrar(pisosFav : ArrayList<DataInmueble>){
        caja.setHasFixedSize(true)
        val layoutmanager = LinearLayoutManager(baseContext)
        caja.layoutManager = layoutmanager
        caja.adapter = AdaptadorInmuebleBusqueda(pisosFav, this)
    }






}


