package com.example.trobify

import com.example.trobify.controladores.FiltrosBusqueda
import com.example.trobify.models.FiltrosModelo
import com.example.trobify.models.GestionFiltros

class GestionFiltrosTest {
    var filtros1 = FiltrosModelo(
        tipoInmueble = "Vivienda",
        tipoVivienda = arrayListOf("Apartamento"),
        numHabitaciones = 4,
        numBaños = 2
    )
    var filtros2 = FiltrosModelo(
        precioMin = 1000,
        precioMax = 3000,
        superficieMin = 90,
        superficieMax = 160
    )
    var filtros3 = FiltrosModelo(
        extras = mutableMapOf("Calefacción" to true,
            "Trastero" to true, "Terraza" to true),
        estado = arrayListOf("Reformado","Bien")
    )

    var snap1 = FiltrosBusqueda().createSnapshot(filtros1)
    var snap2 = FiltrosBusqueda().createSnapshot(filtros2)
    var snap3 = FiltrosBusqueda().createSnapshot(filtros3)



    fun tests(){
        println("RESULTS FILTROS1 : ")
        snap1.restoreData()
        var result = GestionFiltros().aplicar(FiltrosBusqueda.filtros)
        println("${result.size} RESULTS")
        result.forEach {inmueble->
            assertEquals("Vivienda",inmueble.tipoInmueble)
            assertEquals("Apartamento",inmueble.tipoVivienda)
            assertEquals(4,inmueble.numHabitaciones)
            assertEquals(2,inmueble.numBanos)
        }

        println("RESULTS FILTROS2 : ")

        snap2.restoreData()
        result = GestionFiltros().aplicar(FiltrosBusqueda.filtros)
        println("${result.size} RESULTS")
        result.forEach {inmueble->
            assertBetween(1000,3000, inmueble.precio!!)
            assertBetween(90,160, inmueble.superficie!!)
        }

        println("RESULTS FILTROS3 : ")

        snap3.restoreData()
        result = GestionFiltros().aplicar(FiltrosBusqueda.filtros)
        println("${result.size} RESULTS")
        result.forEach {inmueble->
            val estados = arrayListOf("Reformado","Bien")
            val extras = arrayListOf("Calefacción","Terraza","Trastero")
            assertBetween(estados,inmueble.estado)
            assertBetween(extras,inmueble.extras)
        }

        println("END OF TEST")
    }

    private fun assertBetween(min : Int, max : Int, out : Int) {
        val correct = (out >= min) && (out <= max)
        println("EXPECTED VALUE IN: [$min,$max] AND ACTUAL $out RESULT : $correct")
    }
    private fun assertBetween(expected : ArrayList<String>, actual : String?){
        println("EXPECTED VALUE IN: $expected AND ACTUAL $actual RESULT : ${expected.contains(actual)}")
    }
    private fun assertBetween(expected : ArrayList<String>, actual : ArrayList<String>){
        println("EXPECTED VALUE IN: $expected AND ACTUAL $actual RESULT : ${actual.containsAll(expected)}")
    }
    private fun assertEquals(expected : Boolean, actual : Boolean) {
        println("EXPECTED : $expected AND ACTUAL $actual RESULT : ${expected.equals(actual)}")
    }
    private fun assertEquals(expected : String, actual : String?) {
        println("EXPECTED : $expected AND ACTUAL $actual RESULT : ${expected.equals(actual)}")
    }
    private fun assertEquals(expected : Int, actual : Int?) {
        println("EXPECTED : $expected AND ACTUAL $actual RESULT : ${expected.equals(actual)}")
    }
}