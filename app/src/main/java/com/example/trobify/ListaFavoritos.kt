package com.example.trobify

import android.app.SearchManager
import android.content.Intent
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.Serializable
import java.time.LocalDateTime
import kotlin.random.Random

class ListaFavoritos () : AppCompatActivity() , AdaptadorInmuebleBusqueda.OnItemClickListener {

    val db = Firebase.firestore
    lateinit var caja : RecyclerView
    lateinit var userId : String

    override fun onItemClicked(dataInmueble : DataInmueble) {
        val goFicha = Intent(this, AdaptadorFichaInmueble::class.java)
        goFicha.putExtra("inmueble", Inmueble().adaptarInmuble(dataInmueble))
        goFicha.putExtra("user", userId)
        startActivity(goFicha)
        Log.i("inmueble", dataInmueble.id.toString())
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



        var listaIdFavoritos = cargarIdFavoritos(userId)

        var pisosFav = cargarInmueblesFav(listaIdFavoritos)

        mostrar(pisosFav)


    }

    fun cargarIdFavoritos(userId : String) : ArrayList<String>{

        var favoritos : ArrayList<String> = arrayListOf()

        userId.let {db.collection("users").whereEqualTo("id",it)
            .get()
            .addOnCompleteListener(){ task ->
                if(task.isSuccessful) {
                    for(u in task.result) {
                        favoritos = u.data.get("favorites") as ArrayList<String>
                        Log.d("wiiiiiiiiiii" , "los fav de jt : " + favoritos.toString())
                    }

                }else{
                    Log.d("wiiiiiiii", "pk no carga???")
                }
            }
        }
        return favoritos
    }


    fun cargarInmueblesFav(listaIdFavoritos : ArrayList<String>) : ArrayList<DataInmueble> {

        var res : ArrayList<DataInmueble> = arrayListOf()

        Log.d("wiiiiiiiiiii", listaIdFavoritos.size.toString() )

        if(listaIdFavoritos.size != 0){
            for (i in 0..listaIdFavoritos.size){
                var pisoId = listaIdFavoritos.get(i)

                db.collection("inmueblesv2").whereEqualTo("id", pisoId)
                    .get()
                    .addOnCompleteListener() { task ->
                        if (task.isSuccessful) {
                            for (u in task.result) {
                                var piso = u.toObject(DataInmueble::class.java)
                                res.add(piso)
                            }
                        }
                    }
            }
        }
        //else no fav show
        return res
    }

    fun mostrar(pisosFav : ArrayList<DataInmueble>){
        caja.setHasFixedSize(true)
        val layoutmanager = LinearLayoutManager(baseContext)
        caja.layoutManager = layoutmanager
        caja.adapter = AdaptadorInmuebleBusqueda(pisosFav,this)


    }
}


