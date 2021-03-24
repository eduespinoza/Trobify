package com.example.trobify

import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.firebase.ui.database.BuildConfig
import com.google.firebase.firestore.ktx.firestore


class Register : AppCompatActivity() {
    var name:String? = null
    var surname:String? = null
    var email:String? = null
    var password:String? = null
    var comPassword:String? = null

    var db = Firebase.firestore
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
        val user = User(name,surname,email,auth.uid,password)
        val userDb = hashMapOf(
            "name" to name,
            "surname" to surname,
            "email" to email,
            "password" to password,
            "profilePic" to "default",
            "favorites" to arrayListOf<String>()

        )
        user.getId()?.let {
            db.collection("users").document(it)
                .set(userDb)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { Log.w(TAG, "Error writing document" ) }
        }

        finishMessage()
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
        if( emailExist(email) ){ emailAlreadyInUseMessage(); return false}




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

    private fun getAllEmails() : MutableList<String>{
        val emailList : MutableList<String> = mutableListOf()
        val findEmails = db.collection("users").whereEqualTo("email",true)
        findEmails.get()
            .addOnSuccessListener { documents ->
                for (document in documents){
                    emailList.add(document.toString())
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

        return emailList

    }

    private fun emailExist(email : String?) : Boolean{
        val emailList = getAllEmails()
        return emailList.contains(email)

    }

    //Messages
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
        builder.setNeutralButton("  Continue  "){ _, _ -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun incorrectSurnameMessage() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error: Apellido incorrecto")
        builder.setMessage(" Asegúrese de escribir bien el apellido. ")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setNeutralButton("  Continue  "){ _, _ -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun incorrectEmailMessage() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error: Email no valido")
        builder.setMessage(" Asegúrese de escribir bien el email. ")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setNeutralButton("  Continue  "){ _, _ -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun shortPasswordMessage() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error:Contraseña demasiado corta")
        builder.setMessage(" Por razones de seguridad, prueba a escribir una contraseña más larga. ")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setNeutralButton("  Continue  "){ _, _ -> }
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

    private fun finishMessage(){
        val builder =  AlertDialog.Builder(this)
        builder.setTitle("Bienvenido: " + name)
        builder.setMessage(" Su usuario se ha registrado correctamente. ")
        builder.setIcon(android.R.drawable.ic_dialog_email)
        builder.setPositiveButton("  Continue  ", DialogInterface.OnClickListener{ _, _ -> finish() })
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun emailAlreadyInUseMessage(){
        val builder =  AlertDialog.Builder(this)
        builder.setTitle("Error: Correo ya registrado")
        builder.setMessage(" El correo ya esta asignado a otra cuenta ")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton("  Continue  ", DialogInterface.OnClickListener{ _, _ -> finish() })
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}