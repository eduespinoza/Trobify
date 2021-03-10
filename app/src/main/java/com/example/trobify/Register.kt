package com.example.trobify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val bBack = findViewById<ImageButton>(R.id.buttonGoBack)
        bBack.setOnClickListener {
            val goBack = Intent(this, Login::class.java)
            finish()
            
        }

    }
}