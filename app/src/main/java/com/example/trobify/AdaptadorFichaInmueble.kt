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
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.synnapps.carouselview.CarouselView

class AdaptadorFichaInmueble() : AppCompatActivity() {

    //Si se quiere pasar un inmueble hay que pasarlo con el putextra al intent de la siguiente forma
    //val goFicha = Intent(this, AdaptadorFichaInmueble::class.java)
    //goFicha.putExtra("inmueble", el inmueble que queremos pasar)

    val db = Firebase.firestore
    lateinit var userId : String
    lateinit var user : User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ficha_inmueble)

        user = User("Pepe", "Viyuela", "correo@correo.com", "pepe123", "123456789", arrayListOf(""))
        //userId = intent.extras?.get("usuarioId") as User
        userId = user.getId().toString()


        var inmueble = intent.extras?.get("inmueble") as Inmueble
        var fotos = inmueble.getfotos()
        var fotosOrd = inmueble.getfotosord()

        val buttonAtras = findViewById<Button>(R.id.buttonAtrasFicha)
        buttonAtras.setOnClickListener{
            val goMain = Intent(this, MainTrobify::class.java)
            //put extra user para devolverlo con la lista de fav actualizada
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
                    val goContactar = Intent(this, ChatAct::class.java)
                    startActivity(goContactar)
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
            addToFav(inmueble)
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
        name .text = inmueble.propietario?.getName().toString()
        name.setTextColor(Color.BLACK)
        name.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40F)

    }

    private fun llamadaMessage(inmueble : Inmueble) {
        val builder =  AlertDialog.Builder(this)
        val numero = inmueble.propietario?.getPhone()
        builder.setTitle("Contacto")
        builder.setMessage("El numero del propietarios es:  " + numero)
        builder.setIcon(android.R.drawable.ic_menu_call)
        builder.setNeutralButton("  Continuar  "){ _, _ -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun addToFav(inmueble : Inmueble){

        val builder =  AlertDialog.Builder(this)


        var favoritos : ArrayList<String>? = null

        userId.let {db.collection("users").document("pepe123")
            .get()
            .addOnSuccessListener { documentSnapshot ->
               val userr = documentSnapshot.toObject<User>()
                if (userr != null) {
                    favoritos = userr.getFav()
                }
            }
            }
        Log.d("wiiiiiiiiiiii", "los fav   " + favoritos.toString())

        if (favoritos?.contains(inmueble.id.toString()) == true){
                builder.setMessage("Inmueble ya en favoritos")
            }
            else{
                user.addFav(inmueble.id.toString())
                user.getId().toString().let {
                        db.collection("users").document(it).set(user)
                        .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!") }
                        .addOnFailureListener { Log.w(ContentValues.TAG, "Error writing document" ) }
                }
                builder.setMessage("Inmueble añadido a favoritos correctamente")
            }
        builder.setTitle("Favoritos")
        builder.setIcon(android.R.drawable.star_on)
        builder.setNeutralButton("  Continuar  "){ _, _ -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun introduceQuantityOferta(inmueble : Inmueble){
        val builderIntroduceQuantityOferta = AlertDialog.Builder(this@AdaptadorFichaInmueble)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.edit_text_oferta, null)
        with(builderIntroduceQuantityOferta){
            setPositiveButton("Enviar oferta"){dialog, which ->
                val priceIntroduced = dialogLayout.findViewById<EditText>(R.id.editText_oferta).text
                val message = "Oferta : " + inmueble.direccionO?.direccionToString() + " Cantidad ofrecida : " + priceIntroduced
            }
            builderIntroduceQuantityOferta.setNegativeButton("Cancelar") { _, _ -> }
            setView(dialogLayout)
            show()
        }
    }

}