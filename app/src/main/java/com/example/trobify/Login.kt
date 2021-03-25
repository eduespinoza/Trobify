package com.example.trobify

import android.content.ContentValues
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
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject

class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    var name:String? = null
    var email:String? = null
    var password:String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth

        checkUser()

        val buttonRegistrar = findViewById<Button>(R.id.buttonRegister)
        buttonRegistrar.setOnClickListener{
            val goRegister = Intent(this, Register::class.java)
            startActivity(goRegister)
        }

        val buttonAcceder = findViewById<Button>(R.id.buttonAcces)
        buttonAcceder.setOnClickListener{
            if(findViewById<EditText>(R.id.editTextLoginEmail).text.isNullOrEmpty() || findViewById<EditText>(R.id.editTextLoginPassword).text.isNullOrEmpty()){
                errorMessage()
            }else{ login() }
        }
    }

    private fun login(){
        email  = findViewById<EditText>(R.id.editTextLoginEmail).text.toString()
        password  = findViewById<EditText>(R.id.editTextLoginPassword).text.toString()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    checkUser()

                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    errorMessage()
                }
            }

    }

    private fun checkUser(){
        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this, MainTrobify::class.java)
            intent.putExtra("user", currentUser.uid)
            startActivity(intent)

            finish()
        }
    }

    private fun errorMessage() {
        val builder =  AlertDialog.Builder(this)
        builder.setTitle("Error de credenciales " )
        builder.setMessage(" Compruebe los datos introducidos. ")
        builder.setIcon(android.R.drawable.stat_notify_error)
        builder.setNeutralButton("  Continue  "){ _, _ -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
        email = null
        password = null
    }
}