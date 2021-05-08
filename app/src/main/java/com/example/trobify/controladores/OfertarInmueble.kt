package com.example.trobify.controladores

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Intent
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.trobify.R
import com.example.trobify.models.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.here.sdk.core.GeoCoordinates
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.activity_ofertar_inmueble.*
import kotlinx.android.synthetic.main.inmueble_card_busqueda.*
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList


@SuppressLint("StaticFieldLeak")
class OfertarInmueble : AppCompatActivity() {

    private var user : String? = null
    var id : String = UUID.randomUUID().toString()
    var buscador = Busqueda()
    private var fotos : ArrayList<Int> = arrayListOf()
    private var fotosOrd : ArrayList<String> = arrayListOf()
    private var tipoInmueble : String? = null
    private var tipoVivienda : String? = null
    private var tipoAnuncio : String? = null
    private var precioDeVenta : Int? = null
    private var superficie : Int? = null
    private var direccion : Sitio? = null
    private var direccionO : Direccion? = null
    private var numHabitaciones : Int = 0
    private var numBanos : Int = 0
    private var certificadoEnergetico : String? = null  //Desde la A hasta la G, hay que hacer que solo deje poner las letras posibles
    private var descripcion : String? = null
    private var fechaSubida : LocalDateTime? = null
    private var estado : String? = null
    private var imageId : String = "no_tiene"

    private var parking : Boolean = false
    private var ascensor : Boolean = false
    private var amueblado : Boolean = false
    private var calefaccion : Boolean = false
    private var jardin : Boolean = false
    private var piscina : Boolean = false
    private var terraza : Boolean = false
    private var trastero : Boolean = false
    private var image : ImageView? = null
    private lateinit var carimages : CarouselView

    private lateinit var extrasInmueble : ArrayList<String>
    private lateinit var estadoInmueble : ArrayList<String>

    private var caracteristicas : String? = null

    private val SELECT_FILE  = 1

    val elementosExtras = booleanArrayOf(false, false, false, false, false, false, false, false)
    val elementosEstados = booleanArrayOf(false, false, false, false, false, false)

    private var mStorage : StorageReference? = null


    var sampleImages = intArrayOf()

    lateinit var buscadorMapa : SearchView
    var sitio : Sitio? = null
    var direccionCorrecta = false
    //val database = Database()


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

        intent.getStringExtra("user")?.let { user = it }

        mStorage = FirebaseStorage.getInstance().reference



        image  = findViewById<ImageView>(R.id.imageView)
        carimages = findViewById<CarouselView>(R.id.carouselPhotosOfertar) as CarouselView
        carimages.setPageCount(sampleImages.size);
        carimages.setImageListener(imageListener);


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
        bBack.setOnClickListener { goBack() }
        
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
        bPost.setOnClickListener { post() }


