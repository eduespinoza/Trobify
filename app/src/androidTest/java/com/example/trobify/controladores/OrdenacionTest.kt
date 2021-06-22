package com.example.trobify.controladores
import com.example.trobify.controladores.MainTrobify
import com.example.trobify.controladores.PantallaInicial

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onData
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.trobify.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class OrdenacionTest {

    lateinit var scenario : ActivityScenario<MainTrobify>

    @Test
    fun ordenarPrecioAscendente() {
        val intent = Intent(
            ApplicationProvider.getApplicationContext(), PantallaInicial::class.java).putExtra("user", "unaNawww1PcRQTSSFS9OwZPces52")
        scenario = ActivityScenario.launch(intent)

        val textView = onView(
            Matchers.allOf(
                withId(R.id.titulo_pantalla_inicio),
                ViewMatchers.withText("TROBIFY"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                        0
                    ), 0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        textView.perform(ViewActions.click())

        val ordenar = onView(
            Matchers.allOf(
                withId(R.id.ordenarPor),
                childAtPosition(
                    Matchers.allOf(
                        withId(R.id.opciones),
                        childAtPosition(
                            ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                            0
                        )
                    ), 1
                )
            )
        )
        ordenar.perform(ViewActions.click())

        val precioAscendente = onData(Matchers.anything())
            .atPosition(0)
        precioAscendente.perform(ViewActions.click())

    }

    @Test
    fun ordenarPrecioDescendente() {
        val intent = Intent(
            ApplicationProvider.getApplicationContext(),
            PantallaInicial::class.java
        ).putExtra("user", "unaNawww1PcRQTSSFS9OwZPces52")
        scenario = ActivityScenario.launch(intent)

        val textView = onView(
            Matchers.allOf(
                withId(R.id.titulo_pantalla_inicio),
                ViewMatchers.withText("TROBIFY"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                        0
                    ), 0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        textView.perform(ViewActions.click())

        val ordenar = onView(
            Matchers.allOf(
                withId(R.id.ordenarPor),
                childAtPosition(
                    Matchers.allOf(
                        withId(R.id.opciones),
                        childAtPosition(
                            ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                            0
                        )
                    ), 1
                )
            )
        )
        ordenar.perform(ViewActions.click())

        val precioDescendente = onData(Matchers.anything())
            .atPosition(1)
        precioDescendente.perform(ViewActions.click())

    }

    @Test
    fun ordenarMasReciente() {
        val intent = Intent(
            ApplicationProvider.getApplicationContext(),
            PantallaInicial::class.java
        ).putExtra("user", "unaNawww1PcRQTSSFS9OwZPces52")
        scenario = ActivityScenario.launch(intent)

        val textView = onView(
            Matchers.allOf(
                withId(R.id.titulo_pantalla_inicio),
                ViewMatchers.withText("TROBIFY"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                        0
                    ), 0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        textView.perform(ViewActions.click())

        val ordenar = onView(
            Matchers.allOf(
                withId(R.id.ordenarPor),
                childAtPosition(
                    Matchers.allOf(
                        withId(R.id.opciones),
                        childAtPosition(
                            ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                            0
                        )
                    ), 1
                )
            )
        )
        ordenar.perform(ViewActions.click())

        val masRecientes = onData(Matchers.anything())
            .atPosition(2)
        masRecientes.perform(ViewActions.click())
    }

    @Test
    fun ordenarPorAntiguedad() {
        var intent = Intent(ApplicationProvider.getApplicationContext(),MainActivity::class.java)
        scenario = ActivityScenario.launch(intent)

        val count = CountDownLatch(1)
        count.await(4, TimeUnit.SECONDS)

        intent = Intent(ApplicationProvider.getApplicationContext(),PantallaInicial::class.java)
            .putExtra("user","unaNawww1PcRQTSSFS9OwZPces52")
        scenario = ActivityScenario.launch(intent)

        val textView = onView(Matchers.allOf(withId(R.id.titulo_pantalla_inicio), ViewMatchers.withText("TROBIFY"),
                childAtPosition(childAtPosition(ViewMatchers.withClassName(Matchers.`is`("androidx.constraintlayout.widget.ConstraintLayout")), 0), 0),
                ViewMatchers.isDisplayed()))
        textView.perform(ViewActions.click())

        val ordenar = onView(Matchers.allOf(
                withId(R.id.ordenarPor), childAtPosition(Matchers.allOf(
                        withId(R.id.opciones), childAtPosition(ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")), 0)), 1
                )))
        ordenar.perform(ViewActions.click())

        val masAntiguos = onData(Matchers.anything())
            .atPosition(3)
        masAntiguos.perform(ViewActions.click())

        onView(hasItemAtPosition(hasDescendant(withId(R.id.textViewPrecioFicha)), 0) as Matcher<View>?).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
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

    fun hasItemAtPosition(matcher: Matcher<View>, position: Int) : Any {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {

            override fun describeTo(description: Description?) {
                description?.appendText("has item at position $position : ")
                matcher.describeTo(description)
            }

            override fun matchesSafely(item: RecyclerView?): Boolean {
                val viewHolder = item?.findViewHolderForAdapterPosition(position)
                return matcher.matches(viewHolder?.itemView)
            }
        }
    }
}