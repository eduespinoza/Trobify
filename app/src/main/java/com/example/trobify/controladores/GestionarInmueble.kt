package com.example.trobify.controladores

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.trobify.R
import com.example.trobify.models.*
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.CarouselView
import kotlinx.android.synthetic.main.activity_gestion.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class GestionarInmueble : AppCompatActivity() {

    private val db = Firebase.firestore
    val storage = FirebaseStorage.getInstance().reference
    lateinit var userId : String
    lateinit var inmuebleId : String
    lateinit var urls : ArrayList<String>
    lateinit var path : String
    lateinit var buttonPost : Button
    lateinit var tipoAnuncio : Spinner
    lateinit var tipoVivienda : Spinner
    lateinit var tipoInmueble : Spinner
    lateinit var estado : Spinner

    lateinit var precio : EditText
    lateinit var habitaciones : EditText
    lateinit var banos : EditText
    lateinit var superficie : EditText
    lateinit var descripcion : EditText
    lateinit var certificado : EditText

    lateinit var parking : CheckBox
    lateinit var ascensor : CheckBox
    lateinit var amueblado : CheckBox
    lateinit var calefaccion : CheckBox
    lateinit var jardin : CheckBox
    lateinit var piscina : CheckBox
    lateinit var terraza : CheckBox
    lateinit var trastero : CheckBox

    private val SELECT_FILE  = 1

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestion)

        userId = intent.extras?.get("user") as String
        var inmueble = intent.extras?.get("inmueble") as Inmueble
        inmuebleId = inmueble.id as String


        rellenarData(inmueble)

        path = "imagenesinmueble/" + inmueble.getIdd()




        downloadFotos(path, "mostrar")

        val buttonBack = findViewById<Button>(R.id.buttonAtrasGestion)
        buttonBack.setOnClickListener {
            finish()
        }

        val buttonEliminarFotos = findViewById<Button>(R.id.buttonEliminarFotos)
        buttonEliminarFotos.setOnClickListener{
            val intent = Intent(this, EliminarFotos::class.java)
            intent.putExtra("path", path)
            intent.putExtra("urls", urls)
            startActivity(intent)

            downloadFotos(path , "mostrar")
        }

        val addFoto = findViewById<Button>(R.id.buttonAddFotoGestion)
        addFoto.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, SELECT_FILE)
        }

        val realizarAccion = { dialog: DialogInterface, which: Int ->
            when(which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    if(buttonPost.text.equals("Publicar")){
                        Database.toPublicado(DataInmueble2(id = inmueble.id, propietario = inmueble.propietario, numHabitaciones = inmueble.numHabitaciones,
                            numBanos = inmueble.numBanos, superficie = inmueble.superficie, direccion = inmueble.direccionSitio,
                            tipoVivienda = inmueble.tipoVivienda, tipoInmueble = inmueble.tipoInmueble, intencion = inmueble.intencion,
                            precio = inmueble.precio, fotos = inmueble.fotos, descripcion = inmueble.descripcion,  extras = inmueble.booleans2extras(),
                            estado = inmueble.estado, fechaSubida = inmueble.fechaSubida.toString()))
                        postOrNotPost(buttonPost)
                    }
                    else {
                        Database.toNoPublicado(DataInmueble2(id = inmueble.id, propietario = inmueble.propietario, numHabitaciones = inmueble.numHabitaciones,
                            numBanos = inmueble.numBanos, superficie = inmueble.superficie, direccion = inmueble.direccionSitio,
                            tipoVivienda = inmueble.tipoVivienda, tipoInmueble = inmueble.tipoInmueble, intencion = inmueble.intencion,
                            precio = inmueble.precio, fotos = inmueble.fotos, descripcion = inmueble.descripcion, extras = inmueble.booleans2extras(),
                            estado = inmueble.estado, fechaSubida = inmueble.fechaSubida.toString()))
                        postOrNotPost(buttonPost)
                    }
                }
            }
        }
        buttonPost = findViewById<Button>(R.id.postNopost)
        postOrNotPost(buttonPost)
        buttonPost.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            var texto = buttonPost.text
            builder.setMessage("¿Seguro que quieres $texto el inmueble?")
                .setCancelable(false)
                .setPositiveButton("Yes", DialogInterface.OnClickListener(function = realizarAccion))
                .setNegativeButton("No") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }
        val buttonSave = findViewById<Button>(R.id.buttonSaveGestion)
        buttonSave.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setMessage("¿Seguro que quieres guardar asi el inmueble?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    setData(inmueble)



                }
                .setNegativeButton("No") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()

        }
        val buttonDelete = findViewById<Button>(R.id.buttonDeleteGestion)
        buttonDelete.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("¿Seguro que quieres eliminar este inmueble?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->

                    finish()
                }
                .setNegativeButton("No") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }
    }

    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == SELECT_FILE && resultCode == RESULT_OK ) {
            var uri = data?.data
            if (uri != null) {
                val imageId = UUID.randomUUID().toString()
                var filePath : StorageReference? = storage?.child("imagenesinmueble/"+ inmuebleId)?.child(imageId)
                filePath?.putFile(uri)?.addOnSuccessListener(this) { taskSnapshot ->

                    downloadFotos("imagenesinmueble/"+ inmuebleId, "mostrar")
                }
            }
        }
        downloadFotos(path, "mostrar")
    }
    private fun postOrNotPost(button : Button){
        if (Database.isInmueblePost(inmuebleId)){
            button.setText("Despublicar")
            button.setBackgroundColor(Color.RED)
        }else {
            button.setText("Publicar")
            button.setBackgroundColor(Color.BLUE)
        }
    }
    private fun rellenarData(inmueble : Inmueble) {
        /*val dataTipoAnuncio = arrayListOf("Alquiler", "Venta")
        tipoAnuncio = findViewById(R.id.spinnerTipoAnuncio)
        if (tipoAnuncio != null) {
            val adapter = ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item, dataTipoAnuncio
            )
            tipoAnuncio.adapter = adapter
        }
        tipoAnuncio.setSelection(dataTipoAnuncio.indexOf(inmueble.intencion))*/



        /*val dataTipoInmueble = resources.getStringArray(R.array.options_inmueble)
        tipoInmueble = findViewById(R.id.spinnerTipoInmueble)
        if (tipoInmueble != null) {
            val adapter = ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item, dataTipoInmueble
            )
            tipoInmueble.adapter = adapter
        }
        tipoInmueble.setSelection(dataTipoInmueble.indexOf(inmueble.tipoInmueble))*/


        val dataTipoVivienda = resources.getStringArray(R.array.options_vivienda_por_defecto)
        tipoVivienda = findViewById(R.id.spinnerTipoVivienda)
        if (tipoVivienda != null) {
            val adapter = ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item, dataTipoVivienda
            )
            tipoVivienda.adapter = adapter

        }
        tipoVivienda.setSelection(dataTipoVivienda.indexOf(inmueble.tipoVivienda))

        precio = findViewById(R.id.precioBox)
        precio.setText(inmueble.precio.toString())

        habitaciones = findViewById(R.id.habitacionesBox)
        habitaciones.setText(inmueble.numHabitaciones.toString())

        banos = findViewById(R.id.banosBox)
        banos.setText(inmueble.numBanos.toString())

        superficie = findViewById(R.id.superficieBox)
        superficie.setText(inmueble.superficie.toString())

        descripcion = findViewById(R.id.descripcionBox)
        descripcion.setText(inmueble.descripcion)

        val dataEstado = arrayListOf(
            "Obra nueva", "Casi nuevo", "Muy bien",
            "Bien", "Reformado", "A reformar"
        )
        estado = findViewById(R.id.spinnerEstado)
        if (estado != null) {
            val adapter = ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item, dataEstado
            )
            estado.adapter = adapter
        }
        estado.setSelection(dataEstado.indexOf(inmueble.estado))

        //si no funciona esto de as Booleand hacer if de inmuble.pking por ejemplo
        // y depende si es true o flase pones isChekced= true  --NO SE

        //jajjjaja pues no funciona luego lo cambio xd --Ilias


        parking = findViewById(R.id.checkBoxParking)
        parking.isChecked = inmueble.parking == true

        ascensor = findViewById(R.id.checkBoxAscensor)
        ascensor.isChecked = inmueble.ascensor == true

        amueblado = findViewById(R.id.checkBoxAmueblado)
        amueblado.isChecked = inmueble.amueblado == true

        calefaccion = findViewById(R.id.checkBoxCalefaccion)
        calefaccion.isChecked = inmueble.calefaccion == true


        jardin = findViewById(R.id.checkBoxJardin)
        jardin.isChecked = inmueble.jardin == true


        piscina = findViewById(R.id.checkBoxPiscina)
        piscina.isChecked = inmueble.piscina == true


        terraza = findViewById(R.id.checkBoxTerraza)
        terraza.isChecked = inmueble.terraza == true


        trastero = findViewById(R.id.checkBoxTrastero)
        trastero.isChecked = inmueble.trastero == true

    }



    private fun setData(inmueble : Inmueble) {


        val vivendaTipo = tipoVivienda.selectedItem.toString()

        val precio = Integer.parseInt(precioBox.text.toString())
        val habitaciones = Integer.parseInt(habitacionesBox.text.toString())
        val banos = Integer.parseInt(banosBox.text.toString())
        val superficie = Integer.parseInt(superficieBox.text.toString())

        val descripcion = descripcionBox.text.toString()

        val estado = spinnerEstado.selectedItem.toString()

        val parking = parking.isChecked
        val ascensor = ascensor.isChecked
        val amueblado = amueblado.isChecked
        val calefaccion = calefaccion.isChecked
        val jardin = jardin.isChecked
        val piscina = piscina.isChecked
        val terraza = terraza.isChecked
        val trastero = trastero.isChecked

        var res = arrayListOf<String>()
        if(parking == true){ res.add("Parking")}
        if(ascensor == true){ res.add("Ascensor") }
        if(amueblado == true){ res.add("Amueblado") }
        if(calefaccion == true){ res.add("Calefacción")}
        if(jardin == true){ res.add("Jardín") }
        if(piscina == true){res.add("Piscina") }
        if(terraza == true){ res.add("Terraza")}
        if(trastero == true){ res.add("Trastero") }





        val updatedInmueble = DataInmueble2(
            inmueble.getIdd(),
            inmueble.propietario,
            habitaciones,
            banos,
            superficie,
            inmueble.direccionSitio,
            vivendaTipo,
            inmueble.tipoInmueble,
            inmueble.intencion,
            precio,
            inmueble.fotos,
            descripcion,
            res,
            estado,
            inmueble.fechaSubida.toString()

        )


        if(checkFormats()) {
            Database.subirInmueble(updatedInmueble,Database.isInmueblePost(inmuebleId))
            finish()
        }
        else{
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Formato incorrecto, porfavor, compruebe los datos")
                .setCancelable(false)
                .setNeutralButton("OK") { dialog, _ ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()


        }
    }


    private fun checkFormats() : Boolean{

        if(precioBox.text.toString().matches("-?\\d+(\\.\\d+)?".toRegex()) &&
            habitacionesBox.text.toString().matches("-?\\d+(\\.\\d+)?".toRegex()) &&
            banosBox.text.toString().matches("-?\\d+(\\.\\d+)?".toRegex()) &&
            superficieBox.text.toString().matches("-?\\d+(\\.\\d+)?".toRegex())            )
            {
            return true
        }

        return false
    }

    private fun downloadFotos(path : String, para : String ) {

        var ref = storage.child(path)
        val imageList : ArrayList<String> = ArrayList()

        GlobalScope.launch(IO) {

            withContext(Dispatchers.Main){
                val listAllTask : Task<ListResult> = ref.listAll()
                listAllTask.addOnCompleteListener { result ->
                    val items : List<StorageReference> = result.result!!.items
                    items.forEachIndexed { _, item ->
                        item.downloadUrl.addOnSuccessListener {
                            Log.d("itemm", "$it")
                            imageList.add(Item(it.toString()).imageUrl)


                        }.addOnCompleteListener {
                            urls = imageList
                            if (para.equals("mostrar")) {
                                showImages(imageList)
                            }


                        }
                    }
                }
            }
        }


    }

    private fun showImages(urls : ArrayList<String>){
        val carouselView = findViewById<CarouselView>(R.id.carouselGestion)

        carouselView.setImageListener{ position, imageView ->
            Picasso.get().load(urls[position]).into(imageView)

        }
        carouselView.setImageClickListener{ position ->
            Toast.makeText(applicationContext, urls.get(position), Toast.LENGTH_SHORT).show()

        }
        carouselView.pageCount = urls.size
    }


}