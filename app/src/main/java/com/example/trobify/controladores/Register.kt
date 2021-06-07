package com.example.trobify.controladores

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.trobify.R
import com.example.trobify.adapters.Messages
import com.example.trobify.models.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class Register : AppCompatActivity() {
    private var name:String? = null
    private var surname:String? = null
    private var email:String? = null
    private var password:String? = null
    private var comPassword:String? = null
    private var messageCreator = Messages()
    private lateinit var checker : Chek
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth

        val bBack = findViewById<Button>(R.id.buttonGoBack)
        bBack.setOnClickListener { finish() }

        val bRegister = findViewById<Button>(R.id.buttonNext)
        bRegister.setOnClickListener { check() }
    }

    private fun check() {
        name  = findViewById<EditText>(R.id.editTextName).text.toString()
        surname  = findViewById<EditText>(R.id.editTextSurname).text.toString()
        email  = findViewById<EditText>(R.id.editTextEmail).text.toString()
        password  = findViewById<EditText>(R.id.editTextPassword).text.toString()
        comPassword = findViewById<EditText>(R.id.editTextPassword2).text.toString()

        checker = Chek(name!!, surname!!, email!!, password!!, comPassword!!, messageCreator)
        if(checker.isCorrect(this)){createNewUser(email.toString(), password.toString())}
    }

    private  fun createNewUser(uEmail :String, uPassword :String){
        auth.createUserWithEmailAndPassword(uEmail, uPassword).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "createUserWithEmail:success")
                auth.signInWithEmailAndPassword(email,password)
                finishCreation()

            } else {
                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                Toast.makeText(baseContext, "Authentication failed.",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun finishCreation(){
        val user = DataUser(email,
            arrayListOf(),arrayListOf(), arrayListOf(),auth.uid,name,password,"default",surname)
        Database.subirUsuario(user)
        sendEmail()
        messageCreator.finishMessage(this,name!!)
    }

    private fun sendEmail(){
        var us = Firebase.auth.currentUser
        us.sendEmailVerification().addOnSuccessListener {
            println("------------------------------ Email Sended ---------------------------------------")
        }
    }
}