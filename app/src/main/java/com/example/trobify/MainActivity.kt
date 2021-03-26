package com.example.trobify

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()


    @RequiresApi(Build.VERSION_CODES.O)
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
        //val dir = Direccion("españita","valencia","46022","av naranjos","50","2","25")
        //var usuario = User("Pepe", "Viyuela", "correo@correo.com", "pepe123", "123456789")

        //var pisito = Inmueble("125", usuario, 3,1, 75,"calle" ,dir,"piso", "vender", 500,
            //arrayListOf(R.drawable.piso1, R.drawable.piso2, R.drawable.piso3,R.drawable.piso4,R.drawable.piso5),
            //arrayListOf("imagen1","imagen2", "imagen3", "imagen4", "imagen5"), "B", "Piso ideal para estudiantes....bla bla ",
            //"obre nueva", false, true, true, false, false,false, false,false, LocalDateTime.now())


        //val goFicha = Intent(this, AdaptadorFichaInmueble::class.java)
        //goFicha.putExtra("inmueble", pisito )
        //startActivity(goFicha)

        //pa los Favoritos
        //val goFav = Intent(this, ListaFavoritos::class.java)
        //startActivity(goFav)
    }
}


