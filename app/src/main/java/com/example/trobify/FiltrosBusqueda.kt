package com.example.trobify

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class FiltrosBusqueda : AppCompatActivity() {
    var tipoInmueble:String = ""
    var numHabitaciones:Int = 1
    var numBaños:Int = 1
    lateinit var extras:MutableMap<String, Boolean>
    lateinit var estado:ArrayList<String>
    var precioMin:Int = 0
    var precioMax:Int = 0
    var superficieMin:Int = 0
    var superficieMax:Int = 0

    lateinit var tipoVivienda:ArrayList<String>

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

        spinnerInmueble.setOnClickListener {
            if(spinnerInmueble.selectedItem == true){
                tipoInmueble = spinnerInmueble.selectedItem.toString()
            }
        }

        spinnerPrecioMin.setOnClickListener {
            if(spinnerPrecioMin.selectedItem == true){
                precioMin = spinnerPrecioMin.selectedItem.toString().toInt()
            }
        }
        spinnerPrecioMax.setOnClickListener {
            if(spinnerPrecioMax.selectedItem == true){
                precioMax = spinnerPrecioMax.selectedItem.toString().toInt()
            }
        }

        spinnerHabtiaciones.setOnClickListener {
            if(spinnerHabtiaciones.selectedItem == true){
                numHabitaciones = spinnerHabtiaciones.selectedItem.toString().toInt()
            }
        }

        spinnerBaños.setOnClickListener {
            if(spinnerBaños.selectedItem == true){
                numBaños = spinnerBaños.selectedItem.toString().toInt()
            }
        }

        spinnerSuperficieMax.setOnClickListener {
            if(spinnerSuperficieMax.selectedItem == true){
                superficieMax = spinnerSuperficieMax.selectedItem.toString().toInt()
            }
        }
        spinnerSuperficieMin.setOnClickListener {
            if(spinnerSuperficieMin.selectedItem == true){
                superficieMin = spinnerSuperficieMin.selectedItem.toString().toInt()
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
                estado.add("Obra nueva")
            }
            else{
                estado.remove("Obra nueva")
            }
        }
        casiNuevo.setOnClickListener {
            if(casiNuevo.isChecked){
                estado.add("Casi nuevo")
            }
            else{
                estado.remove("Casi nuevo")
            }
        }
        muyBien.setOnClickListener {
            if(muyBien.isChecked){
                estado.add("Muy bien")
            }
            else{
                estado.remove("Muy bien")
            }
        }
        bien.setOnClickListener {
            if(bien.isChecked){
                estado.add("Bien")
            }
            else{
                estado.remove("Bien")
            }
        }
        reformado.setOnClickListener {
            if(reformado.isChecked){
                estado.add("Reformado")
            }
            else{
                estado.remove("Reformado")
            }
        }
        aReformar.setOnClickListener {
            if(aReformar.isChecked){
                estado.add("A reformar")
            }
            else{
                estado.remove("A reformar")
            }
        }

        // EXTRAS /////////////////////////
        extras = mutableMapOf("Parking" to false,
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
                extras.put("Parking", true)
            }
            else{
                extras.put("Parking", false)
            }
        }
        ascensor.setOnClickListener {
            if(ascensor.isChecked){
                extras.put("Ascensor", true)
            }
            else{
                extras.put("Ascensor", false)
            }
        }
        amueblado.setOnClickListener {
            if(amueblado.isChecked){
                extras.put("Amueblado", true)
            }
            else{
                extras.put("Amueblado", false)
            }
        }
        calefaccion.setOnClickListener {
            if(calefaccion.isChecked){
                extras.put("Calefaccion", true)
            }
            else{
                extras.put("Calefaccion", false)
            }
        }
        terraza.setOnClickListener {
            if(terraza.isChecked){
                extras.put("Terraza", true)
            }
            else{
                extras.put("Terraza", false)
            }
        }
        trastero.setOnClickListener {
            if(trastero.isChecked){
                extras.put("Trastero", true)
            }
            else{
                extras.put("Trastero", false)
            }
        }
        piscina.setOnClickListener {
            if(piscina.isChecked){
                extras.put("Piscina", true)
            }
            else{
                extras.put("Piscina", false)
            }
        }
        jardin.setOnClickListener {
            if(jardin.isChecked){
                extras.put("Jardin", true)
            }
            else{
                extras.put("Jardin", false)
            }
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

                builder.setMultiChoiceItems(optionsViviendaEdificio, elementosSeleccionadosTipoEdif){dialog, which, isChecked ->
                    elementosSeleccionadosTipoEdif[which] = isChecked
                    if(elementosSeleccionadosTipoEdif[which]){
                        tipoVivienda.add(optionsViviendaEdificio[which])
                    }
                    else{
                        tipoVivienda.remove(optionsViviendaEdificio[which])
                    }
                }
            }
            else{
                val optionsViviendaPorDefecto = resources.getStringArray(R.array.options_vivienda_por_defecto)

                builder.setMultiChoiceItems(optionsViviendaPorDefecto, elementosSeleccionadosTipoPorDefecto){dialog, which, isChecked ->
                    elementosSeleccionadosTipoPorDefecto[which] = isChecked
                    if(elementosSeleccionadosTipoEdif[which]){
                        tipoVivienda.add(optionsViviendaPorDefecto[which])
                    }
                    else{
                        tipoVivienda.remove(optionsViviendaPorDefecto[which])
                    }
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
            val goSearch = Intent(this, MainTrobify::class.java)
            startActivity(goSearch)
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

