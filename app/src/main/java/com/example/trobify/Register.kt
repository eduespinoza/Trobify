package com.example.trobify

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase



class Register : AppCompatActivity() {
    var name:String? = null
    var surname:String? = null
    var email:String? = null
    var password:String? = null
    var comPassword:String? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth

        val bBack = findViewById<ImageButton>(R.id.buttonGoBack)
        bBack.setOnClickListener { finish() }

        val bRegister = findViewById<Button>(R.id.buttonNext)
        bRegister.setOnClickListener { check() }
    }

    private fun check() {
        if(checkName()){ if(checkSurname()){ if(checkEmail()){ if (checkPassWord()){
                        createNewUser(email.toString(), password.toString())
        } } } }
    }

    private  fun createNewUser(uEmail :String, uPassword :String){
        val id :Int = -1
        auth.createUserWithEmailAndPassword(uEmail, uPassword).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
    }

    private fun updateUI(user: FirebaseUser?) {

    }

    //Checks
    private fun checkName() : Boolean{
        println(2)
        name  = findViewById<EditText>(R.id.editTextName).text.toString()
        if(name!!.isEmpty()){println(3);  emptyMessage(); return false}
        val regex = "^[A-Za-z]*$".toRegex()
        if(!name!!.matches(regex)){incorrectNameMessage(); return false}
        return true
    }

    private fun checkSurname() : Boolean{
        surname  = findViewById<EditText>(R.id.editTextSurname).text.toString()
        if(surname!!.isEmpty()){ emptyMessage(); return false}
        val regex = "^[A-Za-z]*$".toRegex()
        if(!surname!!.matches(regex)){ incorrectSurnameMessage(); return false}
        return true;
    }

    private fun checkEmail() : Boolean{
        email  = findViewById<EditText>(R.id.editTextEmail).text.toString()
        if(email!!.isEmpty()){ emptyMessage(); return false}
        val emailPattern = Patterns.EMAIL_ADDRESS
        if(!emailPattern.matcher(email).matches()){ incorrectEmailMessage(); return false}
        return true
    }

    private fun checkPassWord() : Boolean{
        password  = findViewById<EditText>(R.id.editTextPassword).text.toString()
        comPassword = findViewById<EditText>(R.id.editTextPassword2).text.toString()
        if(password!!.isEmpty() || comPassword!!.isEmpty()){ emptyMessage(); return false}
        if(password!!.length < 7){ shortPasswordMessage(); return false}
        if(!password.equals(comPassword)){ PasswordNotEqualMessage(); return false;}
        return true
    }
    //errors
    private fun emptyMessage() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error: Rellene todos los campos")
        builder.setMessage(" Debe rellenar todos los campos. ")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setNeutralButton("  Continue  "){ _, _ -> }
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

    private fun PasswordNotEqualMessage() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error:Contraseñas no coinciden")
        builder.setMessage(" Revise las contraseñas, deben coincidir ")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setNeutralButton("  Continue  "){ _, _ -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}
