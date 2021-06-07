package com.example.trobify

import android.os.Bundle
import com.example.trobify.controladores.MainTrobify
import com.example.trobify.models.DataInmueble2
import com.example.trobify.models.Inmueble
import java.time.LocalDateTime

class OrdenacionTest {

    val piso1 = Inmueble.Builder().id("piso1").precio(1000).extras(ArrayList()).fechaSubida(LocalDateTime.of(
        2020,
        5,
        10,
        10,
        0,
        0
    )).build().convertToDataInmueble()

    val piso2 = Inmueble.Builder().id("piso2").precio(2000).extras(ArrayList()).fechaSubida(LocalDateTime.of(
        2020,
        5,
        8,
        10,
        0,
        0
    )).build().convertToDataInmueble()

    val piso3 = Inmueble.Builder().id("piso3").precio(1000).extras(ArrayList()).fechaSubida(LocalDateTime.of(
        2020,
        5,
        15,
        10,
        0,
        0
    )).build().convertToDataInmueble()

    val piso4 = Inmueble.Builder().id("piso4").precio(500).extras(ArrayList()).fechaSubida(LocalDateTime.of(
        2020,
        5,
        8,
        7,
        0,
        0
    )).build().convertToDataInmueble()

    var inmuebles : ArrayList<DataInmueble2> = arrayListOf(piso1,piso2,piso3,piso4)
    var inmueblesOrdenados = arrayListOf<DataInmueble2>()

    private fun ordenarPorPrecioAscendente(){
        MainTrobify.orden.ordenSeleccionado = 0
        inmueblesOrdenados = MainTrobify().ordenarInmuebles(inmuebles)
        println("-----------------------------------------------------------------------------------------------------------------------")
        inmueblesOrdenados.forEach{println(it.id.toString() + " " + it.precio.toString())}
        println("-----------------------------------------------------------------------------------------------------------------------")
    }

    private fun ordenarPorPrecioDescendente(){
        MainTrobify.orden.ordenSeleccionado = 1
        inmueblesOrdenados = MainTrobify().ordenarInmuebles(inmuebles)
        println("-----------------------------------------------------------------------------------------------------------------------")
        inmueblesOrdenados.forEach{println(it.id.toString() + " " + it.precio.toString())}
        println("-----------------------------------------------------------------------------------------------------------------------")
    }

    private fun ordenarPorFechaMasAntiguos(){
        MainTrobify.orden.ordenSeleccionado = 2
        inmueblesOrdenados = MainTrobify().ordenarInmuebles(inmuebles)
        println("-----------------------------------------------------------------------------------------------------------------------")
        inmueblesOrdenados.forEach{println(it.id.toString() + " " + it.fechaSubida.toString())}
        println("-----------------------------------------------------------------------------------------------------------------------")
    }

    private fun ordenarPorFechaMasRecientes(){
        MainTrobify.orden.ordenSeleccionado = 3
        inmueblesOrdenados = MainTrobify().ordenarInmuebles(inmuebles)
        println("-----------------------------------------------------------------------------------------------------------------------")
        inmueblesOrdenados.forEach{println(it.id.toString() + " " + it.fechaSubida.toString())}
        println("-----------------------------------------------------------------------------------------------------------------------")
    }

    fun pruebas(){
        //ordenarPorPrecioAscendente()
        //ordenarPorPrecioDescendente()
        //ordenarPorFechaMasAntiguos()
        ordenarPorFechaMasRecientes()
    }
}