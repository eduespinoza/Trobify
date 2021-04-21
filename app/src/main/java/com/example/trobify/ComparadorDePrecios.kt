package com.example.trobify

import kotlinx.android.synthetic.main.inmueble_card_busqueda.view.*

class ComparadorDePrecios{

    fun  extraerPreciosInmublesPorZona(){
        // Bucle que recorra todos los inmuebles de una zona -----> "con caracteristicas similares ( m2, num baños y num habitaciones )" ---> para el sprint 3
        // y por cada inmuble guarde su precio en una variable llamada precioMedio
        // ademas a la vez se guarda el valor mas alto y mas bajo que aprezca.
        // Despues se divide el precioMedio entre el numero de inmubles y se obtiene el precio medio
        var precioMasAlto = 0
        var precioMasBajo = 0
        var precioMedio = 0
        var numInmubles = 0
        /*for(inmueble in ){
            if(precioMasAlto == 0){
                precioMasAlto = inmueble.precio
            }
            else if (precioMasAlto < inmueble.precio){
                precioMasAlto = inmueble.precio
            }

            if (precioMasBajo == 0){
                precioMasBajo = inmueble.precio
            }
            else if(precioMasBajo > inmueble.precio){
                precioMasBajo = inmueble.precio
            }
            precioMedio = precioMedio + inmueble.precio
            numInmubles = numInmubles + 1
        }*/
        precioMedio = precioMedio/numInmubles
    }
}