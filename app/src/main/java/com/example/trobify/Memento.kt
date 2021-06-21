package com.example.trobify

import kotlin.collections.ArrayList

class Memento {

    var state = ArrayList<Any>()

    constructor()

    constructor(state : ArrayList<Any>){
        this.state = state
    }

    @JvmName("getState1")
    fun getState() : ArrayList<Any> {
        return state
    }
}