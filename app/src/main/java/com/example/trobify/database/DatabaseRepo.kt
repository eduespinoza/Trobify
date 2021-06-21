package com.example.trobify.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.trobify.models.DataInmueble2
import com.example.trobify.models.DataUser
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DatabaseRepo {

    val db = Firebase.firestore

    fun consultaInmuebles(): LiveData<MutableList<DataInmueble2>> {
        val mutableData = MutableLiveData<MutableList<DataInmueble2>>()
        db.collection("inmueblesFinal")
            .orderBy("fechaSubida", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                mutableData.value = result.toObjects(DataInmueble2::class.java) as ArrayList<DataInmueble2>
            }
        return mutableData
    }
    fun consultaInmueblesNoPublicados(): LiveData<MutableList<DataInmueble2>> {
        val mutableData = MutableLiveData<MutableList<DataInmueble2>>()
        db.collection("inmueblesNoPost")
            .orderBy("fechaSubida", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                mutableData.value = result.toObjects(DataInmueble2::class.java) as ArrayList<DataInmueble2>
            }
        return mutableData
    }
    fun consultaUsuarios(): LiveData<MutableList<DataUser>> {
        val mutableData = MutableLiveData<MutableList<DataUser>>()
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                mutableData.value = result.toObjects(DataUser::class.java) as ArrayList<DataUser>
            }
        return mutableData
    }

    fun subirDocumento(ruta:String, id:String, valor:Any){
        db.collection(ruta).document(id).set(valor)
    }

    fun borrarDocumento(ruta:String, id:String){
        db.collection(ruta).document(id).delete()
    }

    fun modificarDocumento(ruta:String, id:String, campo : String, valor : Any){
        db.collection(ruta).document(id).update(campo,valor)
    }



}