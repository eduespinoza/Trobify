package com.example.trobify

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListaFavoritos () : AppCompatActivity() {


    var user = User("Pepe", "Viyuela", "correo@correo.com", "pepe123", "123456789")

    private val adaptadorInmueble by lazy {
        //AdaptadorInmuebleBusqueda(user.getFav())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lista_favoritos)
        actionBar?.hide()
        //user = MainTrobify.getUser()
        crearInmueble()

        val buttonAtras= findViewById<Button>(R.id.buttonAtrasFavoritos)
        buttonAtras.setOnClickListener{
            val goMain = Intent(this, MainTrobify::class.java)
            startActivity(goMain)
        }
    }

    fun crearInmueble(){
        var recycler = findViewById<RecyclerView>(R.id.recyclerFavoritos)
        recycler.setHasFixedSize(true)
        val layoutmanager = LinearLayoutManager(baseContext)
        recycler.layoutManager = layoutmanager
        //recycler.adapter = adaptadorInmueble
    }



}
