package com.example.trobify.models

import java.io.Serializable

data class  Sitio (var titulo : String? = null,
                var coordenadas : MutableMap<String,Double>? = null,
                var id : String? = null) : Serializable