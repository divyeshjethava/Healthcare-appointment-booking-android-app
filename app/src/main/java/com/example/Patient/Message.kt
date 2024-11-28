package com.example.Patient

class Message {
    var message:String?= null
    var senderPhone:String?=null

    constructor()

    constructor(message: String?,senderPhone: String?){
        this.message=message
        this.senderPhone=senderPhone
    }
}

