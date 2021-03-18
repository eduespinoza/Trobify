package com.example.trobify


open class Message {
    var textMessage : String? = null
    var urlPicture : String? = null
    var name : String? = null
    var profilePicture : String? = null
    var type_message : String? = null

    constructor() {}
    constructor(
        textMessage : String?,
        name : String?,
        profilePicture : String?,
        type_message : String?
    ) {
        this.textMessage = textMessage
        this.name = name
        this.profilePicture = profilePicture
        this.type_message = type_message
    }

    constructor(
        textMessage : String?,
        urlPicture : String?,
        name : String?,
        profilePicture : String?,
        type_message : String?
    ) {
        this.textMessage = textMessage
        this.urlPicture = urlPicture
        this.name = name
        this.profilePicture = profilePicture
        this.type_message = type_message
    }

}