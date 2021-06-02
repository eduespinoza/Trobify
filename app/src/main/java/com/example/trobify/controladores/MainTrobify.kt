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
import com.example.trobify.models.*
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.trobify_main.*
import kotlinx.coroutines.*
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random.Default.nextBoolean


open class MainTrobify : AppCompatActivity(), AdaptadorInmuebleBusqueda.OnItemClickListener {
    var calles =  arrayListOf("RUSSAFA", "JOSÉ GROLLO", "JOSÉ MARIA FUSTER", "JOSE MARIA HARO (MAGISTRAT)", "BADAJOZ", "JACINT LABAILA", "JACOMART", "PERIODISTA JOSÉ OMBUENA", "JOAQUIM DUALDE",
        "JOAQUIM NAVARRO", "JOSEP AGUIRRE", "IFAC", "ESGLÉSIA DEL FORN D'ALCEDO", "IMPERTINÈNCIES", "ENGINYER AUBÁN", "ENGINYER DICENTA", "ENGINYER JOSÉ SIRERA", "ENGINYER MANUEL CÁNOVAS", "ILLA CABRERA", "JACA", "HISTORIADOR COLOMA", "HISTORIADOR DIAGO", "HÒMENS DE LA MAR", "HONORAT JUAN", "FORN DELS APÒSTOLS", "FORN DEL CABANYAL", "HORTICULTOR BOSCH", "HUMANISTA MARINER", "IBANYES", "GERMANS MACHADO", "GERMANS MARISTES", "GERMANS VILLALONGA", "FERRO", "FIGUEROLES DE DOMENYO", "GONZALO RAMIRO PEDRER", "GOYA", "GRAVADOR ESTEVE", "AGUSTÍ CENTELLES OSSÓ (FOTÒGRAF)", "GUILLEM DANGLESOLA", "GUITARRISTA TÀRREGA", "FARINA", "GIBRALTAR", "GEGANT", "GEPA", "GINER", "GLÒRIA", "GODELLA", "GODELLETA", "GOLES", "GONZÁLEZ MARTÍ", "GENERAL AVILÉS", "JERÓNIMA GALÉS (IMPRESSORA)", "BAIX DE MASSARROJOS", "GENERAL LLORENS", "FÉLIX DEL RÍO(ACTIVISTA VEÏNAL)", "GENERAL TOVAR", "POBLE", "AGRÓ", "GARRIGUES", "GARROFA", "GASCÓ OLIAG", "GASPAR TORRELLA", "GAUDÈNCIA TORRES", "GAVINES", "GENERAL", "GENERAL ALMIRANTE", "FRAULES", "FONT D'EN CORTS", "FONT D'EN CARRÒS", "FUENTERROBLES", "FONT DE SANT AGUSTÍ", "FURS", "GABINO", "GABRIEL Y GALÁN", "GALLINES", "FRANCESC EIXIMENIS", "FRANCESC MARTÍNEZ", "FRANCISCO MORENO USEDO", "SANT JACINT CASTANYEDA", "FRA JOAN MONTSÓ", "FRA J. RODRÍGUEZ", "FRA PERE VIVES", "FONTANARS DELS ALFORINS", "FORATA", "FORTALENY", "FRANCESC CLIMENT", "FRANCESC CUBELLS", "FERRAN EL CATÒLIC", "FERRER I BIGNÉ", "FIGUERETA", "FINESTRAT", "FINLÀNDIA", "FLORS", "FOLCH DE CARDONA", "ESPERANÇA", "ESPERÓ DE CANTA-RANES", "ESQUIF", "ESTAMENYERIA VELLA", "ESTEVE VICTÒRIA", "ESTRIBORD", "FELIPE SALVADOR", "FELIP VIVES DE CANYAMARS", "FENOLLOSA", "ERNEST FERRER", "ERUDIT ORELLANA", "PERE COMPTE , MESTRE EN EL NOBLE ART PEDRA", "ESCOLANO", "ESCRIVÀ", "ESCOLES DE MALILLA", "ESCULTOR AIXA", "ESCULTOR PIQUER", "ESPADÀ", "EN PLOM", "EN RODA", "EN SENDRA", "ENCARNACIÓ", "ENRIQUE OLMOS", "ENTENÇA", "ERMITA", "ERNEST ANASTASIO", "EMILI BARÓ", "EMILI MARÍ", "ENLLAÇ", "EMPERADOR", "EN BORRÀS", "EN GALL", "GUILLEM FERRER", "EN LLOPIS", "EN PINA", "EDUARD BOSCÀ", "PERE MARIA ORTS I BOSCH (HISTORIADOR)", "EXÈRCIT ESPANYOL", "BATXILLER", "RULLO", "SALZE", "ELCANO", "ELIES TORMO", "JOAN DE VILA-RASA", "DON QUIJOTE DE LA MANCHA", "DOS D'ABRIL", "DUC DE GAETA", "JUST RAMÍREZ (ARQUITECTE)", "EDETA", "DOCTOR JOSEP JUAN DÒMINE", "DOCTOR LANDETE", "DOCTOR MACHÍ", "DOCTOR MANUEL CANDELA", "DOCTOR MARAÑÓN", "DOCTOR MONSERRAT", "DOCTOR PESET CERVERA", "JUAN DE DIOS MONTAÑÉS", "PEDRAPIQUERS", "TRAGINERS", "DÉNIA", "DOCTOR ANDREU PIQUER", "DOCTOR BLAY", "DOCTOR CONSTANTÍ GÓMEZ", "DOCTOR GIL I MORTE", "MOSSÉN PALANCA", "XALET", "XELLA", "XERA", "XEST", "XIRIVELLA", "DAMA D'ELX", "DANSES", "DAVIDS", "NINOT", "CUBA", "CONCA", "CUENCA TRAMOYERES", "COVES CAROLINES", "MOSSÉN BAU", "COSTA I BORRÀS", "CRESPINS", "CRESPO", "CRESTA", "CREVILLENT", "CRISÒSTOM MARTÍNEZ", "CRONISTA TORRES", "CREU", "ESTACIONETA ", "CONVENT DE SANT FRANCESC", "COOPERATIVA DE SANT FERRAN", "COR DE JESÚS", "CORDELLATS", "CORONA", "CORREDORS", "COMTE D'ALTEA", "COMTE DE MELITO", "COMTE DE MONTORNÉS", "COMTE DEL REAL", "COMTE DE TORREFIEL", "CONVENT DELS CARMELITES", "CONVENT DE JESÚS", "CLERO", "COBERTÍS", "COFRENTS", "COIX CIURANA", "COLERO (POU APARICI 9)", "COLÓN", "COLL DE RATES", "COMUNIÓ DE SANT JOAN", "CONCHA ESPINA", "CEBRIAN MEZQUITA", "CEMENTERI", "SÉNIA", "PERE BORREGO I GALINDO (FALLER)", "CERAMISTA BAYARRI", "CERAMISTA GIMENO", "CERAMISTA ROS", "PROFESSOR DOCTOR SEVERO OCHOA", "CLARIANO", "CASTELL DE POP", "CASTELLÓ", "CASTIELFABIB", "CATALANS", "CATARROJA", "CAUDETE", "CAYUCO", "CASA DEL VINYERO", "CASA DE LA RASO", "CASA DE SANTAPAU", "CASA DE TOTÀ", "CASAS ALTAS", "CASES D'AMÀLIA", "CASAS BAJAS", "CASES DE BÀRCENA", "CASES DE BARRINTO", "CASES DE CARBONELL", "CASES DE GUERRA", "CASES DE MASENA", "DEMOCRÀCIA", "CAPELLETA", "CARPESA A BORBOTÓ", "CARPESA A MONTCADA", "FUSTERS", "CARTERS", "CARRASCA", "CARRERES PUCHALT", "CASA DEL CACHONDO", "CORONACIÓ", "CASA DE GORRITA", "CASA DE MOLINA", "CASA ÑORRO", "CASA DE PERICO", "CAMPAMENT", "CAMPANA", "CAMPORROBLES", "CANALETA", "CANONGE TÀRREGA", "CAÑETE", "CADIS", "CARABASSES", "CALAMOCHA", "CALA-XARXES", "CALVARI", "CALVO ACACIO", "CAMARENA", "BICORP", "BISBE", "BITÀCOLA", "BLANCO BALLESTER", "BLANQUERIA", "BOIX", "BOMBA", "CABANYAL", "CABILLERS", "BENIDOLEIG", "BENIFARAIG A MONTCADA", "BENIFAIÓ", "BENIMACLET", "BENIMODO", "BENIPEIXCAR", "BERENGUER MALLOL", "BERNABÉ GARCIA", "ALCÀSSER", "ALCÚDIA DE CRESPINS", "BENEFICÈNCIA", "BENET BOSCH", "BENICALAP", "BENICARLÓ", "CENTENAR DE LA PLOMA", "ELÍAS BORRÀS (POETA)", "ADREÇADORS", "AGUSTINA D'ARAGÓ", "ALABAU I ARCE", "ALBAL", "ALBENTOSA", "MARE DE DÉU DE LES INJÚRIES", "MARE DE DÉU DEL LEPANT", "MARE DE DÉU DE LA MISERICÒRDIA", "EDUARD SOLER I PÉREZ", "LLORERS", "ÁNGEL VILLENA", "PORTADORS DE LA VERGE", "METGE RAMÓN TARAZONA", "PUEBLA DE VALVERDE", "FRANCISCO COMES MARTINEZ (DRAMATURG)", "TRES D'ABRIL DE 1979", "JOAN AGUILÓ", "JOAN BAPTISTA COMES", "JOAN BAPTISTA LLOVERA", "JUAN BAUTISTA MARCO", "SEQUIOTA", "JUAN CALATRAVA", "JUAN CASTELLÓ", "JUAN DE CELAYA", "JUAN FABREGAT", "JOAQUIM BENLLOCH", "JOAQUIM COSTA", "VILAFERMOSA", "VILAMARXANT", "VINALOPÓ", "VINATEA", "JAUME L'OBRER", "JAUME ROIG", "XALANS", "XARAFULL", "OLIVERETA", "JERONI DE MONTSORIU", "JESÚS MORANTE BORRÁS", "JIJONA", "DOCTOR VILLENA", "DOCTOR WAKSMAN", "DOLORES ALCAIDE", "DOLORES MARQUÉS", "DOMÉNEC GÓMEZ", "ARMANDO PALACIO VALDÉS", "JAIME BELTRÁN", "CARAVACA", "PRESÓ DE SANT VICENT", "CARDENAL BENLLOCH", "CARITAT", "CARDENAL MONESCILLO", "DOCTOR SERRA", "DOCTOR SORIANO BENLLOCH", "DOCTOR VICENTE PALLARÉS", "BORRULL", "BOTÀNIC", "BRETÓN DE LOS HERREROS", "BUGARRA", "BUSOT", "DOCTOR RODRÍGUEZ DE LA FUENTE", "BEAT JUAN GRANDE", "BEATRIZ TORTOSA", "BETLEM", "BELLO", "BONAIRE", "BORBOTÓ", "BORBOTÓ A MASSARROJOS", "BRODADORS", "BORRASCA", "OEST", "BARÓ DE CORTES", "BARÓ DE PETRÉS", "BARÓ DE SAN PETRILLO", "BARRAQUES DE CASTELLÓ", "BARRAQUES DE LLUNA", "BATEL", "BEATA", "BALANDRA", "BALEARS", "BALER", "BALLESTERS", "BARCELLA", "BARÓ DE BARXETA", "AURORA", "AVE MARIA", "AIACOR", "ASSAGADOR", "ASSAGADOR D'ALBORAIA", "ASSAGADOR DE LES MONGES", "ASSAGADOR DEL MORRO", "ASSAGADOR DE LA TORRE", "AZCÁRRAGA", "ARQUITECTE RODRÍGUEZ", "ARQUITECTE TOLSÀ", "ARTÉS", "ARTS GRÀFIQUES", "ARQUEBISBE ALIAGA", "ARQUEBISBE OLAECHEA", "DALT", "ASIL D'INFANTS", "AROLAS", "ARQUITECTE ALFARO", "ARQUITECTE ARNAU", "ARQUITECTE CARBONELL", "ARQUITECTE JUAN PÉREZ", "ARQUITECTE LUCINI", "MALENA", "ARQUITECTE RIBES", "ANTONI SUÁREZ", "APARICIO ALBIÑANA", "APARISI I GUIJARRO", "ARAS DE LOS OLMOS", "ARBOREDA", "ARCHENA", "ARNAU DE VILANOVA", "EMPAR BALLESTER", "EMPAR GUILLEM", "ANDILLA", "ANDRÉS MANCEBO", "ANGELICOT", "ESTRET DE LA COMPANYIA", "ANGUILERA", "ANOUERS", "ANTONI LÁZARO", "ALQUERIES DE BELLVER", "DALT", "DALT DE LA MAR", "ALTAR DE SANT VICENT", "ÁLVAREZ", "ÁLVAREZ DE SOTOMAYOR", "AMÈRICA", "ALQUERIA D'ALBORS", "ALQUERIA D'ANTEQUERA", "ALQUERIA D'ASNAR", "ALQUERIA DE BENLLOCH", "ALQUERIA DE BURGOS", "ALQUERIA DE CANO", "ALQUERIA DE CASSANY", "ALQUERIA DE GILET", "ALQUERIA DE GINER", "ALQUERIA DELS FRARES", "ALQUERIA DE GINER", "ALQUERIA D'ISIDORO", "ALQUERIA DE MARQUET", "ALQUERIA DE LA MORERA", "ALQUERIA DE PIXA-RODA", "ALQUERIA DE ROCATÍ", "ALQUERIA DEL VOLANT", "ALGARÍN", "ALGEMESÍ", "ALGIRÓS", "ALMÀSSERA", "ÀNIMES", "ALMENAR", "ALMIRALL CADARSO", "ALMUDAINA", "ALQUERIA D'ALBA", "ALZIRA", "ALCOI", "ALEGRET", "ALESSANDRO VOLTA", "ALFAFAR", "ALFONS EL MAGNÀNIM", "ALBERIC", "ALBEROLA", "ALBORAIA", "ALBORAIA", "ALBUIXEC A MAUELLA", "ALCALDE REIG", "ALBENIZ", "ACTOR MORA", "ADOR", "AGUSTÍ SALES", "AIRE", "ALABAU", "ALADRERS", "ALBACETE", "ALBAIDA", "JUAN JOSÉ BARCIA GOYANES (PROFESSOR)", "MARGARITA SALAS (CIENTÍFICA)", "CRISTÓBAL VALLS LLORENS (METGE)", "CASES BAIXES", "SANTA PAULA MONTAL", "SANTA MARIA MAZZARELLO", "DIEGO SEVILLA (CATEDRÀTIC)", "ÁNGEL GUARDIA CORTÉS (FUNDADOR AV. BENIMACLET)", "DECIMO JUNIO BRUTO (CONSOL ROMA)", "HUGO ZARATE", "EUROPA", "PONT DE L'ASSUT DE L'OR", "MELCHOR HOYOS PÉREZ", "BARQUERA", "COCOTERS", "JOSE PASTOR MORENO (PRESIDENT TRIBUNAL AIGÜES)", "EP SECTOR FUENTE SAN LUIS A", "EP SAN VICENTE MÁRTIR 254", "HORTOLANES", "CASA DE LA DEMANÀ", "ACADÈMIC CASTAÑEDA", "CUIRASSAT", "E.P. PE. SAN JUAN DE DIOS", "E.P. PE. FRANCISCO FALCONS", "PARC PROL MOTILLA DEL PALANCAR", "EN PROYECTO JUNTO 346 AV CONSTITUCION", "E.P. PE. DUQUE DE MANDAS", "EN PROYECTO PROL. CARLOS CORTINA", "EN PROYECTO PERP.16 SALU CERVERO")
    var listaIDS = arrayListOf<String>()
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
        println("jaja pues si")
        if(filtrosAplicados == null && opcionesDeInicio == null){
        user = Database.getUser(userId)
        userFav = user.favorites!!
            println("poraquinnopasasverdad hijodeputa")
        mostrarInmuebles(inmuebles)
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
        user = Database.getUser(userId)
        userFav = user.favorites!!
        inmuebles = Database.getAllInmuebles()
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
        //Se obtienen resultados de base de datos

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
        println("prepararprimerosresultadosqcojones")
        mostrarInmuebles(inmuebles)
    }

