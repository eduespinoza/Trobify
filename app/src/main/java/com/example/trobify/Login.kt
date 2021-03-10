package com.example.trobify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val buttonRegistrar = findViewById<Button>(R.id.buttonRegister)
        buttonRegistrar.setOnClickListener{
            val goRegister = Intent(this, Register::class.java)
            startActivity(goRegister)
        }
        val buttonAcceder = findViewById<Button>(R.id.buttonAcces)
        buttonAcceder.setOnClickListener{

        }
    }
}