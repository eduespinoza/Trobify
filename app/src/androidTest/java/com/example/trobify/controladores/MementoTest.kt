package com.example.trobify.controladores


import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions

import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest


import com.example.trobify.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MementoTest {

    //@Rule
    //@JvmField
    //var mActivityTestRule = ActivityTestRule(MainActivity::class.java)
    lateinit var scenario : ActivityScenario<MainActivity>

    @Test
    fun mementoTest() {
        var intent = Intent(ApplicationProvider.getApplicationContext(),MainActivity::class.java)
        scenario = ActivityScenario.launch(intent)
        /*val appCompatEditText = onView(
            allOf(
                withId(R.id.editTextLoginEmail),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        1
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("eduespifau@gmail.com"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.editTextLoginPassword),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        1
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(replaceText("qwerty12345"), closeSoftKeyboard())

        val materialButton = onView(
            allOf(
                withId(R.id.buttonAcces), withText("Acceder"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        2
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())*/

        intent = Intent(ApplicationProvider.getApplicationContext(),PantallaInicial::class.java)
            .putExtra("user","unaNawww1PcRQTSSFS9OwZPces52")
        scenario = ActivityScenario.launch(intent)

        val textView = onView(
            allOf(
                withId(R.id.titulo_pantalla_inicio), withText("TROBIFY"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView.perform(click())

        /*intent = Intent(ApplicationProvider.getApplicationContext(),MainTrobify::class.java)
            .putExtra("user","unaNawww1PcRQTSSFS9OwZPces52")
        scenario = ActivityScenario.launch(intent)*/

        val materialTextView = onView(
            allOf(
                withId(R.id.filtrar), withText("Filtrar"),
                childAtPosition(
                    allOf(
                        withId(R.id.opciones),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialTextView.perform(click())

        /*intent = Intent(ApplicationProvider.getApplicationContext(),FiltrosBusqueda::class.java)
            .putExtra("user","unaNawww1PcRQTSSFS9OwZPces52")
        scenario = ActivityScenario.launch(intent)*/

        val spinner = onView(
            allOf(
                withId(R.id.desplegableTipoInmueble),
                childAtPosition(
                    allOf(
                        withId(R.id.contenedorDentroScrollView),
                        childAtPosition(
                            withClassName(`is`("android.widget.ScrollView")),
                            0
                        )
                    ),
                    1
                )
            )
        )
        spinner.perform(scrollTo(), click())

        val appCompatCheckedTextView = onData(anything())
            .atPosition(1)
        appCompatCheckedTextView.perform(click())

        val button = onView(
            allOf(
                withId(R.id.buttonTipoVivienda), withText("Elegir tipo"),
                childAtPosition(
                    allOf(
                        withId(R.id.contenedorDentroScrollView),
                        childAtPosition(
                            withClassName(`is`("android.widget.ScrollView")),
                            0
                        )
                    ),
                    3
                )
            )
        )
        button.perform(scrollTo(), click())

        val checkedTextView = onData(anything())
            .inAdapterView(
                allOf(
                    withId(R.id.select_dialog_listview),
                    childAtPosition(
                        withId(R.id.contentPanel),
                        0
                    )
                )
            )
            .atPosition(0)
        checkedTextView.perform(click())

        val materialButton2 = onView(
            allOf(
                withId(android.R.id.button1), withText("Ok"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.buttonPanel),
                        0
                    ),
                    3
                )
            )
        )
        materialButton2.perform(scrollTo(), click())

        val spinner2 = onView(
            allOf(
                withId(R.id.desplegableNumHabitaciones),
                childAtPosition(
                    allOf(
                        withId(R.id.contenedorDentroScrollView),
                        childAtPosition(
                            withClassName(`is`("android.widget.ScrollView")),
                            0
                        )
                    ),
                    7
                )
            )
        )
        spinner2.perform(scrollTo(), click())

        val appCompatCheckedTextView2 = onData(anything())
            .atPosition(4)
        appCompatCheckedTextView2.perform(click())

        val spinner3 = onView(
            allOf(
                withId(R.id.desplegableNumBaños),
                childAtPosition(
                    allOf(
                        withId(R.id.contenedorDentroScrollView),
                        childAtPosition(
                            withClassName(`is`("android.widget.ScrollView")),
                            0
                        )
                    ),
                    9
                )
            )
        )
        spinner3.perform(scrollTo(), click())

        val appCompatCheckedTextView3 = onData(anything())
            .atPosition(2)
        appCompatCheckedTextView3.perform(click())

        val button2 = onView(
            allOf(
                withId(R.id.buttonAplicar), withText("Aplicar"),
                childAtPosition(
                    allOf(
                        withId(R.id.contenedor_botones),
                        childAtPosition(
                            withId(R.id.contenedorDentroScrollView),
                            16
                        )
                    ),
                    1
                )
            )
        )
        button2.perform(scrollTo(), click())

        val textView2 = onView(
            allOf(
                withId(R.id.nResultados), withText("3 resultados"),
                withParent(
                    allOf(
                        withId(R.id.linearLayout),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView2.check(ViewAssertions.matches(withText("3 resultados")))


        val materialTextView2 = onView(
            allOf(
                withId(R.id.filtrar), withText("Filtrar"),
                childAtPosition(
                    allOf(
                        withId(R.id.opciones),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialTextView2.perform(click())

        val textView3 = onView(
            allOf(
                withId(android.R.id.text1), withText("Vivienda"),
                withParent(
                    allOf(
                        withId(R.id.desplegableTipoInmueble),
                        withParent(withId(R.id.contenedorDentroScrollView))
                    )
                ),
                isDisplayed()
            )
        )
        textView3.check(ViewAssertions.matches(withText("Vivienda")))

        val textView4 = onView(
            allOf(
                withId(android.R.id.text1), withText("4"),
                withParent(
                    allOf(
                        withId(R.id.desplegableNumHabitaciones),
                        withParent(withId(R.id.contenedorDentroScrollView))
                    )
                ),
                isDisplayed()
            )
        )
        textView4.check(ViewAssertions.matches(withText("4")))


        val textView5 = onView(
            allOf(
                withId(android.R.id.text1), withText("2"),
                withParent(
                    allOf(
                        withId(R.id.desplegableNumBaños),
                        withParent(withId(R.id.contenedorDentroScrollView))
                    )
                ),
                isDisplayed()
            )
        )
        textView5.check(ViewAssertions.matches(withText("2")))

        val button3 = onView(
            allOf(
                withId(R.id.buttonTipoVivienda), withText("Elegir tipo"),
                childAtPosition(
                    allOf(
                        withId(R.id.contenedorDentroScrollView),
                        childAtPosition(
                            withClassName(`is`("android.widget.ScrollView")),
                            0
                        )
                    ),
                    3
                )
            )
        )
        button3.perform(scrollTo(), click())

        val checkedTextView2 = onView(
            allOf(
                withId(android.R.id.text1), withText("Apartamento"),
                withParent(
                    allOf(
                        withId(R.id.select_dialog_listview),
                        withParent(withId(R.id.contentPanel))
                    )
                ),
                isDisplayed()
            )
        )
        checkedTextView2.check(ViewAssertions.matches(isDisplayed()))

        val materialButton22 = onView(
            allOf(
                withId(android.R.id.button1), withText("Ok"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.buttonPanel),
                        0
                    ),
                    3
                )
            )
        )
        materialButton22.perform(scrollTo(), click())

        val button33 = onView(
            allOf(
                withId(R.id.buttonBorrarFiltros), withText("Borrar Filtros"),
                childAtPosition(
                    allOf(
                        withId(R.id.contenedor_botones),
                        childAtPosition(
                            withId(R.id.contenedorDentroScrollView),
                            16
                        )
                    ),
                    0
                )
            )
        )
        button33.perform(scrollTo(), click())

        val materialButton3 = onView(
            allOf(
                withId(android.R.id.button1), withText("Sí"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.buttonPanel),
                        0
                    ),
                    3
                )
            )
        )
        materialButton3.perform(scrollTo(), click())

        val textView6 = onView(
            allOf(
                withId(R.id.nResultados), withText("30 resultados"),
                withParent(
                    allOf(
                        withId(R.id.linearLayout),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView6.check(ViewAssertions.matches(withText("30 resultados")))

        val materialTextView3 = onView(
            allOf(
                withId(R.id.filtrar), withText("Filtrar"),
                childAtPosition(
                    allOf(
                        withId(R.id.opciones),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialTextView3.perform(click())
        val textView7 = onView(
            allOf(
                withId(android.R.id.text1), withText("Cualquiera"),
                withParent(
                    allOf(
                        withId(R.id.desplegableTipoInmueble),
                        withParent(withId(R.id.contenedorDentroScrollView))
                    )
                ),
                isDisplayed()
            )
        )
        textView7.check(ViewAssertions.matches(withText("Cualquiera")))

        val textView8 = onView(
            allOf(
                withId(android.R.id.text1), withText("0"),
                withParent(
                    allOf(
                        withId(R.id.desplegableNumHabitaciones),
                        withParent(withId(R.id.contenedorDentroScrollView))
                    )
                ),
                isDisplayed()
            )
        )
        textView8.check(ViewAssertions.matches(withText("0")))

        val textView9 = onView(
            allOf(
                withId(android.R.id.text1), withText("0"),
                withParent(
                    allOf(
                        withId(R.id.desplegableNumBaños),
                        withParent(withId(R.id.contenedorDentroScrollView))
                    )
                ),
                isDisplayed()
            )
        )
        textView9.check(ViewAssertions.matches(withText("0")))
    }

    private fun childAtPosition(
        parentMatcher : Matcher<View>, position : Int
    ) : Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description : Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view : View) : Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
