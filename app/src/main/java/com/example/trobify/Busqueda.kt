package com.example.trobify

import android.provider.ContactsContract
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Busqueda {
    val db = Firebase.firestore
    var query : String = ""

    fun buscar(busqueda : String){
        this.query = busqueda
    }

    fun obtenerResultados(resultado : (ArrayList<DataInmueble>) -> Unit){
        var inmueblesEncontrados = arrayListOf<DataInmueble>()
        db.collection("inmuebles").
        whereEqualTo("direccion",this.query).get()
            .addOnCompleteListener{ task->
                 if(task.isSuccessful){
                     for(resultadoBusqueda in task.result){
                         var inmueble = resultadoBusqueda.toObject(DataInmueble::class.java)
                         inmueblesEncontrados.add(inmueble)
                     }
                     Log.d("QUERY","$inmueblesEncontrados")
                     resultado(inmueblesEncontrados)
                 }
            }
    }
}