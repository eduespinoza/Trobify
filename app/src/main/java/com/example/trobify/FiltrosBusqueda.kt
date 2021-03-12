package com.example.trobify

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class FiltrosBusqueda : AppCompatActivity(),AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtros_busqueda)

        val spinnerInmueble = findViewById<Spinner>(R.id.desplegableTipoInmueble)
        val spinnerVivienda = findViewById<Spinner>(R.id.desplegableTipoVivienda)
        val spinnerHabtiaciones = findViewById<Spinner>(R.id.desplegableNumHabitaciones)
        val spinnerBaños = findViewById<Spinner>(R.id.desplegableNumBaños)

        addOptionsInmueble()
        addOptionsViviendaPorDefecto()
        addOptionsPrice()
        addOptionsHabitaciones()
        addOptionsBaños()
        addOptionsSuperficie()

        spinnerInmueble.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (spinnerInmueble.selectedItem.equals("Edificio")){
                    addOptionsViviendaEdificio()
                }
                else if (spinnerInmueble.selectedItem.equals("Terreno") || spinnerInmueble.selectedItem.equals("Nave")){
                    // Deshabilitar las opciones de : Tipo de vivienda, Num Habitaciones, Num baños, Esatdo de la vivienda y Extras
                    spinnerVivienda.setEnabled(false)
                    spinnerHabtiaciones.setEnabled(false)
                    spinnerBaños.setEnabled(false)
                }
                else if (spinnerInmueble.selectedItem.equals("Garaje")){
                    // Deshabilitar las opciones de Extras, Num habitaciones, tipo de vivienda y numero de baños
                    spinnerVivienda.setEnabled(false)
                    spinnerHabtiaciones.setEnabled(false)
                    spinnerBaños.setEnabled(false)
                }
                else{
                    spinnerVivienda.setEnabled(true)
                    spinnerHabtiaciones.setEnabled(true)
                    spinnerBaños.setEnabled(true)
                }
            }

        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("Not yet implemented")
    }

    private fun addOptionsInmueble(){
        val spinnerInmueble = findViewById<Spinner>(R.id.desplegableTipoInmueble)
        // El array se encuentra en res -> values -> strings.xml
        // Se ha hecho esto para en caso de traducir la aplicacion que estos elemntos tambien lo hagan
        val options_inmueble = resources.getStringArray(R.array.options_inmueble)

        /* AÑADIR LAS OPCIONES A LOS DESPLEGABLES */
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options_inmueble)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInmueble.adapter = adapter
    }

    private fun addOptionsViviendaPorDefecto(){
        val spinnerVivienda = findViewById<Spinner>(R.id.desplegableTipoVivienda)
        val options_vivienda = ArrayList<String>()
    }

    private fun addOptionsViviendaEdificio(){
        // Solo las opciones de los pisos
    }

    private fun addOptionsPrice(){
        val spinnerPrecioMin = findViewById<Spinner>(R.id.desplegableMinPrecio)
        val spinnerPrecioMax = findViewById<Spinner>(R.id.desplegableMaxPrecio)

        val options_price = listOf<Int>(50000, 75000, 100000, 125000, 150000, 200000, 300000, 400000, 500000)

        /* AÑADIR LAS OPCIONES A LOS DESPLEGABLES */

        val adapter = ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, options_price)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPrecioMin.adapter = adapter
        spinnerPrecioMax.adapter = adapter
    }

    private fun addOptionsHabitaciones(){
        val spinnerHabtiaciones = findViewById<Spinner>(R.id.desplegableNumHabitaciones)

        val options_habitaciones = listOf<Int>(1, 2, 3 ,4 ,5)

        /* AÑADIR LAS OPCIONES A LOS DESPLEGABLES */

        val adapter = ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, options_habitaciones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerHabtiaciones.adapter = adapter
    }

    private fun addOptionsBaños(){
        val spinnerBaños = findViewById<Spinner>(R.id.desplegableNumBaños)

        val options_baños = listOf<Int>(1, 2, 3)

        /* AÑADIR LAS OPCIONES A LOS DESPLEGABLES */
        val adapter = ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, options_baños)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerBaños.adapter = adapter
    }

    private fun addOptionsSuperficie(){
        val spinnerSuperficieMin = findViewById<Spinner>(R.id.desplegableMinSuperficie)
        val spinnerSuperficieMax = findViewById<Spinner>(R.id.desplegableMaxSuperficie)

        val options_superficie = listOf<Int>(60, 80, 100, 120, 140, 160, 180, 200, 220, 240, 260, 280, 300)

        /* AÑADIR LAS OPCIONES A LOS DESPLEGABLES */
        val adapter = ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, options_superficie)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSuperficieMin.adapter = adapter
        spinnerSuperficieMax.adapter = adapter
    }
}