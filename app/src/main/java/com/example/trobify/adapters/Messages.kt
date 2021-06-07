package com.example.trobify.adapters

import android.content.Context
import androidx.appcompat.app.AlertDialog


class Messages {
     fun emptyMessage(context : Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error: Rellene todos los campos")
        builder.setMessage(" Debe rellenar todos los campos. ")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setNeutralButton("  Continue  "){ _, _ -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun incorrectNameMessage(context : Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error: Nombre incorrecto")
        builder.setMessage(" Asegúrese de escribir bien el nombre. ")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setNeutralButton("  Continue  "){ _, _ -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun incorrectSurnameMessage(context : Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error: Apellido incorrecto")
        builder.setMessage(" Asegúrese de escribir bien el apellido. ")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setNeutralButton("  Continue  "){ _, _ -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

     fun incorrectEmailMessage(context : Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error: Email no valido")
        builder.setMessage(" Asegúrese de escribir bien el email. ")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setNeutralButton("  Continue  "){ _, _ -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun shortPasswordMessage(context : Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error:Contraseña demasiado corta")
        builder.setMessage(" Por razones de seguridad, prueba a escribir una contraseña más larga. ")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setNeutralButton("  Continue  "){ _, _ -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun PasswordNotEqualMessage(context : Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error:Contraseñas no coinciden")
        builder.setMessage(" Revise las contraseñas, deben coincidir ")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setNeutralButton("  Continue  "){ _, _ -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun finishMessage(context : Context, name : String){
        val builder =  AlertDialog.Builder(context)
        builder.setTitle("Bienvenido: " + name)
        builder.setMessage(" Su usuario se ha registrado correctamente. " + '\n' + '\n' +
                "Ahora podrá ver los inmuebles publicados, pero para poder publicar sus propios inmuebles o contactar con otros clientes debe confirmar el correo electronico mediante el mensaje que le hemos enviado.")
        builder.setIcon(android.R.drawable.ic_dialog_email)
        builder.setPositiveButton("  Continue  ",{ _, _ -> })
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun emailAlreadyInUseMessage(context : Context){
        val builder =  AlertDialog.Builder(context)
        builder.setTitle("Error: Correo ya registrado")
        builder.setMessage(" El correo ya esta asignado a otra cuenta ")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton("  Continue  ", { _, _ -> })
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}