package com.example.trobify

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.trobify.controladores.FiltrosBusqueda
import com.example.trobify.models.Database
import com.example.trobify.models.FiltrosModelo
import com.example.trobify.models.GestionFiltros
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.EnumSet.allOf


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule var filtrosTest = ActivityScenarioRule<FiltrosBusqueda>(FiltrosBusqueda::class.java)

    @Before
    fun init(){
        Database
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.trobify", appContext.packageName)
    }

    @Test
    fun testing(){
        filtrosTest.scenario
        onView(withId(R.id.desplegableTipoInmueble)).perform(click())
        onView(withText("Vivienda")).perform(click())
        onView(withId(R.id.buttonTipoVivienda)).perform(click())
        onView(withText("Apartamento")).perform(click())

        /*var filtros1 = FiltrosModelo(
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
            extras = mutableMapOf(
                "Calefacción" to true,
                "Trastero" to true, "Terraza" to true
            ),
            estado = arrayListOf("Reformado", "Bien")
        )



        var snap1 = FiltrosBusqueda().createSnapshot(filtros1)
        var snap2 = FiltrosBusqueda().createSnapshot(filtros2)
        var snap3 = FiltrosBusqueda().createSnapshot(filtros3)

        snap1.restoreData()
        var result = GestionFiltros().aplicar(FiltrosBusqueda.filtros)
        result.forEach { inmueble ->
            assertEquals("Vivienda", inmueble.tipoInmueble)
            assertEquals("Apartamento", inmueble.tipoVivienda)
            assertEquals(4, inmueble.numHabitaciones)
            assertEquals(2, inmueble.numBanos)
        }

        snap2.restoreData()
        result = GestionFiltros().aplicar(FiltrosBusqueda.filtros)
        result.forEach { inmueble->
            assertTrue(inmueble.precio!! in 1000..3000)
            assertTrue(inmueble.superficie!! in 90..160)
        }

        snap3.restoreData()
        result = GestionFiltros().aplicar(FiltrosBusqueda.filtros)
        result.forEach { inmueble->
            val estados = arrayListOf("Reformado", "Bien")
            val extras = arrayListOf("Calefacción", "Terraza", "Trastero")
            assertTrue(estados.contains(inmueble.estado))
            assertTrue(inmueble.extras.containsAll(extras))
        }*/
    }
}