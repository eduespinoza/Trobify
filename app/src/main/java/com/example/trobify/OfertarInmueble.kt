package com.example.trobify

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime

class OfertarInmueble : AppCompatActivity() {

    private var user : String? = null

    private var fotos : ArrayList<Int> = arrayListOf()
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

    private var caracteristicas : String? = null

    object estadosVivienda{
        lateinit var obraNueva: CheckBox
        lateinit var casiNuevo: CheckBox
        lateinit var muyBien: CheckBox
        lateinit var bien: CheckBox
        lateinit var reformado: CheckBox
        lateinit var aReformar: CheckBox
    }
    object extrasVivienda{
        lateinit var parking:CheckBox
        lateinit var ascensor:CheckBox
        lateinit var amueblado:CheckBox
        lateinit var calefaccion:CheckBox
        lateinit var jardin:CheckBox
        lateinit var piscina:CheckBox
        lateinit var terraza:CheckBox
        lateinit var trastero:CheckBox
    }

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ofertar_inmueble)

        intent.getStringExtra("user")?.let { user = it }

        val bBack = findViewById<Button>(R.id.buttonBackOfertar)
        bBack.setOnClickListener { goBack() }

        //val bPhoto = findViewById<FloatingActionButton>(R.id.button_add_photo)
        //bPhoto.setOnClickListener{
            //addPhoto()
        //}


        val bCompare = findViewById<Button>(R.id.buttonComparar)
        bCompare.setOnClickListener { compare() }

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

        }

    private fun goBack(){
        val goBack = Intent(this, MainTrobify::class.java)
        goBack.putExtra("user",user)
        startActivity(goBack)
    }
    private fun addPhoto(){



    }
    private fun compare(){
        val builder =  AlertDialog.Builder(this)
        builder.setTitle("Funcion no implementada" )
        builder.setIcon(android.R.drawable.stat_notify_error)
        builder.setNeutralButton("  Continue  "){ _, _ -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }




    private fun chooseInmueble() {

        val builder = AlertDialog.Builder(this@OfertarInmueble)
        builder.setTitle("Selecciona el tipo de inmueble")

        val optionsInmueble = resources.getStringArray(R.array.options_inmueble)
        builder.setItems(optionsInmueble) { _, witch ->

            //if(elementosSeleccionadosTipoEdif[which]){
            //FiltrosBusqueda.filtros.tipoVivienda.add(optionsViviendaEdificio[which].toString())
            //}
            //else{
            //FiltrosBusqueda.filtros.tipoVivienda.remove(optionsViviendaEdificio[which].toString())
            //}
            //}

            //else{
            //val optionsViviendaPorDefecto = resources.getStringArray(R.array.options_vivienda_por_defecto)
            //FiltrosBusqueda.filtros.tipoVivienda.clear()
            //builder.setMultiChoiceItems(optionsViviendaPorDefecto, elementosSeleccionadosTipoPorDefecto){dialog, which, isChecked ->
            //elementosSeleccionadosTipoPorDefecto[which] = isChecked
            //if(elementosSeleccionadosTipoPorDefecto[which]){
            //FiltrosBusqueda.filtros.tipoVivienda.add(optionsViviendaPorDefecto[which].toString())
            //}
            //else{
            //FiltrosBusqueda.filtros.tipoVivienda.remove(optionsViviendaPorDefecto[which].toString())
            //}
            //}

            //builder.setPositiveButton("Ok"){dialog, which -> dialog.dismiss()}
            //builder.setNeutralButton("Cancel"){dialog, which -> dialog.dismiss()}
            //val options = builder.create()
            //options.show()
        }
    }

    private fun chooseAnuncio(){}
    private fun chooseVivienda(){

    }

    private fun selectExtras(){



    }
    private fun selectCondition(){}
    private fun post(){}



    }
