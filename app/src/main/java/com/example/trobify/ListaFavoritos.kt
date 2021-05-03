package com.example.trobify

import android.app.SearchManager
import android.content.ContentValues
import android.content.Intent
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.io.Serializable
import java.time.LocalDateTime
import kotlin.random.Random

class  ListaFavoritos () : AppCompatActivity() , AdaptadorInmuebleBusqueda.OnItemClickListener {

    val db = Firebase.firestore
    lateinit var caja : RecyclerView
    lateinit var userId : String

    override fun onItemClicked(dataInmueble : DataInmueble) {
        val goFicha = Intent(this, AdaptadorFichaInmueble::class.java)
        goFicha.putExtra("inmueble", Inmueble().adaptarInmuble(dataInmueble))
        goFicha.putExtra("user", userId)
        goFicha.putExtra("desdeMapa",false)
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


    }

    fun cargarFavDe(userId : String){


        val sfDocRef = db.collection("users").document(userId.toString())

        db.runTransaction { transaction ->
            val snapshot = transaction.get(sfDocRef)
            var favoritosStringArray = snapshot.get("favorites")!! as ArrayList<String>

            cargarInmuebles(favoritosStringArray.distinct() as ArrayList<String>)


        }.addOnSuccessListener { result ->
            Log.d(ContentValues.TAG, "Transaction success: $result")
        }.addOnFailureListener { e ->
            Log.w(ContentValues.TAG, "Transaction failure.", e)
        }

    }


    fun cargarInmuebles(listaIdFavoritos : ArrayList<String>) {

        var arrayDePisos  = arrayListOf<DataInmueble>()
        Log.d("pepepepepe", "long de array " + listaIdFavoritos.size.toString())
        if(listaIdFavoritos.size != 0) {
            for (i in 0..listaIdFavoritos.size) {
                var pisoId = listaIdFavoritos.get(i)
                Log.d("pepepepepe", "id del i = " + i + " es  " + pisoId)

                val sfDocRef = db.collection("inmueblesv3").document(pisoId)

                sfDocRef.get().addOnSuccessListener { document ->
                    val piso = document.toObject<DataInmueble>()
                    Log.d("pepepepepe", "el inmueble: " + piso.toString())
                    if (piso != null) {
                        arrayDePisos.add(piso)
                    }
                    if (arrayDePisos.size== listaIdFavoritos.size){mostrar(arrayDePisos)}
                }
            }
        }
    }

    fun mostrar(pisosFav : ArrayList<DataInmueble>){
        caja.setHasFixedSize(true)
        val layoutmanager = LinearLayoutManager(baseContext)
        caja.layoutManager = layoutmanager
        caja.adapter = AdaptadorInmuebleBusqueda(pisosFav,this)
    }


}