        val bPhoto = findViewById<Button>(R.id.buttonAddPhoto)
        bPhoto.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, SELECT_FILE)
        }

    }

   override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

       if(requestCode == SELECT_FILE && resultCode == RESULT_OK ) {
           var uri = data?.data
           if (uri != null) {
               imageId = UUID.randomUUID().toString()
               var filePath : StorageReference? = mStorage?.child("imagenesinmueble/"+ id)?.child(imageId)
               fotosOrd.add(filePath.toString())
               filePath?.putFile(uri)?.addOnSuccessListener(this) { taskSnapshot ->

                   filePath.downloadUrl.addOnSuccessListener { url ->
                       //Glide.with(this).load(url).into(image)
                       Glide.with(this).load(url).into(image)
                   }
               }
           }
       }
   }


    var imageListener =
        ImageListener { position, imageView -> imageView.setImageResource(sampleImages[position]) }


    private fun goBack(){
        val goBack = Intent(this, MainTrobify::class.java)
        goBack.putExtra("user", user)
        startActivity(goBack)
    }

    private fun compare(){
        val builder =  AlertDialog.Builder(this)
        builder.setTitle("Funcion no implementada")
        builder.setIcon(android.R.drawable.stat_notify_error)
        builder.setNeutralButton("  Continue  "){ _, _ -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun prepararBuscador(){
        buscadorMapa = findViewById(R.id.buscadorDirecciones)
        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val to = intArrayOf(R.id.item_label)
        var adaptadorCursor = SimpleCursorAdapter(this.baseContext, R.layout.sugerencia_item,
            null,from,to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER)
        buscadorMapa.suggestionsAdapter = adaptadorCursor
        buscadorMapa.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {return false}
            override fun onQueryTextChange(query : String?) : Boolean {
                if (query != null) {
                    buscador.obtenerSugerencias("$query",GeoCoordinates(39.48204,-0.33876))
                    var cursor = MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
                    buscador.sugerencias.forEachIndexed{
                            indice, item ->
                        cursor.addRow(arrayOf(indice,item.title))
                    }
                    adaptadorCursor.changeCursor(cursor)
                }
                direccionCorrecta = false
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
                text.inDireccion.visibility = View.VISIBLE
                text.inDireccion.text = selection
                buscador.sugerencias.forEach{
                    if(it.title.equals(selection)){
                        sitio = Sitio(it.title,
                            mutableMapOf("latitud" to it.geoCoordinates!!.latitude,
                              "longitud" to it.geoCoordinates!!.longitude),it.id)
                        println(sitio)
                        println("por aqui un sitio bomba")
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
                    tipoAnuncio = "Venta"
                    text.textViewPreciodeVenta.setText("Precio de venta")
                }
                which.equals(1)
                -> {
                    text.tipoAnuncioText.text = optionsAnuncio[1]
                    tipoAnuncio = "Alquiler"
                    text.textViewPreciodeVenta.setText("Precio de alquiler")
                }
            }
        }
        builder.setNegativeButton("Cancelar"){_, _ -> }
        val options = builder.create()
        options.show()
    }

    private fun chooseInmueble() {
        val builder = AlertDialog.Builder(this@OfertarInmueble)
        builder.setTitle("Selecciona el tipo de inmueble")
        val tiposInmuebleMenosCualquiera = TipoInmueble.values().drop(1)
        val optionsInmueble = Array<String>(tiposInmuebleMenosCualquiera.size){""}
        var pos = 0
        for(tipo in tiposInmuebleMenosCualquiera){
            optionsInmueble[pos] = tipo.name
            pos++
        }
        builder.setItems(optionsInmueble) { _, which ->
            when {
                which.equals(0)
                -> {
                    text.tipoInmuebleElegidoText.text = optionsInmueble[0]
                    tipoInmueble = TipoInmueble.Vivienda.toString()
                    text.layoutHabitaciones.setVisibility(View.VISIBLE)
                    text.layoutBaños.setVisibility(View.VISIBLE)
                    text.layoutVivienda.setVisibility(View.VISIBLE)
                }
                which.equals(1)
                -> {
                    text.tipoInmuebleElegidoText.text = optionsInmueble[1]
                    tipoInmueble = TipoInmueble.Edificio.toString()
                    text.layoutHabitaciones.setVisibility(View.VISIBLE)
                    text.layoutBaños.setVisibility(View.VISIBLE)
                    text.layoutVivienda.setVisibility(View.VISIBLE)
                }
                which.equals(2)
                -> {
                    text.tipoInmuebleElegidoText.text = optionsInmueble[2]
                    tipoInmueble = TipoInmueble.Oficina.toString()
                    text.layoutHabitaciones.setVisibility(View.GONE)
                    text.layoutBaños.setVisibility(View.VISIBLE)
                    text.layoutVivienda.setVisibility(View.GONE)
                }
                which.equals(3)
                -> {
                    text.tipoInmuebleElegidoText.text = optionsInmueble[3]
                    tipoInmueble = TipoInmueble.Garaje.toString()
                    text.layoutHabitaciones.setVisibility(View.GONE)
                    text.layoutBaños.setVisibility(View.GONE)
                    text.layoutVivienda.setVisibility(View.GONE)
                }
                which.equals(4)
                -> {
                    text.tipoInmuebleElegidoText.text = optionsInmueble[4]
                    tipoInmueble = TipoInmueble.Local.toString()
                    text.layoutHabitaciones.setVisibility(View.GONE)
                    text.layoutVivienda.setVisibility(View.GONE)
                }
                which.equals(5)
                -> {
                    text.tipoInmuebleElegidoText.text = optionsInmueble[5]
                    tipoInmueble = TipoInmueble.Terreno.toString()
                    text.layoutHabitaciones.setVisibility(View.GONE)
                    text.layoutBaños.setVisibility(View.GONE)
                    text.layoutVivienda.setVisibility(View.GONE)
                }
                which.equals(6)
                -> {
                    text.tipoInmuebleElegidoText.text = optionsInmueble[6]
                    tipoInmueble = TipoInmueble.Nave.toString()
                    text.layoutHabitaciones.setVisibility(View.GONE)
                    text.layoutBaños.setVisibility(View.GONE)
                    text.layoutVivienda.setVisibility(View.GONE)
                }
            }
        }
        builder.setNegativeButton("Cancelar"){_, _ -> }
        val options = builder.create()
        options.show()

    }

    private fun chooseVivienda(){
        val builder = AlertDialog.Builder(this@OfertarInmueble)
        builder.setTitle("Selecciona el tipo de vivienda")
        if(text.tipoInmuebleElegidoText.text.equals(TipoInmueble.Edificio) || text.tipoInmuebleElegidoText.text.equals(TipoInmueble.Oficina)){
            val optionsViviendaEdificio = resources.getStringArray(R.array.options_vivienda_edificio)
            builder.setItems(optionsViviendaEdificio) { _, which ->
                when {
                    which.equals(0)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsViviendaEdificio[0]
                        tipoVivienda = "Apartamento"
                    }
                    which.equals(1)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsViviendaEdificio[1]
                        tipoVivienda = "Ático"
                    }
                    which.equals(2)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsViviendaEdificio[2]
                        tipoVivienda = "Dúplex"
                    }
                    which.equals(3)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsViviendaEdificio[3]
                        tipoVivienda = "Loft"
                    }
                    which.equals(4)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsViviendaEdificio[4]
                        tipoVivienda = "Planta"
                    }
                    which.equals(5)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsViviendaEdificio[5]
                        tipoVivienda = "Estudio"
                    }
                }
            }
            builder.setNegativeButton("Cancelar"){_, _ -> }
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
                    }
                    which.equals(1)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsVivienda[1]
                        tipoVivienda = "Ático"
                    }
                    which.equals(2)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsVivienda[2]
                        tipoVivienda = "Dúplex"
                    }
                    which.equals(3)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsVivienda[3]
                        tipoVivienda = "Loft"
                    }
                    which.equals(4)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsVivienda[4]
                        tipoVivienda = "Planta"
                    }
                    which.equals(5)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsVivienda[5]
                        tipoVivienda = "Estudio"
                    }
                    which.equals(6)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsVivienda[6]
                        tipoVivienda = "Casa"
                    }
                    which.equals(7)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsVivienda[7]
                        tipoVivienda = "Chalet"
                    }
                    which.equals(8)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsVivienda[8]
                        tipoVivienda = "Adosado"
                    }
                    which.equals(9)
                    -> {
                        text.tipoViviendaElegidoText.text = optionsVivienda[9]
                        tipoVivienda = "Finca rústica"
                    }
                }
            }
            builder.setNegativeButton("Cancelar"){_, _ -> }
            val options = builder.create()
            options.show()
        }
    }

    private fun selectExtras(){
        val builder = AlertDialog.Builder(this@OfertarInmueble)
        val extrasArray = arrayOf<String>("Parking","Ascensor","Amueblado","Calefacción","Jardín","Piscina","Terraza", "Trastero")
        extrasInmueble = arrayListOf<String>()
        builder.setMultiChoiceItems(extrasArray, elementosExtras){ dialog, which, isChecked ->
            elementosExtras[which] = isChecked
            if(elementosExtras[which]){
                extrasInmueble.add(extrasArray[which].toString())
            }
            else{
                extrasInmueble.remove(extrasArray[which].toString())
            }
        }
        builder.setNegativeButton("Ok"){_, _ -> }
        val options = builder.create()
        options.show()
    }
    private fun selectCondition(){
        val builder = AlertDialog.Builder(this@OfertarInmueble)
        val estadoInmuebleArray = arrayOf("Obra nueva","Casi nuevo","Muy bien","Bien","Reformado","A reformar")
        estadoInmueble = arrayListOf<String>()
        builder.setMultiChoiceItems(estadoInmuebleArray, elementosEstados){ dialog, which, isChecked ->
            elementosEstados[which] = isChecked
            if(elementosEstados[which]){
                estadoInmueble.add(estadoInmuebleArray[which].toString())
            }
            else{
                estadoInmueble.remove(estadoInmuebleArray[which].toString())
            }
        }
        builder.setNegativeButton("Ok"){_, _ -> }
        val options = builder.create()
        options.show()
    }

    private fun post(){
        if(tipoInmueble == null){
            messageInmNull()
        }else if(tipoVivienda == null && text.layoutVivienda.visibility != View.GONE){
            messageVivNull()
        }else if( tipoAnuncio == null){
            messageAnuNull()
        }else if( (text.inPrecio.text.toString()   == null )|| (text.inPrecio.text.toString().toInt()  <= 0)){
            messagePreInc()
        }else if(text.inSuperficie.text.toString() == null || text.inSuperficie.text.toString().toInt()!! <= 0){
            messageSupInc()
        }else if(text.inDireccion.text == "" || !direccionCorrecta){
            messageDirNull()
        }else{

            precioDeVenta = text.inPrecio.text.toString().toInt()
            superficie = text.inSuperficie.text.toString().toInt()
            if(sitio != null) direccion = sitio
            if(text.inDescripcion.text != null){ descripcion = text.inDescripcion.text.toString() }
            else{descripcion = ""}
            if(text.inBaños.text != null && text.inBaños.text.toString() != ""){ numBanos = Integer.parseInt(
                text.inBaños.text.toString())}
            else{numBanos = 0}
            if(text.inHabitaciones.text != null && text.inHabitaciones.text.toString() != ""){ numHabitaciones = Integer.parseInt(
                text.inHabitaciones.text.toString())}
            else{numHabitaciones = 0}

            //Fotos esta vacia, y en fotosord estan las ids de las fotos subidas a firebase y direccion contiene el string de la direccion mientras que direccion0 esta vacio


            val anuncio = DataInmueble(id,user,numHabitaciones,numBanos,superficie,direccion,tipoVivienda,tipoInmueble,tipoAnuncio,precioDeVenta,fotos,fotosOrd,
                "",descripcion,estado,parking,ascensor,amueblado,calefaccion,jardin,piscina,terraza,trastero, LocalDateTime.now().toString())

            Database.subirInmueble(anuncio)


            val builder =  AlertDialog.Builder(this)
            builder.setTitle(" Se ha publicado el piso correctamente " )
            builder.setIcon(android.R.drawable.ic_dialog_info)
            builder.setNeutralButton("  Continue  "){ _, _ -> }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }

    private fun subirInmuebleBD(inmueble : DataInmueble){
        val db = Firebase.firestore

        inmueble.id?.let { db.collection("inmueblesv4").document(it).set(inmueble) }
    }

    private fun messageInmNull(){
        val builder =  AlertDialog.Builder(this)
        builder.setTitle(" Por favor seleccione el tipo de Inmueble" )
        builder.setIcon(android.R.drawable.stat_notify_error)
        builder.setNeutralButton("  Continue  "){ _, _ -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
    private fun messageVivNull(){
        val builder =  AlertDialog.Builder(this)
        builder.setTitle("Por favor seleccione el tipo de Vivienda" )
        builder.setIcon(android.R.drawable.stat_notify_error)
        builder.setNeutralButton("  Continue  "){ _, _ -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
    private fun messageAnuNull(){
        val builder =  AlertDialog.Builder(this)
        builder.setTitle("Por favor seleccione el tipo de Anuncio" )
        builder.setIcon(android.R.drawable.stat_notify_error)
        builder.setNeutralButton("  Continue  "){ _, _ -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
    private fun messagePreInc(){
        val builder =  AlertDialog.Builder(this)
        builder.setTitle("Error en el Precio de venta " )
        builder.setMessage(" Por favor intruduzca un precio de venta valido. ")
        builder.setIcon(android.R.drawable.stat_notify_error)
        builder.setNeutralButton("  Continue  "){ _, _ -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
    private fun messageSupInc(){
        val builder =  AlertDialog.Builder(this)
        builder.setTitle("Error en la superficie " )
        builder.setMessage(" Por favor intruduzca un valor valido para la superficie. ")
        builder.setIcon(android.R.drawable.stat_notify_error)
        builder.setNeutralButton("  Continue  "){ _, _ -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
    private fun messageDirNull(){
        val builder =  AlertDialog.Builder(this)
        builder.setTitle("Error en la dirección " )
        builder.setMessage(" Por favor intruduzca un valor valido para la dirección. ")
        builder.setIcon(android.R.drawable.stat_notify_error)
        builder.setNeutralButton("  Continue  "){ _, _ -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
    private fun iExtras(){
        extrasInmueble
        estadoInmueble


    }

}
