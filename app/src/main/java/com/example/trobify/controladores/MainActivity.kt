package com.example.trobify.controladores

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.trobify.OrdenacionTest
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

        val goLogin = Intent(this, Login::class.java)
        startActivity(goLogin)

        OrdenacionTest().pruebas()
    }
}


