package com.example.trobify

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trobify.adapters.AdaptadorFichaInmueble
import com.example.trobify.adapters.AdaptadorInmuebleBusqueda
import com.example.trobify.models.Inmueble
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlin.collections.ArrayList

class  MisPisos : AppCompatActivity() , AdaptadorInmuebleBusqueda.OnItemClickListener {
    val database = Database()
    val db = Firebase.firestore
    lateinit var caja : RecyclerView
    lateinit var userId : String




    override fun onItemClicked(dataInmueble : DataInmueble) {

        val goFicha = Intent(this, AdaptadorFichaInmueble::class.java)
        goFicha.putExtra("user", userId)
        goFicha.putExtra("inmueble", Inmueble().adaptarInmuble(dataInmueble))
        goFicha.putExtra("desdeMisPisos", true)
        startActivity(goFicha)


    }

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_pisos)
        caja = findViewById(R.id.recyclerMisPisos)

        userId = intent.extras!!.get("user") as String



        val buttonAtras = findViewById<Button>(R.id.buttonAtrasMisPisos)
        buttonAtras.setOnClickListener {
            finish()
        }
        cargarPisosDe(userId)




    }

    fun cargarPisosDe(userId : String){


        val sfDocRef = db.collection("users").document(userId.toString())

        db.runTransaction { transaction ->
            val snapshot = transaction.get(sfDocRef)
            var pisosStringArray = snapshot.get("pisos")
            cargarInmuebles(pisosStringArray as ArrayList<String>)

        }.addOnSuccessListener { result ->
            Log.d(ContentValues.TAG, "Transaction success: $result")
        }.addOnFailureListener { e ->
            Log.w(ContentValues.TAG, "Transaction failure.", e)
        }

    }


    fun cargarInmuebles(listaIdPisos : ArrayList<String>) {
        var arrayDePisos  = arrayListOf<DataInmueble>()

        for (i in 0..listaIdPisos.size - 1) {
            var pisoId = listaIdPisos[i]
            val sfDocRef = db.collection("inmueblesv4").document(pisoId)
            sfDocRef.get().addOnSuccessListener { document ->
                val piso = document.toObject<DataInmueble>()
                if (piso != null) {
                    arrayDePisos.add(piso)
                }
            }.addOnSuccessListener {
                mostrar(arrayDePisos)
            }
        }
    }

    private fun mostrar(pisosFav : ArrayList<DataInmueble>){
        caja.setHasFixedSize(true)
        val layoutmanager = LinearLayoutManager(baseContext)
        caja.layoutManager = layoutmanager
        caja.adapter = AdaptadorInmuebleBusqueda(pisosFav, arrayListOf(""),this)
    }






}
