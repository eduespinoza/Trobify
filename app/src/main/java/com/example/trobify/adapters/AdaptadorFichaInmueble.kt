package com.example.trobify.adapters

import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.trobify.*
import com.example.trobify.controladores.GestionarInmueble
import com.example.trobify.controladores.ListOfChats
import com.example.trobify.controladores.MainTrobify
import com.example.trobify.models.Database
import com.example.trobify.models.Inmueble
import com.example.trobify.models.Item
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.synnapps.carouselview.CarouselView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class AdaptadorFichaInmueble : AppCompatActivity() {

    private val db = Firebase.firestore
    var oferta : Int = 0
    private var propietarioMail : String = ""
    private var propietarioId : String = ""

    lateinit var userId : String
    private var desdeMisPisos = false
    lateinit var inmueble : Inmueble

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ficha_inmueble)

        userId = intent.extras?.get("user") as String
        inmueble = intent.extras?.get("inmueble") as Inmueble
        desdeMisPisos = intent.extras?.get("desdeMisPisos") as Boolean

        val path = "imagenesinmueble/" + inmueble.getIdd()
        //var path = "imagenesinmueble/0Fphs"

        downloadFotos(path)

        val buttonAtras = findViewById<Button>(R.id.buttonAtrasFicha)
        buttonAtras.setOnClickListener{
           /* val goBack = Intent(this, MainTrobify::class.java)
            goBack.putExtra("user", userId)
            startActivity(goBack)*/
            finish()
        }

        val buttonLlamar = findViewById<Button>(R.id.buttonLlamar)
        buttonLlamar.setOnClickListener{
            llamadaMessage(inmueble)
        }

        val buttonContactar = findViewById<Button>(R.id.buttonContactarFicha)
        val builder = AlertDialog.Builder(this@AdaptadorFichaInmueble)
        buttonContactar.setOnClickListener {
            val user = Firebase.auth.currentUser
            if (user != null) {
                if (user.isEmailVerified) {
                    builder.setTitle("Elige una opción: ")
                    builder.setItems(R.array.contactOptions) { _, which ->
                        if (which.equals(0)) {
                            db.collection("users").whereEqualTo("email", propietarioMail).get()
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        for (u in task.result) {
                                            propietarioId = u.id
                                        }

                                        val goCreateChat = Intent(this, ListOfChats::class.java)
                                        goCreateChat.putExtra("user", userId)
                                        goCreateChat.putExtra("otherUserId", propietarioId)
                                        goCreateChat.putExtra(
                                            "inmueble",
                                            inmueble.direccionSitio?.titulo.toString()
                                        )
                                        startActivity(goCreateChat)
                                    }
                                }
                        } else {
                            introduceQuantityOferta(inmueble)
                        }
                    }

                    builder.setNegativeButton("Cancelar") { _, _ -> }
                    val dialog = builder.create()
                    dialog.show()
                } else {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Esta funcion requiere una cuenta verificada")
                    builder.setMessage(" Para poder contactar con el propietario es necesario tener la cuenta verificada." + '\n' +
                            "¿Necesita que le volvamos a enviar el correo de verificación?  ")
                    builder.setIcon(android.R.drawable.ic_dialog_alert)
                    builder.setNeutralButton("  Continuar  ") { _, _ -> }
                    builder.setPositiveButton("  Enviar correo  ")  { _, _ -> user.sendEmailVerification() }
                    val alertDialog : AlertDialog = builder.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                }
            }
        }

        val buttonFav = findViewById<Button>(R.id.buttonFav)
        buttonFav.setOnClickListener {
            //inmueble.id?.let { inmid -> Database.setFav2User(userId, inmid) }
            add2Fav(userId,inmueble)
        }

        val elimarDeFav = findViewById<Button>(R.id.buttonFavEliminar)
        elimarDeFav.setOnClickListener {
            //eliminarDeFav(inmueble, userId)
            removeFav(userId,inmueble)
        }

        val edit = findViewById<Button>(R.id.editButtonGestion)
        edit.setOnClickListener {
            val goGestion = Intent(this, GestionarInmueble::class.java)
            goGestion.putExtra("inmueble", inmueble)
            goGestion.putExtra("user", userId)
            goGestion.putExtra("desdeMapa", false)
            startActivity(goGestion)
        }

        if (desdeMisPisos){
            edit.visibility = View.VISIBLE
            edit.isClickable = true
        }
        else{
            edit.visibility = View.GONE
            edit.isClickable = false
        }
        rellenar(inmueble)

    }

    override fun onResume() {
        super.onResume()
        rellenar(inmueble)
    }
    private fun removeFav(id:String,inmueble:Inmueble){
        inmueble.id?.let { inmid -> Database.removeFav2User(id, inmid) }
        val builder =  AlertDialog.Builder(this)
        builder.setMessage("Inmueble eliminado de favoritos")
            .setCancelable(false)
            .setNeutralButton("  Continuar  "){ _, _ -> }
        val alert= builder.create()
        alert.show()
        showEstrella(false)
    }
    private fun add2Fav(id:String,inmueble:Inmueble){
        inmueble.id?.let { inmid -> Database.setFav2User(id, inmid) }
        val builder =  AlertDialog.Builder(this)
        builder.setMessage("Inmueble añadido a favoritos")
            .setCancelable(false)
            .setNeutralButton("  Continuar  "){ _, _ -> }
        val alert= builder.create()
        alert.show()
        showEstrella(true)
    }

    private fun rellenar(inmueble : Inmueble) {

        val direccion =  findViewById<TextView>(R.id.textViewCalleFicha)
        direccion.text = inmueble.direccionSitio?.titulo
        /*if(inmueble.direccion.equals("")){}
        else{direccion.text = inmueble.direccion}*/

        direccion.setTextColor(Color.BLACK)
        direccion.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40F)

        val precio = findViewById<TextView>(R.id.textViewPrecioFicha)
        precio.text = inmueble.precio.toString()+ " €"
        precio.setTextColor(Color.BLACK)
        precio.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40F)

        val numHabitaciones =  findViewById<TextView>(R.id.textViewHabitacionesFicha)
        if(inmueble.numHabitaciones == 0){
            numHabitaciones.text = ""
        }
        else{
            numHabitaciones.text = inmueble.numHabitaciones.toString() + " habitaciones"
            numHabitaciones.setTextColor(Color.BLACK)
            numHabitaciones.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40F)
        }


        val numBanos = findViewById<TextView>(R.id.textViewBanosFicha)
        if(inmueble.numBanos == 0){
            numBanos.text = ""
        }
        else{
            numBanos.text = inmueble.numBanos.toString() + " baños"
            numBanos.setTextColor(Color.BLACK)
            numBanos.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40F)
        }

        val superficie = findViewById<TextView>(R.id.textViewSuperficieFicha)
        superficie.text = inmueble.superficie.toString() + "m2"
        superficie.setTextColor(Color.BLACK)
        superficie.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40F)

        val descripcion = findViewById<TextView>(R.id.textViewDescripcionFicha)
        descripcion.text = inmueble.descripcion.toString()
        descripcion.setTextColor(Color.BLACK)
        descripcion.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40F)

        val caracteristicas = findViewById<TextView>(R.id.textViewCaracteristicasFicha)
        caracteristicas.text = inmueble.extras.toString()
        caracteristicas.setTextColor(Color.BLACK)
        caracteristicas.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40F)

        val name = findViewById<TextView>(R.id.textViewPropietarioFicha)
        inmueble.propietario?.let {
            db.collection("users").document(it).get()
                .addOnSuccessListener { e ->
                    name.text = e.data?.get("name").toString()
                    propietarioMail = e.data?.get("email").toString()
                    name.setTextColor(Color.BLACK)
                    name.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40F)
                }
        }


        isFav(inmueble, userId)




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

    private fun addToFav(inmueble : Inmueble, userId : String ){

        val sfDocRef = db.collection("users").document(userId)

        db.runTransaction { transaction ->
            val snapshot = transaction.get(sfDocRef)
            val favoritos = snapshot.get("favorites")!! as ArrayList<String>
            if (favoritos.contains(inmueble.id.toString())){

               /* val builder =  AlertDialog.Builder(this)
                builder.setMessage("Inmueble ya en favoritos")
                    .setCancelable(false)
                    .setNeutralButton("  Continuar  "){ _, _ -> }
                val alert= builder.create()
                alert.show()*/

            }
            else{
                if(favoritos.size == 1 && favoritos [0].equals("NoFav")){
                    favoritos.remove("NoFav")
                    favoritos.add(inmueble.id.toString())
                }
                else{favoritos.add(inmueble.id.toString())}

                userId.let { db.collection("users").document(it)
                    .update("favorites", favoritos.distinct())
                    .addOnSuccessListener {
                        Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!")

                        val builder =  AlertDialog.Builder(this)
                        builder.setMessage("Inmueble añadido a favoritos")
                            .setCancelable(false)
                            .setNeutralButton("  Continuar  "){ _, _ -> }
                        val alert= builder.create()
                        alert.show()

                        showEstrella(true)
                    }
                    .addOnFailureListener { Log.w(ContentValues.TAG, "Error writing document" ) }
                }

            }


        }.addOnSuccessListener { result ->
            Log.d(ContentValues.TAG, "Transaction success: $result")
        }.addOnFailureListener { e ->
            Log.w(ContentValues.TAG, "Transaction failure.", e)
        }

    }

    private fun     eliminarDeFav(inmueble : Inmueble, userId : String ){


        val sfDocRef = db.collection("users").document(userId)

        db.runTransaction { transaction ->
            val snapshot = transaction.get(sfDocRef)
            var favoritos = snapshot.get("favorites")!! as ArrayList<String>
            if (favoritos.contains(inmueble.id.toString())){

                if (favoritos.size ==1 ){favoritos = arrayListOf("NoFav")}
                else{ favoritos.remove(inmueble.id.toString())}

                userId.let { db.collection("users").document(it)
                    .update("favorites", favoritos)
                    .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!")

                        val builder =  AlertDialog.Builder(this)
                        builder.setMessage("Inmueble eliminado de favoritos")
                            .setCancelable(false)
                            .setNeutralButton("  Continuar  "){ _, _ -> }
                        val alert= builder.create()
                        alert.show()
                        showEstrella(false)
                    }
                    .addOnFailureListener { Log.w(ContentValues.TAG, "Error writing document" ) }
                }

            }
        }.addOnSuccessListener { result ->
            Log.d(ContentValues.TAG, "Transaction success: $result")
        }.addOnFailureListener { e ->
            Log.w(ContentValues.TAG, "Transaction failure.", e)
        }

    }



    private fun isFav(inmueble : Inmueble, userId : String){
        val sfDocRef = db.collection("users").document(userId)

        db.runTransaction { transaction ->
            val snapshot = transaction.get(sfDocRef)
            val favoritos = snapshot.get("favorites")!! as ArrayList<String>
            if (favoritos.contains(inmueble.id.toString())){
                showEstrella(true)
            }
            else{showEstrella(false)}

        }

    }

    private fun showEstrella(visible : Boolean){
        val estrella = findViewById<Button>(R.id.estrellita_ficha)
        if(visible){estrella.visibility = View.VISIBLE}
        else{estrella.visibility = View.GONE}

    }

    private fun downloadFotos(path : String ) {
        val storage = FirebaseStorage.getInstance().reference
        val ref = storage.child(path)
        val imageList : ArrayList<String> = ArrayList()

        val listAllTask: Task<ListResult> = ref.listAll()
        listAllTask.addOnCompleteListener { result ->
            val items : List<StorageReference> = result.result!!.items
            items.forEachIndexed { _ , item ->
                    item.downloadUrl.addOnSuccessListener {
                        Log.d("itemm", "$it")
                        imageList.add(Item(it.toString()).imageUrl)


                    }.addOnCompleteListener{
                        showImages(imageList)
                    }
            }
        }
    }

    private fun showImages(urls : ArrayList<String> ){
        val carouselView = findViewById<CarouselView>(R.id.carouselView)

        carouselView.setImageListener{ position, imageView ->
            Picasso.get().load(urls[position]).into(imageView)

        }
        carouselView.setImageClickListener{ position ->
            Toast.makeText(applicationContext, urls.get(position), Toast.LENGTH_SHORT).show()
        }

        carouselView.pageCount = urls.size
    }

    private fun introduceQuantityOferta(inmueble : Inmueble) {
        val builderIntroduceQuantityOferta = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.edit_text_oferta, null)
        with(builderIntroduceQuantityOferta) {
            setPositiveButton("Enviar oferta") { _, _ ->
                val priceIntroduced = dialogLayout.findViewById<EditText>(R.id.editText_oferta).text
                if (priceIntroduced.toString().toInt().compareTo(inmueble.precio.toString().toInt()) > 0) {
                    val builderAviso = AlertDialog.Builder(this@AdaptadorFichaInmueble)
                    val message =
                        "Oferta : " + inmueble.direccion + '\n' + " Cantidad ofrecida : " + priceIntroduced
                    with(builderAviso) {

                        setTitle("El precio es superior al del inmueble, quiere enviar la oferta?")

                        setPositiveButton("Sí") { _, _ ->
                            val goCreateChat =
                                Intent(this@AdaptadorFichaInmueble, ListOfChats::class.java)
                            db.collection("users").whereEqualTo("email", propietarioMail)
                                .get()
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        for (u in task.result) {
                                            propietarioId = u.id
                                        }
                                        goCreateChat.putExtra("user", userId)
                                        goCreateChat.putExtra("otherUserId", propietarioId)
                                        goCreateChat.putExtra("message", message)
                                        goCreateChat.putExtra("inmueble",inmueble.direccion.toString())

                                        startActivity(goCreateChat)
                                    }
                                }
                        }
                        setNegativeButton("No") { _, _ -> }
                        create()
                        show()
                    }
                }
                else {
                    val message =
                        "Oferta : " + inmueble.direccion + '\n' + " Cantidad ofrecida : " + priceIntroduced
                    val goCreateChat =
                        Intent(this@AdaptadorFichaInmueble, ListOfChats::class.java)
                    db.collection("users").whereEqualTo("email", propietarioMail)
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                for (u in task.result) {
                                    propietarioId = u.id
                                }
                                goCreateChat.putExtra("user", userId)
                                goCreateChat.putExtra("otherUserId", propietarioId)
                                goCreateChat.putExtra("message", message)
                                goCreateChat.putExtra("inmueble",inmueble.direccion.toString())

                                startActivity(goCreateChat)
                            }
                        }
                }
            }
            setNegativeButton("Cancelar") { _, _ -> }
            setView(dialogLayout)
            show()
        }
    }
}