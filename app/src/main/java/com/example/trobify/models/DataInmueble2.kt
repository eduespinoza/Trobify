package com.example.trobify.models

data class DataInmueble2 (var id : String? = null,
                          var propietario : String? = null,
                          var numHabitaciones : Int? = null,
                          var numBanos : Int? = null,
                          var superficie : Int? = null,
                          var direccion : Sitio? = null,
                          var tipoVivienda : String? = null,
                          var tipoInmueble : String? = null,
                          var intencion : String? = null,
                          var precio : Int? = null,
                          var fotos : ArrayList<Int> = arrayListOf(),
                          var descripcion : String? = null,
                          var extras : ArrayList<String> = arrayListOf(),
                          var estado : String? = null,
                            var fechaSubida : String? = null)
