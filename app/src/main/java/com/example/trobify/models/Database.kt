package com.example.trobify.models


import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.here.sdk.core.GeoCoordinates
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import kotlin.collections.ArrayList
//patrón singleton -> solo instancia una sola vez, luego se puede usar
//en cualquier parte del proyecto, será la misma instancia
object Database {
    val db = Firebase.firestore
    var inmueblesNoPost = arrayListOf<DataInmueble2>()
    var inmuebles = arrayListOf<DataInmueble2>()
    var users = arrayListOf<DataUser>()
    init{
        runBlocking {
            val queryNoPost = async {
                db.collection("inmueblesNoPost").orderBy("fechaSubida",Query.Direction.DESCENDING).get()
            }
            val queryInmuebles = async {
                db.collection("inmueblesFinal").orderBy("fechaSubida",Query.Direction.DESCENDING).get()
            }
            val queryUsers = async {
                db.collection("users").get()
            }
            //tiempo para que llegue la respuesta a la consulta asíncrona
            delay(11000)
            inmueblesNoPost = queryNoPost.getCompleted().result.toObjects(DataInmueble2::class.java) as ArrayList<DataInmueble2>
            inmuebles = queryInmuebles.getCompleted().result.toObjects(DataInmueble2::class.java) as ArrayList<DataInmueble2>
            users = queryUsers.getCompleted().result.toObjects(DataUser::class.java) as ArrayList<DataUser>
        }
    }

    fun toNoPublicado(inmueble : DataInmueble2){
        inmueble.id?.let { db.collection("inmueblesNoPost").document(it).set(inmueble) }
        inmueblesNoPost.add(inmueble)
        inmuebles.remove(inmueble)
        db.collection("inmueblesFinal").document(inmueble.id!!).delete()
        var user = getUserById(inmueble.propietario!!)
        user.pisosNoPost?.add(inmueble.id!!)
        db.collection("users").
        document(user.id!!).update("pisosNoPost",user.pisosNoPost)
        user.pisos?.remove(inmueble.id!!)
        db.collection("users").
        document(user.id!!).update("pisos",user.pisos)
    }
    fun toPublicado(inmueble : DataInmueble2){
        inmueble.id?.let { db.collection("inmueblesFinal").document(it).set(inmueble) }
        inmueblesNoPost.remove(inmueble)
        inmuebles.add(0,inmueble)
        db.collection("inmueblesNoPost").document(inmueble.id!!).delete()
        var user = getUserById(inmueble.propietario!!)
        user.pisosNoPost?.remove(inmueble.id!!)
        db.collection("users").
        document(user.id!!).update("pisosNoPost",user.pisosNoPost)
        user.pisos?.add(inmueble.id!!)
        db.collection("users").
        document(user.id!!).update("pisos",user.pisos)
    }

    fun getAllInmuebles():ArrayList<DataInmueble2>{
        return inmuebles
    }

