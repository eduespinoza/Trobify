package com.example.trobify

import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.synnapps.carouselview.CarouselView

class AdaptadorFichaInmueble() : AppCompatActivity() {

    //Si se quiere pasar un inmueble hay que pasarlo con el putextra al intent de la siguiente forma
    //val goFicha = Intent(this, AdaptadorFichaInmueble::class.java)
    //goFicha.putExtra("inmueble", el inmueble que queremos pasar)

    private val db = Firebase.firestore
    var favoritos = arrayListOf<String>()
    var oferta : Int = 0
    var propietarioMail : String = ""
    var propietarioId : String = ""

    lateinit var userId:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ficha_inmueble)

        var userId = intent.extras?.get("user") as String
        var inmueble = intent.extras?.get("inmueble") as Inmueble

        var fotos = inmueble.getfotos()
        var fotosOrd = inmueble.getfotosord()



        val buttonAtras = findViewById<Button>(R.id.buttonAtrasFicha)
        buttonAtras.setOnClickListener{
            val goMain = Intent(this, MainTrobify::class.java)
            goMain.putExtra("user", userId.toString())
            startActivity(goMain)
        }

        val buttonLlamar = findViewById<Button>(R.id.buttonLlamar)
        buttonLlamar.setOnClickListener{
            llamadaMessage(inmueble)
        }

        val buttonContactar = findViewById<Button>(R.id.buttonContactarFicha)
        val builder = AlertDialog.Builder(this@AdaptadorFichaInmueble)
        buttonContactar.setOnClickListener {
            builder.setTitle("Elige una opción: ")
            builder.setItems(R.array.contactOptions) { dialog, which ->
                if(which.equals(0)) {
                    db.collection("users").whereEqualTo("email",propietarioMail.toString()).get()
                        .addOnCompleteListener { task->
                            if(task.isSuccessful){
                                for(u in task.result) {
                                    propietarioId = u.id.toString()
                                }
                                val goCreateChat = Intent(this, ListOfChats::class.java)
                                goCreateChat.putExtra("user",userId.toString())
                                goCreateChat.putExtra("otherUserId",propietarioId.toString())
                                startActivity(goCreateChat)
                            }
                        }
                }
                else {
                    introduceQuantityOferta(inmueble)
                }
            }

            builder.setNegativeButton("Cancelar") { _, _ -> }
            val dialog = builder.create()
            dialog.show()
        }

        val buttonFav = findViewById<Button>(R.id.buttonFav)
        buttonFav.setOnClickListener {
            addToFav(inmueble, userId)
        }

        rellenar(inmueble)

        val carouselView = findViewById<CarouselView>(R.id.carouselView)
        carouselView.setImageListener{ position, imageView ->
                imageView.setImageResource(fotos.get(position))

        }


        carouselView.setImageClickListener{ position ->
            Toast.makeText(applicationContext, fotosOrd.get(position), Toast.LENGTH_SHORT).show()
        }
        carouselView.pageCount = fotosOrd.size

    }

    private fun rellenar(inmueble : Inmueble) {

        val direccion =  findViewById<TextView>(R.id.textViewCalleFicha)
        direccion.text = inmueble.direccionO?.direccionToString()
        if(inmueble.direccion.equals("")){}else{direccion.text = inmueble.direccion}

        direccion.setTextColor(Color.BLACK)
        direccion.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40F)

        val precio = findViewById<TextView>(R.id.textViewPrecioFicha)
        precio.text = inmueble.precio.toString()+ " €"
        precio.setTextColor(Color.BLACK)
        precio.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40F)

        val numHabitaciones =  findViewById<TextView>(R.id.textViewHabitacionesFicha)
        numHabitaciones.text = inmueble.numHabitaciones.toString() + " habitaciones"
        numHabitaciones.setTextColor(Color.BLACK)
        numHabitaciones.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40F)

        val numBanos = findViewById<TextView>(R.id.textViewBanosFicha)
        numBanos.text = inmueble.numBanos.toString() + " baños"
        numBanos.setTextColor(Color.BLACK)
        numBanos.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40F)

        val superficie = findViewById<TextView>(R.id.textViewSuperficieFicha)
        superficie.text = inmueble.superficie.toString() + "m2"
        superficie.setTextColor(Color.BLACK)
        superficie.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40F)

        val descripcion = findViewById<TextView>(R.id.textViewDescripcionFicha)
        descripcion.text = inmueble.descripcion.toString()
        descripcion.setTextColor(Color.BLACK)
        descripcion.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40F)

        val caracteristicas = findViewById<TextView>(R.id.textViewCaracteristicasFicha)
        caracteristicas.text = inmueble.caracteristicas
        caracteristicas.setTextColor(Color.BLACK)
        caracteristicas.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40F)

        val name = findViewById<TextView>(R.id.textViewPropietarioFicha)
        name.text = inmueble.propietario?.toString()
        propietarioMail = name.text.toString()
        name.setTextColor(Color.BLACK)
        name.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40F)

    }

    private fun llamadaMessage(inmueble : Inmueble) {
        val builder =  AlertDialog.Builder(this)
        //val numero = inmueble.propietario?.getPhone()
        builder.setTitle("Contacto")
        //builder.setMessage("El numero del propietarios es:  " + numero)
        builder.setIcon(android.R.drawable.ic_menu_call)
        builder.setNeutralButton("  Continuar  "){ _, _ -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun addToFav(inmueble : Inmueble , userId : String ){
        //cuando funcione hacer que saque el user al principio con el user id que le llega,
        // y con ese user trabajar, luego cuando actualizamos user en fireston lo actualizamos tmb aqui

        val builder =  AlertDialog.Builder(this)



        userId.let {db.collection("users").whereEqualTo("id",it)
            .get()
            .addOnCompleteListener(){ task ->
                if(task.isSuccessful) {
                    for(u in task.result) {
                        favoritos = u.data.get("favorites") as ArrayList<String>
                    }
                }
            }
        }
        if (favoritos.contains(inmueble.id.toString())){
            builder.setMessage("Inmueble ya en favoritos")
            builder.setTitle("Favoritos")
            builder.setIcon(android.R.drawable.star_on)
            builder.setNeutralButton("  Continuar  "){ _, _ -> }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
        else{
            favoritos.add(inmueble.id.toString())
            userId.let { db.collection("users").document(it)
                .update("favorites", favoritos)
                .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { Log.w(ContentValues.TAG, "Error writing document" ) }
            }
            builder.setMessage("Inmueble añadido a favoritos correctamente")
            builder.setTitle("Favoritos")
            builder.setIcon(android.R.drawable.star_on)
            builder.setNeutralButton("  Continuar  "){ _, _ -> }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }

    private fun introduceQuantityOferta(inmueble : Inmueble) {
        val builderIntroduceQuantityOferta = AlertDialog.Builder(this@AdaptadorFichaInmueble)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.edit_text_oferta, null)
        println("------------------------------------------------------- 1")
        with(builderIntroduceQuantityOferta) {
            println("------------------------------------------------------- 2")
            setPositiveButton("Enviar oferta") { dialog, which ->
                val priceIntroduced = dialogLayout.findViewById<EditText>(R.id.editText_oferta).text
                if (compareValues(
                        inmueble.precio.toString().toInt(),
                        priceIntroduced.toString().toInt()
                    ) > 0
                ) {
                    val builderAviso = AlertDialog.Builder(this@AdaptadorFichaInmueble)
                    val message =
                        "Oferta : " + inmueble.direccionO?.direccionToString() + " Cantidad ofrecida : " + priceIntroduced
                    builderAviso.setTitle("El precio que ha introducido es superior al del inmueble, esta seguro de que quiere enviar la oferta?")
                    builderAviso.setPositiveButton("Sí") { dialog, which ->
                        val goCreateChat =
                            Intent(this@AdaptadorFichaInmueble, ListOfChats::class.java)
                        db.collection("users").whereEqualTo("email", propietarioMail.toString())
                            .get()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    for (u in task.result) {
                                        propietarioId = u.id.toString()
                                    }
                                    goCreateChat.putExtra("user", userId)
                                    goCreateChat.putExtra("otherUserId", propietarioId)
                                    goCreateChat.putExtra("message", message)
                                    println("-------------------------------------------------------message: "+ message)
                                    startActivity(goCreateChat)
                                }
                            }
                        builderAviso.setNegativeButton("No") { _, _ -> }
                        val dialog = builderAviso.create()
                        dialog.show()
                    }
                }
                builderIntroduceQuantityOferta.setNegativeButton("Cancelar") { _, _ -> }
                setView(dialogLayout)
                show()
            }
        }
    }
}