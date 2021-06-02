package com.example.trobify.controladores

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.trobify.R
import com.example.trobify.models.Database
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        Database
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val goPublicarInmueble = Intent(this, OfertarInmueble::class.java)
        //startActivity(goPublicarInmueble)

        val goLogin = Intent(this, Login::class.java)
        startActivity(goLogin)

        //var pisito = Inmueble("125", usuario, 3,1, 75,"calle" ,dir,"piso", "vender", 500,
            //arrayListOf(R.drawable.piso1, R.drawable.piso2, R.drawable.piso3,R.drawable.piso4,R.drawable.piso5),
            //arrayListOf("imagen1","imagen2", "imagen3", "imagen4", "imagen5"), "B", "Piso ideal para estudiantes....bla bla ",
            //"obre nueva", false, true, true, false, false,false, false,false, LocalDateTime.now())
    }
}


