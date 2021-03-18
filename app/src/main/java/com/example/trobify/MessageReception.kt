package com.example.trobify

class MessageReception : Message {
    var time : Long? = null

    constructor() {}
    constructor(hora : Long?) { this.time = time }
    constructor(
        textMessage : String?,
        urlPicture : String?,
        name : String?,
        profilePicture : String?,
        type_message : String?,
        time : Long?
    ) : super(textMessage, urlPicture, name, profilePicture, type_message) {
        this.time = time
    }

}