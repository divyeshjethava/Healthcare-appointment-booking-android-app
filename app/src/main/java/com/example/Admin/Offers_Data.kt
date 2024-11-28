package com.example.Admin

class Offers_Data {

    var offerImg:String?=null
    var offerTitle:String?=null
    var offerStatus:String?=null

    constructor()


    constructor(offerImg:String,offerTitle:String,offerStatus:String)
    {
        this.offerImg=offerImg
        this.offerTitle=offerTitle
        this.offerStatus=offerStatus
    }
}