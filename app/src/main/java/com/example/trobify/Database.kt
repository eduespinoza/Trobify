package com.example.trobify

import android.provider.ContactsContract
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.collections.ArrayList

class Database {
    val db = Firebase.firestore
    var inmuebles = arrayListOf<DataInmueble>()
    var users = arrayListOf<DataUser>()

    init {
        getAllInmuebles(callback = object : FirebaseCallback {
            override fun <E> onCallback(cal : ArrayList<E>) {
                inmuebles = cal as ArrayList<DataInmueble>
            }
        })
        getAllUsers(callback = object : FirebaseCallback {
            override fun <E> onCallback(cal : ArrayList<E>) {
                users = cal as ArrayList<DataUser>
            }
        })

    }

    fun gimme() : Task<QuerySnapshot> {
        return db.collection("inmueblesv4").get()
    }

    interface FirebaseCallback{
        fun <E> onCallback(cal : ArrayList<E>)
    }

    private fun getAllInmuebles(callback : FirebaseCallback){
        db.collection("inmueblesv4")
            .get().addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    for(inmueble in task.result){
                        var pisito = inmueble.toObject(DataInmueble::class.java)
                        inmuebles.add(pisito)
                    }
                    callback.onCallback(inmuebles)
                }
            }
    }
    private fun getAllUsers(callback : FirebaseCallback){
        db.collection("users")
            .get().addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    for(user in task.result){
                        var usr = user.toObject(DataUser::class.java)
                        users.add(usr)
                    }
                    callback.onCallback(users)
                }
            }
    }
    // INMUEBLES QUERIES
    fun subirInmueble(inmueble : DataInmueble){
        inmueble.id?.let { db.collection("inmueblesv4").document(it).set(inmueble) }
        inmuebles.add(inmueble)
        inmueble.propietario?.let { inmueble.id?.let { it1 -> setPisoUser(it, it1) } }
    }
    fun getInmueblesByIds(ids : ArrayList<String>):ArrayList<DataInmueble>{
        var inmueblesEncontrados = arrayListOf<DataInmueble>()
        for (ident in ids) {
            inmuebles.forEach ok@{inmueble->
                if (inmueble.id.equals(ident))
                    inmueblesEncontrados.add(inmueble)
                return@ok
            }
        }
        return inmueblesEncontrados
    }
    fun getInmueblesIntencion(opcion:String):ArrayList<DataInmueble> {
        var inmueblesEncontrados = arrayListOf<DataInmueble>()
        inmuebles.forEach { inmueble ->
            if (inmueble.intencion.equals(opcion))
                inmueblesEncontrados.add(inmueble)
        }
        return inmueblesEncontrados
    }
    fun getInmueblesByTipoVivienda(tipo : String):ArrayList<String>{
        var inmueblesEncontrados = arrayListOf<String>()
        inmuebles.forEach { inmueble ->
            if (inmueble.tipoInmueble.equals(tipo))
                inmueble.id?.let { inmueblesEncontrados.add(it) }
        }
        return inmueblesEncontrados
    }
    fun getInmueblesByTipoInmueble(tipo : String):ArrayList<String>{
        var inmueblesEncontrados = arrayListOf<String>()
        inmuebles.forEach { inmueble ->
            if (inmueble.tipoVivienda.equals(tipo))
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

    //USER QUERIES
    private fun setPisoUser(idUser : String, idInmueble : String){
        var user = getUserById(idUser)
        user.pisos?.add(idInmueble)
        db.collection("users").
        document(idUser).update("pisos",user.pisos)
    }
    fun subirUsuario(user : DataUser){
        user.id?.let { db.collection("users").document(it).set(user)}
        users.add(user)
    }
    fun getUser(id:String):DataUser? = runBlocking{
        db.collection("users").document(id).get().await().toObject(DataUser::class.java)
    }
    fun getUserById(id : String) : DataUser {
        var userResult = DataUser()
        users.forEach { user ->
            if(user.id.equals(id)){
                userResult = user
                return@forEach
            }
        }
        return userResult
    }
    fun getPisosUser(userId : String) : ArrayList<DataInmueble>? {
        var user = getUserById(userId)
        var pisos = user.pisos
        return pisos?.let { getInmueblesByIds(it) }
    }
    fun getFavsUser(userId : String) : ArrayList<String>? {
        var user = getUserById(userId)
        return user.favorites
    }
    fun getAllUsersEmails() : MutableList<String>{
        var result = mutableListOf<String>()
        users.forEach{ user ->
            user.email?.let { result.add(it) }
        }
        return result
    }
}

















