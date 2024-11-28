package com.example.Patient

class CardData {

    var cardNo:String?=null
    var cardHolderName:String?=null
    var expiryDate:String?=null
    var cvv:String?=null

    constructor()

    constructor(cardNo: String?, cardHolderName: String?, expiryDate: String?, cvv: String?) {
        this.cardNo = cardNo
        this.cardHolderName = cardHolderName
        this.expiryDate = expiryDate
        this.cvv = cvv
    }
}