package com.example.trobify.models

import com.example.trobify.controladores.MainTrobify

open class GuardaOrdenacion : MainTrobify() {
    object guardaOrdenacion{
        var ordenGuardado = orden.ordenSeleccionado
    }
}