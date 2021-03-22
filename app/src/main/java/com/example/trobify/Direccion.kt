package com.example.trobify

class Direccion {

    private var pais : String? = null
    private var ciudad : String? = null
    private var codigoPostal : String? = null
    private var calle : String? = null
    private var numeroPortal : String? = null
    private var planta : String? = null
    private var numeroPuerta : String? = null

    constructor(
        pais : String?,
        ciudad : String?,
        codigoPostal : String?,
        calle : String?,
        numeroPortal : String?,
        planta : String?,
        numeroPuerta : String?
    ) {
        this.pais = pais
        this.ciudad = ciudad
        this.codigoPostal = codigoPostal
        this.calle = calle
        this.numeroPortal = numeroPortal
        this.planta = planta
        this.numeroPuerta = numeroPuerta
    }

    fun direccionToString() : String{
        return calle + ", numero " + numeroPortal + ", planta " + planta + ", puerta" + numeroPuerta
    }

}