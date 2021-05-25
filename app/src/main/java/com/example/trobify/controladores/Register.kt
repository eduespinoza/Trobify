package com.example.trobify.controladores

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.trobify.models.DataUser
import com.example.trobify.R
import com.example.trobify.models.Database
import com.example.trobify.models.Messages
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.regex.Matcher
import java.util.regex.Pattern


class Register : AppCompatActivity() {
    private var name:String? = null
    private var surname:String? = null
    private var email:String? = null
    private var password:String? = null
    private var comPassword:String? = null
    private var messageCreator = Messages()
    private var result : Boolean = false

    private var db = Firebase.firestore
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
        if(checkName()){ if(checkSurname()){ if(checkEmail()){ if (checkPassWord()){
            createNewUser(email.toString(), password.toString())
        } } } }
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

    //Checks
    private fun checkName() : Boolean{
        name  = findViewById<EditText>(R.id.editTextName).text.toString()
        val pattern : Pattern = Pattern.compile("[ 0-9A-Za-zñÑáéíóúÁÉÍÓÚ]{1,50}")
        if(name!!.isEmpty()){println(3);  messageCreator.emptyMessage(this); return false}
        for (element in name!!) {
            val matcher : Matcher = pattern.matcher(element.toString())
            if (!(matcher.matches()|| element == ' ')) {
                messageCreator.incorrectNameMessage(this)
                return false
            }
        }
        return true
    }

    private fun checkSurname() : Boolean{
        surname  = findViewById<EditText>(R.id.editTextSurname).text.toString()
        val pattern : Pattern = Pattern.compile("[ 0-9A-Za-zñÑáéíóúÁÉÍÓÚ]{1,50}")
        if(surname!!.isEmpty()){ messageCreator.emptyMessage(this); return false}
        for (element in surname!!) {
            val matcher : Matcher = pattern.matcher(element.toString())
            if (!(matcher.matches()|| element == ' ')) {
                messageCreator.incorrectSurnameMessage(this)
                return false
            }
        }
        return true
    }

    private fun checkEmail() : Boolean{
        email  = findViewById<EditText>(R.id.editTextEmail).text.toString()
        if(email!!.isEmpty()){ messageCreator.emptyMessage(this); return false}
        val emailPattern = Patterns.EMAIL_ADDRESS
        if(!emailPattern.matcher(email).matches()){ messageCreator.incorrectEmailMessage(this); return false}
        val emailList = Database.getAllUsersEmails()
        if(emailList.contains(email!!)){ messageCreator.emailAlreadyInUseMessage(this); return false}
        return true
    }

    private fun checkPassWord() : Boolean{
        password  = findViewById<EditText>(R.id.editTextPassword).text.toString()
        comPassword = findViewById<EditText>(R.id.editTextPassword2).text.toString()
        if(password!!.isEmpty() || comPassword!!.isEmpty()){ messageCreator.emptyMessage(this); return false}
        if(password!!.length < 7){ messageCreator.shortPasswordMessage(this); return false}
        if(!password.equals(comPassword)){ messageCreator.PasswordNotEqualMessage(this); return false;}
        return true
    }
}