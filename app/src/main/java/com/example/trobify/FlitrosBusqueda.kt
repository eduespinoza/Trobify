package com.example.trobify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.*
import android.widget.Toast
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FlitrosBusqueda : AppCompatActivity() {

    var desplegable_inmueble = findViewById<Spinner>(R.id.desplegable_tipo_inmueble)
    var desplegable_vivienda = findViewById<Spinner>(R.id.desplegable_tipo_vivienda)
    var desplegable_precio_min = findViewById<Spinner>(R.id.desplegable_min_precio)
    var desplegable_precio_max = findViewById<Spinner>(R.id.desplegable_max_precio)
    var desplegable_habitaciones = findViewById<Spinner>(R.id.desplegable_num_habitaciones)
    var desplegable_baños = findViewById<Spinner>(R.id.desplegable_num_baños)
    var desplegable_superficie_min = findViewById<Spinner>(R.id.desplegable_min_superficie)
    var desplegable_superficie_max = findViewById<Spinner>(R.id.desplegable_max_superficie)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flitros__busqueda)

        addOptionsInmueble()
        addOptionsViviendaPorDefecto()
        addOptionsPrice()
        addOptionsHabitaciones()
        addOptionsBaños()
        addOptionsSuperficie()
        //selectedOptionInmueble()
    }

    private fun selectedOptionInmueble(selectedListener: AdapterView.OnItemSelectedListener){
        if (desplegable_inmueble.selectedItem.equals("Edificio")){
            addOptionsViviendaEdificio()
        }
        else if (desplegable_inmueble.selectedItem.equals("Terreno") || desplegable_inmueble.selectedItem.equals("Nave")){
            // Deshabilitar las opciones de : Tipo de vivienda, Num Habitaciones, Num baños, Esatdo de la vivienda y Extras
            desplegable_vivienda.setEnabled(false)
            desplegable_habitaciones.setEnabled(false)
            desplegable_baños.setEnabled(false)

        }
        else if (desplegable_inmueble.selectedItem.equals("Garaje")){
            // Deshabilitar las opciones de Extras, Num habitaciones, tipo de vivienda y numero de baños
            desplegable_vivienda.setEnabled(false)
            desplegable_habitaciones.setEnabled(false)
            desplegable_baños.setEnabled(false)
        }
    }

    private fun addOptionsInmueble(){
        var options_inmueble = ArrayList<String>()

        options_inmueble.add("Vivienda")
        options_inmueble.add("Edificio")
        options_inmueble.add("Oficina")
        options_inmueble.add("Garaje")
        options_inmueble.add("Local")
        options_inmueble.add("Terreno")
        options_inmueble.add("Nave")

        /* AÑADIR LAS OPCIONES A LOS DESPLEGABLES */

        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options_inmueble)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        desplegable_inmueble!!.adapter = adapter
    }

    private fun addOptionsViviendaPorDefecto(){
        var options_vivienda = ArrayList<String>()
    }

    private fun addOptionsViviendaEdificio(){
        // Solo las opciones de los pisos
    }

    private fun addOptionsPrice(){
        var options_price_min = ArrayList<Int>()
        var options_price_max = ArrayList<Int>()

        options_price_min.add(50000)
        options_price_min.add(75000)
        options_price_min.add(100000)
        options_price_min.add(125000)
        options_price_min.add(150000)
        options_price_min.add(200000)
        options_price_min.add(300000)
        options_price_min.add(400000)
        options_price_min.add(500000)
        //-------------------------------------------
        options_price_max.add(50000)
        options_price_max.add(75000)
        options_price_max.add(100000)
        options_price_max.add(125000)
        options_price_max.add(150000)
        options_price_max.add(200000)
        options_price_max.add(300000)
        options_price_max.add(400000)
        options_price_min.add(500000)

        /* AÑADIR LAS OPCIONES A LOS DESPLEGABLES */

        var adapter = ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, options_price_min)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        desplegable_precio_min!!.adapter = adapter

        var adapter1 = ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, options_price_max)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        desplegable_precio_max!!.adapter = adapter1
    }

    private fun addOptionsHabitaciones(){
        var options_habitaciones = ArrayList<Int>()

        options_habitaciones.add(1)
        options_habitaciones.add(2)
        options_habitaciones.add(3)
        options_habitaciones.add(4)

        /* AÑADIR LAS OPCIONES A LOS DESPLEGABLES */

        var adapter = ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, options_habitaciones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        desplegable_habitaciones!!.adapter = adapter
    }

    private fun addOptionsBaños(){
        var options_baños = ArrayList<Int>()

        options_baños.add(1)
        options_baños.add(2)
        options_baños.add(3)

        /* AÑADIR LAS OPCIONES A LOS DESPLEGABLES */

        var adapter = ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, options_baños)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        desplegable_baños!!.adapter = adapter
    }

    private fun addOptionsSuperficie(){
        var options_superficie_min = ArrayList<Int>()
        var options_superficie_max = ArrayList<Int>()

        options_superficie_min.add(60)
        options_superficie_min.add(80)
        options_superficie_min.add(100)
        options_superficie_min.add(120)
        options_superficie_min.add(140)
        options_superficie_min.add(160)
        options_superficie_min.add(180)
        options_superficie_min.add(200)
        options_superficie_min.add(220)
        options_superficie_min.add(240)
        options_superficie_min.add(260)
        options_superficie_min.add(300)
        //-------------------------------------
        options_superficie_max.add(60)
        options_superficie_max.add(80)
        options_superficie_max.add(100)
        options_superficie_max.add(120)
        options_superficie_max.add(140)
        options_superficie_max.add(160)
        options_superficie_max.add(180)
        options_superficie_max.add(200)
        options_superficie_max.add(220)
        options_superficie_max.add(240)
        options_superficie_max.add(260)
        options_superficie_max.add(300)

        /* AÑADIR LAS OPCIONES A LOS DESPLEGABLES */

        var adapter = ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, options_superficie_min)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        desplegable_superficie_min!!.adapter = adapter

        var adapter1 = ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, options_superficie_max)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        desplegable_superficie_max!!.adapter = adapter1
    }
}