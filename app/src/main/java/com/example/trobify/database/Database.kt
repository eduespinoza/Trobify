package com.example.trobify.database


import androidx.lifecycle.*
import com.example.trobify.models.DataInmueble2
import com.example.trobify.models.DataUser
import com.here.sdk.core.GeoCoordinates
import kotlinx.coroutines.*
import kotlin.collections.ArrayList
//patrón singleton -> solo instancia una sola vez, luego se puede usar
//en cualquier parte del proyecto, será la misma instancia


object Database {
    //val db = Firebase.firestore
    var inmueblesNoPublicados = arrayListOf<DataInmueble2>()
    var inmuebles = arrayListOf<DataInmueble2>()
    var usuarios = arrayListOf<DataUser>()
    private val dbRepo = DatabaseRepo()
    init {
        GlobalScope.launch(Dispatchers.Main) {
        dbRepo.consultaInmuebles().observeForever( Observer {
            inmuebles = it as ArrayList<DataInmueble2>
        })
            dbRepo.consultaInmueblesNoPublicados().observeForever(Observer {
            inmueblesNoPublicados = it as ArrayList<DataInmueble2>
        })
            dbRepo.consultaUsuarios().observeForever( Observer {
            usuarios = it as ArrayList<DataUser>
        })
        }
    }

    fun toNoPublicado(inmueble : DataInmueble2){
        dbRepo.subirDocumento("inmueblesNoPost",inmueble.id!!,inmueble)
        inmueblesNoPublicados.add(inmueble)
        inmuebles.remove(inmueble)
        dbRepo.borrarDocumento("inmueblesFinal",inmueble.id!!)
        var user = obtenerUsuarioSegunId(inmueble.propietario!!)
        user.pisosNoPost?.add(inmueble.id!!)
        dbRepo.modificarDocumento("users",user.id!!,"pisosNoPost",user.pisosNoPost!!)
        //db.collection("users").
        //document(user.id!!).update("pisosNoPost",user.pisosNoPost)
        user.pisos?.remove(inmueble.id!!)
        dbRepo.modificarDocumento("users",user.id!!,"pisos",user.pisos!!)
        //db.collection("users").
        //document(user.id!!).update("pisos",user.pisos)
    }
    fun toPublicado(inmueble : DataInmueble2){
        dbRepo.subirDocumento("inmueblesFinal",inmueble.id!!,inmueble)
        //inmueble.id?.let { db.collection("inmueblesFinal").document(it).set(inmueble) }
        inmueblesNoPublicados.remove(inmueble)
        inmuebles.add(0,inmueble)
        dbRepo.borrarDocumento("inmueblesNoPost",inmueble.id!!)
        //db.collection("inmueblesNoPost").document(inmueble.id!!).delete()
        var user = obtenerUsuarioSegunId(inmueble.propietario!!)
        user.pisosNoPost?.remove(inmueble.id!!)
        dbRepo.modificarDocumento("users",user.id!!,"pisosNoPost",user.pisosNoPost!!)
        //db.collection("users").
        //document(user.id!!).update("pisosNoPost",user.pisosNoPost)
        user.pisos?.add(inmueble.id!!)
        dbRepo.modificarDocumento("users",user.id!!,"pisos",user.pisos!!)
        //db.collection("users").
        //document(user.id!!).update("pisos",user.pisos)
    }

    fun obtenerInmuebles():ArrayList<DataInmueble2>{
        return inmuebles
    }

