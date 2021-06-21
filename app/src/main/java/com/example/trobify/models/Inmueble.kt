package com.example.trobify.models

import java.io.Serializable
import java.time.LocalDateTime
import kotlin.collections.ArrayList


class Inmueble (
    var id : String?,
    var propietario : String? ,
    var numHabitaciones : Int?,
    var numBanos : Int? ,
    var superficie : Int? ,
    var direccion : String? ,
    var direccionO : Direccion? ,
    var tipoVivienda : String? ,
    var tipoInmueble : String?,
    var intencion : String? ,
    var precio : Int? ,

    var fotos : ArrayList<Int> = arrayListOf(),
    var fotosOrd : ArrayList<String> = arrayListOf(),
    var descripcion : String? ,

    var fechaSubida : LocalDateTime?,
    var estado : String?,
    var extras : ArrayList<String>?,
    var parking : Boolean?,
    var ascensor : Boolean?,
    var amueblado : Boolean?,
    var calefaccion : Boolean?,
    var jardin : Boolean?,
    var piscina : Boolean?,
    var terraza : Boolean?,
    var trastero : Boolean?,

    var caracteristicas : String?,
    var direccionSitio : Sitio?


) : Serializable {
    private constructor(builder: Builder) : this(
        builder.id, builder.propietario, builder.numHabitaciones, builder.numBanos, builder.superficie,
        builder.direccion, builder.direccionO, builder.tipoVivienda, builder.tipoInmueble,
        builder.intencion, builder.precio, builder.fotos, builder.fotosOrd,
        builder.descripcion, builder.fechaSubida, builder.estado,builder.extras, builder.parking,
        builder.ascensor, builder.amueblado, builder.calefaccion, builder.jardin, builder.piscina,
        builder.terraza, builder.trastero, builder.caracteristicas, builder.direccionSitio
    )

    fun convertToDataInmueble() : DataInmueble2{
        return DataInmueble2(id = id, propietario = propietario, numHabitaciones = numHabitaciones,
            numBanos = numBanos, superficie = superficie, direccion = direccionSitio,
            tipoVivienda = tipoVivienda, tipoInmueble = tipoInmueble, intencion = intencion,
            precio = precio, fotos = fotosOrd, descripcion = descripcion,  extras = extras!!,
            estado = estado, fechaSubida = fechaSubida.toString())
    }

    class Builder{
        var id : String? = null
            private set
        fun id(id : String?) = apply { this.id = id }

        var propietario : String? = null
            private set
        fun propietario(propietario : String?) = apply { this.propietario = propietario }

        var numHabitaciones : Int? = null
            private set
        fun numHabitaciones(numHabitaciones : Int?) = apply { this.numHabitaciones = numHabitaciones }

        var numBanos : Int? = null
            private set
        fun numBanos(numBanos : Int?) = apply { this.numBanos = numBanos }

        var superficie : Int? = null
            private set
        fun superficie(superficie : Int?) = apply { this.superficie = superficie }

        var direccion : String? = null
            private set
        fun direccion(direccion : String?) = apply { this.direccion = direccion }

        var direccionO : Direccion? = null
            private set
        fun direccionO(direccionO : Direccion?) = apply { this.direccionO = direccionO }

        var tipoVivienda : String? = null
            private set
        fun tipoVivienda(tipoVivienda : String?) = apply { this.tipoVivienda = tipoVivienda }

        var tipoInmueble : String? = null
            private set
        fun tipoInmueble(tipoInmueble : String?) = apply { this.tipoInmueble = tipoInmueble }

        var intencion : String? = null
            private set
        fun intencion(intencion : String?) = apply { this.intencion = intencion }

        var precio : Int? = null
            private set
        fun precio(precio : Int?) = apply { this.precio = precio }

        var fotos : ArrayList<Int> = arrayListOf()
            private set
        fun fotos(fotos : ArrayList<Int>) = apply { this.fotos = fotos }

        var fotosOrd : ArrayList<String> = arrayListOf()
            private set
        fun fotosOrd(fotosOrd : ArrayList<String>) = apply { this.fotosOrd = fotosOrd }

        var descripcion : String? = null
            private set
        fun descripcion(descripcion : String?) = apply { this.descripcion = descripcion }

        var fechaSubida : LocalDateTime? = null
            private set
        fun fechaSubida(fechaSubida : LocalDateTime?) = apply { this.fechaSubida = fechaSubida }

        var estado : String? = null
            private set
        fun estado(estado : String?) = apply { this.estado = estado }

        var extras : ArrayList<String>? = null
            private set
        fun extras(extras : ArrayList<String>?) = apply { this.extras = extras }

        var parking : Boolean? = null
            private set
        fun parking(parking : Boolean?) = apply { this.parking = parking }

        var ascensor : Boolean? = null
            private set
        fun ascensor(ascensor : Boolean?) = apply { this.ascensor = ascensor }

        var amueblado : Boolean? = null
            private set
        fun amueblado(amueblado : Boolean?) = apply { this.amueblado = amueblado }

        var calefaccion : Boolean? = null
            private set
        fun calefaccion(calefaccion : Boolean?) = apply { this.calefaccion = calefaccion }

        var jardin : Boolean? = null
            private set
        fun jardin(jardin : Boolean?) = apply { this.jardin = jardin }

        var piscina : Boolean? = null
            private set
        fun piscina(piscina : Boolean?) = apply { this.piscina = piscina }

        var terraza : Boolean? = null
            private set
        fun terraza(terraza : Boolean?) = apply { this.terraza = terraza }

        var trastero : Boolean? = null
            private set
        fun trastero(trastero : Boolean?) = apply { this.trastero = trastero }

        var caracteristicas : String? = null
            private set
        fun caracteristicas(caracteristicas : String?) = apply { this.caracteristicas = caracteristicas }

        var direccionSitio : Sitio? = null
            private set
        fun direccionSitio(direccionSitio : Sitio?) = apply { this.direccionSitio = direccionSitio }


        fun build() = Inmueble(this)
    }

    fun caracteristicasToString( ) : String {
        var res = "El piso dispone de las siguientes caracteristicas: \n"

        if(parking == true){ res += "-Parking\n" }
        if(ascensor == true){ res += "-Ascensor\n" }
        if(amueblado == true){ res += "-Amueblado\n" }
        if(calefaccion == true){ res += "-Calefaccion\n" }
        if(jardin == true){ res += "-Jardin\n" }
        if(piscina == true){ res += "-Piscina\n" }
        if(terraza == true){ res += "-Terraza\n" }
        if(trastero == true){ res += "-Trastero\n" }

        return res
    }

    fun booleans2extras() : ArrayList<String> {
        var res = arrayListOf<String>()
        if(parking == true){ res.add("Parking")}
        if(ascensor == true){ res.add("Ascensor") }
        if(amueblado == true){ res.add("Amueblado") }
        if(calefaccion == true){ res.add("Calefacción")}
        if(jardin == true){ res.add("Jardín") }
        if(piscina == true){res.add("Piscina") }
        if(terraza == true){ res.add("Terraza")}
        if(trastero == true){ res.add("Trastero") }
        return res
    }

    fun getIdd() : String?{
        return id
    }

    fun adaptadorInm(data : DataInmueble2) : Inmueble{
        return Inmueble.Builder()
            .id(data.id)
            .propietario(data.propietario)
            .descripcion(data.descripcion)
            .tipoInmueble(data.tipoInmueble)
            .tipoVivienda(data.tipoVivienda)
            .numBanos(data.numBanos)
            .numHabitaciones(data.numHabitaciones)
            .precio(data.precio)
            .superficie(data.superficie)
            .intencion(data.intencion)
            .fotosOrd(data.fotos)
            .estado(data.estado)
            .extras(data.extras)
            .direccionSitio(data.direccion)
            .fechaSubida(LocalDateTime.parse(data.fechaSubida))
            .build()
    }

}










