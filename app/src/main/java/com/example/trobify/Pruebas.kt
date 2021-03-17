package com.example.trobify

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class Pruebas : AppCompatActivity() {

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pruebas)
        val email = intent.getStringExtra("Email")
        val text: TextView = findViewById(R.id.textViewUser) as TextView
        text.setOnClickListener {
            text.setText(email)
        }

    }
}