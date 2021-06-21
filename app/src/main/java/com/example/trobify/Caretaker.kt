package com.example.trobify

import com.example.trobify.controladores.FiltrosBusqueda
import java.util.*
import kotlin.collections.ArrayList

class Caretaker {

    lateinit var arrayDeMementos : MutableList<Memento>

    fun saveMemento(){
        arrayDeMementos.add(FiltrosBusqueda().createMemento())
    }

    fun restoreMemento(){
        if(arrayDeMementos.count() < 2){
            FiltrosBusqueda().restoreMemento(arrayDeMementos[0])
        }
        else{
            FiltrosBusqueda().restoreMemento(arrayDeMementos[1])
        }
    }
}