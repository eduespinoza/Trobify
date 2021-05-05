package com.example.trobify

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_gestion.*

class GestionarInmueble : AppCompatActivity() {

    private val db = Firebase.firestore
    lateinit var userId : String

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


    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestion)

        userId = intent.extras?.get("user") as String
        var inmueble = intent.extras?.get("inmueble") as Inmueble


        rellenarData(inmueble)

        val buttonBack = findViewById<Button>(R.id.buttonAtrasGestion)
        buttonBack.setOnClickListener {
            finish()
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
                    delete(inmueble)

                    val intent = Intent(this, MainTrobify::class.java)
                    intent.putExtra("user", userId)
                    startActivity(intent)

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

    private fun rellenarData(inmueble : Inmueble) {
        val dataTipoAnuncio = arrayListOf("Alquiler", "Venta")
        tipoAnuncio = findViewById(R.id.spinnerTipoAnuncio)
        if (tipoAnuncio != null) {
            val adapter = ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item, dataTipoAnuncio
            )
            tipoAnuncio.adapter = adapter
        }
        tipoAnuncio.setSelection(dataTipoAnuncio.indexOf(inmueble.intencion))


        val dataTipoInmueble = arrayListOf(
            "Vivienda", "Edificio", "Oficina", "Garaje",
            "Local", "Terreno", "Nave"
        )
        tipoInmueble = findViewById(R.id.spinnerTipoInmueble)
        if (tipoInmueble != null) {
            val adapter = ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item, dataTipoInmueble
            )
            tipoInmueble.adapter = adapter
        }
        tipoInmueble.setSelection(dataTipoInmueble.indexOf(inmueble.tipoInmueble))


        val dataTipoVivienda = arrayListOf(
            "Apartamento", "Ático", "Dúplex", "Loft",
            "Planta baja", "Estudio", "Casa", "Chalet", "Adosado", "Finca rústica"
        )
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

        certificado = findViewById(R.id.certificadoBox)
        certificado.setText(inmueble.certificadoEnergetico)

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
        // y depende si es true o flase pones isChekced= true
        parking = findViewById(R.id.checkBoxParking)
        parking.isChecked = inmueble.parking as Boolean

        ascensor = findViewById(R.id.checkBoxAscensor)
        ascensor.isChecked = inmueble.ascensor as Boolean

        amueblado = findViewById(R.id.checkBoxAmueblado)
        amueblado.isChecked = inmueble.amueblado as Boolean

        calefaccion = findViewById(R.id.checkBoxCalefaccion)
        calefaccion.isChecked = inmueble.calefaccion as Boolean

        jardin = findViewById(R.id.checkBoxJardin)
        jardin.isChecked = inmueble.jardin as Boolean

        piscina = findViewById(R.id.checkBoxPiscina)
        piscina.isChecked = inmueble.piscina as Boolean

        terraza = findViewById(R.id.checkBoxTerraza)
        terraza.isChecked = inmueble.terraza as Boolean

        trastero = findViewById(R.id.checkBoxTrastero)
        trastero.isChecked = inmueble.trastero as Boolean

    }

    private fun setData(inmueble : Inmueble) {



        val anuncioTipo = tipoAnuncio.selectedItem.toString()
        val inmuebleTipo = tipoInmueble.selectedItem.toString()
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

        val certificado = certificado.text.toString()


        val updatedInmueble = DataInmueble(
            inmueble.getIdd(),
            inmueble.propietario,
            habitaciones,
            banos,
            superficie,
            inmueble.direccionSitio,
            vivendaTipo,
            inmuebleTipo,
            anuncioTipo,
            precio,
            inmueble.fotos,
            inmueble.fotosOrd,
            certificado,
            descripcion,
            estado,
            parking,
            ascensor,
            amueblado,
            calefaccion,
            jardin,
            piscina,
            terraza,
            trastero,
            inmueble.fechaSubida.toString()
        )
        val ref = db.collection("inmueblesv3").document(inmueble.getIdd().toString())

        if(checkFormats()) {
            db.runBatch { batch ->
                batch.set(ref, updatedInmueble)
            }.addOnCompleteListener {
                val intent = Intent(this, MainTrobify::class.java)
                intent.putExtra("user", userId)
                startActivity(intent)

                finish()

            }
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

    private fun delete(inmueble : Inmueble) {

        val ref = db.collection("inmueblesv3").document(inmueble.getIdd().toString())
        val refUser = db.collection("users").document(userId)
        db.runBatch { batch ->
            batch.delete(ref)
        }.addOnCompleteListener {}

        db.runTransaction { transaction ->
            val snapshot = transaction.get(refUser)
            var pisos = snapshot.get("pisos")!! as ArrayList<String>
            pisos.remove(inmueble.getIdd())

            userId.let {
                db.collection("users").document(it)
                    .update("pisos", pisos)
                    .addOnSuccessListener {
                        Log.d(
                            ContentValues.TAG,
                            "DocumentSnapshot successfully written!"
                        )
                    }
                    .addOnFailureListener { Log.w(ContentValues.TAG, "Error writing document") }
            }
        }
    }

    private fun checkFormats() : Boolean{


        if(precioBox.text.toString().matches("-?\\d+(\\.\\d+)?".toRegex()) &&
            habitacionesBox.text.toString().matches("-?\\d+(\\.\\d+)?".toRegex()) &&
            banosBox.text.toString().matches("-?\\d+(\\.\\d+)?".toRegex()) &&
            superficieBox.text.toString().matches("-?\\d+(\\.\\d+)?".toRegex()) &&
            certificadoBox.text.toString().trim().length == 1)
            {
            return true
        }

        return false
    }
}