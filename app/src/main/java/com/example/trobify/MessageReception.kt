package com.example.trobify

class MessageReception : Message {
    var hora : Long? = null

    constructor() {}
    constructor(hora : Long?) {
        this.hora = hora
    }

    constructor(
        mensaje : String?,
        urlFoto : String?,
        nombre : String?,
        fotoPerfil : String?,
        type_mensaje : String?,
        hora : Long?
    ) : super(mensaje, urlFoto, nombre, fotoPerfil, type_mensaje) {
        this.hora = hora
    }

}