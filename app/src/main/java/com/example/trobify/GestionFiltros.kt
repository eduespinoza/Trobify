package com.example.trobify

class GestionFiltros {
    constructor(db : Database){

    }
    lateinit var database : Database
    val inmuebles = database.inmuebles
    lateinit var filtros : FiltrosBusqueda.filtros

    fun introducirFiltros(f : FiltrosBusqueda.filtros){
        filtros = f
    }
}