    private fun getInmuebleById(id : String,post : Boolean):DataInmueble2{
        var inmuebleEncontrado = DataInmueble2()
        if(post){
            inmuebles.forEach lit@{ inmueble ->
                if(inmueble.id.equals(id)) {
                    inmuebleEncontrado = inmueble
                    return@lit
                }

            }
        }
        else{
            inmueblesNoPost.forEach lit@{ inmueble ->
                if(inmueble.id.equals(id)){
                    inmuebleEncontrado = inmueble
                    return@lit
                }
            }
        }
        return inmuebleEncontrado
    }
    fun borrarInmueble(idInmueble : String, post : Boolean){
        var inmueble = getInmuebleById(idInmueble,post)
        if(post){
            inmueble.id?.let { db.collection("inmueblesFinal").document(it).delete() }
            inmuebles.remove(inmueble)
            inmueble.propietario?.let { inmueble.id?.let { it1 -> removePisoUser(it, it1) } }
        }else{
            inmueble.id?.let { db.collection("inmueblesNoPost").document(it).delete() }
            inmueblesNoPost.remove(inmueble)
            inmueble.propietario?.let { inmueble.id?.let { it1 -> removePisoNoPostUser(it, it1) } }
        }
    }
    fun modificarInmueble(inmueble : DataInmueble2, post: Boolean){
        if(post){
            inmueble.id?.let { db.collection("inmueblesFinal").document(it).set(inmueble) }

        }else{
            inmueble.id?.let { db.collection("inmueblesNoPost").document(it).set(inmueble) }

        }
    }
    fun subirInmueble(inmueble : DataInmueble2, post: Boolean){
        if(post){
            inmueble.id?.let { db.collection("inmueblesFinal").document(it).set(inmueble) }
            inmuebles.add(0,inmueble)
            inmueble.propietario?.let { inmueble.id?.let { it1 -> setPisoUser(it, it1) } }
        }else{
            inmueble.id?.let { db.collection("inmueblesNoPost").document(it).set(inmueble) }
            inmueblesNoPost.add(inmueble)
            inmueble.propietario?.let { inmueble.id?.let { it1 -> setPisoNoPostUser(it, it1) } }
        }
    }
    fun isInmueblePost(id:String):Boolean{
        inmuebles.forEach { inmueble ->
            if (inmueble.id.equals(id)) return true
        }
        return false
    }
    fun getInmueblesBusqueda(query : String, intencion :String?):ArrayList<DataInmueble2>{
        println("dentro de buscador")
        println(inmuebles.size)
        var resultado = arrayListOf<DataInmueble2>()
        if(intencion != null){
            inmuebles.forEach {
                if(it.direccion?.titulo?.toUpperCase()?.contains(query)!!
                    && it.intencion.equals(intencion))resultado.add(it)
            }
        }
        else{
            inmuebles.forEach {
                if(it.direccion?.titulo?.toUpperCase()?.contains(query)!!)
                    resultado.add(it)
            }
        }
        return resultado
    }

    fun getInmueblesByIds(ids : ArrayList<String>):ArrayList<DataInmueble2>{
        var inmueblesEncontrados = arrayListOf<DataInmueble2>()
        for (ident in ids) {
            inmuebles.forEach ok@{ inmueble->
                if (inmueble.id.equals(ident))
                    inmueblesEncontrados.add(inmueble)
                return@ok
            }
        }
        return inmueblesEncontrados
    }
    fun getInmueblesByIdsNoPost(ids : ArrayList<String>):ArrayList<DataInmueble2>{
        var inmueblesEncontrados = arrayListOf<DataInmueble2>()
        for (ident in ids) {
            inmueblesNoPost.forEach ok@{ inmueble->
                if (inmueble.id.equals(ident))
                    inmueblesEncontrados.add(inmueble)
                return@ok
            }
        }
        return inmueblesEncontrados
    }
    fun getInmueblesIntencion(opcion:String):ArrayList<DataInmueble2> {
        var inmueblesEncontrados = arrayListOf<DataInmueble2>( )
        inmuebles.forEach { inmueble ->
            if (inmueble.intencion.equals(opcion))
                inmueblesEncontrados.add(inmueble)
        }
        return inmueblesEncontrados
    }
    private fun getInmueblesByIntecion(op:String):ArrayList<String>{
        var inmueblesEncontrados = arrayListOf<String>()
        inmuebles.forEach { inmueble ->
            if (inmueble.intencion.equals(op))
                inmueble.id?.let { inmueblesEncontrados.add(it) }
        }
        return inmueblesEncontrados
    }
    fun getInmueblesByTipoVivienda(tipo : String):ArrayList<String>{
        var inmueblesEncontrados = arrayListOf<String>()
        inmuebles.forEach { inmueble ->
            if (inmueble.tipoVivienda.equals(tipo))
                inmueble.id?.let { inmueblesEncontrados.add(it) }
        }
        return inmueblesEncontrados
    }
    fun getInmueblesByTipoInmueble(tipo : String):ArrayList<String>{
        var inmueblesEncontrados = arrayListOf<String>()
        inmuebles.forEach { inmueble ->
            if (inmueble.tipoInmueble.equals(tipo))
                inmueble.id?.let { inmueblesEncontrados.add(it) }
        }
        return inmueblesEncontrados
    }
    fun getInmueblesByPrecioMenorIgual(precio : Int):ArrayList<String>{
        var inmueblesEncontrados = arrayListOf<String>()
        inmuebles.forEach { inmueble ->
            if (inmueble.precio!! <= precio)
                inmueble.id?.let { inmueblesEncontrados.add(it) }
        }
        return inmueblesEncontrados
    }

