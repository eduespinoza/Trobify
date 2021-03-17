package com.example.trobify

class MessageReception : Message {
    var time : Long? = null

    constructor() {}
    constructor(time : Long?) {
        this.time = time
    }

    constructor(
        message : String?,
        urlPicture : String?,
        name : String?,
        profilePicture : String?,
        type_message : String?,
        time : Long?
    ) : super(message!!, urlPicture!! , name!! , profilePicture!!, type_message!! ) { this.time = time }

}