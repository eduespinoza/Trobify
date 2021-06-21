package com.example.trobify.controladores

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Intent
import android.database.MatrixCursor
import android.net.Uri
import android.os.Bundle
import android.provider.BaseColumns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.trobify.R
import com.example.trobify.models.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.here.sdk.core.GeoCoordinates
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.CarouselView
import kotlinx.android.synthetic.main.activity_ofertar_inmueble.*
import kotlinx.android.synthetic.main.inmueble_card_busqueda.*
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList


@SuppressLint("StaticFieldLeak")
class OfertarInmueble : AppCompatActivity() {

    private var inmuebleBuild = Inmueble.Builder()
    private var user : String? = null
    var id : String = UUID.randomUUID().toString()
    var buscador = Busqueda()
    private var fotosOrd : ArrayList<String> = arrayListOf()
    private var tipoInmueble : String? = null
    private var tipoVivienda : String? = null
    private var tipoAnuncio : String? = null
    private var precioDeVenta : Int? = null
    private var superficie : Int? = null
    private var direccion : Sitio? = null
    private var numHabitaciones : Int = 0
    private var numBanos : Int = 0
    private var descripcion : String? = null
    private var imageId : String = "no_tiene"
    private lateinit var carimages : CarouselView
    private var extrasInmueble : ArrayList<String>? = null
    private var estadoInmueble : String? = null
    private val SELECT_FILE  = 1

    val elementosExtras = booleanArrayOf(false, false, false, false, false, false, false, false)

    private var mStorage : StorageReference? = null


    lateinit var buscadorMapa : SearchView
    var sitio : Sitio? = null
    var direccionCorrecta = false

    var urls = arrayListOf<Uri?>()

