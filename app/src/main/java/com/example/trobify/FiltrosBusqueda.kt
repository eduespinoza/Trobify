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
    lateinit var buttonVivienda:Button

    object desplegables{
        lateinit var spinnerInmueble:Spinner
        lateinit var spinnerBaños:Spinner
        lateinit var spinnerHabitaciones:Spinner
        lateinit var spinnerPrecioMin:Spinner
        lateinit var spinnerPrecioMax:Spinner
        lateinit var spinnerSuperficieMin:Spinner
        lateinit var spinnerSuperficieMax:Spinner
    }
    object estadosVivienda{
        lateinit var obraNueva:CheckBox
        lateinit var casiNuevo:CheckBox
        lateinit var muyBien:CheckBox
        lateinit var bien:CheckBox
        lateinit var reformado:CheckBox
        lateinit var aReformar:CheckBox
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtros_busqueda)

        desplegables.spinnerInmueble = findViewById<Spinner>(R.id.desplegableTipoInmueble)
        desplegables.spinnerHabitaciones = findViewById<Spinner>(R.id.desplegableNumHabitaciones)
        desplegables.spinnerBaños = findViewById<Spinner>(R.id.desplegableNumBaños)
        desplegables.spinnerPrecioMin = findViewById<Spinner>(R.id.desplegableMinPrecio)
        desplegables.spinnerPrecioMax = findViewById<Spinner>(R.id.desplegableMaxPrecio)
        desplegables.spinnerSuperficieMin = findViewById<Spinner>(R.id.desplegableMinSuperficie)
        desplegables.spinnerSuperficieMax = findViewById<Spinner>(R.id.desplegableMaxSuperficie)
        buttonVivienda = findViewById<Button>(R.id.buttonTipoVivienda)

        estadosVivienda.obraNueva = findViewById<CheckBox>(R.id.checkBoxEstadoObraNueva)
        estadosVivienda.casiNuevo = findViewById<CheckBox>(R.id.checkBoxEstadoCasiNuevo)
        estadosVivienda.muyBien = findViewById<CheckBox>(R.id.checkBoxEstadoMuyBien)
        estadosVivienda.bien = findViewById<CheckBox>(R.id.checkBoxEstadoBien)
        estadosVivienda.reformado = findViewById<CheckBox>(R.id.checkBoxEstadoReformado)
        estadosVivienda.aReformar = findViewById<CheckBox>(R.id.checkBoxEstadoAReformar)

        extrasVivienda.parking = findViewById<CheckBox>(R.id.checkBoxExtrasParking)
        extrasVivienda.ascensor = findViewById<CheckBox>(R.id.checkBoxExtrasAscensor)
        extrasVivienda.amueblado = findViewById<CheckBox>(R.id.checkBoxExtrasAmueblado)
        extrasVivienda.calefaccion = findViewById<CheckBox>(R.id.checkBoxExtrasCalefaccion)
        extrasVivienda.jardin = findViewById<CheckBox>(R.id.checkBoxExtrasJardin)
        extrasVivienda.piscina = findViewById<CheckBox>(R.id.checkBoxExtrasPiscina)
        extrasVivienda.terraza = findViewById<CheckBox>(R.id.checkBoxExtrasTerraza)
        extrasVivienda.trastero = findViewById<CheckBox>(R.id.checkBoxExtrasTrastero)

        addOptionsInmueble()
        addOptionsPrice()
        addOptionsHabitaciones()
        addOptionsBaños()
        addOptionsSuperficie()

        desplegables.spinnerPrecioMin.onItemSelectedListener {
            filtros.precioMin = desplegables.spinnerPrecioMin.selectedItem.toString().toInt()
        }
        desplegables.spinnerPrecioMax.onItemSelectedListener {
            filtros.precioMax = desplegables.spinnerPrecioMax.selectedItem.toString().toInt()
        }
        desplegables.spinnerHabitaciones.onItemSelectedListener {
            filtros.numHabitaciones = desplegables.spinnerHabitaciones.selectedItem.toString().toInt()
        }
        desplegables.spinnerBaños.onItemSelectedListener {
            filtros.numBaños = desplegables.spinnerBaños.selectedItem.toString().toInt()
        }
        desplegables.spinnerSuperficieMax.onItemSelectedListener {
            filtros.superficieMax = desplegables.spinnerSuperficieMax.selectedItem.toString().toInt()
        }
        desplegables.spinnerSuperficieMin.onItemSelectedListener {
            filtros.superficieMin = desplegables.spinnerSuperficieMin.selectedItem.toString().toInt()
        }

        desplegables.spinnerInmueble.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if (desplegables.spinnerInmueble.selectedItem.equals("Terreno") || desplegables.spinnerInmueble.selectedItem.equals("Nave")){
                    buttonVivienda.setEnabled(false)
                    desplegables.spinnerHabitaciones.setEnabled(false)
                    desplegables.spinnerBaños.setEnabled(false)

                    setFalseEstadosVivienda()
                    setFalseExtras()
                }
                else if (desplegables.spinnerInmueble.selectedItem.equals("Garaje")){
                    buttonVivienda.setEnabled(false)
                    desplegables.spinnerHabitaciones.setEnabled(false)
                    desplegables.spinnerBaños.setEnabled(false)

                    setFalseExtras()
                }
                else if (desplegables.spinnerInmueble.selectedItem.equals("Local")){
                    buttonVivienda.setEnabled(false)

                    setTrueEstadosVivienda()
                    setFalseExtras()
                }
                else {
                    buttonVivienda.setEnabled(true)
                    desplegables.spinnerHabitaciones.setEnabled(true)
                    desplegables.spinnerBaños.setEnabled(true)
                    desplegables.spinnerBaños.setEnabled(true)

                    setTrueEstadosVivienda()
                    setTrueExtras()
                }
                filtros.tipoInmueble = desplegables.spinnerInmueble.selectedItem.toString()
            }
        }

        // ESTADO DE LA VIVIENDA ////////////////////////////

        estadosVivienda.obraNueva.setOnClickListener {
            if(estadosVivienda.obraNueva.isChecked){
                filtros.estado.add("Obra nueva")
            }
            else{
                filtros.estado.remove("Obra nueva")
            }
            println(filtros.estado)
        }
        estadosVivienda.casiNuevo.setOnClickListener {
            if(estadosVivienda.casiNuevo.isChecked){
                filtros.estado.add("Casi nuevo")
            }
            else{
                filtros.estado.remove("Casi nuevo")
            }
            println(filtros.estado)
        }
        estadosVivienda.muyBien.setOnClickListener {
            if(estadosVivienda.muyBien.isChecked){
                filtros.estado.add("Muy bien")
            }
            else{
                filtros.estado.remove("Muy bien")
            }
            println(filtros.estado)
        }
        estadosVivienda.bien.setOnClickListener {
            if(estadosVivienda.bien.isChecked){
                filtros.estado.add("Bien")
            }
            else{
                filtros.estado.remove("Bien")
            }
            println(filtros.estado)
        }
        estadosVivienda.reformado.setOnClickListener {
            if(estadosVivienda.reformado.isChecked){
                filtros.estado.add("Reformado")
            }
            else{
                filtros.estado.remove("Reformado")
            }
            println(filtros.estado)
        }
        estadosVivienda.aReformar.setOnClickListener {
            if(estadosVivienda.aReformar.isChecked){
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
        extrasVivienda.parking.setOnClickListener {
            if(extrasVivienda.parking.isChecked){
                filtros.extras.put("Parking", true)
            }
            else{
                filtros.extras.put("Parking", false)
            }
            println(filtros.extras)
        }
        extrasVivienda.ascensor.setOnClickListener {
            if(extrasVivienda.ascensor.isChecked){
                filtros.extras.put("Ascensor", true)
            }
            else{
                filtros.extras.put("Ascensor", false)
            }
            println(filtros.extras)
        }
        extrasVivienda.amueblado.setOnClickListener {
            if(extrasVivienda.amueblado.isChecked){
                filtros.extras.put("Amueblado", true)
            }
            else{
                filtros.extras.put("Amueblado", false)
            }
            println(filtros.extras)
        }
        extrasVivienda.calefaccion.setOnClickListener {
            if(extrasVivienda.calefaccion.isChecked){
                filtros.extras.put("Calefaccion", true)
            }
            else{
                filtros.extras.put("Calefaccion", false)
            }
            println(filtros.extras)
        }
        extrasVivienda.terraza.setOnClickListener {
            if(extrasVivienda.terraza.isChecked){
                filtros.extras.put("Terraza", true)
            }
            else{
                filtros.extras.put("Terraza", false)
            }
            println(filtros.extras)

        }
        extrasVivienda.trastero.setOnClickListener {
            if(extrasVivienda.trastero.isChecked){
                filtros.extras.put("Trastero", true)
            }
            else{
                filtros.extras.put("Trastero", false)
            }
            println(filtros.extras)
        }
        extrasVivienda.piscina.setOnClickListener {
            if(extrasVivienda.piscina.isChecked){
                filtros.extras.put("Piscina", true)
            }
            else{
                filtros.extras.put("Piscina", false)
            }
            println(filtros.extras)
        }
        extrasVivienda.jardin.setOnClickListener {
            if(extrasVivienda.jardin.isChecked){
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
            if(desplegables.spinnerInmueble.selectedItem.equals("Edificio") || desplegables.spinnerInmueble.selectedItem.equals("Oficina")){
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
                onPause()
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

        val options_price = listOf<Int>(0, 50000, 75000, 100000, 125000, 150000, 200000, 300000, 400000, 500000)
        val options_price_max = listOf<Int>(0, 50000, 75000, 100000, 125000, 150000, 200000, 300000, 400000, 500000)

        /* AÑADIR LAS OPCIONES A LOS DESPLEGABLES */
        val adapter0 = ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, options_price)
        val adapter1 = ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, options_price_max)
        adapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        desplegables.spinnerPrecioMin.adapter = adapter0
        desplegables.spinnerPrecioMax.adapter = adapter1
    }

    private fun addOptionsHabitaciones(){
        val options_habitaciones = listOf<Int>(1, 2, 3 ,4 ,5)

        /* AÑADIR LAS OPCIONES A LOS DESPLEGABLES */
        val adapter = ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, options_habitaciones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        desplegables.spinnerHabitaciones.adapter = adapter
    }

    private fun addOptionsBaños(){
        val options_baños = listOf<Int>(0, 1, 2, 3)

        /* AÑADIR LAS OPCIONES A LOS DESPLEGABLES */
        val adapter = ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, options_baños)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        desplegables.spinnerBaños.adapter = adapter
    }

    private fun addOptionsSuperficie(){
        val options_superficie = listOf<Int>(0, 60, 80, 100, 120, 140, 160, 180, 200, 220, 240, 260, 280, 300)

        /* AÑADIR LAS OPCIONES A LOS DESPLEGABLES */
        val adapter = ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, options_superficie)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        desplegables.spinnerSuperficieMin.adapter = adapter
        desplegables.spinnerSuperficieMax.adapter = adapter
    }

    private fun checkPrice(){
        if(compareValues(desplegables.spinnerPrecioMin.selectedItem.toString().toInt(), desplegables.spinnerPrecioMax.selectedItem.toString().toInt()) > 0){
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
        if(compareValues(desplegables.spinnerSuperficieMin.selectedItem.toString().toInt(), desplegables.spinnerSuperficieMax.selectedItem.toString().toInt()) > 0){
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

    private fun setFalseExtras(){
        extrasVivienda.parking.setEnabled(false)
        extrasVivienda.ascensor.setEnabled(false)
        extrasVivienda.amueblado.setEnabled(false)
        extrasVivienda.calefaccion.setEnabled(false)
        extrasVivienda.jardin.setEnabled(false)
        extrasVivienda.piscina.setEnabled(false)
        extrasVivienda.terraza.setEnabled(false)
        extrasVivienda.trastero.setEnabled(false)
    }

    private fun setTrueExtras(){
        extrasVivienda.parking.setEnabled(true)
        extrasVivienda.ascensor.setEnabled(true)
        extrasVivienda.amueblado.setEnabled(true)
        extrasVivienda.calefaccion.setEnabled(true)
        extrasVivienda.jardin.setEnabled(true)
        extrasVivienda.piscina.setEnabled(true)
        extrasVivienda.terraza.setEnabled(true)
        extrasVivienda.trastero.setEnabled(true)
    }

    private fun setFalseEstadosVivienda(){
        estadosVivienda.obraNueva.setEnabled(false)
        estadosVivienda.casiNuevo.setEnabled(false)
        estadosVivienda.muyBien.setEnabled(false)
        estadosVivienda.bien.setEnabled(false)
        estadosVivienda.reformado.setEnabled(false)
        estadosVivienda.aReformar.setEnabled(false)
    }

    private fun setTrueEstadosVivienda(){
        estadosVivienda.obraNueva.setEnabled(true)
        estadosVivienda.casiNuevo.setEnabled(true)
        estadosVivienda.muyBien.setEnabled(true)
        estadosVivienda.bien.setEnabled(true)
        estadosVivienda.reformado.setEnabled(true)
        estadosVivienda.aReformar.setEnabled(true)
    }
}

private operator fun AdapterView.OnItemSelectedListener?.invoke(function : () -> Unit) {}
