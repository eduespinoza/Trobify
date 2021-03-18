package com.example.trobify

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MessageOfertaContraoferta  : AppCompatActivity(){

    var quantityOk:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_oferta_contraoferta)

        val buttonSendOffer = findViewById<Button>(R.id.buttonSendOffer)

        val textNameInmueble = findViewById<TextView>(R.id.textNameInmueble)
        textNameInmueble.setText()

        val textFeaturesInmueble = findViewById<TextView>(R.id.textFeaturesInmueble)
        textFeaturesInmueble.setText()

        val textLocationInmueble = findViewById<TextView>(R.id.textLocationInmueble)
        textLocationInmueble.setText()

        val textPriceInmueble = findViewById<TextView>(R.id.textPriceInmueble)
        textPriceInmueble.setText()

        buttonSendOffer.setOnClickListener {
            checkOffer()
            if(quantityOk == true){
                createMessage()
                sendMessage()
            }
        }
    }

    private fun checkOffer(){
        val textOfferQuantity = findViewById<EditText>(R.id.textOfferQuantity)
        if(textOfferQuantity.text.toString().toInt() <= 0){
            quantityOk = false
            AlertDialog.Builder(this@MessageOfertaContraoferta).apply {
                setTitle("Error")
                setMessage("El número introducido no puede ser menor o igual a 0.")
                setPositiveButton("Ok", null)
            }.show()
        }
    }

    private fun createMessage(){
        val textOfferQuantity = findViewById<EditText>(R.id.textOfferQuantity)
        Message().setMessage("(Nombre de la perosna) te ha enviado una oferta" + "Nombre del inmueble" + "Cantidad ofrecida: " + textOfferQuantity.toString())
    }

    private fun sendMessage(){
        MessageSend()
    }
}