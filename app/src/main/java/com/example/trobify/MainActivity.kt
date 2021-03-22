package com.example.trobify

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val goLogin = Intent(this, Login::class.java)
        startActivity(goLogin)

        // para probar maintrobify
        //val goSearch = Intent(this, MainTrobify::class.java)
        //startActivity(goSearch)

        // para probar Filtros
        //val goFiltros = Intent(this, FiltrosBusqueda::class.java)
        //startActivity(goFiltros)

        // pa probar Ficha
        //val goFicha = Intent(this, AdaptadorFichaInmueble::class.java)
        //startActivity(goFicha)

        // pa los Favoritos
        val goFav = Intent(this, ListaFavoritos::class.java)
        startActivity(goFav)
    }
}


