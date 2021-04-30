package com.example.trobify

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import java.time.LocalDateTime
import java.util.*


class OfertarInmueble : AppCompatActivity() {

    private var user : String? = null

    private var fotos : ArrayList<Image> = arrayListOf()
    private var fotosOrd : ArrayList<String> = arrayListOf()
    private var tipoInmueble : String? = null
    private var tipoVivienda : String? = null
    private var tipoAnuncio : String? = null
    private var precioDeVenta : Int? = null
    private var superficie : Int? = null
    private var direccion : String? = null
    private var direccionO : Direccion? = null
    private var numHabitaciones : Int? = null
    private var numBanos : Int? = null
    private var certificadoEnergetico : String? = null  //Desde la A hasta la G, hay que hacer que solo deje poner las letras posibles
    private var descripcion : String? = null
    private var fechaSubida : LocalDateTime? = null
    private var estado : String? = null

    private var parking : Boolean? = null
    private var ascensor : Boolean? = null
    private var amueblado : Boolean? = null
    private var calefaccion : Boolean? = null
    private var jardin : Boolean? = null
    private var piscina : Boolean? = null
    private var terraza : Boolean? = null
    private var trastero : Boolean? = null
    private var image : ImageView? = null
    private lateinit var carimages : CarouselView

    private var caracteristicas : String? = null

    private val SELECT_FILE  = 1
    private var pos = 0



    private var mStorage : StorageReference? = null


    var sampleImages = intArrayOf()


    object text {
        lateinit var tipoInmuebleElegidoText:TextView
        lateinit var tipoViviendaElegidoText:TextView
    }

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ofertar_inmueble)

        intent.getStringExtra("user")?.let { user = it }

        mStorage = FirebaseStorage.getInstance().reference



        image  = findViewById<ImageView>(R.id.imageView)
        carimages = findViewById<CarouselView>(R.id.carouselPhotosOfertar) as CarouselView
        carimages.setPageCount(sampleImages.size);
        carimages.setImageListener(imageListener);

        text.tipoInmuebleElegidoText = findViewById(R.id.textViewTipodetipo)
        text.tipoViviendaElegidoText = findViewById(R.id.textViewTipodeViviendaElegido)

        val bBack = findViewById<Button>(R.id.buttonBackOfertar)
        bBack.setOnClickListener { goBack() }
        
        val bExtra = findViewById<Button>(R.id.buttonExtras)
        bExtra.setOnClickListener { selectExtras()}

        val bCondition = findViewById<Button>(R.id.buttonEstado)
        bCondition.setOnClickListener { selectCondition() }

        val bChooseInmueble = findViewById<ImageButton>(R.id.imageButtonTipoInmueble)
        bChooseInmueble.setOnClickListener { chooseInmueble() }

        val bChooseVivienda = findViewById<ImageButton>(R.id.imageButtonTipoVivienda)
        bChooseVivienda.setOnClickListener { chooseVivienda() }

        val bChooseAnunucio = findViewById<ImageButton>(R.id.imageButtonTipoAnuncio)
        bChooseAnunucio.setOnClickListener { chooseAnuncio() }

        val bPost = findViewById<Button>(R.id.buttonPublicar)
        bPost.setOnClickListener { post() }


        val bPhoto = findViewById<Button>(R.id.buttonAddPhoto)
        bPhoto.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, SELECT_FILE)
        }

    }

   override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

       if(requestCode == SELECT_FILE && resultCode == RESULT_OK ) {
           var uri = data?.data
           if (uri != null) {
               var imageId : String = UUID.randomUUID().toString()
               var filePath : StorageReference? = mStorage?.child("imagenesinmueble")?.child(imageId)
               filePath?.putFile(uri)?.addOnSuccessListener(this) { taskSnapshot ->

                   filePath.downloadUrl.addOnSuccessListener { url ->
                       //Glide.with(this).load(url).into(image)
                       Glide.with(this).load(url).into(image)
                   }


               }


           }
       }

   }


    var imageListener =
        ImageListener { position, imageView -> imageView.setImageResource(sampleImages[position]) }


    private fun goBack(){
        val goBack = Intent(this, MainTrobify::class.java)
        goBack.putExtra("user", user)
        startActivity(goBack)
    }

    private fun compare(){
        val builder =  AlertDialog.Builder(this)
        builder.setTitle("Funcion no implementada")
        builder.setIcon(android.R.drawable.stat_notify_error)
        builder.setNeutralButton("  Continue  "){ _, _ -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }




    private fun chooseInmueble() {
        val builder = AlertDialog.Builder(this@OfertarInmueble)
        builder.setTitle("Selecciona el tipo de inmueble")
        val optionsInmueble = arrayOf("Vivienda","Edificio","Oficina","Garaje","Local","Terreno","Nave")
        builder.setItems(optionsInmueble) { _, which ->
            when {
                which.equals(0) // Vivienda
                -> {
                    text.tipoInmuebleElegidoText.text = optionsInmueble[0]
                }
                which.equals(1)// Edificio
                -> {
                    text.tipoInmuebleElegidoText.text = optionsInmueble[1]
                }
                which.equals(2) // Oficina
                -> {
                    text.tipoInmuebleElegidoText.text = optionsInmueble[2]
                }
                which.equals(3) // Garaje
                -> {
                    text.tipoInmuebleElegidoText.text = optionsInmueble[3]
                }
                which.equals(4) // Local
                -> {
                    text.tipoInmuebleElegidoText.text = optionsInmueble[4]
                }
                which.equals(5) // Terreno
                -> {
                    text.tipoInmuebleElegidoText.text = optionsInmueble[5]
                }
                which.equals(6) // Nave
                -> {
                    text.tipoInmuebleElegidoText.text = optionsInmueble[6]
                }
            }
        }
        builder.setNegativeButton("Cancelar"){_, _ -> }
        val options = builder.create()
        options.show()

    }

    private fun chooseAnuncio(){}
    private fun chooseVivienda(){
        val builder = AlertDialog.Builder(this@OfertarInmueble)
        builder.setTitle("Selecciona el tipo de vivienda")
        if(text.tipoInmuebleElegidoText.text == "Edificio" || text.tipoInmuebleElegidoText.text == "Oficina"){
            val optionsViviendaEdificio = resources.getStringArray(R.array.options_vivienda_edificio)
            builder.setItems(optionsViviendaEdificio) { _, which ->
                when {
                    which.equals(0)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsViviendaEdificio[0]
                    }
                    which.equals(1)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsViviendaEdificio[1]
                    }
                    which.equals(2)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsViviendaEdificio[2]
                    }
                    which.equals(3)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsViviendaEdificio[3]
                    }
                    which.equals(4)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsViviendaEdificio[4]
                    }
                    which.equals(5)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsViviendaEdificio[5]
                    }
                }
            }
            builder.setNegativeButton("Cancelar"){_, _ -> }
            val options = builder.create()
            options.show()
        }
        else{
            val optionsVivienda = arrayOf("Casa","Chalet","Adosado","Finca rÃºstica")
            builder.setItems(optionsVivienda) { _, which ->
                when {
                    which.equals(0)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsVivienda[0]
                    }
                    which.equals(1)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsVivienda[1]
                    }
                    which.equals(2)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsVivienda[2]
                    }
                    which.equals(3)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsVivienda[3]
                    }
                }
            }
            builder.setNegativeButton("Cancelar"){_, _ -> }
            val options = builder.create()
            options.show()
        }
    }

    private fun selectExtras(){



    }
    private fun selectCondition(){}
    private fun post(){}



    }