    private fun obtenerInmuebleSegunId(id : String, post : Boolean): DataInmueble2 {
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
            inmueblesNoPublicados.forEach lit@{ inmueble ->
                if(inmueble.id.equals(id)){
                    inmuebleEncontrado = inmueble
                    return@lit
                }
            }
        }
        return inmuebleEncontrado
    }
    fun borrarInmueble(idInmueble : String, post : Boolean){
        var inmueble = obtenerInmuebleSegunId(idInmueble,post)
        if(post){
            dbRepo.borrarDocumento("inmueblesFinal",inmueble.id!!)
            //inmueble.id?.let { db.collection("inmueblesFinal").document(it).delete() }
            inmuebles.remove(inmueble)
            inmueble.propietario?.let { inmueble.id?.let { it1 -> eliminarPisoUsuario(it, it1) } }
        }else{
            dbRepo.borrarDocumento("inmueblesNoPost",inmueble.id!!)
            //inmueble.id?.let { db.collection("inmueblesNoPost").document(it).delete() }
            inmueblesNoPublicados.remove(inmueble)
            inmueble.propietario?.let { inmueble.id?.let { it1 -> eliminarPisoNoPublicadoUsuario(it, it1) } }
        }
    }
    fun modificarInmueble(inmueble : DataInmueble2, post: Boolean){
        if(post){
            //inmueble.id?.let { db.collection("inmueblesFinal").document(it).set(inmueble) }
            dbRepo.subirDocumento("inmueblesFinal",inmueble.id!!,inmueble)
        }else{
            //inmueble.id?.let { db.collection("inmueblesNoPost").document(it).set(inmueble) }
            dbRepo.subirDocumento("inmueblesNoPost",inmueble.id!!,inmueble)
        }
    }
    fun subirInmueble(inmueble : DataInmueble2, post: Boolean){
        if(post){
            dbRepo.subirDocumento("inmueblesFinal",inmueble.id!!,inmueble)
            //inmueble.id?.let { db.collection("inmueblesFinal").document(it).set(inmueble) }
            inmuebles.add(0,inmueble)
            inmueble.propietario?.let { inmueble.id?.let { it1 -> anadirPisoUsuario(it, it1) } }
        }else{
            dbRepo.subirDocumento("inmueblesNoPost",inmueble.id!!,inmueble)
            //inmueble.id?.let { db.collection("inmueblesNoPost").document(it).set(inmueble) }
            inmueblesNoPublicados.add(inmueble)
            inmueble.propietario?.let { inmueble.id?.let { it1 -> anadirPisoNoPublicadoUsuario(it, it1) } }
        }
    }
    fun estaPublicadoInmueble(id:String):Boolean{
        inmuebles.forEach { inmueble ->
            if (inmueble.id.equals(id)) return true
        }
        return false
    }
    fun obtenerInmueblesSegunBusqueda(query : String, intencion :String?):ArrayList<DataInmueble2>{
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
    fun obtenerInmueblesSegunIds(ids : ArrayList<String>):ArrayList<DataInmueble2>{
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
    fun obtenerInmueblesNoPublicadosSegunIds(ids : ArrayList<String>):ArrayList<DataInmueble2>{
        var inmueblesEncontrados = arrayListOf<DataInmueble2>()
        for (ident in ids) {
            inmueblesNoPublicados.forEach ok@{ inmueble->
                if (inmueble.id.equals(ident))
                    inmueblesEncontrados.add(inmueble)
                return@ok
            }
        }
        return inmueblesEncontrados
    }
    fun obtenerInmueblesSegunIntencion(opcion:String):ArrayList<DataInmueble2> {
        var inmueblesEncontrados = arrayListOf<DataInmueble2>( )
        inmuebles.forEach { inmueble ->
            if (inmueble.intencion.equals(opcion))
                inmueblesEncontrados.add(inmueble)
        }
        return inmueblesEncontrados
    }
    fun obtenerInmueblesSegunTipoVivienda(tipo : String):ArrayList<String>{
        var inmueblesEncontrados = arrayListOf<String>()
        inmuebles.forEach { inmueble ->
            if (inmueble.tipoVivienda.equals(tipo))
                inmueble.id?.let { inmueblesEncontrados.add(it) }
        }
        return inmueblesEncontrados
    }
    fun obtenerInmbueblesSegunTipoInmueble(tipo : String):ArrayList<String>{
        var inmueblesEncontrados = arrayListOf<String>()
        inmuebles.forEach { inmueble ->
            if (inmueble.tipoInmueble.equals(tipo))
                inmueble.id?.let { inmueblesEncontrados.add(it) }
        }
        return inmueblesEncontrados
    }
    fun obtenerInmueblesSegunPrecioMenorIgual(precio : Int):ArrayList<String>{
        var inmueblesEncontrados = arrayListOf<String>()
        inmuebles.forEach { inmueble ->
            if (inmueble.precio!! <= precio)
                inmueble.id?.let { inmueblesEncontrados.add(it) }
        }
        return inmueblesEncontrados
    }
    fun obtenerInmueblesSegunPrecioMayorIgual(precio : Int):ArrayList<String>{
        var inmueblesEncontrados = arrayListOf<String>()
        inmuebles.forEach { inmueble ->
            if (inmueble.precio!! >= precio)
                inmueble.id?.let { inmueblesEncontrados.add(it) }
        }
        return inmueblesEncontrados
    }
    fun obtenerInmueblesSegunSuperficieMax(sup : Int):ArrayList<String>{
        var inmueblesEncontrados = arrayListOf<String>()
        inmuebles.forEach { inmueble ->
            if (inmueble.superficie!! <= sup)
                inmueble.id?.let { inmueblesEncontrados.add(it) }
        }
        return inmueblesEncontrados
    }
    fun obtenerInmueblesSegunSuperficieMin(sup : Int):ArrayList<String>{
        var inmueblesEncontrados = arrayListOf<String>()
        inmuebles.forEach { inmueble ->
            if (inmueble.superficie!! >= sup)
                inmueble.id?.let { inmueblesEncontrados.add(it) }
        }
        return inmueblesEncontrados
    }
    fun obtenerInmueblesSegunBanos(baños:Int):ArrayList<String>{
        var inmueblesEncontrados = arrayListOf<String>()
        inmuebles.forEach { inmueble ->
            if (inmueble.numBanos == baños)
                inmueble.id?.let { inmueblesEncontrados.add(it) }
        }
        return inmueblesEncontrados
    }
    fun obtenerInmueblesSegunHabitaciones(habs:Int):ArrayList<String>{
        var inmueblesEncontrados = arrayListOf<String>()
        inmuebles.forEach { inmueble ->
            if (inmueble.numHabitaciones == habs)
                inmueble.id?.let { inmueblesEncontrados.add(it) }
        }
        return inmueblesEncontrados
    }
    fun obtenerInmueblesSegunEstado(estados:ArrayList<String>):ArrayList<String>{
        var inmueblesEncontrados = arrayListOf<String>()
        inmuebles.forEach { inmueble ->
            if(estados.contains(inmueble.estado)){
                inmueble.id?.let { inmueblesEncontrados.add(it) }
            }
        }
        return inmueblesEncontrados
    }
    fun obtenerInmueblesSegunExtras(extras:ArrayList<String>):ArrayList<String>{
        var inmueblesEncontrados = arrayListOf<String>()
        inmuebles.forEach { inmueble ->
            if(inmueble.extras!!.containsAll(extras)){
                inmueble.id?.let { inmueblesEncontrados.add(it)
            }
        }
        }
        return inmueblesEncontrados
    }
    fun obtenerInmueblesSegunOpciones(opciones:ArrayList<String>):ArrayList<String>{
        var size = 1
        var list = arrayListOf<String>()
        var result = arrayListOf<String>()
        var coord = GeoCoordinates(opciones[2].toDouble(),opciones[3].toDouble())
        list.addAll(obtenerInmueblesSegunIntencionIds(opciones[0]))
        if(!opciones[1].equals("Cualquiera")){
            size++
            list.addAll(obtenerInmbueblesSegunTipoInmueble(opciones[1]))
        }
        var cercanos = obtenerInmueblesCercanos(coord)
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
    fun obtenerInmueblesCercanos(geoCoordinates : GeoCoordinates):ArrayList<String>{
        var inmueblesEncontrados = arrayListOf<String>()
        inmuebles.forEach { inmueble ->
            var lat = inmueble.direccion?.coordenadas?.get("latitud")
            var long = inmueble.direccion?.coordenadas?.get("longitud")
            var coord = GeoCoordinates(lat!!,long!!)
            if(geoCoordinates.distanceTo(coord) <= 500.0){
                inmueblesEncontrados.add(inmueble.id!!)
            }
        }
        return inmueblesEncontrados
    }
    private fun obtenerInmueblesSegunIntencionIds(op:String):ArrayList<String>{
        var inmueblesEncontrados = arrayListOf<String>()
        inmuebles.forEach { inmueble ->
            if (inmueble.intencion.equals(op))
                inmueble.id?.let { inmueblesEncontrados.add(it) }
        }
        return inmueblesEncontrados
    }

    //USER QUERIES
    private fun eliminarPisoUsuario(idUser : String, idInmueble : String){
        var user = obtenerUsuarioSegunId(idUser)
        user.pisos?.remove(idInmueble)
        dbRepo.modificarDocumento("users",user.id!!,"pisos",user.pisos!!)
        //db.collection("users").
        //document(idUser).update("pisos",user.pisos)
    }
    private fun eliminarPisoNoPublicadoUsuario(idUser : String, idInmueble : String){
        var user = obtenerUsuarioSegunId(idUser)
        user.pisosNoPost?.remove(idInmueble)
        dbRepo.modificarDocumento("users",user.id!!,"pisosNoPost",user.pisosNoPost!!)
        //db.collection("users").
        //document(idUser).update("pisosNoPost",user.pisosNoPost)
    }
    private fun anadirPisoUsuario(idUser : String, idInmueble : String){
        var user = obtenerUsuarioSegunId(idUser)
        user.pisos?.add(idInmueble)
        dbRepo.modificarDocumento("users",user.id!!,"pisos",user.pisos!!)
        //db.collection("users").
        //document(idUser).update("pisos",user.pisos)
    }
    private fun anadirPisoNoPublicadoUsuario(idUser : String, idInmueble : String){
        var user = obtenerUsuarioSegunId(idUser)
        user.pisosNoPost?.add(idInmueble)
        dbRepo.modificarDocumento("users",user.id!!,"pisosNoPost",user.pisosNoPost!!)
        //db.collection("users").
        //document(idUser).update("pisosNoPost",user.pisosNoPost)
    }
    fun anadirFavoritoUsuario(idUser : String, idInmueble : String):Boolean{
        var user = obtenerUsuarioSegunId(idUser)
        if(!user.favorites?.contains(idInmueble)!!){
            user.favorites?.add(idInmueble)
            dbRepo.modificarDocumento("users",user.id!!,"favorites",user.favorites!!)
            //db.collection("users").
            //document(idUser).update("favorites",user.favorites)
            return true
        }
        return false
    }
    fun eliminarFavoritoUsuario(idUser:String, idInmueble : String):Boolean{
        var user = obtenerUsuarioSegunId(idUser)
        if(user.favorites?.contains(idInmueble)!!){
            user.favorites?.remove(idInmueble)
            dbRepo.modificarDocumento("users",user.id!!,"favorites",user.favorites!!)
            //db.collection("users").
            //document(idUser).update("favorites",user.favorites)
            return true
        }
        return false
    }
    fun subirUsuario(user : DataUser){
        dbRepo.subirDocumento("users",user.id!!,user)
        //user.id?.let { db.collection("users").document(it).set(user)}
        usuarios.add(user)
    }
    fun obtenerUsuario(id:String): DataUser {
        return  obtenerUsuarioSegunId(id)
    }
    private fun obtenerUsuarioSegunId(id : String) : DataUser {
        var userResult = DataUser()
        usuarios.forEach { user ->
            if(user.id.equals(id)){
                userResult = user
                return@forEach
            }
        }
        return userResult
    }
    fun obtenerPisosNoPublicadosUsuario(userId : String) : ArrayList<DataInmueble2>?{
        var user = obtenerUsuarioSegunId(userId)
        var pisos = user.pisosNoPost
        return pisos?.let { obtenerInmueblesNoPublicadosSegunIds(it) }
    }
    fun obtenerPisosUsuario(userId : String) : ArrayList<DataInmueble2>? {
        var user = obtenerUsuarioSegunId(userId)
        var pisos = user.pisos
        return pisos?.let { obtenerInmueblesSegunIds(it) }
    }
    fun obtenerFavoritosUsuario(userId : String) : ArrayList<DataInmueble2>? {
        val user = obtenerUsuarioSegunId(userId)
        return user.favorites?.let { obtenerInmueblesSegunIds(it) }
    }
    fun obtenerUsuariosEmails() : MutableList<String>{
        var result = mutableListOf<String>()
        usuarios.forEach{ user ->
            user.email?.let { result.add(it) }
        }
        return result
    }

}
