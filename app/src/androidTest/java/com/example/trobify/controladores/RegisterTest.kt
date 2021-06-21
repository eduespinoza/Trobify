package com.example.trobify.controladores

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.trobify.R
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class RegisterTest {
    lateinit var scenario : ActivityScenario<Register>

    lateinit var name : String
    lateinit var surname : String
    lateinit var email : String
    lateinit var password : String
    lateinit var comPassword : String

    @Test
    fun emptyField(){
        var intent = Intent(ApplicationProvider.getApplicationContext(),Register::class.java)
        scenario = ActivityScenario.launch(intent)

        this.name = "David"
        this.surname = "Garcia"
        this.email = "david@correo.es"
        this.password = ""
        this.comPassword =""

        onView(withId(R.id.editTextName)).perform(ViewActions.typeText(name))
        onView(withId(R.id.editTextSurname)).perform(ViewActions.typeText(surname))
        onView(withId(R.id.editTextEmail)).perform(ViewActions.typeText(email))
        onView(withId(R.id.editTextPassword)).perform(ViewActions.typeText(password))
        onView(withId(R.id.editTextPassword2)).perform(ViewActions.typeText(comPassword))
        onView(withId(R.id.buttonNext)).perform(ViewActions.click())

        onView(withText("Error: Rellene todos los campos"))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))

    }

    @Test
    fun shortPass(){
        var intent = Intent(ApplicationProvider.getApplicationContext(),Register::class.java)
        scenario = ActivityScenario.launch(intent)

        this.name = "David"
        this.surname = "Garcia"
        this.email = "david@correo.es"
        this.password = "12345"
        this.comPassword ="12345"

        onView(withId(R.id.editTextName)).perform(ViewActions.typeText(name))
        onView(withId(R.id.editTextSurname)).perform(ViewActions.typeText(surname))
        onView(withId(R.id.editTextEmail)).perform(ViewActions.typeText(email))
        onView(withId(R.id.editTextPassword)).perform(ViewActions.typeText(password))
        onView(withId(R.id.editTextPassword2)).perform(ViewActions.typeText(comPassword))

        onView(withId(R.id.buttonNext)).perform(ViewActions.click())

        onView(withText("Error:Contraseña demasiado corta"))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))

    }

    @Test
    fun differentPass(){
        var intent = Intent(ApplicationProvider.getApplicationContext(),Register::class.java)
        scenario = ActivityScenario.launch(intent)

        this.name = "David"
        this.surname = "Garcia"
        this.email = "david@correo.es"
        this.password = "12345678"
        this.comPassword ="abcdef"

        onView(withId(R.id.editTextName)).perform(ViewActions.typeText(name))
        onView(withId(R.id.editTextSurname)).perform(ViewActions.typeText(surname))
        onView(withId(R.id.editTextEmail)).perform(ViewActions.typeText(email))
        onView(withId(R.id.editTextPassword)).perform(ViewActions.typeText(password))
        onView(withId(R.id.editTextPassword2)).perform(ViewActions.typeText(comPassword))

        onView(withId(R.id.buttonNext)).perform(ViewActions.click())

        onView(withText("Error:Contraseñas no coinciden"))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
    }

    @Test
    fun emailInUse(){
        var intent = Intent(ApplicationProvider.getApplicationContext(),Register::class.java)
        scenario = ActivityScenario.launch(intent)

        this.name = "David"
        this.surname = "Garcia"
        this.email = "123@123.123"
        this.password = "12345678"
        this.comPassword ="12345678"

        onView(withId(R.id.editTextName)).perform(ViewActions.typeText(name))
        onView(withId(R.id.editTextSurname)).perform(ViewActions.typeText(surname))
        onView(withId(R.id.editTextEmail)).perform(ViewActions.typeText(email))
        onView(withId(R.id.editTextPassword)).perform(ViewActions.typeText(password))
        onView(withId(R.id.editTextPassword2)).perform(ViewActions.typeText(comPassword))

        onView(withId(R.id.buttonNext)).perform(ViewActions.click())

        onView(withText("Error: Correo ya registrado"))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))

    }

    @Test
    fun badEmail(){
        var intent = Intent(ApplicationProvider.getApplicationContext(),Register::class.java)
        scenario = ActivityScenario.launch(intent)

        this.name = "David"
        this.surname = "Garcia"
        this.email = "david-correo.es"
        this.password = "12345678"
        this.comPassword ="12345678"

        onView(withId(R.id.editTextName)).perform(ViewActions.typeText(name))
        onView(withId(R.id.editTextSurname)).perform(ViewActions.typeText(surname))
        onView(withId(R.id.editTextEmail)).perform(ViewActions.typeText(email))
        onView(withId(R.id.editTextPassword)).perform(ViewActions.typeText(password))
        onView(withId(R.id.editTextPassword2)).perform(ViewActions.typeText(comPassword))

        onView(withId(R.id.buttonNext)).perform(ViewActions.click())

        onView(withText("Error: Email no valido"))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
    }

    @Test
    fun badName(){
        var intent = Intent(ApplicationProvider.getApplicationContext(),Register::class.java)
        scenario = ActivityScenario.launch(intent)

        this.name = "Pepe-"
        this.surname = "Garcia"
        this.email = "david@correo.es"
        this.password = "12345678"
        this.comPassword ="12345678"

        onView(withId(R.id.editTextName)).perform(ViewActions.typeText(name))
        onView(withId(R.id.editTextSurname)).perform(ViewActions.typeText(surname))
        onView(withId(R.id.editTextEmail)).perform(ViewActions.typeText(email))
        onView(withId(R.id.editTextPassword)).perform(ViewActions.typeText(password))
        onView(withId(R.id.editTextPassword2)).perform(ViewActions.typeText(comPassword))

        onView(withId(R.id.buttonNext)).perform(ViewActions.click())

        onView(withText("Error: Nombre incorrecto"))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
    }

    @Test
    fun badSurname(){
        var intent = Intent(ApplicationProvider.getApplicationContext(),Register::class.java)
        scenario = ActivityScenario.launch(intent)

        this.name = "Pepe"
        this.surname = "Garcia-"
        this.email = "david@correo.es"
        this.password = "12345678"
        this.comPassword ="12345678"

        onView(withId(R.id.editTextName)).perform(ViewActions.typeText(name))
        onView(withId(R.id.editTextSurname)).perform(ViewActions.typeText(surname))
        onView(withId(R.id.editTextEmail)).perform(ViewActions.typeText(email))
        onView(withId(R.id.editTextPassword)).perform(ViewActions.typeText(password))
        onView(withId(R.id.editTextPassword2)).perform(ViewActions.typeText(comPassword))

        onView(withId(R.id.buttonNext)).perform(ViewActions.click())

        onView(withText("Error: Apellido incorrecto"))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
    }
}

