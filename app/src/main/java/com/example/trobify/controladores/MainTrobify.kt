package com.example.trobify.controladores

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trobify.R
import com.example.trobify.adapters.AdaptadorFichaInmueble
import com.example.trobify.adapters.AdaptadorInmuebleBusqueda
import com.example.trobify.controladores.*
import com.example.trobify.database.Database
import com.example.trobify.models.*
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.trobify_main.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList


open class MainTrobify : AppCompatActivity(), AdaptadorInmuebleBusqueda.OnItemClickListener {
    lateinit var listaConResultados : RecyclerView
    lateinit var auth : FirebaseAuth
    lateinit var inmuebles : ArrayList<DataInmueble2>
    val db = Firebase.firestore
    var inmueblesEnPantalla : ArrayList<DataInmueble2> = arrayListOf()
    lateinit var userId : String
    lateinit var user : DataUser
    lateinit var nuevaBusqueda : Busqueda
    var alquilerActivado = false
    var ventaActivado = false
    lateinit var cabecera : TextView
    lateinit var nResultados : TextView
    lateinit var colorDefaultText : ColorStateList
    var filtrosAplicados : FiltrosBusqueda.filtros? = null
    var opcionesDeInicio : ArrayList<String>? = null
    lateinit var userFav : ArrayList<String>

    object orden{
        var ordenSeleccionado : Int = 2
    }

    override fun onItemClicked(dataInmueble : DataInmueble2) {
        val goFicha = Intent(this, AdaptadorFichaInmueble::class.java)
        goFicha.putExtra("user", userId.toString())
        goFicha.putExtra("inmueble", Inmueble.Builder().build().adaptadorInm(dataInmueble))
        goFicha.putExtra("desdeMisPisos", false)
        startActivity(goFicha)
    }

    override fun onResume() {
        super.onResume()
        if(filtrosAplicados == null && opcionesDeInicio == null){
        user = Database.obtenerUsuario(userId)
        userFav = arrayListOf()
        mostrarInmuebles(inmueblesEnPantalla)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.desplegable)
        listaConResultados = findViewById(R.id.recycler)
        cabecera = findViewById(R.id.cabecera)
        nResultados = findViewById(R.id.nResultados)
        userId = (intent.extras!!.get("user") as String?).toString()
        opcionesDeInicio = intent.extras!!.get("opciones") as ArrayList<String>?
        filtrosAplicados = intent.extras!!.get("filtros") as FiltrosBusqueda.filtros?
        user = Database.obtenerUsuario(userId)
        userFav = arrayListOf()
        inmuebles = Database.obtenerInmuebles()

        if(opcionesDeInicio != null){
            println(opcionesDeInicio)
            mostrarResultadosInicio(opcionesDeInicio!!)
        }
        else if(filtrosAplicados != null){
            val result = GestionFiltros().aplicar(filtrosAplicados!!)
            if(result.size != 0)
                mostrarResultadosFiltros(result)
            else {
                prepararPrimerosResultados(inmuebles)
                AlertDialog.Builder(this@MainTrobify).apply {
                    setTitle("Información")
                    setMessage("No se han obtenido resultados con esos filtros")
                    setPositiveButton("Ok", null)
                }.show()
            }
        }
        else prepararPrimerosResultados(inmuebles)

        auth = Firebase.auth
        nuevaBusqueda = Busqueda()
        prepararBuscador()
        setListeners()
        if(!inmueblesEnPantalla.isNullOrEmpty()) {verficarOrdenacion()}
    }
    @SuppressLint("SetTextI18n")
    private fun mostrarResultadosFiltros(listaDeInmuebles : ArrayList<DataInmueble2>){
        cabecera.text = "Inmuebles con filtros aplicados"
        mostrarInmuebles(listaDeInmuebles)
    }

    @SuppressLint("SetTextI18n")
    private fun prepararPrimerosResultados(listaDeInmuebles : ArrayList<DataInmueble2>){
        inmuebles = listaDeInmuebles
        cabecera.text = "Inmuebles añadidos recientemente"
        mostrarInmuebles(inmuebles)
    }

    private fun mostrarResultadosInicio(opciones : ArrayList<String>){
        cabecera.text = "Primeros resultados"
        var inm = Database.obtenerInmueblesSegunIds(opciones)
        println(inm.size)
        println("resultadosiniciojoderqcojones")
        mostrarInmuebles(inm)
    }

