package com.example.trobify.models


import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlin.collections.ArrayList
//patrón singleton -> solo instancia una sola vez, luego se puede usar
//en cualquier parte del proyecto, será la misma instancia
object Database {
    val db = Firebase.firestore
    var inmuebles = arrayListOf<DataInmueble2>()
    var users = arrayListOf<DataUser>()
    init{
        runBlocking {
            val queryInmuebles = async {
                db.collection("inmueblesv5").get()
            }
            val queryUsers = async {
                db.collection("users").get()
            }
            delay(7000)
            inmuebles = queryInmuebles.getCompleted().result.toObjects(DataInmueble2::class.java) as ArrayList<DataInmueble2>
            users = queryUsers.getCompleted().result.toObjects(DataUser::class.java) as ArrayList<DataUser>
        }
    }

    fun getAllInmuebles():ArrayList<DataInmueble2>{
        return inmuebles
    }

    // INMUEBLES QUERIES
    fun subirInmueble(inmueble : DataInmueble2){
        inmueble.id?.let { db.collection("inmueblesv5").document(it).set(inmueble) }
        inmuebles.add(inmueble)
        inmueble.propietario?.let { inmueble.id?.let { it1 -> setPisoUser(it, it1) } }
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
        println("ya tengo resultado maaaaanin")
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
    fun getInmueblesIntencion(opcion:String):ArrayList<DataInmueble2> {
        var inmueblesEncontrados = arrayListOf<DataInmueble2>( )
        inmuebles.forEach { inmueble ->
            if (inmueble.intencion.equals(opcion))
                inmueblesEncontrados.add(inmueble)
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
            if (inmueble.numBanos == habs)
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
    //USER QUERIES
    private fun setPisoUser(idUser : String, idInmueble : String){
        var user = getUserById(idUser)
        user.pisos?.add(idInmueble)
        db.collection("users").
        document(idUser).update("pisos",user.pisos)
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
