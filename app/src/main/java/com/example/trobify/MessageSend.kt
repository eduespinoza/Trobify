package com.example.trobify

class MessageSend : Message {
    var hora : Map<*, *>? = null

    constructor() {}
    constructor(hora : Map<*, *>?) {
        this.hora = hora
    }

    constructor(
        mensaje : String?,
        nombre : String?,
        fotoPerfil : String?,
        type_mensaje : String?,
        hora : Map<*, *>?
    ) : super(mensaje, nombre, fotoPerfil, type_mensaje) { this.hora = hora }

    constructor(
        mensaje : String?,
        urlFoto : String?,
        nombre : String?,
        fotoPerfil : String?,
        type_mensaje : String?,
        hora : Map<*, *>?
    ) : super(mensaje, urlFoto, nombre, fotoPerfil, type_mensaje) { this.hora = hora }

}
