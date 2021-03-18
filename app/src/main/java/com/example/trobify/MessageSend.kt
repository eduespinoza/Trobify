package com.example.trobify

class MessageSend : Message {
    var time : Map<*, *>? = null

    constructor() {}
    constructor(hora : Map<*, *>?) { this.time = time }

    constructor(
        textMessage : String?,
        name : String?,
        profilePicture : String?,
        type_message : String?,
        time : Map<*, *>?
    ) : super(textMessage, name, profilePicture, type_message) { this.time = time }

    constructor(
        textMessage : String?,
        urlPicture : String?,
        name : String?,
        profilePicture : String?,
        type_message : String?,
        time : Map<*, *>?
    ) : super(textMessage, urlPicture, name, profilePicture, type_message) { this.time = time }

}
