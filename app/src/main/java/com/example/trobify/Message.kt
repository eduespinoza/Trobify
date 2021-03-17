package com.example.trobify


open class Message {
    var message : String? = null
    var urlPicture : String? = null
    var name : String? = null
    var profilePicture : String? = null
    var type_message : String? = null

    constructor() {}
    constructor(
        message : String?,
        name : String?,
        profilePicture : String?,
        type_message : String?
    ) {
        this.message = message
        this.name = name
        this.profilePicture = profilePicture
        this.type_message = type_message
    }

    constructor(
        message : String?,
        urlPicture : String?,
        name : String?,
        profilePicture : String?,
        type_message : String?
    ) {
        this.message =message
        this.urlPicture = urlPicture
        this.name = name
        this.profilePicture = profilePicture
        this.type_message = type_message
    }

}