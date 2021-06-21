package com.example.trobify.controladores

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.trobify.R
import com.example.trobify.database.Database
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        Database
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       //OrdenacionTest().pruebas()

        val goLogin = Intent(this, Login::class.java)
        startActivity(goLogin)

        //GestionFiltrosTest().tests()
    }
}


