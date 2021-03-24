package com.example.trobify

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

open class FiltrosBusqueda : AppCompatActivity() {

    object filtros {
        var tipoInmueble = GuardaFiltros.filtrosGuardados.tipoInmueble
        var numHabitaciones = GuardaFiltros.filtrosGuardados.numHabitaciones
        var numBaños:Int = GuardaFiltros.filtrosGuardados.numBaños
        var extras = GuardaFiltros.filtrosGuardados.extras
        var estado = GuardaFiltros.filtrosGuardados.estado
        var precioMin:Int = GuardaFiltros.filtrosGuardados.precioMin
        var precioMax:Int = GuardaFiltros.filtrosGuardados.precioMax
        var superficieMin:Int = GuardaFiltros.filtrosGuardados.superficieMin
        var superficieMax:Int = GuardaFiltros.filtrosGuardados.superficieMax
        var tipoVivienda = GuardaFiltros.filtrosGuardados.tipoVivienda
    }
    var checkPriceBool = true
    var checkSurfaceBool = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtros_busqueda)

        val spinnerInmueble = findViewById<Spinner>(R.id.desplegableTipoInmueble)
        val buttonVivienda = findViewById<Button>(R.id.buttonTipoVivienda)
        val spinnerHabtiaciones = findViewById<Spinner>(R.id.desplegableNumHabitaciones)
        val spinnerBaños = findViewById<Spinner>(R.id.desplegableNumBaños)
        val spinnerPrecioMin = findViewById<Spinner>(R.id.desplegableMinPrecio)
        val spinnerPrecioMax = findViewById<Spinner>(R.id.desplegableMaxPrecio)
        val spinnerSuperficieMin = findViewById<Spinner>(R.id.desplegableMinSuperficie)
        val spinnerSuperficieMax = findViewById<Spinner>(R.id.desplegableMaxSuperficie)

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

        spinnerInmueble.onItemSelectedListener {
            if(spinnerInmueble.selectedItem == true){
                filtros.tipoInmueble = spinnerInmueble.selectedItem.toString()
            }
        }

        spinnerPrecioMin.onItemSelectedListener {
            if(spinnerPrecioMin.selectedItem == true){
                filtros.precioMin = spinnerPrecioMin.selectedItem.toString().toInt()
            }
        }
        spinnerPrecioMax.onItemSelectedListener {
            if(spinnerPrecioMax.selectedItem == true){
                filtros.precioMax = spinnerPrecioMax.selectedItem.toString().toInt()
            }
        }

        spinnerHabtiaciones.onItemSelectedListener {
            if(spinnerHabtiaciones.selectedItem == true){
                filtros.numHabitaciones = spinnerHabtiaciones.selectedItem.toString().toInt()
            }
        }

        spinnerBaños.onItemSelectedListener {
            if(spinnerBaños.selectedItem == true){
                filtros.numBaños = spinnerBaños.selectedItem.toString().toInt()
            }
        }

        spinnerSuperficieMax.onItemSelectedListener {
            if(spinnerSuperficieMax.selectedItem == true){
                filtros.superficieMax = spinnerSuperficieMax.selectedItem.toString().toInt()
            }
        }
        spinnerSuperficieMin.onItemSelectedListener {
            if(spinnerSuperficieMin.selectedItem == true){
                filtros.superficieMin = spinnerSuperficieMin.selectedItem.toString().toInt()
            }
        }

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

        // ESTADO DE LA VIVIENDA ////////////////////////////

        obraNueva.setOnClickListener {
            if(obraNueva.isChecked){
                filtros.estado.add("Obra nueva")
            }
            else{
                filtros.estado.remove("Obra nueva")
            }
            println(filtros.estado)
        }
        casiNuevo.setOnClickListener {
            if(casiNuevo.isChecked){
                filtros.estado.add("Casi nuevo")
            }
            else{
                filtros.estado.remove("Casi nuevo")
            }
            println(filtros.estado)
        }
        muyBien.setOnClickListener {
            if(muyBien.isChecked){
                filtros.estado.add("Muy bien")
            }
            else{
                filtros.estado.remove("Muy bien")
            }
            println(filtros.estado)
        }
        bien.setOnClickListener {
            if(bien.isChecked){
                filtros.estado.add("Bien")
            }
            else{
                filtros.estado.remove("Bien")
            }
            println(filtros.estado)
        }
        reformado.setOnClickListener {
            if(reformado.isChecked){
                filtros.estado.add("Reformado")
            }
            else{
                filtros.estado.remove("Reformado")
            }
            println(filtros.estado)
        }
        aReformar.setOnClickListener {
            if(aReformar.isChecked){
                filtros.estado.add("A reformar")
            }
            else{
                filtros.estado.remove("A reformar")
            }
            println(filtros.estado)
        }

        // EXTRAS /////////////////////////
        filtros.extras = mutableMapOf("Parking" to false,
            "Ascensor" to false,
            "Amueblado" to false,
            "Calefaccion" to false,
            "Terraza" to false,
            "Trastero" to false,
            "Piscina" to false,
            "Jardin" to false
        )
        parking.setOnClickListener {
            if(parking.isChecked){
                filtros.extras.put("Parking", true)
            }
            else{
                filtros.extras.put("Parking", false)
            }
            println(filtros.extras)
        }
        ascensor.setOnClickListener {
            if(ascensor.isChecked){
                filtros.extras.put("Ascensor", true)
            }
            else{
                filtros.extras.put("Ascensor", false)
            }
            println(filtros.extras)
        }
        amueblado.setOnClickListener {
            if(amueblado.isChecked){
                filtros.extras.put("Amueblado", true)
            }
            else{
                filtros.extras.put("Amueblado", false)
            }
            println(filtros.extras)
        }
        calefaccion.setOnClickListener {
            if(calefaccion.isChecked){
                filtros.extras.put("Calefaccion", true)
            }
            else{
                filtros.extras.put("Calefaccion", false)
            }
            println(filtros.extras)
        }
        terraza.setOnClickListener {
            if(terraza.isChecked){
                filtros.extras.put("Terraza", true)
            }
            else{
                filtros.extras.put("Terraza", false)
            }
            println(filtros.extras)

        }
        trastero.setOnClickListener {
            if(trastero.isChecked){
                filtros.extras.put("Trastero", true)
            }
            else{
                filtros.extras.put("Trastero", false)
            }
            println(filtros.extras)
        }
        piscina.setOnClickListener {
            if(piscina.isChecked){
                filtros.extras.put("Piscina", true)
            }
            else{
                filtros.extras.put("Piscina", false)
            }
            println(filtros.extras)
        }
        jardin.setOnClickListener {
            if(jardin.isChecked){
                filtros.extras.put("Jardin", true)
            }
            else{
                filtros.extras.put("Jardin", false)
            }
            println(filtros.extras)
        }

        val builder = AlertDialog.Builder(this@FiltrosBusqueda)
        builder.setTitle("Selecciona el tipo de vivienda")
        val elementosSeleccionadosTipoEdif = booleanArrayOf(
            false, // Apartamento
            false, // Ático
            false, // Dúplex
            false, // Loft
            false, // Planta baja
            false  // Estudio
        )
        val elementosSeleccionadosTipoPorDefecto = booleanArrayOf(
            false, // Apartamento
            false, // Ático
            false, // Dúplex
            false, // Loft
            false, // Planta baja
            false, // Estudio
            false, // Casa
            false, // Chalet
            false, // Adosado
            false  // Finca rústica
        )

        buttonVivienda.setOnClickListener{
            if(spinnerInmueble.selectedItem.equals("Edificio") || spinnerInmueble.selectedItem.equals("Oficina")){
                val optionsViviendaEdificio = resources.getStringArray(R.array.options_vivienda_edificio)
                filtros.tipoVivienda.clear()
                builder.setMultiChoiceItems(optionsViviendaEdificio, elementosSeleccionadosTipoEdif){dialog, which, isChecked ->
                    elementosSeleccionadosTipoEdif[which] = isChecked
                    if(elementosSeleccionadosTipoEdif[which]){
                        filtros.tipoVivienda.add(optionsViviendaEdificio[which].toString())
                    }
                    else{
                        filtros.tipoVivienda.remove(optionsViviendaEdificio[which].toString())
                    }
                    println(filtros.tipoVivienda)
                }
            }
            else{
                val optionsViviendaPorDefecto = resources.getStringArray(R.array.options_vivienda_por_defecto)
                filtros.tipoVivienda.clear()
                builder.setMultiChoiceItems(optionsViviendaPorDefecto, elementosSeleccionadosTipoPorDefecto){dialog, which, isChecked ->
                    elementosSeleccionadosTipoPorDefecto[which] = isChecked
                    if(elementosSeleccionadosTipoPorDefecto[which]){
                        filtros.tipoVivienda.add(optionsViviendaPorDefecto[which].toString())
                    }
                    else{
                        filtros.tipoVivienda.remove(optionsViviendaPorDefecto[which].toString())
                    }
                    println(filtros.tipoVivienda)
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
            if(checkPriceBool && checkSurfaceBool){
                saveFiltros()
                val goSearch = Intent(this, MainTrobify::class.java)
                startActivity(goSearch)
            }
            else{}
        }

        val buttonBack = findViewById<ImageButton>(R.id.buttonBack)
        buttonBack.setOnClickListener{
            finish()
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
        val options_price_max = listOf<Int>(0, 50000, 75000, 100000, 125000, 150000, 200000, 300000, 400000, 500000)

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

        val options_baños = listOf<Int>(0, 1, 2, 3)

        /* AÑADIR LAS OPCIONES A LOS DESPLEGABLES */
        val adapter = ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, options_baños)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerBaños.adapter = adapter
    }

    private fun addOptionsSuperficie(){
        val spinnerSuperficieMin = findViewById<Spinner>(R.id.desplegableMinSuperficie)
        val spinnerSuperficieMax = findViewById<Spinner>(R.id.desplegableMaxSuperficie)

        val options_superficie = listOf<Int>(0, 60, 80, 100, 120, 140, 160, 180, 200, 220, 240, 260, 280, 300)

        /* AÑADIR LAS OPCIONES A LOS DESPLEGABLES */
        val adapter = ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, options_superficie)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSuperficieMin.adapter = adapter
        spinnerSuperficieMax.adapter = adapter
    }

    private fun checkPrice(){
        val spinnerPrecioMin = findViewById<Spinner>(R.id.desplegableMinPrecio)
        val spinnerPrecioMax = findViewById<Spinner>(R.id.desplegableMaxPrecio)
        if(compareValues(spinnerPrecioMin.selectedItem.toString().toInt(), spinnerPrecioMax.selectedItem.toString().toInt()) > 0){
            AlertDialog.Builder(this@FiltrosBusqueda).apply {
                checkPriceBool = false
                setTitle("Error")
                setMessage("El rango de valores de precio es incorrecto.")
                setPositiveButton("Ok", null)
            }.show()
        }
        else{
            checkPriceBool = true
        }
    }

    private fun checkSurface(){
        val spinnerSuperficieMin = findViewById<Spinner>(R.id.desplegableMinSuperficie)
        val spinnerSuperficieMax = findViewById<Spinner>(R.id.desplegableMaxSuperficie)
        if(compareValues(spinnerSuperficieMin.selectedItem.toString().toInt(), spinnerSuperficieMax.selectedItem.toString().toInt()) > 0){
            AlertDialog.Builder(this@FiltrosBusqueda).apply {
                checkSurfaceBool = false
                setTitle("Error")
                setMessage("El rango de valores de superficie es incorrecto.")
                setPositiveButton("Ok", null)
            }.show()
        }
        else{
            checkSurfaceBool = true
        }
    }

    private fun saveFiltros(){
        GuardaFiltros.filtrosGuardados.tipoInmueble = filtros.tipoInmueble
        GuardaFiltros.filtrosGuardados.tipoVivienda = filtros.tipoVivienda
        GuardaFiltros.filtrosGuardados.numHabitaciones = filtros.numHabitaciones
        GuardaFiltros.filtrosGuardados.numBaños = filtros.numBaños
        GuardaFiltros.filtrosGuardados.extras = filtros.extras
        GuardaFiltros.filtrosGuardados.precioMin = filtros.precioMin
        GuardaFiltros.filtrosGuardados.precioMax = filtros.precioMax
        GuardaFiltros.filtrosGuardados.superficieMin = filtros.superficieMin
        GuardaFiltros.filtrosGuardados.superficieMax = filtros.superficieMax
        GuardaFiltros.filtrosGuardados.estado = filtros.estado
    }
}

private operator fun AdapterView.OnItemSelectedListener?.invoke(function : () -> Unit) {}

