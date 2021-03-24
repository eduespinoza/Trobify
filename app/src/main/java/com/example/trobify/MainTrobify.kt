package com.example.trobify

import android.app.SearchManager
import android.content.Intent
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import kotlin.random.Random


class MainTrobify : AppCompatActivity(), AdaptadorInmuebleBusqueda.OnItemClickListener {
    //private lateinit var database: DatabaseReference
    var calles =  arrayListOf("RUSSAFA", "JOSÉ GROLLO", "JOSÉ MARIA FUSTER", "JOSE MARIA HARO (MAGISTRAT)", "BADAJOZ", "JACINT LABAILA", "JACOMART", "PERIODISTA JOSÉ OMBUENA", "JOAQUIM DUALDE",
            "JOAQUIM NAVARRO", "JOSEP AGUIRRE", "IFAC", "ESGLÉSIA DEL FORN D'ALCEDO", "IMPERTINÈNCIES", "ENGINYER AUBÁN", "ENGINYER DICENTA", "ENGINYER JOSÉ SIRERA", "ENGINYER MANUEL CÁNOVAS", "ILLA CABRERA", "JACA", "HISTORIADOR COLOMA", "HISTORIADOR DIAGO", "HÒMENS DE LA MAR", "HONORAT JUAN", "FORN DELS APÒSTOLS", "FORN DEL CABANYAL", "HORTICULTOR BOSCH", "HUMANISTA MARINER", "IBANYES", "GERMANS MACHADO", "GERMANS MARISTES", "GERMANS VILLALONGA", "FERRO", "FIGUEROLES DE DOMENYO", "GONZALO RAMIRO PEDRER", "GOYA", "GRAVADOR ESTEVE", "AGUSTÍ CENTELLES OSSÓ (FOTÒGRAF)", "GUILLEM DANGLESOLA", "GUITARRISTA TÀRREGA", "FARINA", "GIBRALTAR", "GEGANT", "GEPA", "GINER", "GLÒRIA", "GODELLA", "GODELLETA", "GOLES", "GONZÁLEZ MARTÍ", "GENERAL AVILÉS", "JERÓNIMA GALÉS (IMPRESSORA)", "BAIX DE MASSARROJOS", "GENERAL LLORENS", "FÉLIX DEL RÍO(ACTIVISTA VEÏNAL)", "GENERAL TOVAR", "POBLE", "AGRÓ", "GARRIGUES", "GARROFA", "GASCÓ OLIAG", "GASPAR TORRELLA", "GAUDÈNCIA TORRES", "GAVINES", "GENERAL", "GENERAL ALMIRANTE", "FRAULES", "FONT D'EN CORTS", "FONT D'EN CARRÒS", "FUENTERROBLES", "FONT DE SANT AGUSTÍ", "FURS", "GABINO", "GABRIEL Y GALÁN", "GALLINES", "FRANCESC EIXIMENIS", "FRANCESC MARTÍNEZ", "FRANCISCO MORENO USEDO", "SANT JACINT CASTANYEDA", "FRA JOAN MONTSÓ", "FRA J. RODRÍGUEZ", "FRA PERE VIVES", "FONTANARS DELS ALFORINS", "FORATA", "FORTALENY", "FRANCESC CLIMENT", "FRANCESC CUBELLS", "FERRAN EL CATÒLIC", "FERRER I BIGNÉ", "FIGUERETA", "FINESTRAT", "FINLÀNDIA", "FLORS", "FOLCH DE CARDONA", "ESPERANÇA", "ESPERÓ DE CANTA-RANES", "ESQUIF", "ESTAMENYERIA VELLA", "ESTEVE VICTÒRIA", "ESTRIBORD", "FELIPE SALVADOR", "FELIP VIVES DE CANYAMARS", "FENOLLOSA", "ERNEST FERRER", "ERUDIT ORELLANA", "PERE COMPTE , MESTRE EN EL NOBLE ART PEDRA", "ESCOLANO", "ESCRIVÀ", "ESCOLES DE MALILLA", "ESCULTOR AIXA", "ESCULTOR PIQUER", "ESPADÀ", "EN PLOM", "EN RODA", "EN SENDRA", "ENCARNACIÓ", "ENRIQUE OLMOS", "ENTENÇA", "ERMITA", "ERNEST ANASTASIO", "EMILI BARÓ", "EMILI MARÍ", "ENLLAÇ", "EMPERADOR", "EN BORRÀS", "EN GALL", "GUILLEM FERRER", "EN LLOPIS", "EN PINA", "EDUARD BOSCÀ", "PERE MARIA ORTS I BOSCH (HISTORIADOR)", "EXÈRCIT ESPANYOL", "BATXILLER", "RULLO", "SALZE", "ELCANO", "ELIES TORMO", "JOAN DE VILA-RASA", "DON QUIJOTE DE LA MANCHA", "DOS D'ABRIL", "DUC DE GAETA", "JUST RAMÍREZ (ARQUITECTE)", "EDETA", "DOCTOR JOSEP JUAN DÒMINE", "DOCTOR LANDETE", "DOCTOR MACHÍ", "DOCTOR MANUEL CANDELA", "DOCTOR MARAÑÓN", "DOCTOR MONSERRAT", "DOCTOR PESET CERVERA", "JUAN DE DIOS MONTAÑÉS", "PEDRAPIQUERS", "TRAGINERS", "DÉNIA", "DOCTOR ANDREU PIQUER", "DOCTOR BLAY", "DOCTOR CONSTANTÍ GÓMEZ", "DOCTOR GIL I MORTE", "MOSSÉN PALANCA", "XALET", "XELLA", "XERA", "XEST", "XIRIVELLA", "DAMA D'ELX", "DANSES", "DAVIDS", "NINOT", "CUBA", "CONCA", "CUENCA TRAMOYERES", "COVES CAROLINES", "MOSSÉN BAU", "COSTA I BORRÀS", "CRESPINS", "CRESPO", "CRESTA", "CREVILLENT", "CRISÒSTOM MARTÍNEZ", "CRONISTA TORRES", "CREU", "ESTACIONETA ", "CONVENT DE SANT FRANCESC", "COOPERATIVA DE SANT FERRAN", "COR DE JESÚS", "CORDELLATS", "CORONA", "CORREDORS", "COMTE D'ALTEA", "COMTE DE MELITO", "COMTE DE MONTORNÉS", "COMTE DEL REAL", "COMTE DE TORREFIEL", "CONVENT DELS CARMELITES", "CONVENT DE JESÚS", "CLERO", "COBERTÍS", "COFRENTS", "COIX CIURANA", "COLERO (POU APARICI 9)", "COLÓN", "COLL DE RATES", "COMUNIÓ DE SANT JOAN", "CONCHA ESPINA", "CEBRIAN MEZQUITA", "CEMENTERI", "SÉNIA", "PERE BORREGO I GALINDO (FALLER)", "CERAMISTA BAYARRI", "CERAMISTA GIMENO", "CERAMISTA ROS", "PROFESSOR DOCTOR SEVERO OCHOA", "CLARIANO", "CASTELL DE POP", "CASTELLÓ", "CASTIELFABIB", "CATALANS", "CATARROJA", "CAUDETE", "CAYUCO", "CASA DEL VINYERO", "CASA DE LA RASO", "CASA DE SANTAPAU", "CASA DE TOTÀ", "CASAS ALTAS", "CASES D'AMÀLIA", "CASAS BAJAS", "CASES DE BÀRCENA", "CASES DE BARRINTO", "CASES DE CARBONELL", "CASES DE GUERRA", "CASES DE MASENA", "DEMOCRÀCIA", "CAPELLETA", "CARPESA A BORBOTÓ", "CARPESA A MONTCADA", "FUSTERS", "CARTERS", "CARRASCA", "CARRERES PUCHALT", "CASA DEL CACHONDO", "CORONACIÓ", "CASA DE GORRITA", "CASA DE MOLINA", "CASA ÑORRO", "CASA DE PERICO", "CAMPAMENT", "CAMPANA", "CAMPORROBLES", "CANALETA", "CANONGE TÀRREGA", "CAÑETE", "CADIS", "CARABASSES", "CALAMOCHA", "CALA-XARXES", "CALVARI", "CALVO ACACIO", "CAMARENA", "BICORP", "BISBE", "BITÀCOLA", "BLANCO BALLESTER", "BLANQUERIA", "BOIX", "BOMBA", "CABANYAL", "CABILLERS", "BENIDOLEIG", "BENIFARAIG A MONTCADA", "BENIFAIÓ", "BENIMACLET", "BENIMODO", "BENIPEIXCAR", "BERENGUER MALLOL", "BERNABÉ GARCIA", "ALCÀSSER", "ALCÚDIA DE CRESPINS", "BENEFICÈNCIA", "BENET BOSCH", "BENICALAP", "BENICARLÓ", "CENTENAR DE LA PLOMA", "ELÍAS BORRÀS (POETA)", "ADREÇADORS", "AGUSTINA D'ARAGÓ", "ALABAU I ARCE", "ALBAL", "ALBENTOSA", "MARE DE DÉU DE LES INJÚRIES", "MARE DE DÉU DEL LEPANT", "MARE DE DÉU DE LA MISERICÒRDIA", "EDUARD SOLER I PÉREZ", "LLORERS", "ÁNGEL VILLENA", "PORTADORS DE LA VERGE", "METGE RAMÓN TARAZONA", "PUEBLA DE VALVERDE", "FRANCISCO COMES MARTINEZ (DRAMATURG)", "TRES D'ABRIL DE 1979", "JOAN AGUILÓ", "JOAN BAPTISTA COMES", "JOAN BAPTISTA LLOVERA", "JUAN BAUTISTA MARCO", "SEQUIOTA", "JUAN CALATRAVA", "JUAN CASTELLÓ", "JUAN DE CELAYA", "JUAN FABREGAT", "JOAQUIM BENLLOCH", "JOAQUIM COSTA", "VILAFERMOSA", "VILAMARXANT", "VINALOPÓ", "VINATEA", "JAUME L'OBRER", "JAUME ROIG", "XALANS", "XARAFULL", "OLIVERETA", "JERONI DE MONTSORIU", "JESÚS MORANTE BORRÁS", "JIJONA", "DOCTOR VILLENA", "DOCTOR WAKSMAN", "DOLORES ALCAIDE", "DOLORES MARQUÉS", "DOMÉNEC GÓMEZ", "ARMANDO PALACIO VALDÉS", "JAIME BELTRÁN", "CARAVACA", "PRESÓ DE SANT VICENT", "CARDENAL BENLLOCH", "CARITAT", "CARDENAL MONESCILLO", "DOCTOR SERRA", "DOCTOR SORIANO BENLLOCH", "DOCTOR VICENTE PALLARÉS", "BORRULL", "BOTÀNIC", "BRETÓN DE LOS HERREROS", "BUGARRA", "BUSOT", "DOCTOR RODRÍGUEZ DE LA FUENTE", "BEAT JUAN GRANDE", "BEATRIZ TORTOSA", "BETLEM", "BELLO", "BONAIRE", "BORBOTÓ", "BORBOTÓ A MASSARROJOS", "BRODADORS", "BORRASCA", "OEST", "BARÓ DE CORTES", "BARÓ DE PETRÉS", "BARÓ DE SAN PETRILLO", "BARRAQUES DE CASTELLÓ", "BARRAQUES DE LLUNA", "BATEL", "BEATA", "BALANDRA", "BALEARS", "BALER", "BALLESTERS", "BARCELLA", "BARÓ DE BARXETA", "AURORA", "AVE MARIA", "AIACOR", "ASSAGADOR", "ASSAGADOR D'ALBORAIA", "ASSAGADOR DE LES MONGES", "ASSAGADOR DEL MORRO", "ASSAGADOR DE LA TORRE", "AZCÁRRAGA", "ARQUITECTE RODRÍGUEZ", "ARQUITECTE TOLSÀ", "ARTÉS", "ARTS GRÀFIQUES", "ARQUEBISBE ALIAGA", "ARQUEBISBE OLAECHEA", "DALT", "ASIL D'INFANTS", "AROLAS", "ARQUITECTE ALFARO", "ARQUITECTE ARNAU", "ARQUITECTE CARBONELL", "ARQUITECTE JUAN PÉREZ", "ARQUITECTE LUCINI", "MALENA", "ARQUITECTE RIBES", "ANTONI SUÁREZ", "APARICIO ALBIÑANA", "APARISI I GUIJARRO", "ARAS DE LOS OLMOS", "ARBOREDA", "ARCHENA", "ARNAU DE VILANOVA", "EMPAR BALLESTER", "EMPAR GUILLEM", "ANDILLA", "ANDRÉS MANCEBO", "ANGELICOT", "ESTRET DE LA COMPANYIA", "ANGUILERA", "ANOUERS", "ANTONI LÁZARO", "ALQUERIES DE BELLVER", "DALT", "DALT DE LA MAR", "ALTAR DE SANT VICENT", "ÁLVAREZ", "ÁLVAREZ DE SOTOMAYOR", "AMÈRICA", "ALQUERIA D'ALBORS", "ALQUERIA D'ANTEQUERA", "ALQUERIA D'ASNAR", "ALQUERIA DE BENLLOCH", "ALQUERIA DE BURGOS", "ALQUERIA DE CANO", "ALQUERIA DE CASSANY", "ALQUERIA DE GILET", "ALQUERIA DE GINER", "ALQUERIA DELS FRARES", "ALQUERIA DE GINER", "ALQUERIA D'ISIDORO", "ALQUERIA DE MARQUET", "ALQUERIA DE LA MORERA", "ALQUERIA DE PIXA-RODA", "ALQUERIA DE ROCATÍ", "ALQUERIA DEL VOLANT", "ALGARÍN", "ALGEMESÍ", "ALGIRÓS", "ALMÀSSERA", "ÀNIMES", "ALMENAR", "ALMIRALL CADARSO", "ALMUDAINA", "ALQUERIA D'ALBA", "ALZIRA", "ALCOI", "ALEGRET", "ALESSANDRO VOLTA", "ALFAFAR", "ALFONS EL MAGNÀNIM", "ALBERIC", "ALBEROLA", "ALBORAIA", "ALBORAIA", "ALBUIXEC A MAUELLA", "ALCALDE REIG", "ALBENIZ", "ACTOR MORA", "ADOR", "AGUSTÍ SALES", "AIRE", "ALABAU", "ALADRERS", "ALBACETE", "ALBAIDA", "JUAN JOSÉ BARCIA GOYANES (PROFESSOR)", "MARGARITA SALAS (CIENTÍFICA)", "CRISTÓBAL VALLS LLORENS (METGE)", "CASES BAIXES", "SANTA PAULA MONTAL", "SANTA MARIA MAZZARELLO", "DIEGO SEVILLA (CATEDRÀTIC)", "ÁNGEL GUARDIA CORTÉS (FUNDADOR AV. BENIMACLET)", "DECIMO JUNIO BRUTO (CONSOL ROMA)", "HUGO ZARATE", "EUROPA", "PONT DE L'ASSUT DE L'OR", "MELCHOR HOYOS PÉREZ", "BARQUERA", "COCOTERS", "JOSE PASTOR MORENO (PRESIDENT TRIBUNAL AIGÜES)", "EP SECTOR FUENTE SAN LUIS A", "EP SAN VICENTE MÁRTIR 254", "HORTOLANES", "CASA DE LA DEMANÀ", "ACADÈMIC CASTAÑEDA", "CUIRASSAT", "E.P. PE. SAN JUAN DE DIOS", "E.P. PE. FRANCISCO FALCONS", "PARC PROL MOTILLA DEL PALANCAR", "EN PROYECTO JUNTO 346 AV CONSTITUCION", "E.P. PE. DUQUE DE MANDAS", "EN PROYECTO PROL. CARLOS CORTINA", "EN PROYECTO PERP.16 SALU CERVERO")

