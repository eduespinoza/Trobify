package com.example.trobify.controladores

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Intent
import android.content.res.ColorStateList
import android.database.MatrixCursor
import android.graphics.Color
import android.os.Bundle
import android.provider.BaseColumns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.trobify.R
import com.example.trobify.models.Busqueda
import com.example.trobify.models.Database
import com.example.trobify.models.Sitio
import com.here.sdk.core.GeoCoordinates
import kotlinx.android.synthetic.main.trobify_main.*
import kotlin.properties.Delegates

class PantallaInicial : AppCompatActivity() {
    lateinit var userId : String
    lateinit var botonBuscar : Button
    var todoOk : ArrayList<Boolean> by Delegates.observable(arrayListOf(false,false), onChange = {
        property, oldValue, new ->
        botonBuscar.isEnabled = new[0] && new[1]
    })

    var alquilerActivado : Boolean by Delegates.observable(false){
            p,old,new ->
            if(new){todoOk = arrayListOf(true,todoOk[1])}
            if(!new && !ventaActivado) todoOk = arrayListOf(false,todoOk[1])
    }
    var ventaActivado : Boolean by Delegates.observable(false){
            p,old,new ->
            if(new){todoOk = arrayListOf(true,todoOk[1])}
            if(!new && !alquilerActivado) todoOk = arrayListOf(false,todoOk[1])
    }
    lateinit var colorDefaultText : ColorStateList
    var buscador = Busqueda()
    lateinit var buscadorMapa : SearchView
    var opcionesElegidas = arrayListOf("","","","")
    var sitio : Sitio by Delegates.observable(Sitio()){
        p,old,new-> println("tengo calle AMANOS")
        todoOk = arrayListOf(todoOk[0],true)
    }
    lateinit var inDireccion : TextView
    lateinit var trobify : TextView

