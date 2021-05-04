package com.example.trobify

import android.provider.ContactsContract
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class Database {
    val db = Firebase.firestore
    var observador = false
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
        return db.collection("inmueblesv3").get()
    }

    interface FirebaseCallback{
        fun <E> onCallback(cal : ArrayList<E>)
    }

    private fun getAllInmuebles(callback : FirebaseCallback){
        db.collection("inmueblesv3")
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
    fun getInmueblesIntencion(opcion:String):ArrayList<DataInmueble> {
        var inmueblesEncontrados = arrayListOf<DataInmueble>()
        inmuebles.forEach { inmueble ->
            if (inmueble.intencion.equals(opcion)) inmueblesEncontrados.add(inmueble)
        }
        return inmueblesEncontrados
    }

    fun getInmueblesByTipoInmueble(tipo : String):ArrayList<DataInmueble>{
        var inmueblesEncontrados = arrayListOf<DataInmueble>()
        inmuebles.forEach { inmueble ->
            if (inmueble.tipoInmueble.equals(tipo))
                inmueblesEncontrados.add(inmueble)
        }
        return inmueblesEncontrados
    }
    fun getInmueblesByPrecioMenorIgual(precio : Int):ArrayList<DataInmueble>{
        var inmueblesEncontrados = arrayListOf<DataInmueble>()
        inmuebles.forEach { inmueble ->
            if (inmueble.precio!! <= precio)
                inmueblesEncontrados.add(inmueble)
        }
        return inmueblesEncontrados
    }

    fun getInmueblesByPrecioMayorIgual(precio : Int):ArrayList<DataInmueble>{
        var inmueblesEncontrados = arrayListOf<DataInmueble>()
        inmuebles.forEach { inmueble ->
            if (inmueble.precio!! >= precio)
                inmueblesEncontrados.add(inmueble)
        }
        return inmueblesEncontrados
    }

    //USER QUERIES
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
}