    private fun prepararBuscador(){
        var buscador : SearchView = findViewById(R.id.buscarView)
        buscador.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                resultadosConNuevaBusqueda(query)
                Log.d("busc",query)
                return false
            }
            override fun onQueryTextChange(query : String?) : Boolean {
                return false
            }
        })
    }
    @SuppressLint("SetTextI18n")
    fun setListeners() {
        val botonLateral : Button = findViewById(R.id.botonLateral)
        val menuLateral : DrawerLayout = findViewById(R.id.drawer_layout)
        val navigator : NavigationView = findViewById(R.id.navigator)
        val menuHeader = navigator.inflateHeaderView(R.layout.menu_desplegable_header)
        val nombreUser : TextView = menuHeader.findViewById(R.id.nombreUsuario)
        val cerrarMenu : Button = menuHeader.findViewById(R.id.volverAtras)
        val filtrar : TextView = findViewById(R.id.filtrar)
        val mapa : TextView = findViewById(R.id.verMapa)
        val cerrarSesion : Button = menuHeader.findViewById(R.id.cerrarSesion)
        val alquiler : TextView = findViewById(R.id.BAlquiler)
        val venta : TextView = findViewById(R.id.BVenta)
        colorDefaultText = venta.textColors
        val textOrdenar = findViewById<TextView>(R.id.ordenarPor)
        navigator.setNavigationItemSelectedListener { item ->
            when (item.getItemId()) {
                R.id.mischats -> {
                    val irAChats =
                        Intent(this@MainTrobify, ListOfChats::class.java)
                    irAChats.putExtra("user", userId.toString())
                    startActivity(irAChats)
                    menuLateral.closeDrawers()
                }
                R.id.misfavoritos -> {
                    val irAMisFavoritos =
                        Intent(this@MainTrobify, ListaFavoritos::class.java)
                    irAMisFavoritos.putExtra("user", userId.toString())
                    startActivity(irAMisFavoritos)
                    menuLateral.closeDrawers()
                }
                R.id.ofertar -> {
                    val user = Firebase.auth.currentUser
                        if (user != null) {
                            if (user.isEmailVerified) {
                                val irAOfertarInmueble =
                                    Intent(this@MainTrobify, OfertarInmueble::class.java)
                                irAOfertarInmueble.putExtra("user", userId.toString())
                                startActivity(irAOfertarInmueble)
                                menuLateral.closeDrawers()
                            } else {
                                val builder = AlertDialog.Builder(this)
                                builder.setTitle("Esta funcion requiere una cuenta verificada")
                                builder.setMessage(" Para poder contactar con el propietario es necesario tener la cuenta verificada." + '\n' +
                                        "¿Necesita que le volvamos a enviar el correo de verificación?  ")
                                builder.setIcon(android.R.drawable.ic_dialog_alert)
                                builder.setNeutralButton("  Continuar  ") { _, _ -> }
                                builder.setPositiveButton("  Enviar correo  ")  { _, _ -> user.sendEmailVerification() }
                                val alertDialog : AlertDialog = builder.create()
                                alertDialog.setCancelable(false)
                                alertDialog.show()
                            }
                        }
                }
                R.id.mispisos -> {
                    val irAMisPisos = Intent(this, MisPisos::class.java)
                    irAMisPisos.putExtra("user",userId)
                    startActivity(irAMisPisos)
                }

            }
            true
        }
        cerrarMenu.setOnClickListener {
            menuLateral.closeDrawers()
        }
        cerrarSesion.setOnClickListener {
            auth.signOut()
            val volverALogin =
                Intent(this@MainTrobify, Login::class.java)
            startActivity(volverALogin)
        }

        nombreUser.text = user.name

        botonLateral.setOnClickListener {
            menuLateral.openDrawer(GravityCompat.START)
        }

        filtrar.setOnClickListener {
            val irAFiltrar = Intent(this, FiltrosBusqueda::class.java)
            irAFiltrar.putExtra("user", userId)
            startActivity(irAFiltrar)
        }
        mapa.setOnClickListener {
            val irAMapa = Intent(this, Mapa::class.java)
            irAMapa.putExtra("user", userId)
            startActivity(irAMapa)
        }
        alquiler.setOnClickListener {
            if(alquilerActivado)desactivarIntenciones(alquiler,venta)
            else activalAlquiler(alquiler, venta)
        }
        venta.setOnClickListener {
            if(ventaActivado)desactivarIntenciones(alquiler,venta)
            else activarVenta(alquiler, venta)
        }

        val builder = AlertDialog.Builder(this)
        textOrdenar.setOnClickListener {
            builder.setItems(R.array.orderOptions) { _, which ->
                when {
                    which.equals(0) // Ordenar por precio ascendente
                    -> {
                        orden.ordenSeleccionado = 0
                        inmueblesEnPantalla = ordenarInmuebles(inmueblesEnPantalla)
                        cabecera.text = "Inmuebles ordenados ascendentemente"
                    }
                    which.equals(1)// Ordenar por precio descendente
                    -> {
                        orden.ordenSeleccionado = 1
                        inmueblesEnPantalla = ordenarInmuebles(inmueblesEnPantalla)
                        cabecera.text = "Inmuebles ordenados descendentemente"
                    }
                    which.equals(2) // Ordenar por más recientes
                    -> {
                        orden.ordenSeleccionado = 2
                        inmueblesEnPantalla = ordenarInmuebles(inmueblesEnPantalla)
                        cabecera.text = "Inmuebles añadidos recientemente"
                    }
                    which.equals(3) // Ordenar por más antiguos
                    -> {
                        orden.ordenSeleccionado = 3
                        inmueblesEnPantalla = ordenarInmuebles(inmueblesEnPantalla)
                        cabecera.text = "Inmuebles ordenados por antiguedad"
                    }
                }
                mostrarInmuebles(inmueblesEnPantalla)
            }
            builder.setNegativeButton("Cancelar") { _, _ -> }
            val dialog = builder.create()
            dialog.show()
        }
    }

    private fun desactivarIntenciones(alquiler : TextView, venta : TextView){
        alquilerActivado = false
        ventaActivado = false
        alquiler.setBackgroundColor(Color.WHITE)
        alquiler.setTextColor(colorDefaultText)
        venta.setTextColor(colorDefaultText)
        venta.setBackgroundColor(Color.WHITE)
        mostrarInmuebles(inmuebles)
    }
    private fun activalAlquiler(alquiler : TextView, venta : TextView){
        alquilerActivado = true
        ventaActivado = false
        alquiler.setBackgroundColor(Color.BLUE)
        alquiler.setTextColor(Color.WHITE)
        venta.setTextColor(Color.BLACK)
        venta.setBackgroundColor(Color.GRAY)
        alquilerOVenta("Alquiler")
    }
    private fun activarVenta(alquiler : TextView, venta : TextView){
        alquilerActivado = false
        ventaActivado = true
        venta.setBackgroundColor(Color.BLUE)
        venta.setTextColor(Color.WHITE)
        alquiler.setTextColor(Color.BLACK)
        alquiler.setBackgroundColor(Color.GRAY)
        alquilerOVenta("Vender")
    }

    private fun intencionActual():String{
        println("$alquilerActivado $ventaActivado")
        if(alquilerActivado)return "Alquiler"
        else return "Vender"
    }
    @SuppressLint("SetTextI18n")
    fun resultadosConNuevaBusqueda(busqueda : String){
        if(alquilerActivado || ventaActivado) inmueblesEnPantalla =
            nuevaBusqueda.buscarConIntencion(busqueda.toUpperCase(Locale.ROOT), intencionActual())
        else inmueblesEnPantalla = nuevaBusqueda.buscar(busqueda.toUpperCase(Locale.ROOT))
        mostrarInmuebles(inmueblesEnPantalla)
        cabecera.text = "Inmuebles en $busqueda"
    }
    private fun alquilerOVenta(opcion : String){
        inmueblesEnPantalla = Database.obtenerInmueblesSegunIntencion(opcion)
        mostrarInmuebles(inmueblesEnPantalla)
    }

    fun ordenarInmuebles(inmueblesEnPantalla:ArrayList<DataInmueble2>) : ArrayList<DataInmueble2>{
        lateinit var inmueblesOrdenados : List<DataInmueble2>
        val tipoDeOrden = orden.ordenSeleccionado
        if(tipoDeOrden == 0){
            inmueblesOrdenados = inmueblesEnPantalla.sortedBy{it.precio}
        }
        else if(tipoDeOrden == 1){
            inmueblesOrdenados = inmueblesEnPantalla.sortedByDescending{it.precio}
        }
        else if(tipoDeOrden == 2){
            inmueblesOrdenados = inmueblesEnPantalla.sortedByDescending{it.fechaSubida}
        }
        else if(tipoDeOrden == 3){
            inmueblesOrdenados = inmueblesEnPantalla.sortedBy{it.fechaSubida}
        }
        return inmueblesOrdenados.toMutableList() as ArrayList<DataInmueble2>
    }

    @SuppressLint("SetTextI18n")
    fun mostrarInmuebles(listaInmuebles : ArrayList<DataInmueble2>){
        inmueblesEnPantalla = listaInmuebles
        val layoutmanager = LinearLayoutManager(baseContext)
        listaConResultados.layoutManager = layoutmanager
        listaConResultados.setHasFixedSize(true)
        listaConResultados.adapter = AdaptadorInmuebleBusqueda(listaInmuebles, userFav,this)
        nResultados.text = "${listaInmuebles.size} resultados"
    }

    private fun verficarOrdenacion(){
        GuardaOrdenacion.guardaOrdenacion.ordenGuardado = orden.ordenSeleccionado
        ordenarInmuebles(inmueblesEnPantalla)
    }

}
