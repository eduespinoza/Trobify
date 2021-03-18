package com.example.trobify


open class Message {
    var textMessage : String? = null
    var urlPicture : String? = null
    var name : String? = null
    var profilePicture : String? = null
    var type_mensage : String? = null

    constructor() {}
    constructor(
        textMessage : String?,
        name : String?,
        profilePicture : String?,
        type_mensage : String?
    ) {
        this.textMessage = textMessage
        this.name = name
        this.profilePicture = profilePicture
        this.type_mensage = type_mensage
    }

    constructor(
        textMessage : String?,
        urlPicture : String?,
        name : String?,
        profilePicture : String?,
        type_mensage : String?
    ) {
        this.textMessage = textMessage
        this.urlPicture = urlPicture
        this.name = name
        this.profilePicture = profilePicture
        this.type_mensage = type_mensage
    }

}