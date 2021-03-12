package com.example.trobify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.content.ContentValues.TAG

class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    var name:String? = null
    var email:String? = null
    var password:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth
        

        val buttonRegistrar = findViewById<Button>(R.id.buttonRegister)
        buttonRegistrar.setOnClickListener{
            val goRegister = Intent(this, Register::class.java)
            startActivity(goRegister)
        }

        val buttonAcceder = findViewById<Button>(R.id.buttonAcces)
        buttonAcceder.setOnClickListener{
            email  = findViewById<EditText>(R.id.editTextLoginEmail).text.toString()
            password  = findViewById<EditText>(R.id.editTextLoginPassword).text.toString()
            
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        println("Bienvenido: " )

    }
}