    var test = Inmueble("la playa", 700, 69, arrayOf(R.drawable.piso4, R.drawable.piso5))
    var test4 = Inmueble("la playa", 700, 69, arrayOf(R.drawable.piso4, R.drawable.piso5))
    var test5 = Inmueble("la playa", 700, 69, arrayOf(R.drawable.piso4, R.drawable.piso5))
    var test2 = Inmueble("el campo", 700, 69, arrayOf(R.drawable.piso5, R.drawable.piso5))
    var test3 = Inmueble("la montaña", 700, 69, arrayOf(R.drawable.piso4, R.drawable.piso5))

    lateinit var listaConResultados : RecyclerView
    val sugerencias = Busqueda()
    lateinit var cabecera : TextView

    override fun onItemClicked(dataInmueble : DataInmueble) {
        val goFicha = Intent(this, AdaptadorFichaInmueble::class.java)
        goFicha.putExtra("inmueble", Inmueble().adaptarInmuble(dataInmueble))
        startActivity(goFicha)
        Log.i("inmueble",dataInmueble.id.toString())
    }
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trobify_main)
        listaConResultados = findViewById(R.id.recycler)
        cabecera = findViewById(R.id.cabecera)
        actionBar?.hide()
        prepararPrimerosResultados()
        prepararBuscador()
        setListeners()
    }
    private val adaptadorInmueble by lazy {
        //AdaptadorInmuebleBusqueda()
    }

    fun prepararPrimerosResultados(){
        listaConResultados.setHasFixedSize(true)
        val layoutmanager = LinearLayoutManager(baseContext)
        listaConResultados.layoutManager = layoutmanager
        var data = cargarInmueblesDesdeBd {
                var adapter = AdaptadorInmuebleBusqueda(it,this)
                listaConResultados.adapter = adapter
        }
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
                cabecera.text = "Inmuebles en " + query
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
        var filtrar : TextView = findViewById(R.id.filtrar)
        var mapa : TextView = findViewById(R.id.verMapa)
        var navigator : NavigationView = findViewById(R.id.nav_view)
        //listener abrir menu lateral
        botonLateral.setOnClickListener {
            menuLateral.openDrawer(GravityCompat.START)
        }

        filtrar.setOnClickListener {
            val irAFiltrar = Intent(this, FiltrosBusqueda::class.java)
            startActivity(irAFiltrar)
        }
        mapa.setOnClickListener {
            //abrir mapa
            //generatePisos()
            Log.d("drawe","subiendo ..")
        }

        /*nav.setOnClickListener {
            Log.d("drawe","HOOOOLA")
        }*/
    }


    fun mostrarResultados(busqueda : String){
        var nuevaBusqueda = Busqueda()
        nuevaBusqueda.buscar(busqueda.toUpperCase())
        var data = nuevaBusqueda.obtenerResultados{
            listaConResultados.adapter = AdaptadorInmuebleBusqueda(it,this)
        }
    }

    //poner los 10 ultimos añadidos
    fun cargarInmueblesDesdeBd(myCallback : (ArrayList<DataInmueble>) -> Unit){
        val db = Firebase.firestore
        var pisosTochos = arrayListOf<DataInmueble>()
        db.collection("inmueblesv2").limit(10)
            .get().addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    for(ficha in task.result){
                    var pisito = ficha.toObject(DataInmueble::class.java)
                    pisosTochos.add(pisito)
                    }
                    myCallback(pisosTochos)
                }
            }
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
        return kotlin.random.Random.nextInt(1,4)
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

    //fun para generar pisos en bd
    fun generatePisos() {
        var calles = arrayListOf<String>("RUSSAFA", "JOSÉ GROLLO", "JOSÉ MARIA FUSTER", "JOSE MARIA HARO (MAGISTRAT)","BADAJOZ", "JACINT", "LABAILA", "JACOMART", "PERIODISTA JOSÉ OMBUENA", "JOAQUIM DUALDE", "JOAQUIM NAVARRO")
        for (venga in 1..100){
            subirInmueblesBD(DataInmueble(generateRandomId(),null,getRandom1a4(),
                getRandom1a4(),getRandomSuperficie(),getRandomDireccion(),getRandomTipoVivienda(),"Vender",
                getRandomPrice(), arrayListOf(R.drawable.piso1, R.drawable.piso2, R.drawable.piso3,
                    R.drawable.piso4,R.drawable.piso5),
                arrayListOf("imagen1","imagen2", "imagen3", "imagen4", "imagen5"),
                getRandomCertificado(),"Bueno bonito y barato",getRandomEstado(), Random.nextBoolean(),Random.nextBoolean(),
                Random.nextBoolean(),Random.nextBoolean(),Random.nextBoolean(),Random.nextBoolean(),Random.nextBoolean(),Random.nextBoolean(),
                getRandomDate()))
        }
    }
    private fun subirInmueblesBD(inmueble : DataInmueble){
        val database = Firebase.firestore
        inmueble.id?.let { database.collection("inmueblesv2").document(it).set(inmueble) }
    }

}
