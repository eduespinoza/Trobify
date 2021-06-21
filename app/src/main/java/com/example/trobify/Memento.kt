package com.example.trobify

import com.example.trobify.controladores.FiltrosBusqueda
import kotlin.collections.ArrayList

class Memento {

    var state = ArrayList<Any>()

    constructor()

    constructor(state : ArrayList<Any>){
        this.state = state
    }
}