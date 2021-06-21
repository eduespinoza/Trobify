package com.example.trobify

import com.example.trobify.controladores.MainTrobify
import com.example.trobify.models.DataInmueble2
import com.example.trobify.models.Inmueble
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import java.time.LocalDateTime

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UnitTest1 {

    val piso1 = Inmueble.Builder().id("piso1").precio(1000).extras(ArrayList()).fechaSubida(
        LocalDateTime.of(
        2020,
        5,
        10,
        10,
        0,
        0
    )).build().convertToDataInmueble()

    val piso2 = Inmueble.Builder().id("piso2").precio(2000).extras(ArrayList()).fechaSubida(
        LocalDateTime.of(
        2020,
        5,
        8,
        10,
        0,
        0
    )).build().convertToDataInmueble()

    val piso3 = Inmueble.Builder().id("piso3").precio(1000).extras(ArrayList()).fechaSubida(
        LocalDateTime.of(
        2020,
        5,
        15,
        10,
        0,
        0
    )).build().convertToDataInmueble()

    val piso4 = Inmueble.Builder().id("piso4").precio(500).extras(ArrayList()).fechaSubida(
        LocalDateTime.of(
        2020,
        5,
        8,
        7,
        0,
        0
    )).build().convertToDataInmueble()

    var inmuebles : ArrayList<DataInmueble2> = arrayListOf(piso1,piso2,piso3,piso4)
    var inmueblesOrdenados = arrayListOf<DataInmueble2>()

    @Test
    private fun ordenarPorPrecioAscendente(){
        MainTrobify.orden.ordenSeleccionado = 0
        inmueblesOrdenados = MainTrobify().ordenarInmuebles(inmuebles)
        println("-----------------------------------------------------------------------------------------------------------------------")
        inmueblesOrdenados.forEach{println(it.id.toString() + " " + it.precio.toString())}
        println("-----------------------------------------------------------------------------------------------------------------------")
    }
    @Test
    private fun ordenarPorPrecioDescendente(){
        MainTrobify.orden.ordenSeleccionado = 1
        inmueblesOrdenados = MainTrobify().ordenarInmuebles(inmuebles)
        println("-----------------------------------------------------------------------------------------------------------------------")
        inmueblesOrdenados.forEach{println(it.id.toString() + " " + it.precio.toString())}
        println("-----------------------------------------------------------------------------------------------------------------------")
    }
    @Test
    private fun ordenarPorFechaMasAntiguos(){
        MainTrobify.orden.ordenSeleccionado = 3
        inmueblesOrdenados = MainTrobify().ordenarInmuebles(inmuebles)
        println("-----------------------------------------------------------------------------------------------------------------------")
        inmueblesOrdenados.forEach{println(it.id.toString() + " " + it.fechaSubida.toString())}
        println("-----------------------------------------------------------------------------------------------------------------------")
    }
    @Test
    private fun ordenarPorFechaMasRecientes(){
        MainTrobify.orden.ordenSeleccionado = 2
        inmueblesOrdenados = MainTrobify().ordenarInmuebles(inmuebles)
        println("-----------------------------------------------------------------------------------------------------------------------")
        inmueblesOrdenados.forEach{println(it.id.toString() + " " + it.fechaSubida.toString())}
        println("-----------------------------------------------------------------------------------------------------------------------")
    }
}