package com.example.trobify.controladores

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.trobify.Caretaker
import com.example.trobify.Memento
import com.example.trobify.R
import com.example.trobify.models.GuardaVistaFiltros
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_filtros_busqueda.*
import java.io.Serializable

@SuppressLint("StaticFieldLeak")
open class FiltrosBusqueda : AppCompatActivity() {

    var state = Memento()
    var cuidador = Caretaker()

    object filtros : Serializable {
        var tipoInmueble:String = "Cualquiera"
        var numHabitaciones:Int = 0
        var numBaños:Int = 0
        var extras = mutableMapOf<String, Boolean>()
        var estado = arrayListOf<String>()
        var precioMin:Int = 0
        var precioMax:Int = 0
        var superficieMin:Int = 0
        var superficieMax:Int = 0
        var tipoVivienda = arrayListOf<String>()

        var elementosSeleccionadosTipoEdif:BooleanArray = booleanArrayOf(false,/*Apartamento*/ false,/*Ático*/ false,/*Dúplex*/ false,/*Loft*/ false,/*Planta baja*/ false/*Estudio*/)
        var elementosSeleccionadosTipoPorDefecto:BooleanArray = booleanArrayOf(false,/*Apartamento*/ false,/*Ático*/ false,/*Dúplex*/ false,/*Loft*/ false,/*Planta baja*/ false,/*Estudio*/ false, /*Casa*/ false,/*Chalet*/ false,/*Adosado*/ false/*Finca rústica*/)
    }

    lateinit var options_price:List<Int>

    var checkPriceBool = true
    var checkSurfaceBool = true
    lateinit var buttonVivienda:Button

    @SuppressLint("StaticFieldLeak")
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

    private lateinit var userId:String

