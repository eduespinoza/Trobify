package com.example.trobify

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class Register : AppCompatActivity() {
    var name:String = ""
    var surname:String = ""
    var email:String = ""
    var password:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val bBack = findViewById<ImageButton>(R.id.buttonGoBack)
        bBack.setOnClickListener { finish() }

        val bRegister = findViewById<Button>(R.id.buttonNext)
        bRegister.setOnClickListener {
            checkName()
            checkSurname()
            checkEmail()
            checkPassWord()
            crearUsuario()
        }
    }


    private fun checkName(){
        name  = findViewById<TextInputEditText>(R.id.textInputNombre).toString()
        if(name.isEmpty()){ emptyMessage()}
        if(name.filter { it in 'A' .. 'Z' || it in 'a' .. 'z' }.length == name.length){  incorrectNameMessage()}
    }

    private fun checkSurname(){
        surname  = findViewById<TextInputEditText>(R.id.textInputApellidos).toString()
        if(surname.isEmpty()){ emptyMessage()}
        if(surname.filter { it in 'A' .. 'Z' || it in 'a' .. 'z' }.length == surname.length){ incorrectSurnameMessage()}
    }

    private fun checkEmail(){
        email  = findViewById<TextInputEditText>(R.id.textInputEmail).toString()
        if(email.isEmpty()){ emptyMessage()}
        val emailPattern = Patterns.EMAIL_ADDRESS
        if(!emailPattern.matcher(email).matches()){ incorrectEmailMessage()}
    }

    private fun checkPassWord(){
        password  = findViewById<TextInputEditText>(R.id.textInputPassword).toString()
        if(password.isEmpty()){ emptyMessage()}
        if(password.length < 7){ shortPasswordMessage()}
    }

    private fun crearUsuario() : Boolean{
        var bool = true
        lateinit var auth: FirebaseAuth
        auth = Firebase.auth

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                } else {
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    bool = false
                }

            }
        return  bool
    }



    //errors
    private fun emptyMessage() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error: Rellene todos los campos")
        builder.setMessage(" Debe rellenar todos los campos. ")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setNeutralButton("  Continue  "){dialogInterface , which -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun incorrectNameMessage() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error: Nombre incorrecto")
        builder.setMessage(" Asegúrese de escribir bien el nombre. ")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setNeutralButton("  Continue  "){dialogInterface , which -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun incorrectSurnameMessage() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error: Apellido incorrecto")
        builder.setMessage(" Asegúrese de escribir bien el apellido. ")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setNeutralButton("  Continue  "){dialogInterface , which -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun incorrectEmailMessage() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error: Email no valido")
        builder.setMessage(" Asegúrese de escribir bien el email. ")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setNeutralButton("  Continue  "){dialogInterface , which -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun shortPasswordMessage() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error:Contraseña demasiado corta")
        builder.setMessage(" Por razones de seguridad, prueba a escribir una contraseña más larga. ")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setNeutralButton("  Continue  "){dialogInterface , which -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}