    fun getInmueblesByPrecioMayorIgual(precio : Int):ArrayList<String>{
        var inmueblesEncontrados = arrayListOf<String>()
        inmuebles.forEach { inmueble ->
            if (inmueble.precio!! >= precio)
                inmueble.id?.let { inmueblesEncontrados.add(it) }
        }
        return inmueblesEncontrados
    }
    fun getInmueblesBySuperficieMax(sup : Int):ArrayList<String>{
        var inmueblesEncontrados = arrayListOf<String>()
        inmuebles.forEach { inmueble ->
            if (inmueble.superficie!! <= sup)
                inmueble.id?.let { inmueblesEncontrados.add(it) }
        }
        return inmueblesEncontrados
    }
    fun getInmueblesBySuperficieMin(sup : Int):ArrayList<String>{
        var inmueblesEncontrados = arrayListOf<String>()
        inmuebles.forEach { inmueble ->
            if (inmueble.superficie!! >= sup)
                inmueble.id?.let { inmueblesEncontrados.add(it) }
        }
        return inmueblesEncontrados
    }
    fun getInmueblesByBaños(baños:Int):ArrayList<String>{
        var inmueblesEncontrados = arrayListOf<String>()
        inmuebles.forEach { inmueble ->
            if (inmueble.numBanos == baños)
                inmueble.id?.let { inmueblesEncontrados.add(it) }
        }
        return inmueblesEncontrados
    }
    fun getInmueblesByHabs(habs:Int):ArrayList<String>{
        var inmueblesEncontrados = arrayListOf<String>()
        inmuebles.forEach { inmueble ->
            if (inmueble.numHabitaciones == habs)
                inmueble.id?.let { inmueblesEncontrados.add(it) }
        }
        return inmueblesEncontrados
    }
    fun getInmueblesByEstado(estados:ArrayList<String>):ArrayList<String>{
        var inmueblesEncontrados = arrayListOf<String>()
        inmuebles.forEach { inmueble ->
            if(estados.contains(inmueble.estado)){
                inmueble.id?.let { inmueblesEncontrados.add(it) }
            }
        }
        return inmueblesEncontrados
    }
    fun getInmueblesByExtras(extras:ArrayList<String>):ArrayList<String>{
        var inmueblesEncontrados = arrayListOf<String>()
        inmuebles.forEach { inmueble ->
            if(inmueble.extras.containsAll(extras)){
                inmueble.id?.let { inmueblesEncontrados.add(it)
            }
        }
        }
        return inmueblesEncontrados
    }
    fun getInmueblesByOpciones(opciones:ArrayList<String>):ArrayList<String>{
        var size = 1
        var list = arrayListOf<String>()
        var result = arrayListOf<String>()
        var coord = GeoCoordinates(opciones[2].toDouble(),opciones[3].toDouble())
        list.addAll(getInmueblesByIntecion(opciones[0]))
        if(!opciones[1].equals("Cualquiera")){
            size++
            list.addAll(getInmueblesByTipoInmueble(opciones[1]))
        }
        var cercanos = getInmueblesCercanos(coord)
        if(cercanos.isNotEmpty()){
            println("hay cercanos ni de coña maninininin")
            size++
            list.addAll(cercanos)
        }
        else {return arrayListOf()}
        list.groupBy{it}.forEach{mapa ->
            if(mapa.value.size == size)
                result.add(mapa.key)
        }
        if(result.isNotEmpty()){return result}
        return arrayListOf()
    }
    fun getInmueblesCercanos(geoCoordinates : GeoCoordinates):ArrayList<String>{
        var inmueblesEncontrados = arrayListOf<String>()
        inmuebles.forEach { inmueble ->
            var lat = inmueble.direccion?.coordenadas?.get("latitud")
            var long = inmueble.direccion?.coordenadas?.get("longitud")
            var coord = GeoCoordinates(lat!!,long!!)
            if(geoCoordinates.distanceTo(coord) <= 500.0){
                println("${geoCoordinates.distanceTo(coord)} DISTAAAANCIA")
                inmueblesEncontrados.add(inmueble.id!!)
            }
        }
        println("cercanos maninn $inmueblesEncontrados")
        println(inmueblesEncontrados.isEmpty())
        println(inmueblesEncontrados.isNotEmpty())
        return inmueblesEncontrados
    }
    //USER QUERIES
    private fun removePisoUser(idUser : String, idInmueble : String){
        var user = getUserById(idUser)
        user.pisos?.remove(idInmueble)
        db.collection("users").
        document(idUser).update("pisos",user.pisos)
    }
    private fun removePisoNoPostUser(idUser : String, idInmueble : String){
        var user = getUserById(idUser)
        user.pisosNoPost?.remove(idInmueble)
        db.collection("users").
        document(idUser).update("pisosNoPost",user.pisosNoPost)
    }
    private fun setPisoUser(idUser : String, idInmueble : String){
        var user = getUserById(idUser)
        user.pisos?.add(idInmueble)
        db.collection("users").
        document(idUser).update("pisos",user.pisos)
    }
    private fun setPisoNoPostUser(idUser : String, idInmueble : String){
        var user = getUserById(idUser)
        user.pisosNoPost?.add(idInmueble)
        db.collection("users").
        document(idUser).update("pisosNoPost",user.pisosNoPost)
    }
    fun setFav2User(idUser : String, idInmueble : String):Boolean{
        var user = getUserById(idUser)
        if(!user.favorites?.contains(idInmueble)!!){
            user.favorites?.add(idInmueble)
            db.collection("users").
            document(idUser).update("favorites",user.favorites)
            return true
        }
        return false
    }
    fun removeFav2User(idUser:String, idInmueble : String):Boolean{
        var user = getUserById(idUser)
        if(user.favorites?.contains(idInmueble)!!){
            user.favorites?.remove(idInmueble)
            db.collection("users").
            document(idUser).update("favorites",user.favorites)
            return true
        }
        return false
    }
    fun subirUsuario(user : DataUser){
        user.id?.let { db.collection("users").document(it).set(user)}
        users.add(user)
    }
    fun getUser(id:String): DataUser {
        return  getUserById(id)
    }
    private fun getUserById(id : String) : DataUser {
        var userResult = DataUser()
        users.forEach { user ->
            if(user.id.equals(id)){
                userResult = user
                return@forEach
            }
        }
        return userResult
    }
    fun getPisosNoPostUser(userId : String) : ArrayList<DataInmueble2>?{
        var user = getUserById(userId)
        var pisos = user.pisosNoPost
        return pisos?.let { getInmueblesByIdsNoPost(it) }
    }
    fun getPisosUser(userId : String) : ArrayList<DataInmueble2>? {
        var user = getUserById(userId)
        var pisos = user.pisos
        return pisos?.let { getInmueblesByIds(it) }
    }
    fun getFavsUser(userId : String) : ArrayList<DataInmueble2>? {
        val user = getUserById(userId)
        return user.favorites?.let { getInmueblesByIds(it) }
    }
    fun getAllUsersEmails() : MutableList<String>{
        var result = mutableListOf<String>()
        users.forEach{ user ->
            user.email?.let { result.add(it) }
        }
        return result
    }
}
