package com.example.trobify

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
import androidx.core.view.size
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.tasks.await


open class MainTrobify : AppCompatActivity(), AdaptadorInmuebleBusqueda.OnItemClickListener {
    //private lateinit var database: DatabaseReference
    var calles =  arrayListOf("RUSSAFA", "JOSÉ GROLLO", "JOSÉ MARIA FUSTER", "JOSE MARIA HARO (MAGISTRAT)", "BADAJOZ", "JACINT LABAILA", "JACOMART", "PERIODISTA JOSÉ OMBUENA", "JOAQUIM DUALDE",
        "JOAQUIM NAVARRO", "JOSEP AGUIRRE", "IFAC", "ESGLÉSIA DEL FORN D'ALCEDO", "IMPERTINÈNCIES", "ENGINYER AUBÁN", "ENGINYER DICENTA", "ENGINYER JOSÉ SIRERA", "ENGINYER MANUEL CÁNOVAS", "ILLA CABRERA", "JACA", "HISTORIADOR COLOMA", "HISTORIADOR DIAGO", "HÒMENS DE LA MAR", "HONORAT JUAN", "FORN DELS APÒSTOLS", "FORN DEL CABANYAL", "HORTICULTOR BOSCH", "HUMANISTA MARINER", "IBANYES", "GERMANS MACHADO", "GERMANS MARISTES", "GERMANS VILLALONGA", "FERRO", "FIGUEROLES DE DOMENYO", "GONZALO RAMIRO PEDRER", "GOYA", "GRAVADOR ESTEVE", "AGUSTÍ CENTELLES OSSÓ (FOTÒGRAF)", "GUILLEM DANGLESOLA", "GUITARRISTA TÀRREGA", "FARINA", "GIBRALTAR", "GEGANT", "GEPA", "GINER", "GLÒRIA", "GODELLA", "GODELLETA", "GOLES", "GONZÁLEZ MARTÍ", "GENERAL AVILÉS", "JERÓNIMA GALÉS (IMPRESSORA)", "BAIX DE MASSARROJOS", "GENERAL LLORENS", "FÉLIX DEL RÍO(ACTIVISTA VEÏNAL)", "GENERAL TOVAR", "POBLE", "AGRÓ", "GARRIGUES", "GARROFA", "GASCÓ OLIAG", "GASPAR TORRELLA", "GAUDÈNCIA TORRES", "GAVINES", "GENERAL", "GENERAL ALMIRANTE", "FRAULES", "FONT D'EN CORTS", "FONT D'EN CARRÒS", "FUENTERROBLES", "FONT DE SANT AGUSTÍ", "FURS", "GABINO", "GABRIEL Y GALÁN", "GALLINES", "FRANCESC EIXIMENIS", "FRANCESC MARTÍNEZ", "FRANCISCO MORENO USEDO", "SANT JACINT CASTANYEDA", "FRA JOAN MONTSÓ", "FRA J. RODRÍGUEZ", "FRA PERE VIVES", "FONTANARS DELS ALFORINS", "FORATA", "FORTALENY", "FRANCESC CLIMENT", "FRANCESC CUBELLS", "FERRAN EL CATÒLIC", "FERRER I BIGNÉ", "FIGUERETA", "FINESTRAT", "FINLÀNDIA", "FLORS", "FOLCH DE CARDONA", "ESPERANÇA", "ESPERÓ DE CANTA-RANES", "ESQUIF", "ESTAMENYERIA VELLA", "ESTEVE VICTÒRIA", "ESTRIBORD", "FELIPE SALVADOR", "FELIP VIVES DE CANYAMARS", "FENOLLOSA", "ERNEST FERRER", "ERUDIT ORELLANA", "PERE COMPTE , MESTRE EN EL NOBLE ART PEDRA", "ESCOLANO", "ESCRIVÀ", "ESCOLES DE MALILLA", "ESCULTOR AIXA", "ESCULTOR PIQUER", "ESPADÀ", "EN PLOM", "EN RODA", "EN SENDRA", "ENCARNACIÓ", "ENRIQUE OLMOS", "ENTENÇA", "ERMITA", "ERNEST ANASTASIO", "EMILI BARÓ", "EMILI MARÍ", "ENLLAÇ", "EMPERADOR", "EN BORRÀS", "EN GALL", "GUILLEM FERRER", "EN LLOPIS", "EN PINA", "EDUARD BOSCÀ", "PERE MARIA ORTS I BOSCH (HISTORIADOR)", "EXÈRCIT ESPANYOL", "BATXILLER", "RULLO", "SALZE", "ELCANO", "ELIES TORMO", "JOAN DE VILA-RASA", "DON QUIJOTE DE LA MANCHA", "DOS D'ABRIL", "DUC DE GAETA", "JUST RAMÍREZ (ARQUITECTE)", "EDETA", "DOCTOR JOSEP JUAN DÒMINE", "DOCTOR LANDETE", "DOCTOR MACHÍ", "DOCTOR MANUEL CANDELA", "DOCTOR MARAÑÓN", "DOCTOR MONSERRAT", "DOCTOR PESET CERVERA", "JUAN DE DIOS MONTAÑÉS", "PEDRAPIQUERS", "TRAGINERS", "DÉNIA", "DOCTOR ANDREU PIQUER", "DOCTOR BLAY", "DOCTOR CONSTANTÍ GÓMEZ", "DOCTOR GIL I MORTE", "MOSSÉN PALANCA", "XALET", "XELLA", "XERA", "XEST", "XIRIVELLA", "DAMA D'ELX", "DANSES", "DAVIDS", "NINOT", "CUBA", "CONCA", "CUENCA TRAMOYERES", "COVES CAROLINES", "MOSSÉN BAU", "COSTA I BORRÀS", "CRESPINS", "CRESPO", "CRESTA", "CREVILLENT", "CRISÒSTOM MARTÍNEZ", "CRONISTA TORRES", "CREU", "ESTACIONETA ", "CONVENT DE SANT FRANCESC", "COOPERATIVA DE SANT FERRAN", "COR DE JESÚS", "CORDELLATS", "CORONA", "CORREDORS", "COMTE D'ALTEA", "COMTE DE MELITO", "COMTE DE MONTORNÉS", "COMTE DEL REAL", "COMTE DE TORREFIEL", "CONVENT DELS CARMELITES", "CONVENT DE JESÚS", "CLERO", "COBERTÍS", "COFRENTS", "COIX CIURANA", "COLERO (POU APARICI 9)", "COLÓN", "COLL DE RATES", "COMUNIÓ DE SANT JOAN", "CONCHA ESPINA", "CEBRIAN MEZQUITA", "CEMENTERI", "SÉNIA", "PERE BORREGO I GALINDO (FALLER)", "CERAMISTA BAYARRI", "CERAMISTA GIMENO", "CERAMISTA ROS", "PROFESSOR DOCTOR SEVERO OCHOA", "CLARIANO", "CASTELL DE POP", "CASTELLÓ", "CASTIELFABIB", "CATALANS", "CATARROJA", "CAUDETE", "CAYUCO", "CASA DEL VINYERO", "CASA DE LA RASO", "CASA DE SANTAPAU", "CASA DE TOTÀ", "CASAS ALTAS", "CASES D'AMÀLIA", "CASAS BAJAS", "CASES DE BÀRCENA", "CASES DE BARRINTO", "CASES DE CARBONELL", "CASES DE GUERRA", "CASES DE MASENA", "DEMOCRÀCIA", "CAPELLETA", "CARPESA A BORBOTÓ", "CARPESA A MONTCADA", "FUSTERS", "CARTERS", "CARRASCA", "CARRERES PUCHALT", "CASA DEL CACHONDO", "CORONACIÓ", "CASA DE GORRITA", "CASA DE MOLINA", "CASA ÑORRO", "CASA DE PERICO", "CAMPAMENT", "CAMPANA", "CAMPORROBLES", "CANALETA", "CANONGE TÀRREGA", "CAÑETE", "CADIS", "CARABASSES", "CALAMOCHA", "CALA-XARXES", "CALVARI", "CALVO ACACIO", "CAMARENA", "BICORP", "BISBE", "BITÀCOLA", "BLANCO BALLESTER", "BLANQUERIA", "BOIX", "BOMBA", "CABANYAL", "CABILLERS", "BENIDOLEIG", "BENIFARAIG A MONTCADA", "BENIFAIÓ", "BENIMACLET", "BENIMODO", "BENIPEIXCAR", "BERENGUER MALLOL", "BERNABÉ GARCIA", "ALCÀSSER", "ALCÚDIA DE CRESPINS", "BENEFICÈNCIA", "BENET BOSCH", "BENICALAP", "BENICARLÓ", "CENTENAR DE LA PLOMA", "ELÍAS BORRÀS (POETA)", "ADREÇADORS", "AGUSTINA D'ARAGÓ", "ALABAU I ARCE", "ALBAL", "ALBENTOSA", "MARE DE DÉU DE LES INJÚRIES", "MARE DE DÉU DEL LEPANT", "MARE DE DÉU DE LA MISERICÒRDIA", "EDUARD SOLER I PÉREZ", "LLORERS", "ÁNGEL VILLENA", "PORTADORS DE LA VERGE", "METGE RAMÓN TARAZONA", "PUEBLA DE VALVERDE", "FRANCISCO COMES MARTINEZ (DRAMATURG)", "TRES D'ABRIL DE 1979", "JOAN AGUILÓ", "JOAN BAPTISTA COMES", "JOAN BAPTISTA LLOVERA", "JUAN BAUTISTA MARCO", "SEQUIOTA", "JUAN CALATRAVA", "JUAN CASTELLÓ", "JUAN DE CELAYA", "JUAN FABREGAT", "JOAQUIM BENLLOCH", "JOAQUIM COSTA", "VILAFERMOSA", "VILAMARXANT", "VINALOPÓ", "VINATEA", "JAUME L'OBRER", "JAUME ROIG", "XALANS", "XARAFULL", "OLIVERETA", "JERONI DE MONTSORIU", "JESÚS MORANTE BORRÁS", "JIJONA", "DOCTOR VILLENA", "DOCTOR WAKSMAN", "DOLORES ALCAIDE", "DOLORES MARQUÉS", "DOMÉNEC GÓMEZ", "ARMANDO PALACIO VALDÉS", "JAIME BELTRÁN", "CARAVACA", "PRESÓ DE SANT VICENT", "CARDENAL BENLLOCH", "CARITAT", "CARDENAL MONESCILLO", "DOCTOR SERRA", "DOCTOR SORIANO BENLLOCH", "DOCTOR VICENTE PALLARÉS", "BORRULL", "BOTÀNIC", "BRETÓN DE LOS HERREROS", "BUGARRA", "BUSOT", "DOCTOR RODRÍGUEZ DE LA FUENTE", "BEAT JUAN GRANDE", "BEATRIZ TORTOSA", "BETLEM", "BELLO", "BONAIRE", "BORBOTÓ", "BORBOTÓ A MASSARROJOS", "BRODADORS", "BORRASCA", "OEST", "BARÓ DE CORTES", "BARÓ DE PETRÉS", "BARÓ DE SAN PETRILLO", "BARRAQUES DE CASTELLÓ", "BARRAQUES DE LLUNA", "BATEL", "BEATA", "BALANDRA", "BALEARS", "BALER", "BALLESTERS", "BARCELLA", "BARÓ DE BARXETA", "AURORA", "AVE MARIA", "AIACOR", "ASSAGADOR", "ASSAGADOR D'ALBORAIA", "ASSAGADOR DE LES MONGES", "ASSAGADOR DEL MORRO", "ASSAGADOR DE LA TORRE", "AZCÁRRAGA", "ARQUITECTE RODRÍGUEZ", "ARQUITECTE TOLSÀ", "ARTÉS", "ARTS GRÀFIQUES", "ARQUEBISBE ALIAGA", "ARQUEBISBE OLAECHEA", "DALT", "ASIL D'INFANTS", "AROLAS", "ARQUITECTE ALFARO", "ARQUITECTE ARNAU", "ARQUITECTE CARBONELL", "ARQUITECTE JUAN PÉREZ", "ARQUITECTE LUCINI", "MALENA", "ARQUITECTE RIBES", "ANTONI SUÁREZ", "APARICIO ALBIÑANA", "APARISI I GUIJARRO", "ARAS DE LOS OLMOS", "ARBOREDA", "ARCHENA", "ARNAU DE VILANOVA", "EMPAR BALLESTER", "EMPAR GUILLEM", "ANDILLA", "ANDRÉS MANCEBO", "ANGELICOT", "ESTRET DE LA COMPANYIA", "ANGUILERA", "ANOUERS", "ANTONI LÁZARO", "ALQUERIES DE BELLVER", "DALT", "DALT DE LA MAR", "ALTAR DE SANT VICENT", "ÁLVAREZ", "ÁLVAREZ DE SOTOMAYOR", "AMÈRICA", "ALQUERIA D'ALBORS", "ALQUERIA D'ANTEQUERA", "ALQUERIA D'ASNAR", "ALQUERIA DE BENLLOCH", "ALQUERIA DE BURGOS", "ALQUERIA DE CANO", "ALQUERIA DE CASSANY", "ALQUERIA DE GILET", "ALQUERIA DE GINER", "ALQUERIA DELS FRARES", "ALQUERIA DE GINER", "ALQUERIA D'ISIDORO", "ALQUERIA DE MARQUET", "ALQUERIA DE LA MORERA", "ALQUERIA DE PIXA-RODA", "ALQUERIA DE ROCATÍ", "ALQUERIA DEL VOLANT", "ALGARÍN", "ALGEMESÍ", "ALGIRÓS", "ALMÀSSERA", "ÀNIMES", "ALMENAR", "ALMIRALL CADARSO", "ALMUDAINA", "ALQUERIA D'ALBA", "ALZIRA", "ALCOI", "ALEGRET", "ALESSANDRO VOLTA", "ALFAFAR", "ALFONS EL MAGNÀNIM", "ALBERIC", "ALBEROLA", "ALBORAIA", "ALBORAIA", "ALBUIXEC A MAUELLA", "ALCALDE REIG", "ALBENIZ", "ACTOR MORA", "ADOR", "AGUSTÍ SALES", "AIRE", "ALABAU", "ALADRERS", "ALBACETE", "ALBAIDA", "JUAN JOSÉ BARCIA GOYANES (PROFESSOR)", "MARGARITA SALAS (CIENTÍFICA)", "CRISTÓBAL VALLS LLORENS (METGE)", "CASES BAIXES", "SANTA PAULA MONTAL", "SANTA MARIA MAZZARELLO", "DIEGO SEVILLA (CATEDRÀTIC)", "ÁNGEL GUARDIA CORTÉS (FUNDADOR AV. BENIMACLET)", "DECIMO JUNIO BRUTO (CONSOL ROMA)", "HUGO ZARATE", "EUROPA", "PONT DE L'ASSUT DE L'OR", "MELCHOR HOYOS PÉREZ", "BARQUERA", "COCOTERS", "JOSE PASTOR MORENO (PRESIDENT TRIBUNAL AIGÜES)", "EP SECTOR FUENTE SAN LUIS A", "EP SAN VICENTE MÁRTIR 254", "HORTOLANES", "CASA DE LA DEMANÀ", "ACADÈMIC CASTAÑEDA", "CUIRASSAT", "E.P. PE. SAN JUAN DE DIOS", "E.P. PE. FRANCISCO FALCONS", "PARC PROL MOTILLA DEL PALANCAR", "EN PROYECTO JUNTO 346 AV CONSTITUCION", "E.P. PE. DUQUE DE MANDAS", "EN PROYECTO PROL. CARLOS CORTINA", "EN PROYECTO PERP.16 SALU CERVERO")
    var listaIDS = arrayListOf<String>()
    lateinit var listaConResultados : RecyclerView
    lateinit var auth : FirebaseAuth
    val database = Database()
    lateinit var inmuebles : ArrayList<DataInmueble>
    val db = Firebase.firestore

