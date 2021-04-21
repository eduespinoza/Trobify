package com.example.trobify.models

import com.example.trobify.AdaptadorFichaInmueble

data class Chat(
    var id: String = "",
    var name: String = "",
    var users: List<String> = emptyList(),
    var inmuebleId : String = ""
)