package com.example.trobify.models

import android.content.Context
import android.util.Patterns
import android.widget.EditText
import com.example.trobify.R
import java.util.regex.Matcher
import java.util.regex.Pattern


class Chek(name:String, surname:String, email : String, password : String, password2 : String, messageCreator : Messages){
    private var name = name
    private var surname = surname
    private var email = email
    private var password = password
    private var password2 = password2
    private var messageCreator = messageCreator
    private lateinit var cont: Context

    fun isCorrect(cont : Context) : Boolean {
        this.cont = cont
        if (correctName()) { if (correctSurname()) { if (correctEmail()) { if (correctPassword()) { return true } } } }
        return false
    }

    private fun correctName() : Boolean{
                    val pattern : Pattern = Pattern.compile("[ 0-9A-Za-zñÑáéíóúÁÉÍÓÚ]{1,50}")
                    if(name!!.isEmpty()){println(3);  messageCreator.emptyMessage(cont); return false}
                    for (element in name!!) {
                        val matcher : Matcher = pattern.matcher(element.toString())
                        if (!(matcher.matches()|| element == ' ')) {
                            messageCreator.incorrectNameMessage(cont)
                            return false
                        }
                    }
                    return true
                }

    private fun correctSurname() : Boolean{
                    val pattern : Pattern = Pattern.compile("[ 0-9A-Za-zñÑáéíóúÁÉÍÓÚ]{1,50}")
                    if(surname!!.isEmpty()){ messageCreator.emptyMessage(cont); return false}
                    for (element in surname!!) {
                        val matcher : Matcher = pattern.matcher(element.toString())
                        if (!(matcher.matches()|| element == ' ')) {
                            messageCreator.incorrectSurnameMessage(cont)
                            return false
                        }
                    }
                    return true
                }

    private fun correctEmail() : Boolean{
                    if(email!!.isEmpty()){ messageCreator.emptyMessage(cont); return false}
                    val emailPattern = Patterns.EMAIL_ADDRESS
                    if(!emailPattern.matcher(email).matches()){ messageCreator.incorrectEmailMessage(cont); return false}
                    val emailList = Database.getAllUsersEmails()
                    if(emailList.contains(email!!)){ messageCreator.emailAlreadyInUseMessage(cont); return false}
                    return true
                }

    private fun correctPassword() : Boolean{
                    if(password!!.isEmpty() || password2!!.isEmpty()){ messageCreator.emptyMessage(cont); return false}
                    if(password!!.length < 7){ messageCreator.shortPasswordMessage(cont); return false}
                    if(!password.equals(password2)){ messageCreator.PasswordNotEqualMessage(cont); return false;}
                    return true
                }
}
