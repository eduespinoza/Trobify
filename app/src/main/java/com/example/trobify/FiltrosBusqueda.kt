package com.example.trobify

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class FiltrosBusqueda : AppCompatActivity(),AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtros_busqueda)

        val spinnerInmueble = findViewById<Spinner>(R.id.desplegableTipoInmueble)
        val buttonVivienda = findViewById<Button>(R.id.buttonTipoVivienda)
        val spinnerHabtiaciones = findViewById<Spinner>(R.id.desplegableNumHabitaciones)
        val spinnerBaños = findViewById<Spinner>(R.id.desplegableNumBaños)

        addOptionsInmueble()
        addOptionsPrice()
        addOptionsHabitaciones()
        addOptionsBaños()
        addOptionsSuperficie()

        spinnerInmueble.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (spinnerInmueble.selectedItem.equals("Terreno") || spinnerInmueble.selectedItem.equals("Nave")){
                    // Deshabilitar las opciones de : Tipo de vivienda, Num Habitaciones, Num baños, Estado de la vivienda y Extras
                    buttonVivienda.setEnabled(false)
                    spinnerHabtiaciones.setEnabled(false)
                    spinnerBaños.setEnabled(false)

                    val obraNueva = findViewById<CheckBox>(R.id.checkBoxEstadoObraNueva)
                    val casiNuevo = findViewById<CheckBox>(R.id.checkBoxEstadoCasiNuevo)
                    val muyBien = findViewById<CheckBox>(R.id.checkBoxEstadoMuyBien)
                    val bien = findViewById<CheckBox>(R.id.checkBoxEstadoBien)
                    val reformado = findViewById<CheckBox>(R.id.checkBoxEstadoReformado)
                    val aReformar = findViewById<CheckBox>(R.id.checkBoxEstadoAReformar)
                    obraNueva.setEnabled(false)
                    casiNuevo.setEnabled(false)
                    muyBien.setEnabled(false)
                    bien.setEnabled(false)
                    reformado.setEnabled(false)
                    aReformar.setEnabled(false)

                        /*val contenedorEstadoVivienda = findViewById<LinearLayout>(R.id.contenedorEstadoVivienda)
                        contenedorEstadoVivienda.setVisibility(View.INVISIBLE)*/

                }
                else if (spinnerInmueble.selectedItem.equals("Garaje")){
                    // Deshabilitar las opciones de Extras, Num habitaciones, tipo de vivienda y numero de baños
                    buttonVivienda.setEnabled(false)
                    spinnerHabtiaciones.setEnabled(false)
                    spinnerBaños.setEnabled(false)
                }
                else{
                    buttonVivienda.setEnabled(true)
                    spinnerHabtiaciones.setEnabled(true)
                    spinnerBaños.setEnabled(true)
                }
            }
        }

        val builder = AlertDialog.Builder(this@FiltrosBusqueda)
        builder.setTitle("Selecciona el tipo de vivienda")
        val elementosSeleccionadosTipoEdif = booleanArrayOf(false, false, false, false, false, false, false)
        val elementosSeleccionadosTipoPorDefecto = booleanArrayOf(false, false, false, false, false, false, false, false, false, false)

        buttonVivienda.setOnClickListener{
            if(spinnerInmueble.selectedItem.equals("Edificio")){
                val optionsViviendaEdificio = resources.getStringArray(R.array.options_vivienda_edificio)

                builder.setMultiChoiceItems(optionsViviendaEdificio, elementosSeleccionadosTipoEdif){dialog, which, isChecked ->
                    elementosSeleccionadosTipoEdif[which] = isChecked
                }
            }
            else{
                val optionsViviendaArray = resources.getStringArray(R.array.options_vivienda_por_defecto)

                builder.setMultiChoiceItems(optionsViviendaArray, elementosSeleccionadosTipoPorDefecto){dialog, which, isChecked ->
                    elementosSeleccionadosTipoPorDefecto[which] = isChecked
                }
            }
            builder.setPositiveButton("Ok"){dialog, which -> dialog.dismiss()}
            builder.setNeutralButton("Cancel"){dialog, which -> dialog.dismiss()}
            val dialog = builder.create()
            dialog.show()
        }

        val buttonAplicar = findViewById<Button>(R.id.buttonAplicar)
        buttonAplicar.setOnClickListener{
            // ACABAR
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
        // Se ha hecho esto para en caso de traducir la aplicación que estos elemntos también lo hagan
        val options_inmueble = resources.getStringArray(R.array.options_inmueble)

        /* AÑADIR LAS OPCIONES A LOS DESPLEGABLES */
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options_inmueble)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInmueble.adapter = adapter
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