package com.example.trobify.models

data class DataUser(var email :String? = null,
                    var pisos : ArrayList<String>? = null,
                    var pisosNoPost : ArrayList<String>?=null,
                    var favorites : ArrayList<String>? = null,
                    var id : String? = null,
                    var name : String? = null,
                    var password : String? = null,
                    var profilePic : String? = null,
                    var surname : String? = null)