    object text {
        lateinit var tipoInmuebleElegidoText:TextView
        lateinit var tipoViviendaElegidoText:TextView
        lateinit var tipoAnuncioText:TextView
        lateinit var textViewPreciodeVenta:TextView
        lateinit var layoutBaños : LinearLayout
        lateinit var layoutHabitaciones : LinearLayout
        lateinit var inPrecio : EditText
        lateinit var inSuperficie : EditText
        lateinit var inDireccion  : TextView
        lateinit var inHabitaciones : EditText
        lateinit var inBaños : EditText
        lateinit var inDescripcion : EditText
        lateinit var layoutVivienda : LinearLayout
    }


    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ofertar_inmueble)
        println(id)
        carimages = findViewById(R.id.carouselPhotosOfertar)
        intent.getStringExtra("user")?.let { user = it }

        mStorage = FirebaseStorage.getInstance().reference

        inmuebleBuild.id(id)
        inmuebleBuild.propietario(user)

        text.layoutVivienda = findViewById(R.id.layoutTipoVivienda)
        text.tipoInmuebleElegidoText = findViewById(R.id.textViewTipoElegido)
        text.tipoViviendaElegidoText = findViewById(R.id.textViewTipodeViviendaElegido)
        text.tipoAnuncioText = findViewById(R.id.textViewTipodeanuncioelegido)
        text.textViewPreciodeVenta = findViewById(R.id.textViewPreciodeVenta)
        text.layoutHabitaciones = findViewById(R.id.layoutHabitaciones)
        text.layoutBaños = findViewById(R.id.layoutBaños)
        text.inPrecio = findViewById(R.id.inPrecio)
        text.inSuperficie = findViewById(R.id.inSuperficie)
        text.inDireccion = findViewById(R.id.inDireccion)
        text.inHabitaciones = findViewById(R.id.inHabitaciones)
        text.inBaños = findViewById(R.id.inBaños)
        text.inDescripcion = findViewById(R.id.inDescripcion)

        prepararBuscador()

        val bBack = findViewById<Button>(R.id.buttonBackOfertar)
        bBack.setOnClickListener {
            finish()
        }
        
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
        bPost.setOnClickListener { post(true)
        //finish()
        }

        val bSubir = findViewById<Button>(R.id.buttonSubir)
        bSubir.setOnClickListener { post(false)

        //finish()
        }

        val bPhoto = findViewById<Button>(R.id.buttonAddPhoto)
        bPhoto.setOnClickListener{
            val intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
        }

    }
   override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?) {
       super.onActivityResult(requestCode, resultCode, data)
           var uri = data?.data
       if(requestCode == SELECT_FILE){
           if(resultCode == RESULT_OK){
               var uris = data!!.clipData
               if(uris != null){
                   for(i in 0 until uris.itemCount){
                       val uri = uris.getItemAt(i).uri
                       imageId = UUID.randomUUID().toString()
                       val filePath : StorageReference? = mStorage?.child("imagenesinmueble/" + id)?.child(
                           imageId
                       )
                       fotosOrd.add(filePath.toString())
                       urls.add(uri)
                       mostrarImagenes(urls)
                   }
               }
           else if (uri != null){
               imageId = UUID.randomUUID().toString()
               var filePath : StorageReference? = mStorage?.child("imagenesinmueble/" + id)?.child(
                   imageId)
               fotosOrd.add(filePath.toString())
                   urls.add(uri)
                   mostrarImagenes(urls)
           }}}
   }

    private fun mostrarImagenes(uris : ArrayList<Uri?> ){
        carimages.setImageListener{ position, imageView ->
            Picasso.get().load(uris[position]).into(imageView)

        }
        carimages.setImageClickListener{ position ->
            Toast.makeText(applicationContext, uris[position]!!.path, Toast.LENGTH_SHORT).show()
        }

        carimages.pageCount = urls.size
    }

    private fun prepararBuscador(){
        buscadorMapa = findViewById(R.id.buscadorDirecciones)
        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val to = intArrayOf(R.id.item_label)
        var adaptadorCursor = SimpleCursorAdapter(
            this.baseContext, R.layout.sugerencia_item,
            null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        )
        buscadorMapa.suggestionsAdapter = adaptadorCursor
        buscadorMapa.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query : String) : Boolean {
                return false
            }

            override fun onQueryTextChange(query : String?) : Boolean {
                if (query != null) {
                    buscador.obtenerSugerencias("$query", GeoCoordinates(39.48204, -0.33876))
                    var cursor = MatrixCursor(
                        arrayOf(
                            BaseColumns._ID,
                            SearchManager.SUGGEST_COLUMN_TEXT_1
                        )
                    )
                    buscador.sugerencias.forEachIndexed { indice, item ->
                        cursor.addRow(arrayOf(indice, item.title))
                    }
                    adaptadorCursor.changeCursor(cursor)
                }
                direccionCorrecta = false
                return false
            }
        })
        buscadorMapa.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position : Int) : Boolean {
                return false
            }

            override fun onSuggestionClick(position : Int) : Boolean {
                val cursor = buscadorMapa.suggestionsAdapter.getItem(position) as MatrixCursor
                val selection =
                    cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
                buscadorMapa.setQuery("", true)
                buscadorMapa.isIconified = true
                text.inDireccion.visibility = View.VISIBLE
                text.inDireccion.text = selection
                buscador.suggeries.forEach {
                    if (it.titulo.equals(selection)) {
                        sitio = it
                        inmuebleBuild.direccionSitio(sitio)
                    }
                }
                direccionCorrecta = true
                text.inHabitaciones.requestFocus()
                return true
            }
        })
        buscadorMapa.setOnSearchClickListener{
            text.inDireccion.text = ""
            text.inDireccion.visibility = View.GONE
        }
    }

    private fun chooseAnuncio(){
        val builder = AlertDialog.Builder(this@OfertarInmueble)
        builder.setTitle("Selecciona el tipo de anuncio")
        val optionsAnuncio = resources.getStringArray(R.array.options_anuncio)
        builder.setItems(optionsAnuncio) { _, which ->
            when {
                which.equals(0)
                -> {
                    text.tipoAnuncioText.text = optionsAnuncio[0]
                    tipoAnuncio = "Vender"
                    inmuebleBuild.intencion("Vender")
                    text.textViewPreciodeVenta.setText("Precio de venta")
                }
                which.equals(1)
                -> {
                    text.tipoAnuncioText.text = optionsAnuncio[1]
                    tipoAnuncio = "Alquiler"
                    inmuebleBuild.intencion("Alquiler")
                    text.textViewPreciodeVenta.setText("Precio de alquiler")
                }
            }
        }
        builder.setNegativeButton("Cancelar"){ _, _ -> }
        val options = builder.create()
        options.show()
    }

    private fun chooseInmueble() {
        val builder = AlertDialog.Builder(this@OfertarInmueble)
        val optionsInmueble = resources.getStringArray(R.array.options_inmueble)
        builder.setItems(optionsInmueble) { _, which ->
            when {
                which.equals(0)
                -> {
                    text.tipoInmuebleElegidoText.text = optionsInmueble[0]
                    tipoInmueble = optionsInmueble[0].toString()
                    inmuebleBuild.tipoInmueble(optionsInmueble[0].toString())
                    text.layoutHabitaciones.setVisibility(View.VISIBLE)
                    text.layoutBaños.setVisibility(View.VISIBLE)
                    text.layoutVivienda.setVisibility(View.VISIBLE)
                }
                which.equals(1)
                -> {
                    text.tipoInmuebleElegidoText.text = optionsInmueble[1]
                    tipoInmueble = optionsInmueble[1].toString()
                    inmuebleBuild.tipoInmueble(optionsInmueble[1].toString())
                    text.layoutHabitaciones.setVisibility(View.VISIBLE)
                    text.layoutBaños.setVisibility(View.VISIBLE)
                    text.layoutVivienda.setVisibility(View.VISIBLE)
                }
                which.equals(2)
                -> {
                    text.tipoInmuebleElegidoText.text = optionsInmueble[2]
                    tipoInmueble = optionsInmueble[2].toString()
                    inmuebleBuild.tipoInmueble(optionsInmueble[2].toString())
                    text.layoutHabitaciones.setVisibility(View.VISIBLE)
                    text.layoutBaños.setVisibility(View.VISIBLE)
                    text.layoutVivienda.setVisibility(View.VISIBLE)
                }
                which.equals(3)
                -> {
                    text.tipoInmuebleElegidoText.text = optionsInmueble[3]
                    tipoInmueble = optionsInmueble[3].toString()
                    inmuebleBuild.tipoInmueble(optionsInmueble[3].toString())
                    text.layoutHabitaciones.setVisibility(View.GONE)
                    text.layoutBaños.setVisibility(View.GONE)
                    text.layoutVivienda.setVisibility(View.GONE)
                }
                which.equals(4)
                -> {
                    text.tipoInmuebleElegidoText.text = optionsInmueble[4]
                    tipoInmueble = optionsInmueble[4].toString()
                    inmuebleBuild.tipoInmueble(optionsInmueble[4].toString())
                    text.layoutHabitaciones.setVisibility(View.GONE)
                    text.layoutVivienda.setVisibility(View.GONE)
                }
                which.equals(5)
                -> {
                    text.tipoInmuebleElegidoText.text = optionsInmueble[5]
                    tipoInmueble = optionsInmueble[5].toString()
                    inmuebleBuild.tipoInmueble(optionsInmueble[5].toString())
                    text.layoutHabitaciones.setVisibility(View.GONE)
                    text.layoutBaños.setVisibility(View.GONE)
                    text.layoutVivienda.setVisibility(View.GONE)
                }
                which.equals(6)
                -> {
                    text.tipoInmuebleElegidoText.text = optionsInmueble[6]
                    tipoInmueble = optionsInmueble[6].toString()
                    inmuebleBuild.tipoInmueble(optionsInmueble[6].toString())
                    text.layoutHabitaciones.setVisibility(View.GONE)
                    text.layoutBaños.setVisibility(View.GONE)
                    text.layoutVivienda.setVisibility(View.GONE)
                }
                which.equals(7)
                -> {
                    text.tipoInmuebleElegidoText.text = optionsInmueble[7]
                    tipoInmueble = optionsInmueble[7].toString()
                    inmuebleBuild.tipoInmueble(optionsInmueble[7].toString())
                    text.layoutHabitaciones.setVisibility(View.GONE)
                    text.layoutBaños.setVisibility(View.GONE)
                    text.layoutVivienda.setVisibility(View.GONE)
                }
            }
        }
        builder.setNegativeButton("Cancelar"){ _, _ -> }
        val options = builder.create()
        options.show()

    }

    private fun chooseVivienda(){
        val builder = AlertDialog.Builder(this@OfertarInmueble)
        builder.setTitle("Selecciona el tipo de vivienda")
        if(text.tipoInmuebleElegidoText.text.equals("Edificio") || text.tipoInmuebleElegidoText.text.equals(
                "Oficina"
            )){
            val optionsViviendaEdificio = resources.getStringArray(R.array.options_vivienda_edificio)
            builder.setItems(optionsViviendaEdificio) { _, which ->
                when {
                    which.equals(0)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsViviendaEdificio[0]
                        tipoVivienda = "Apartamento"
                        inmuebleBuild.tipoVivienda("Apartamento")
                    }
                    which.equals(1)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsViviendaEdificio[1]
                        tipoVivienda = "Ático"
                        inmuebleBuild.tipoVivienda("Ático")
                    }
                    which.equals(2)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsViviendaEdificio[2]
                        tipoVivienda = "Dúplex"
                        inmuebleBuild.tipoVivienda("Dúplex")
                    }
                    which.equals(3)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsViviendaEdificio[3]
                        tipoVivienda = "Loft"
                        inmuebleBuild.tipoVivienda("Loft")
                    }
                    which.equals(4)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsViviendaEdificio[4]
                        tipoVivienda = "Planta"
                        inmuebleBuild.tipoVivienda("Planta")
                    }
                    which.equals(5)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsViviendaEdificio[5]
                        tipoVivienda = "Estudio"
                        inmuebleBuild.tipoVivienda("Estudio")
                    }
                }
            }
            builder.setNegativeButton("Cancelar"){ _, _ -> }
            val options = builder.create()
            options.show()
        }
        else{
            val optionsVivienda = resources.getStringArray(R.array.options_vivienda_por_defecto)
            builder.setItems(optionsVivienda) { _, which ->
                when {
                    which.equals(0)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsVivienda[0]
                        tipoVivienda = "Apartamento"
                        inmuebleBuild.tipoVivienda("Apartamento")
                    }
                    which.equals(1)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsVivienda[1]
                        tipoVivienda = "Ático"
                        inmuebleBuild.tipoVivienda("Ático")
                    }
                    which.equals(2)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsVivienda[2]
                        tipoVivienda = "Dúplex"
                        inmuebleBuild.tipoVivienda("Dúplex")
                    }
                    which.equals(3)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsVivienda[3]
                        tipoVivienda = "Loft"
                        inmuebleBuild.tipoVivienda("Loft")
                    }
                    which.equals(4)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsVivienda[4]
                        tipoVivienda = "Planta"
                        inmuebleBuild.tipoVivienda("Planta")
                    }
                    which.equals(5)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsVivienda[5]
                        tipoVivienda = "Estudio"
                        inmuebleBuild.tipoVivienda("Estudio")
                    }
                    which.equals(6)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsVivienda[6]
                        tipoVivienda = "Casa"
                        inmuebleBuild.tipoVivienda("Casa")
                    }
                    which.equals(7)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsVivienda[7]
                        tipoVivienda = "Chalet"
                        inmuebleBuild.tipoVivienda("Chalet")
                    }
                    which.equals(8)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsVivienda[8]
                        tipoVivienda = "Adosado"
                        inmuebleBuild.tipoVivienda("Adosado")
                    }
                    which.equals(9)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsVivienda[9]
                        tipoVivienda = "Finca rústica"
                        inmuebleBuild.tipoVivienda("Finca rústica")
                    }
                }
            }
            builder.setNegativeButton("Cancelar"){ _, _ -> }
            val options = builder.create()
            options.show()
        }
    }

    private fun selectExtras(){
        val builder = AlertDialog.Builder(this@OfertarInmueble)
        val extrasArray = arrayOf<String>(
            "Parking",
            "Ascensor",
            "Amueblado",
            "Calefacción",
            "Jardín",
            "Piscina",
            "Terraza",
            "Trastero"
        )
        extrasInmueble = arrayListOf<String>()
        builder.setMultiChoiceItems(extrasArray, elementosExtras){ dialog, which, isChecked ->
            elementosExtras[which] = isChecked
            if(elementosExtras[which]){
                extrasInmueble!!.add(extrasArray[which].toString())
            }
            else{
                extrasInmueble!!.remove(extrasArray[which].toString())
            }
            inmuebleBuild.extras(extrasInmueble)
        }
        builder.setNegativeButton("Ok"){ _, _ -> }
        val options = builder.create()
        options.show()
    }

    private fun selectCondition(){
        val builder = AlertDialog.Builder(this@OfertarInmueble)
        val estado = resources.getStringArray(R.array.estado_inmueble)
        builder.setItems(estado) { _, which ->
            when {
                which.equals(0)
                -> {
                    estadoInmueble = "Obra nueva"
                    inmuebleBuild.estado(estado[0])
                }
                which.equals(1)
                -> {
                    estadoInmueble = "Casi nuevo"
                    inmuebleBuild.estado("Casi nuevo")
                }
                which.equals(2)
                -> {
                    estadoInmueble = "Muy bien"
                    inmuebleBuild.estado("Muy bien")
                }
                which.equals(3)
                -> {
                    estadoInmueble = "Bien"
                    inmuebleBuild.estado("Bien")
                }
                which.equals(4)
                -> {
                    estadoInmueble = "Reformado"
                    inmuebleBuild.estado("Reformado")

                }
                which.equals(5)
                -> {
                    estadoInmueble = "A reformar"
                    inmuebleBuild.estado("A reformar")

                }
            }
        }
        builder.setNegativeButton("Cancelar"){ _, _ -> }
        val options = builder.create()
        options.show()

    }

    private fun post(post : Boolean){
        if(tipoInmueble == null){
            mostrarMensajeCampoObligatorio("Por favor seleccione el tipo de Inmueble", null)
        }else if(tipoVivienda == null && text.layoutVivienda.visibility != View.GONE){
            mostrarMensajeCampoObligatorio("Por favor seleccione el tipo de Vivienda", null)
        }else if( tipoAnuncio == null){
            mostrarMensajeCampoObligatorio("Por favor seleccione el tipo de Anuncio", null)
        }else if( (text.inPrecio.text.toString()   == null )|| (text.inPrecio.text.toString().toInt()  <= 0)){
            mostrarMensajeCampoObligatorio(
                "Error en el Precio de venta ",
                " Por favor intruduzca un precio de venta valido. "
            )
        }else if(text.inSuperficie.text.toString() == null || text.inSuperficie.text.toString().toInt()!! <= 0){
            mostrarMensajeCampoObligatorio(
                "Error en la superficie ",
                " Por favor intruduzca un valor valido para la superficie. "
            )
        }else if(text.inDireccion.text == "" || !direccionCorrecta){
            mostrarMensajeCampoObligatorio(
                "Error en la dirección ",
                " Por favor intruduzca un valor valido para la dirección. "
            )
        }else if(fotosOrd.size == 0){
            mostrarMensajeCampoObligatorio("Por favor seleccione al menos una foto", null)
        }
        else{

            precioDeVenta = text.inPrecio.text.toString().toInt()
            superficie = text.inSuperficie.text.toString().toInt()
            if(sitio != null) direccion = sitio
            if(text.inDescripcion.text != null){
                descripcion = text.inDescripcion.text.toString()
                inmuebleBuild.descripcion(text.inDescripcion.text.toString())
            }
            else{descripcion = ""}
            if(text.inBaños.text != null && text.inBaños.text.toString() != ""){
                numBanos = Integer.parseInt(text.inBaños.text.toString())
                inmuebleBuild.numBanos(text.inBaños.text.toString().toInt())
            }
            else{numBanos = 0}
            if(text.inHabitaciones.text != null && text.inHabitaciones.text.toString() != ""){
                numHabitaciones = Integer.parseInt(text.inHabitaciones.text.toString())
                inmuebleBuild.numHabitaciones(text.inHabitaciones.text.toString().toInt())
            }
            else{numHabitaciones = 0}

            inmuebleBuild.precio(text.inPrecio.text.toString().toInt())
            inmuebleBuild.superficie(text.inSuperficie.text.toString().toInt())
            inmuebleBuild.fotosOrd(fotosOrd)
            inmuebleBuild.fechaSubida(LocalDateTime.now())
            var inmuebleInstance = inmuebleBuild.build()
            println(inmuebleInstance.toString())
            println(inmuebleInstance.convertToDataInmueble().toString())

            val subelo = DataInmueble2(
                id,
                user,
                numHabitaciones,
                numBanos,
                superficie,
                direccion,
                tipoVivienda,
                tipoInmueble,
                tipoAnuncio,
                precioDeVenta,
                fotosOrd,
                descripcion,
                extrasInmueble,
                estadoInmueble,
                LocalDateTime.now().toString()
            )
            println(subelo)
            //Database.subirInmueble(subelo, post)
            subirFotos()
            mostrarMensajeExito(post)
        }
    }

    private fun subirFotos(){
        var position = -1
        fotosOrd.forEach { idImg ->
            position++
            val filePath : StorageReference? = mStorage?.
            child("imagenesinmueble/" + id)?.
            child(idImg.substring(85))
            filePath?.putFile(urls[position]!!)
        }
    }

    private fun mostrarMensajeExito(post : Boolean) {
        var texto = "subido"
        if (post) texto = "$texto y publicado"
        val builder = AlertDialog.Builder(this)
        builder.setTitle(" Se ha $texto el piso correctamente ")
        builder.setIcon(android.R.drawable.ic_dialog_info)
        builder.setNeutralButton("  Continue  ") { _, _ -> }
        val alertDialog : AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun mostrarMensajeCampoObligatorio(titulo : String, mensaje : String?) {
        val builder =  AlertDialog.Builder(this)
        if(mensaje!=null)builder.setMessage(mensaje)
        builder.setTitle(titulo)
        builder.setIcon(android.R.drawable.stat_notify_error)
        builder.setNeutralButton("  Continue  "){ _, _ -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }


}