    private fun mostrarResultadosInicio(opciones : ArrayList<String>){
        cabecera.text = "Primeros resultados"
        var inm = Database.getInmueblesByIds(opciones)
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

        //listener abrir menu lateral
        botonLateral.setOnClickListener {
            menuLateral.openDrawer(GravityCompat.START)
        }

        filtrar.setOnClickListener {
            val irAFiltrar = Intent(this, FiltrosBusqueda::class.java)
            irAFiltrar.putExtra("user", userId)
            startActivity(irAFiltrar)
        }
        mapa.setOnClickListener {
            //abrir mapa
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
                        inmueblesEnPantalla = ordenarInmuebles()
                        cabecera.text = "Inmuebles ordenados ascendentemente"
                    }
                    which.equals(1)// Ordenar por precio descendente
                    -> {
                        orden.ordenSeleccionado = 1
                        inmueblesEnPantalla = ordenarInmuebles()
                        cabecera.text = "Inmuebles ordenados descendentemente"
                    }
                    which.equals(2) // Ordenar por más recientes
                    -> {
                        orden.ordenSeleccionado = 2
                        inmueblesEnPantalla = ordenarInmuebles()
                        cabecera.text = "Inmuebles añadidos recientemente"
                    }
                    which.equals(3) // Ordenar por más antiguos
                    -> {
                        orden.ordenSeleccionado = 3
                        inmueblesEnPantalla = ordenarInmuebles()
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
        if(alquilerActivado || ventaActivado) inmueblesEnPantalla = //firebase.getInmueblesIntecion()
            nuevaBusqueda.buscarConIntencion(busqueda.toUpperCase(Locale.ROOT), intencionActual())
        else inmueblesEnPantalla = nuevaBusqueda.buscar(busqueda.toUpperCase(Locale.ROOT))
        mostrarInmuebles(inmueblesEnPantalla)
        cabecera.text = "Inmuebles en $busqueda"
    }
    private fun alquilerOVenta(opcion : String){
        inmueblesEnPantalla = Database.getInmueblesIntencion(opcion)
        mostrarInmuebles(inmueblesEnPantalla)
    }

    //fun para generar id's de inmuebles aleatorios *** se podria meter en clase inmueble
    // no se comprueba que el id se repita con otro ya puesto
    fun generateRandomId() : String {
        val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val pathId = (1..5)
            .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("");
        return pathId
    }
    fun getRandom1a4() : Int{
        return kotlin.random.Random.nextInt(1,5)
    }
    fun getRandomSuperficie() : Int{
        return kotlin.random.Random.nextInt(40,220)
    }
    fun getRandomTipoVivienda() : String{
        return arrayListOf("Apartamento","Ático","Dúplex","Loft",
            "Planta baja","Estudio","Casa","Chalet","Adosado","Finca rústica")[kotlin.random.Random.nextInt(0,10)]
    }
    fun getRandomExtras():ArrayList<String>{
        var cuanto = kotlin.random.Random.nextInt(1,4)
        var res = arrayListOf<String>()
        for(i in 1..cuanto){
            var otro = getRandomCertificado()
            if(!res.contains(otro)){res.add(otro)}
        }
        return res
    }
    fun getRandomTipos(ey : DataInmueble2):DataInmueble2{
        var res = getRandomTipoInmueble()
        ey.id = generateRandomId()
        ey.propietario = null
        ey.tipoInmueble = res
        ey.fotos = arrayListOf()
        ey.superficie = getRandomSuperficie()
        ey.precio = getRandomPrice()
        ey.intencion = getRandomIntencion()
        ey.descripcion = "Bueno, bonito y barato"
        ey.fechaSubida = getRandomDate()
        if(res.equals("Vivienda")){
            ey.tipoVivienda = getRandomTipoVivienda()
            ey.numHabitaciones = getRandom1a4()
            ey.numBanos = getRandom1a4()
            ey.estado = getRandomEstado()
            ey.extras = getRandomExtras()
        }
        if(res.equals("Edificio")){
            ey.tipoVivienda = getRandomTipoViviendaEdificio()
            ey.numHabitaciones = getRandom1a4()
            ey.numBanos = getRandom1a4()
            ey.estado = getRandomEstado()
            ey.extras = getRandomExtras()
        }
        if (res.equals("Oficina")|| res.equals("Local")){
            ey.numBanos = getRandom1a4()
            ey.estado = getRandomEstado()
            ey.extras = getRandomExtras()
        }
        if (res.equals("Garaje")){
            ey.estado = getRandomEstado()
        }
        return ey
    }
    fun getRandomTipoInmueble() : String{
        return arrayListOf("Vivienda","Edificio","Oficina","Garaje",
            "Local","Terreno","Nave")[kotlin.random.Random.nextInt(0,7)]
    }
    fun getRandomTipoViviendaEdificio() : String {
        return arrayListOf("Apartamento","Ático","Dúplex","Loft",
            "Planta baja","Estudio")[kotlin.random.Random.nextInt(0,6)]
    }
    fun getRandomCertificado() : String {
        return arrayListOf("Parking","Ascensor","Amueblado","Calefacción",
            "Jardín","Piscina","Terraza","Trastero")[kotlin.random.Random.nextInt(0,8)]
    }
    fun getRandomPrice() : Int {
        return arrayListOf<Int>(50000, 75000, 100000, 125000, 150000, 200000,
            300000, 400000, 500000)[kotlin.random.Random.nextInt(0,9)]
    }
    fun getRandomEstado() : String {
        return arrayListOf("Obra nueva","Casi nuevo","Muy bien","Bien","Reformado","A reformar")[kotlin.random.Random.nextInt(0,5)]
    }

    fun getRandomDate() : String {
        return LocalDateTime.now().minusDays(kotlin.random.Random.nextInt(0,20).toLong()).toString()
    }

    fun getPlaces(){
        var sitios = arrayListOf<Sitio>()
        runBlocking(Dispatchers.Default) {
            db.collection("testingPlacesv2").get().addOnCompleteListener {
                if(it.isSuccessful){
                    for(result in it.result){
                        var place = result.toObject(Sitio::class.java)
                        sitios.add(place)
                    }
                    generatePisos(sitios)
                }
            }}
    }
    fun getRandomIntencion():String{
        return arrayListOf("Alquiler","Vender")[kotlin.random.Random.nextInt(0,2)]
    }
    private fun getInmuebleMano():DataInmueble2{
        var aux = DataInmueble2()
        var inmueble = getRandomTipos(aux)
        return inmueble
    }
    //fun para generar pisos en bd
    fun generatePisos(sitios : ArrayList<Sitio>) {
        for (venga in sitios){
            var inmueble = getInmuebleMano()
            inmueble.direccion = venga
            inmueble.id?.let { Database.db.collection("inmueblesv5").document(it).set(inmueble) }
        }
    }

    private fun ordenarInmuebles() : ArrayList<DataInmueble2>{
        lateinit var inmueblesOrdenados :List<DataInmueble2>
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
        ordenarInmuebles()
    }

}