    lateinit var inmueblesEnPantalla : ArrayList<DataInmueble>
    lateinit var user : String
    val nuevaBusqueda = Busqueda()
    var alquilerActivado = false
    var ventaActivado = false
    val sugerencias = Busqueda()
    lateinit var cabecera : TextView
    lateinit var nResultados : TextView
    lateinit var colorDefaultText : ColorStateList
    //var sitios = arrayListOf<Sitio>()
    var filtrosAplicados : FiltrosBusqueda.filtros? = null

    object orden{
        var ordenSeleccionado : Int = 2
    }

    override fun onItemClicked(dataInmueble : DataInmueble) {
        val goFicha = Intent(this, AdaptadorFichaInmueble::class.java)
        goFicha.putExtra("user", user.toString())
        goFicha.putExtra("inmueble", Inmueble().adaptarInmuble(dataInmueble))
        goFicha.putExtra("desdeMapa",false)
        startActivity(goFicha)
    }
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_trobify2)
        user = (intent.extras!!.get("user") as String?).toString()
        filtrosAplicados = intent.extras!!.get("filtros") as FiltrosBusqueda.filtros?
        //Se obtienen resultados de la base de datos
        GlobalScope.launch(IO){
            var pisos = database.gimme().await().toObjects(DataInmueble::class.java)
            withContext(Dispatchers.Main){
                inmuebles = pisos as ArrayList<DataInmueble>
                if(filtrosAplicados != null){
                    //comprobacionDeFiltrosAplicados()
                    //aplicarFiltros()
                    println("${filtrosAplicados!!.tipoInmueble}")
                    println("${filtrosAplicados!!.tipoVivienda}")
                    println("${filtrosAplicados!!.precioMin}")
                    println("${filtrosAplicados!!.precioMax}")
                    prepararPrimerosResultados(inmuebles)
                }
                else prepararPrimerosResultados(inmuebles)
            }
        }
        //Se obtienen resultados de base de datos

        listaConResultados = findViewById(R.id.recycler)
        cabecera = findViewById(R.id.cabecera)
        nResultados = findViewById(R.id.nResultados)
        auth = Firebase.auth

        prepararBuscador()
        setListeners()


    }
    fun mostrarResultadosFiltros(inmuebles : ArrayList<DataInmueble>){
        inmueblesEnPantalla = inmuebles
        cabecera.text = "Inmuebles con filtros aplicados"
        listaConResultados.setHasFixedSize(true)
        val layoutmanager = LinearLayoutManager(baseContext)
        listaConResultados.layoutManager = layoutmanager
        listaConResultados.adapter = AdaptadorInmuebleBusqueda(inmuebles,this)
        nResultados.text = "${inmuebles.size} resultados"
    }


    fun prepararPrimerosResultados(inm : ArrayList<DataInmueble>){
        inmuebles = inm
        cabecera.text = "Inmuebles añadidos recientemente"
        listaConResultados.setHasFixedSize(true)
        val layoutmanager = LinearLayoutManager(baseContext)
        listaConResultados.layoutManager = layoutmanager
        listaConResultados.adapter = AdaptadorInmuebleBusqueda(inmuebles,this)
        nResultados.text = "${inmuebles.size} resultados"
        println("${listaConResultados.size} resultadoooooos")
    }
    fun prepararBuscador(){
        var buscador : SearchView = findViewById(R.id.buscarView)
        /*
        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val to = intArrayOf(R.id.item_label)
        var adaptadorCursor = SimpleCursorAdapter(this.baseContext,R.layout.sugerencia_item,
            null,from,to,CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER)
        buscador.suggestionsAdapter = adaptadorCursor*/
        buscador.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                mostrarResultados(query)
                Log.d("busc",query)
                return false
            }
            override fun onQueryTextChange(query : String?) : Boolean {
                //no le vamos a mostrar las suegerencias en el sprint 1
                /*if (query != null) {
                    sugerencias.obtenerSugerencias(query)
                    var cursor = MatrixCursor(arrayOf(BaseColumns._ID,SearchManager.SUGGEST_COLUMN_TEXT_1))
                    sugerencias.sugerencias.forEachIndexed{
                        indice, item ->
                        cursor.addRow(arrayOf(indice,item))
                    }
                    adaptadorCursor.changeCursor(cursor)
                }*/
                return false
            }
        })
    }
    fun setListeners() {
        var botonLateral : Button = findViewById(R.id.botonLateral)
        var menuLateral : DrawerLayout = findViewById(R.id.drawer_layout)
        var navigator : NavigationView = findViewById(R.id.navigator)
        var menuHeader = navigator.inflateHeaderView(R.layout.menu_desplegable_header)
        var nombreUser : TextView = menuHeader.findViewById(R.id.nombreUsuario)
        var cerrarMenu : Button = menuHeader.findViewById(R.id.volverAtras)
        var filtrar : TextView = findViewById(R.id.filtrar)
        var mapa : TextView = findViewById(R.id.verMapa)
        var cerrarSesion : Button = menuHeader.findViewById(R.id.cerrarSesion)
        var alquiler : TextView = findViewById(R.id.BAlquiler)
        var venta : TextView = findViewById(R.id.BVenta)
        colorDefaultText = venta.textColors
        var ordenar = findViewById<TextView>(R.id.ordenarPor)
        navigator.setNavigationItemSelectedListener { item ->
            when (item.getItemId()) {
                R.id.mischats -> {
                    val irAChats =
                        Intent(this@MainTrobify, ListOfChats::class.java)
                    irAChats.putExtra("user", user.toString())
                    startActivity(irAChats)
                    menuLateral.closeDrawers()
                }
                R.id.misfavoritos-> {
                    val irAMisFavoritos =
                        Intent(this@MainTrobify, ListaFavoritos::class.java)
                    irAMisFavoritos.putExtra("user", user.toString())
                    startActivity(irAMisFavoritos)
                    menuLateral.closeDrawers()
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
        db.collection("users").document(user.toString()).get()
            .addOnSuccessListener { e ->
                nombreUser.text = e.data?.get("name").toString()
            }
        //listener abrir menu lateral
        botonLateral.setOnClickListener {
            menuLateral.openDrawer(GravityCompat.START)
        }

        filtrar.setOnClickListener {
            val irAFiltrar = Intent(this, FiltrosBusqueda::class.java)
            irAFiltrar.putExtra("user", user)
            startActivity(irAFiltrar)
        }
        mapa.setOnClickListener {
            //abrir mapa
            val irAMapa = Intent(this, Mapa::class.java)
            irAMapa.putExtra("user", user)
            startActivity(irAMapa)
            //generatePisos()
        }
        alquiler.setOnClickListener {
            if(alquilerActivado)desactivarIntenciones(alquiler,venta)
            else activalAlquiler(alquiler, venta)
        }
        venta.setOnClickListener {
            if(ventaActivado)desactivarIntenciones(alquiler,venta)
            else activarVenta(alquiler, venta)
        }

        //verificarOrdenacion()
        val builder = AlertDialog.Builder(this)
        ordenar.setOnClickListener {
            builder.setItems(R.array.orderOptions) { _, which ->
                when {
                    which.equals(0) // Ordenar por precio ascendente
                    -> {
                        ordenar(0)
                        orden.ordenSeleccionado = 0
                    }
                    which.equals(1)// Ordenar por precio descendente
                    -> {
                        ordenar(1)
                        orden.ordenSeleccionado = 1
                    }
                    which.equals(2) // Ordenar por más recientes
                    -> {
                        ordenar(2)
                        orden.ordenSeleccionado = 2
                    }
                    which.equals(3) // Ordenar por más antiguos
                    -> {
                        ordenar(3)
                        orden.ordenSeleccionado = 3
                    }
                }
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
        prepararPrimerosResultados(inmuebles)
    }
    private fun activalAlquiler(alquiler : TextView, venta : TextView){
        alquilerActivado = true
        ventaActivado = false
        alquiler.setBackgroundColor(Color.BLUE)
        alquiler.setTextColor(Color.WHITE)
        venta.setTextColor(Color.BLACK)
        venta.setBackgroundColor(Color.DKGRAY)
        alquilerOVenta("Alquiler")
    }
    private fun activarVenta(alquiler : TextView, venta : TextView){
        alquilerActivado = false
        ventaActivado = true
        venta.setBackgroundColor(Color.BLUE)
        venta.setTextColor(Color.WHITE)
        alquiler.setTextColor(Color.BLACK)
        alquiler.setBackgroundColor(Color.DKGRAY)
        alquilerOVenta("Vender")
    }
    fun getInmueblesFromIds(listillo : Set<String>, myCallback : (DataInmueble) -> Unit){
        for (listo in listillo){
            db.collection("inmueblesv3").document(listo).get().addOnCompleteListener{ task ->
                if (task.isSuccessful){
                    var pis = task.result.toObject(DataInmueble::class.java)
                    if (pis != null) {
                        myCallback(pis)
                    }
                }
            }
        }
    }
    fun intencionActual():String{
        println("$alquilerActivado $ventaActivado")
        if(alquilerActivado)return "Alquiler"
        else return "Vender"
    }
    fun mostrarResultados(busqueda : String){

        if(alquilerActivado || ventaActivado) inmueblesEnPantalla = //firebase.getInmueblesIntecion()
            nuevaBusqueda.buscarConIntencion(busqueda.toUpperCase(),intencionActual())
        else inmueblesEnPantalla = nuevaBusqueda.buscar(busqueda.toUpperCase())
        listaConResultados.adapter = AdaptadorInmuebleBusqueda(inmueblesEnPantalla,this)
        nResultados.text = "${inmueblesEnPantalla.size} resultados"
        cabecera.text = "Inmuebles en " + busqueda
        /*nuevaBusqueda.obtenerResultados{
            inmueblesEnPantalla = it
            listaConResultados.adapter = AdaptadorInmuebleBusqueda(it,this)
            nResultados.text = "${it.size} resultados"
        }*/
    }
    fun alquilerOVenta(opcion : String){
        inmueblesEnPantalla = database.getInmueblesIntencion(opcion)

        //inmueblesEnPantalla = nuevaBusqueda.getInmueblesIntencion(opcion)

        listaConResultados.adapter = AdaptadorInmuebleBusqueda(inmueblesEnPantalla,this@MainTrobify)
        nResultados.text = "${inmueblesEnPantalla.size} resultados"

    }

    /*fun cargarInmueblesConFiltros(myCallback : (ArrayList<DataInmueble>) -> Unit) {
        var pisosTochos = arrayListOf<DataInmueble>()

        db.collection("inmueblesv2").whereEqualTo(
            "numHabitaciones",
            filtrosAplicados?.numHabitaciones
        )
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (ficha in task.result) {
                        var pisito = ficha.toObject(DataInmueble::class.java)
                        pisosTochos.add(pisito)
                    }
                    myCallback(pisosTochos)
                }
            }
    }*/

    //fun para generar id's de inmuebles aleatorios *** se podria meter en clase inmueble
    // no se comprueba que el id se repita con otro ya puesto
    /*fun generateRandomId() : String {
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
        return kotlin.random.Random.nextInt(40,180)
    }
    fun getRandomTipoVivienda() : String{
        return arrayListOf("Vivienda","Edificio","Oficina","Garaje",
            "Local","Terreno","Nave")[kotlin.random.Random.nextInt(0,6)]
    }
    fun getRandomCertificado() : String {
        return arrayListOf("A","B","C","D","E","F","G")[kotlin.random.Random.nextInt(0,6)]
    }
    fun getRandomPrice() : Int {
        return arrayListOf<Int>(0, 50000, 75000, 100000, 125000, 150000, 200000,
            300000, 400000, 500000)[kotlin.random.Random.nextInt(0,9)]
    }
    fun getRandomEstado() : String {
        return arrayListOf("Obra nueva","Casi nuevo","Muy bien","Bien","Reformado","A reformar")[kotlin.random.Random.nextInt(0,5)]
    }

    fun getRandomDate() : String {
        return LocalDateTime.now().minusDays(kotlin.random.Random.nextInt(0,20).toLong()).toString()
    }
    fun getRandomDireccion(): String {
        return calles[kotlin.random.Random.nextInt(0,calles.size)]
    }
    fun getPlaces(){
        runBlocking(Dispatchers.Default) {
            db.collection("testingPlacesv2").get().addOnCompleteListener {
                if(it.isSuccessful){
                    for(result in it.result){
                        var place = result.toObject(Sitio::class.java)
                        //sitios.add(place)
                    }}
            }}
    }
    fun getRandomIntencion():String{
        return arrayListOf("Alquiler","Vender")[kotlin.random.Random.nextInt(0,2)]
    }
    //fun para generar pisos en bd
    fun generatePisos() {
        for (venga in 1..10){
            subirInmueblesBD(DataInmueble(generateRandomId(),null,getRandom1a4(),
                getRandom1a4(),getRandomSuperficie(),Sitio(),getRandomTipoVivienda(),getRandomIntencion(),
                getRandomPrice(), arrayListOf(R.drawable.piso1, R.drawable.piso2, R.drawable.piso3,
                    R.drawable.piso4,R.drawable.piso5),
                arrayListOf("imagen1","imagen2", "imagen3", "imagen4", "imagen5"),
                getRandomCertificado(),"Bueno bonito y barato",getRandomEstado(), Random.nextBoolean(),Random.nextBoolean(),
                Random.nextBoolean(),Random.nextBoolean(),Random.nextBoolean(),Random.nextBoolean(),Random.nextBoolean(),Random.nextBoolean(),
                getRandomDate()))
        }
    }
    private fun subirInmueblesBD(inmueble : DataInmueble){
        inmueble.id?.let { db.collection("inmueblesv3").document(it).set(inmueble) }
    }*/

    fun ordenar(tipoSeleccionado:Int){
        lateinit var inmueblesOrdenados :List<DataInmueble>
        if(tipoSeleccionado.equals(0)){
            inmueblesOrdenados = inmueblesEnPantalla.sortedBy{it.precio}
            println("MENOR A MAYOR ******************************************************************************************")
            //println(h.toString())
            println(inmueblesEnPantalla.toString())
            cabecera.text = "Inmuebles ordenados ascendentemente"
        }
        else if(tipoSeleccionado.equals(1)){
            inmueblesOrdenados = inmueblesEnPantalla.sortedByDescending{it.precio}
            println("MAYOR A MENOR ******************************************************************************************")
            println(inmueblesEnPantalla.toString())
            cabecera.text = "Inmuebles ordenados descendentemente"
        }
        else if(tipoSeleccionado.equals(2)){
            inmueblesOrdenados = inmueblesEnPantalla.sortedByDescending{it.fechaSubida}
            println("RECIENTES ******************************************************************************************")
            println(inmueblesEnPantalla.toString())
            cabecera.text = "Inmuebles añadidos recientemente"
        }
        else if(tipoSeleccionado.equals(3)){
            inmueblesOrdenados = inmueblesEnPantalla.sortedBy{it.fechaSubida}
            println("ANTIGUOS ******************************************************************************************")
            println(inmueblesEnPantalla.toString())
            cabecera.text = "Inmuebles ordenados por antiguedad"
        }
        inmueblesEnPantalla = inmueblesOrdenados.toMutableList() as ArrayList<DataInmueble>
            println("¿MUESTRO LOS INMUEBLES? ******************************************************************************************")
            listaConResultados.adapter = AdaptadorInmuebleBusqueda(inmueblesEnPantalla,this)
            println("MOSTRANDO LISTA CON RESULTADOS ******************************************************************************************")
            println(listaConResultados.toString())
            nResultados.text = "${inmueblesEnPantalla.size} resultados"
    }

    fun verificarOrdenacion(){
        ordenar(GuardaOrdenacion.guardaOrdenacion.ordenGuardado)
    }

}