    @SuppressLint("StaticFieldLeak")
    object busquedaInicial{
        lateinit var desplegableTipoInmueble:Spinner
        lateinit var alquiler:TextView
        lateinit var venta:TextView
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.pantalla_inicio)
        super.onCreate(savedInstanceState)
        trobify = findViewById(R.id.titulo_pantalla_inicio)
        botonBuscar = findViewById(R.id.buttonBusquedaInicial)
        botonBuscar.isEnabled = false
        userId = (intent.extras!!.get("user") as String?).toString()
        busquedaInicial.alquiler = findViewById(
            R.id.text_opcion_alquiler
        )
        busquedaInicial.venta = findViewById(
            R.id.text_opcion_comprar
        )
        colorDefaultText = busquedaInicial.venta.textColors
        inDireccion = findViewById(R.id.direccionText)
        busquedaInicial.alquiler.setOnClickListener {
            if(alquilerActivado)desactivarIntenciones(
                busquedaInicial.alquiler,
                busquedaInicial.venta
            )
            else activalAlquiler(
                busquedaInicial.alquiler,
                busquedaInicial.venta
            )
        }
        busquedaInicial.venta.setOnClickListener {
            if(ventaActivado)desactivarIntenciones(
                busquedaInicial.alquiler,
                busquedaInicial.venta
            )
            else activarVenta(
                busquedaInicial.alquiler,
                busquedaInicial.venta
            )
        }
        trobify.setOnClickListener {
            val goTrobify = Intent(this, MainTrobify::class.java)
            goTrobify.putExtra("user", userId)
            startActivity(goTrobify)
        }
        busquedaInicial.desplegableTipoInmueble = findViewById(R.id.spinner_tipoInmueble_inicio)
        addOptionsInmueble()
        prepararBuscador()
        busquedaInicial.desplegableTipoInmueble.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (parent != null) {
                    opcionesElegidas[1] = parent.getItemAtPosition(position) as String
                }
            }
        }
        /*busquedaInicial.desplegableTipoInmueble.setOnClickListener {
            var tipoEscogido = busquedaInicial.desplegableTipoInmueble.selectedItem.toString()
            opcionesElegidas[1] = tipoEscogido
        }*/

        botonBuscar.setOnClickListener {
            println(opcionesElegidas)
            var fstRes = Database.getInmueblesByOpciones(opcionesElegidas)
            println(fstRes.size)
            if(fstRes.size == 0){
                AlertDialog.Builder(this).apply {
                    setTitle("Información")
                    setMessage("No se han obtenido resultados con esas opciones, prueba con otra dirección")
                    setPositiveButton("Ok", null)
                }.show()
            }else{
                val goTrobify = Intent(this, MainTrobify::class.java)
                goTrobify.putExtra("user", userId)
                goTrobify.putExtra("opciones",fstRes)
                startActivity(goTrobify)
            }
        }
    }
    private fun prepararBuscador(){
        buscadorMapa = findViewById(R.id.buscadorDirecciones)
        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val to = intArrayOf(R.id.item_label)
        var adaptadorCursor = SimpleCursorAdapter(this.baseContext,
            R.layout.sugerencia_item,
            null,from,to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER)
        buscadorMapa.suggestionsAdapter = adaptadorCursor
        buscadorMapa.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {return false}
            override fun onQueryTextChange(query : String?) : Boolean {
                if (query != null) {
                    buscador.obtenerSugerencias("$query", GeoCoordinates(39.48204,-0.33876))
                    var cursor = MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
                    buscador.sugerencias.forEachIndexed{
                            indice, item ->
                        cursor.addRow(arrayOf(indice,item.title))
                    }
                    adaptadorCursor.changeCursor(cursor)
                }
                return false
            }
        })
        buscadorMapa.setOnSuggestionListener(object: SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {return false}
            override fun onSuggestionClick(position: Int): Boolean {
                val cursor = buscadorMapa.suggestionsAdapter.getItem(position) as MatrixCursor
                val selection = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
                buscadorMapa.setQuery("",true)
                buscadorMapa.isIconified = true
                inDireccion.visibility = View.VISIBLE
                inDireccion.text = selection
                buscador.suggeries.forEach{
                    if(it.titulo.equals(selection)){
                        sitio = it
                        opcionesElegidas[2] = sitio.coordenadas?.get("latitud").toString()
                        opcionesElegidas[3] = sitio.coordenadas?.get("longitud").toString()
                    }
                }
                return true
            }
        })
        buscadorMapa.setOnSearchClickListener{
            inDireccion.text = ""
            inDireccion.visibility = View.GONE
        }
    }
    private fun addOptionsInmueble(){
        val options_inmueble = arrayOf("Cualquiera","Vivienda","Edificio","Oficina","Garaje","Local","Terreno","Nave")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options_inmueble)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        busquedaInicial.desplegableTipoInmueble.adapter = adapter
    }

    private fun desactivarIntenciones(alquiler : TextView, venta : TextView){
        alquilerActivado = false
        ventaActivado = false
        alquiler.background = ContextCompat.getDrawable(this, R.drawable.border2)
        alquiler.setTextColor(colorDefaultText)
        venta.setTextColor(colorDefaultText)
        venta.background = ContextCompat.getDrawable(this, R.drawable.border2)
    }
    private fun activalAlquiler(alquiler : TextView, venta : TextView){
        alquilerActivado = true
        ventaActivado = false
        alquiler.setBackgroundColor(Color.BLUE)
        alquiler.setTextColor(Color.WHITE)
        venta.setTextColor(Color.BLACK)
        venta.setBackgroundColor(Color.GRAY)
        opcionesElegidas[0] = "Alquiler"
    }
    private fun activarVenta(alquiler : TextView, venta : TextView){
        alquilerActivado = false
        ventaActivado = true
        venta.setBackgroundColor(Color.BLUE)
        venta.setTextColor(Color.WHITE)
        alquiler.setTextColor(Color.BLACK)
        alquiler.setBackgroundColor(Color.GRAY)
        opcionesElegidas[0] = "Vender"
    }
}

