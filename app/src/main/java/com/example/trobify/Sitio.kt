package com.example.trobify

data class Sitio(var titulo : String? = null,
                var coordenadas : MutableMap<String,Double>? = null,
                var id : String? = null)