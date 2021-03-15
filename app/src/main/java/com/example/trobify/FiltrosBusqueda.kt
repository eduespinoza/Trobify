package com.example.trobify

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class FiltrosBusqueda : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtros_busqueda)

        val spinnerInmueble = findViewById<Spinner>(R.id.desplegableTipoInmueble)
        val buttonVivienda = findViewById<Button>(R.id.buttonTipoVivienda)
        val spinnerHabtiaciones = findViewById<Spinner>(R.id.desplegableNumHabitaciones)
        val spinnerBaños = findViewById<Spinner>(R.id.desplegableNumBaños)

        val obraNueva = findViewById<CheckBox>(R.id.checkBoxEstadoObraNueva)
        val casiNuevo = findViewById<CheckBox>(R.id.checkBoxEstadoCasiNuevo)
        val muyBien = findViewById<CheckBox>(R.id.checkBoxEstadoMuyBien)
        val bien = findViewById<CheckBox>(R.id.checkBoxEstadoBien)
        val reformado = findViewById<CheckBox>(R.id.checkBoxEstadoReformado)
        val aReformar = findViewById<CheckBox>(R.id.checkBoxEstadoAReformar)

        val parking = findViewById<CheckBox>(R.id.checkBoxExtrasParking)
        val ascensor = findViewById<CheckBox>(R.id.checkBoxExtrasAscensor)
        val amueblado = findViewById<CheckBox>(R.id.checkBoxExtrasAmueblado)
        val calefaccion = findViewById<CheckBox>(R.id.checkBoxExtrasCalefaccion)
        val jardin = findViewById<CheckBox>(R.id.checkBoxExtrasJardin)
        val piscina = findViewById<CheckBox>(R.id.checkBoxExtrasPiscina)
        val terraza = findViewById<CheckBox>(R.id.checkBoxExtrasTerraza)
        val trastero = findViewById<CheckBox>(R.id.checkBoxExtrasTrastero)

        addOptionsInmueble()
        addOptionsPrice()
        addOptionsHabitaciones()
        addOptionsBaños()
        addOptionsSuperficie()

        val builder = AlertDialog.Builder(this@FiltrosBusqueda)
        builder.setTitle("Selecciona el tipo de vivienda")
        val elementosSeleccionadosTipoEdif = booleanArrayOf(false, false, false, false, false, false, false)
        val elementosSeleccionadosTipoPorDefecto = booleanArrayOf(false, false, false, false, false, false, false, false, false, false)

        spinnerInmueble.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (spinnerInmueble.selectedItem.equals("Terreno") || spinnerInmueble.selectedItem.equals("Nave")){
                    buttonVivienda.setEnabled(false)
                    spinnerHabtiaciones.setEnabled(false)
                    spinnerBaños.setEnabled(false)

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
                    buttonVivienda.setEnabled(false)
                    spinnerHabtiaciones.setEnabled(false)
                    spinnerBaños.setEnabled(false)

                    parking.setEnabled(false)
                    ascensor.setEnabled(false)
                    amueblado.setEnabled(false)
                    calefaccion.setEnabled(false)
                    jardin.setEnabled(false)
                    piscina.setEnabled(false)
                    terraza.setEnabled(false)
                    trastero.setEnabled(false)
                }
                else if (spinnerInmueble.selectedItem.equals("Local")){
                    buttonVivienda.setEnabled(false)

                    obraNueva.setEnabled(true)
                    casiNuevo.setEnabled(true)
                    muyBien.setEnabled(true)
                    bien.setEnabled(true)
                    reformado.setEnabled(true)
                    aReformar.setEnabled(true)

                    parking.setEnabled(true)
                    ascensor.setEnabled(true)
                    amueblado.setEnabled(true)
                    calefaccion.setEnabled(true)
                    terraza.setEnabled(true)
                    trastero.setEnabled(false)
                    piscina.setEnabled(false)
                    jardin.setEnabled(false)
                }
                else {
                    buttonVivienda.setEnabled(true)
                    spinnerHabtiaciones.setEnabled(true)
                    spinnerBaños.setEnabled(true)
                    spinnerBaños.setEnabled(true)

                    obraNueva.setEnabled(true)
                    casiNuevo.setEnabled(true)
                    muyBien.setEnabled(true)
                    bien.setEnabled(true)
                    reformado.setEnabled(true)
                    aReformar.setEnabled(true)

                    parking.setEnabled(true)
                    ascensor.setEnabled(true)
                    amueblado.setEnabled(true)
                    calefaccion.setEnabled(true)
                    jardin.setEnabled(true)
                    piscina.setEnabled(true)
                    terraza.setEnabled(true)
                    trastero.setEnabled(true)
                }
            }
        }

        buttonVivienda.setOnClickListener{
            if(spinnerInmueble.selectedItem.equals("Edificio") || spinnerInmueble.selectedItem.equals("Oficina")){
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
            checkPrice()
            checkSurface()
        }
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

        val options_price = listOf<Int>(0, 50000, 75000, 100000, 125000, 150000, 200000, 300000, 400000, 500000)
        val options_price_max = listOf<Int>(50000, 75000, 100000, 125000, 150000, 200000, 300000, 400000, 500000)

        /* AÑADIR LAS OPCIONES A LOS DESPLEGABLES */
        val adapter0 = ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, options_price)
        val adapter1 = ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, options_price_max)
        adapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPrecioMin.adapter = adapter0
        spinnerPrecioMax.adapter = adapter1
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

    private fun checkPrice(){
        val spinnerPrecioMin = findViewById<Spinner>(R.id.desplegableMinPrecio)
        val spinnerPrecioMax = findViewById<Spinner>(R.id.desplegableMaxPrecio)
        if(compareValues(spinnerPrecioMin.selectedItem.toString().toInt(), spinnerPrecioMax.selectedItem.toString().toInt()) >= 0){
            AlertDialog.Builder(this@FiltrosBusqueda).apply {
                setTitle("Error")
                setMessage("El rango de valores de precio es incorrecto.")
                setPositiveButton("Ok", null)
            }.show()
        }
    }

    private fun checkSurface(){
        val spinnerSuperficieMin = findViewById<Spinner>(R.id.desplegableMinSuperficie)
        val spinnerSuperficieMax = findViewById<Spinner>(R.id.desplegableMaxSuperficie)
        if(compareValues(spinnerSuperficieMin.selectedItem.toString().toInt(), spinnerSuperficieMax.selectedItem.toString().toInt()) >= 0){
            AlertDialog.Builder(this@FiltrosBusqueda).apply {
                setTitle("Error")
                setMessage("El rango de valores de superficie es incorrecto.")
                setPositiveButton("Ok", null)
            }.show()
        }
    }
}