    override fun onCreate(savedInstanceState: Bundle?) {

        intent.getStringExtra("user")?.let { userId = it }

        setContentView(R.layout.activity_filtros_busqueda)

        cuidador.saveMemento() // Guarda el estado inicial de los filtros
        cuidador.restoreMemento()


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

        reestrablecerVistaFiltrosExtras()
        reestablecerVistaFiltrosEstado()

        desplegables.spinnerPrecioMin.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                filtros.precioMin = desplegables.spinnerPrecioMin.selectedItem.toString().toInt()
            }
        }
        desplegables.spinnerPrecioMax.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                filtros.precioMax = desplegables.spinnerPrecioMax.selectedItem.toString().toInt()
            }
        }
        desplegables.spinnerHabitaciones.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                filtros.numHabitaciones = desplegables.spinnerHabitaciones.selectedItem.toString().toInt()
            }
        }
        desplegables.spinnerBaños.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                filtros.numBaños = desplegables.spinnerBaños.selectedItem.toString().toInt()
            }
        }
        desplegables.spinnerSuperficieMax.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                filtros.superficieMax = desplegables.spinnerSuperficieMax.selectedItem.toString().toInt()
            }
        }
        desplegables.spinnerSuperficieMin.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                filtros.superficieMin = desplegables.spinnerSuperficieMin.selectedItem.toString().toInt()
            }
        }
        desplegables.spinnerInmueble.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var tituloTipoVivienda = findViewById<TextView>(R.id.textTipoDeVivienda)
                var tituloNumeroDeHabitaciones = findViewById<TextView>(R.id.textNumeroDeHabitaciones)
                var tituloNumeroDeBaños = findViewById<TextView>(R.id.textNumeroDeBaños)
                if (desplegables.spinnerInmueble.selectedItem.equals("Terreno") || desplegables.spinnerInmueble.selectedItem.equals("Nave")){
                    tituloTipoVivienda.setVisibility(View.GONE)
                    buttonVivienda.setVisibility(View.GONE)
                    tituloNumeroDeHabitaciones.setVisibility(View.GONE)
                    desplegables.spinnerHabitaciones.setVisibility(View.GONE)
                    tituloNumeroDeBaños.setVisibility(View.GONE)
                    desplegables.spinnerBaños.setVisibility(View.GONE)

                    setFalseEstadosVivienda()
                    setFalseExtras()
                }
                else if (desplegables.spinnerInmueble.selectedItem.equals("Garaje")){
                    tituloTipoVivienda.setVisibility(View.GONE)
                    buttonVivienda.setVisibility(View.GONE)
                    tituloNumeroDeHabitaciones.setVisibility(View.GONE)
                    desplegables.spinnerHabitaciones.setVisibility(View.GONE)
                    tituloNumeroDeBaños.setVisibility(View.GONE)
                    desplegables.spinnerBaños.setVisibility(View.GONE)

                    setFalseExtras()
                    setTrueEstadosVivienda()
                }
                else if (desplegables.spinnerInmueble.selectedItem.equals("Local") || desplegables.spinnerInmueble.selectedItem.equals("Oficina")){
                    tituloTipoVivienda.setVisibility(View.GONE)
                    buttonVivienda.setVisibility(View.GONE)
                    tituloNumeroDeHabitaciones.setVisibility(View.GONE)
                    desplegables.spinnerHabitaciones.setVisibility(View.GONE)
                    tituloNumeroDeBaños.setVisibility(View.VISIBLE)
                    desplegables.spinnerBaños.setVisibility(View.VISIBLE)

                    setTrueEstadosVivienda()
                    setTrueExtras()
                }
                else {
                    tituloTipoVivienda.setVisibility(View.VISIBLE)
                    buttonVivienda.setVisibility(View.VISIBLE)
                    tituloNumeroDeHabitaciones.setVisibility(View.VISIBLE)
                    desplegables.spinnerHabitaciones.setVisibility(View.VISIBLE)
                    tituloNumeroDeBaños.setVisibility(View.VISIBLE)
                    desplegables.spinnerBaños.setVisibility(View.VISIBLE)

                    setTrueEstadosVivienda()
                    setTrueExtras()
                }
                addOptionsPrice()
                filtros.tipoInmueble = desplegables.spinnerInmueble.selectedItem.toString()
            }
        }

        estadosVivienda.obraNueva.setOnClickListener {
            if(estadosVivienda.obraNueva.isChecked){
                filtros.estado.add("Obra nueva")
            }
            else{
                filtros.estado.remove("Obra nueva")
            }
        }
        estadosVivienda.casiNuevo.setOnClickListener {
            if(estadosVivienda.casiNuevo.isChecked){
                filtros.estado.add("Casi nuevo")
            }
            else{
                filtros.estado.remove("Casi nuevo")
            }
        }
        estadosVivienda.muyBien.setOnClickListener {
            if(estadosVivienda.muyBien.isChecked){
                filtros.estado.add("Muy bien")
            }
            else{
                filtros.estado.remove("Muy bien")
            }
        }
        estadosVivienda.bien.setOnClickListener {
            if(estadosVivienda.bien.isChecked){
                filtros.estado.add("Bien")
            }
            else{
                filtros.estado.remove("Bien")
            }
        }
        estadosVivienda.reformado.setOnClickListener {
            if(estadosVivienda.reformado.isChecked){
                filtros.estado.add("Reformado")
            }
            else{
                filtros.estado.remove("Reformado")
            }
        }
        estadosVivienda.aReformar.setOnClickListener {
            if(estadosVivienda.aReformar.isChecked){
                filtros.estado.add("A reformar")
            }
            else{
                filtros.estado.remove("A reformar")
            }
        }

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
        }
        extrasVivienda.ascensor.setOnClickListener {
            if(extrasVivienda.ascensor.isChecked){
                filtros.extras.put("Ascensor", true)
            }
            else{
                filtros.extras.put("Ascensor", false)
            }
        }
        extrasVivienda.amueblado.setOnClickListener {
            if(extrasVivienda.amueblado.isChecked){
                filtros.extras.put("Amueblado", true)
            }
            else{
                filtros.extras.put("Amueblado", false)
            }
        }
        extrasVivienda.calefaccion.setOnClickListener {
            if(extrasVivienda.calefaccion.isChecked){
                filtros.extras.put("Calefaccion", true)
            }
            else{
                filtros.extras.put("Calefaccion", false)
            }
        }
        extrasVivienda.terraza.setOnClickListener {
            if(extrasVivienda.terraza.isChecked){
                filtros.extras.put("Terraza", true)
            }
            else{
                filtros.extras.put("Terraza", false)
            }
        }
        extrasVivienda.trastero.setOnClickListener {
            if(extrasVivienda.trastero.isChecked){
                filtros.extras.put("Trastero", true)
            }
            else{
                filtros.extras.put("Trastero", false)
            }
        }
        extrasVivienda.piscina.setOnClickListener {
            if(extrasVivienda.piscina.isChecked){
                filtros.extras.put("Piscina", true)
            }
            else{
                filtros.extras.put("Piscina", false)
            }
        }
        extrasVivienda.jardin.setOnClickListener {
            if(extrasVivienda.jardin.isChecked){
                filtros.extras.put("Jardin", true)
            }
            else{
                filtros.extras.put("Jardin", false)
            }
        }

        val builder = AlertDialog.Builder(this@FiltrosBusqueda)
        builder.setTitle("Selecciona el tipo de vivienda")

        buttonVivienda.setOnClickListener{
            if(desplegables.spinnerInmueble.selectedItem.equals("Edificio") || desplegables.spinnerInmueble.selectedItem.equals("Oficina")){
                if(!(GuardaVistaFiltros.vistaGuardada.desTipoInmueble.equals(2) || GuardaVistaFiltros.vistaGuardada.desTipoInmueble.equals(3))){
                    filtros.tipoVivienda.clear()
                }
                val optionsViviendaEdificio = resources.getStringArray(R.array.options_vivienda_edificio)
                builder.setMultiChoiceItems(optionsViviendaEdificio, filtros.elementosSeleccionadosTipoEdif){ dialog, which, isChecked ->
                    filtros.elementosSeleccionadosTipoEdif[which] = isChecked
                    if(filtros.elementosSeleccionadosTipoEdif[which]){
                        filtros.tipoVivienda.add(optionsViviendaEdificio[which].toString())
                    }
                    else{
                        filtros.tipoVivienda.remove(optionsViviendaEdificio[which].toString())
                    }
                }
            }
            else if(desplegables.spinnerInmueble.selectedItem.equals("Cualquiera") ||  desplegables.spinnerInmueble.selectedItem.equals("Vivienda")){
                if(!(GuardaVistaFiltros.vistaGuardada.desTipoInmueble.equals(0) || GuardaVistaFiltros.vistaGuardada.desTipoInmueble.equals(1))){
                    filtros.tipoVivienda.clear()
                }
                val optionsViviendaPorDefecto = resources.getStringArray(R.array.options_vivienda_por_defecto)
                builder.setMultiChoiceItems(optionsViviendaPorDefecto, filtros.elementosSeleccionadosTipoPorDefecto){dialog, which, isChecked ->
                    filtros.elementosSeleccionadosTipoPorDefecto[which] = isChecked
                    if(filtros.elementosSeleccionadosTipoPorDefecto[which]){
                        filtros.tipoVivienda.add(optionsViviendaPorDefecto[which].toString())
                    }
                    else{
                        filtros.tipoVivienda.remove(optionsViviendaPorDefecto[which].toString())
                    }
                }
            }
            else{
                filtros.tipoVivienda.clear()
            }
            builder.setPositiveButton("Ok"){dialog, which -> dialog.dismiss()}
            builder.setNeutralButton("Cancel"){dialog, which -> dialog.dismiss()}
            val options = builder.create()
            options.show()
        }

        val buttonAplicar = findViewById<Button>(R.id.buttonAplicar)
        buttonAplicar.setOnClickListener{
            checkPrice()
            checkSurface()
            if(checkPriceBool && checkSurfaceBool){

                cuidador.saveMemento()

                saveVistaFiltros()
                val goSearch = Intent(this, MainTrobify::class.java)
                goSearch.putExtra("user", userId.toString())
                goSearch.putExtra("filtros", filtros)
                startActivity(goSearch)
            }
            else{}
        }

        val buttonBorrarFiltros = findViewById<Button>(R.id.buttonBorrarFiltros)
        buttonBorrarFiltros.setOnClickListener {
            val builder = AlertDialog.Builder(this@FiltrosBusqueda)
            builder.setMessage("¿ Estas seguro de querer borrar los filtros seleccionados ?")
            builder.setPositiveButton("Sí"){_, _ ->
                limpiarFiltros()
                val goSearch = Intent(this, MainTrobify::class.java)
                goSearch.putExtra("user", userId)
                startActivity(goSearch)
            }
            builder.setNegativeButton("No"){_, _ ->}
            val options = builder.create()
            options.show()
        }

        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener{
            finish()
        }
        super.onCreate(savedInstanceState)
    }

    private fun addOptionsInmueble(){
        val options_inmueble = resources.getStringArray(R.array.options_inmueble)

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options_inmueble)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        desplegables.spinnerInmueble.adapter = adapter

        desplegables.spinnerInmueble.setSelection(GuardaVistaFiltros.vistaGuardada.desTipoInmueble)
    }

    private fun addOptionsPrice(){
        if(desplegables.spinnerInmueble.selectedItem.equals("Garaje")){
            options_price = listOf<Int>(0, 5000, 7500, 10000, 12500, 15000, 20000, 30000, 40000, 50000, 70000)
        }
        else if(desplegables.spinnerInmueble.selectedItem.equals("Local")){
            options_price = listOf<Int>(0, 10000, 15000, 20000, 25000, 30000, 40000, 50000, 80000, 90000, 100000)
        }
        else{
            options_price = listOf<Int>(0, 50000, 75000, 100000, 125000, 150000, 200000, 300000, 400000, 500000, 700000)
        }

        val adapter = ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, options_price)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        desplegables.spinnerPrecioMin.adapter = adapter
        desplegables.spinnerPrecioMax.adapter = adapter

        desplegables.spinnerPrecioMin.setSelection(GuardaVistaFiltros.vistaGuardada.desPrecioMin)
        desplegables.spinnerPrecioMax.setSelection(GuardaVistaFiltros.vistaGuardada.desPrecioMax)
    }

    private fun addOptionsHabitaciones(){
        val options_habitaciones = listOf<Int>(0, 1, 2, 3 ,4 ,5)

        val adapter = ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, options_habitaciones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        desplegables.spinnerHabitaciones.adapter = adapter

        desplegables.spinnerHabitaciones.setSelection(GuardaVistaFiltros.vistaGuardada.desNumHabitaciones)
    }

    private fun addOptionsBaños(){
        val options_baños = listOf<Int>(0, 1, 2, 3)

        val adapter = ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, options_baños)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        desplegables.spinnerBaños.adapter = adapter

        desplegables.spinnerBaños.setSelection(GuardaVistaFiltros.vistaGuardada.desNumBaños)
    }

    private fun addOptionsSuperficie(){
        val options_superficie = listOf<Int>(0, 60, 80, 100, 120, 140, 160, 180, 200, 220, 240, 260, 280, 300)

        val adapter = ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, options_superficie)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        desplegables.spinnerSuperficieMin.adapter = adapter
        desplegables.spinnerSuperficieMax.adapter = adapter

        desplegables.spinnerSuperficieMin.setSelection(GuardaVistaFiltros.vistaGuardada.desSurfaceMin)
        desplegables.spinnerSuperficieMax.setSelection(GuardaVistaFiltros.vistaGuardada.desSurfaceMax)
    }

    private fun checkPrice(){
        if(compareValues(filtros.precioMin, filtros.precioMax) > 0 && (filtros.precioMax != 0)){
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
        if(compareValues(filtros.superficieMin, filtros.superficieMax) > 0 && (filtros.precioMax != 0)){
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

    fun createMemento() : Memento{
        state = Memento(arrayListOf(filtros.tipoInmueble,filtros.numHabitaciones,filtros.numBaños,filtros.extras,filtros.estado,filtros.precioMin,filtros.precioMax,
        filtros.superficieMin,filtros.superficieMax,filtros.tipoVivienda,filtros.elementosSeleccionadosTipoEdif,filtros.elementosSeleccionadosTipoPorDefecto))
        println("--------------------------------------------------")
        println(state.toString())
        println("--------------------------------------------------")
        return state
    }

    fun restoreMemento(memento : Memento) {
        var m = memento.getState()
        filtros.tipoInmueble = m[0] as String
        filtros.numHabitaciones = m[1] as Int
        filtros.numBaños = m[2] as Int
        filtros.extras = m[3] as MutableMap<String, Boolean>
        filtros.estado = m[4] as ArrayList<String>
        filtros.precioMin = m[5] as Int
        filtros.precioMax = m[6] as Int
        filtros.superficieMin = m[7] as Int
        filtros.superficieMax = m[8] as Int
        filtros.tipoVivienda = m[9] as ArrayList<String>
        filtros.elementosSeleccionadosTipoEdif = m[10] as BooleanArray
        filtros.elementosSeleccionadosTipoPorDefecto = m[11] as BooleanArray
        println("--------------------------------------------------")
        for(f in m){
            println(m.toString())
        }
        println("--------------------------------------------------")
    }

    private fun saveVistaFiltros(){
        GuardaVistaFiltros.vistaGuardada.desTipoInmueble = desplegables.spinnerInmueble.selectedItemId.toInt()
        GuardaVistaFiltros.vistaGuardada.desNumHabitaciones = desplegables.spinnerHabitaciones.selectedItemId.toInt()
        GuardaVistaFiltros.vistaGuardada.desNumBaños = desplegables.spinnerBaños.selectedItemId.toInt()
        GuardaVistaFiltros.vistaGuardada.desPrecioMin = desplegables.spinnerPrecioMin.selectedItemId.toInt()
        GuardaVistaFiltros.vistaGuardada.desPrecioMax = desplegables.spinnerPrecioMax.selectedItemId.toInt()
        GuardaVistaFiltros.vistaGuardada.desSurfaceMin = desplegables.spinnerSuperficieMin.selectedItemId.toInt()
        GuardaVistaFiltros.vistaGuardada.desSurfaceMax = desplegables.spinnerSuperficieMax.selectedItemId.toInt()

        GuardaVistaFiltros.vistaGuardada.boxObraNueva = estadosVivienda.obraNueva.isChecked
        GuardaVistaFiltros.vistaGuardada.boxMuyBien = estadosVivienda.muyBien.isChecked
        GuardaVistaFiltros.vistaGuardada.boxBien = estadosVivienda.bien.isChecked
        GuardaVistaFiltros.vistaGuardada.boxReformado = estadosVivienda.reformado.isChecked
        GuardaVistaFiltros.vistaGuardada.boxAReformar = estadosVivienda.aReformar.isChecked
        GuardaVistaFiltros.vistaGuardada.boxCasiNuevo = estadosVivienda.casiNuevo.isChecked

        GuardaVistaFiltros.vistaGuardada.boxParking = extrasVivienda.parking.isChecked
        GuardaVistaFiltros.vistaGuardada.boxAscensor = extrasVivienda.ascensor.isChecked
        GuardaVistaFiltros.vistaGuardada.boxAmueblado = extrasVivienda.amueblado.isChecked
        GuardaVistaFiltros.vistaGuardada.boxCalefaccion = extrasVivienda.calefaccion.isChecked
        GuardaVistaFiltros.vistaGuardada.boxJardin = extrasVivienda.jardin.isChecked
        GuardaVistaFiltros.vistaGuardada.boxPiscina = extrasVivienda.piscina.isChecked
        GuardaVistaFiltros.vistaGuardada.boxTerraza = extrasVivienda.terraza.isChecked
        GuardaVistaFiltros.vistaGuardada.boxTrastero = extrasVivienda.trastero.isChecked
    }

    private fun setFalseExtras(){
        var contenedorExtras:LinearLayout = findViewById<LinearLayout>(R.id.contenedorExtras)
        var tituloExtras = findViewById<TextView>(R.id.textExtras)
        tituloExtras.setVisibility(View.GONE)
        contenedorExtras.setVisibility(View.GONE)
    }

    private fun setTrueExtras(){
        var contenedorExtras:LinearLayout = findViewById<LinearLayout>(R.id.contenedorExtras)
        var tituloExtras = findViewById<TextView>(R.id.textExtras)
        tituloExtras.setVisibility(View.VISIBLE)
        contenedorExtras.setVisibility(View.VISIBLE)
    }

    private fun setFalseEstadosVivienda(){
        var contenedorEstadoVivienda = findViewById<LinearLayout>(R.id.contenedorEstadoVivienda)
        var tituloEstadoVivienda = findViewById<TextView>(R.id.textEstadoVivienda)
        tituloEstadoVivienda.setVisibility(View.GONE)
        contenedorEstadoVivienda.setVisibility(View.GONE)
    }

    private fun setTrueEstadosVivienda(){
        var contenedorEstadoVivienda = findViewById<LinearLayout>(R.id.contenedorEstadoVivienda)
        var tituloEstadoVivienda = findViewById<TextView>(R.id.textEstadoVivienda)
        tituloEstadoVivienda.setVisibility(View.VISIBLE)
        contenedorEstadoVivienda.setVisibility(View.VISIBLE)
    }

    private fun reestrablecerVistaFiltrosExtras(){
        if(GuardaVistaFiltros.vistaGuardada.boxParking == true){
            extrasVivienda.parking.isChecked = true
        }
        if(GuardaVistaFiltros.vistaGuardada.boxAscensor == true){
            extrasVivienda.ascensor.isChecked = true
        }
        if(GuardaVistaFiltros.vistaGuardada.boxAmueblado == true){
            extrasVivienda.amueblado.isChecked = true
        }
        if(GuardaVistaFiltros.vistaGuardada.boxCalefaccion == true){
            extrasVivienda.calefaccion.isChecked = true
        }
        if(GuardaVistaFiltros.vistaGuardada.boxJardin == true){
            extrasVivienda.jardin.isChecked = true
        }
        if(GuardaVistaFiltros.vistaGuardada.boxPiscina == true){
            extrasVivienda.piscina.isChecked = true
        }
        if(GuardaVistaFiltros.vistaGuardada.boxTerraza == true){
            extrasVivienda.terraza.isChecked = true
        }
        if(GuardaVistaFiltros.vistaGuardada.boxTrastero == true){
            extrasVivienda.trastero.isChecked = true
        }
    }

    private fun reestablecerVistaFiltrosEstado(){
        if(GuardaVistaFiltros.vistaGuardada.boxObraNueva == true){
            estadosVivienda.obraNueva.isChecked = true
        }
        if(GuardaVistaFiltros.vistaGuardada.boxMuyBien == true){
            estadosVivienda.muyBien.isChecked = true
        }
        if(GuardaVistaFiltros.vistaGuardada.boxBien == true){
            estadosVivienda.bien.isChecked = true
        }
        if(GuardaVistaFiltros.vistaGuardada.boxReformado == true){
            estadosVivienda.reformado.isChecked = true
        }
        if(GuardaVistaFiltros.vistaGuardada.boxAReformar == true){
            estadosVivienda.aReformar.isChecked = true
        }
        if(GuardaVistaFiltros.vistaGuardada.boxCasiNuevo == true){
            estadosVivienda.casiNuevo.isChecked = true
        }
    }

    private fun limpiarFiltros(){
        filtros.tipoInmueble = "Cualquiera"
        filtros.numHabitaciones = 0
        filtros.numBaños = 0
        filtros.extras = mutableMapOf<String, Boolean>()
        filtros.estado = arrayListOf<String>()
        filtros.precioMin = 0
        filtros.precioMax = 0
        filtros.superficieMin = 0
        filtros.superficieMax = 0
        filtros.tipoVivienda = arrayListOf<String>()

        desplegables.spinnerInmueble.setSelection(0)
        desplegables.spinnerHabitaciones.setSelection(0)
        desplegables.spinnerBaños.setSelection(0)
        desplegables.spinnerPrecioMin.setSelection(0)
        desplegables.spinnerPrecioMax.setSelection(0)
        desplegables.spinnerSuperficieMin.setSelection(0)
        desplegables.spinnerSuperficieMax.setSelection(0)

        estadosVivienda.obraNueva.isChecked = false
        estadosVivienda.muyBien.isChecked = false
        estadosVivienda.bien.isChecked = false
        estadosVivienda.casiNuevo.isChecked = false
        estadosVivienda.aReformar.isChecked = false
        estadosVivienda.reformado.isChecked = false

        extrasVivienda.parking.isChecked = false
        extrasVivienda.ascensor.isChecked = false
        extrasVivienda.amueblado.isChecked = false
        extrasVivienda.calefaccion.isChecked = false
        extrasVivienda.jardin.isChecked = false
        extrasVivienda.piscina.isChecked = false
        extrasVivienda.terraza.isChecked = false
        extrasVivienda.trastero.isChecked = false

        filtros.elementosSeleccionadosTipoEdif = booleanArrayOf(false,/*Apartamento*/ false,/*Ático*/ false,/*Dúplex*/ false,/*Loft*/ false,/*Planta baja*/ false/*Estudio*/)
        filtros.elementosSeleccionadosTipoPorDefecto = booleanArrayOf(false,/*Apartamento*/ false,/*Ático*/ false,/*Dúplex*/ false,/*Loft*/ false,/*Planta baja*/ false,/*Estudio*/ false, /*Casa*/ false,/*Chalet*/ false,/*Adosado*/ false/*Finca rústica*/)

        cuidador.saveMemento()

        saveVistaFiltros()
    }
}
