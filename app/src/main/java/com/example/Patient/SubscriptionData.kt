package com.example.Patient

class SubscriptionData {

    var subs_type:String?=null
    var subs_validate:String?=null
    var subs_price:String?=null
    var subs_date:String?=null

    constructor()


    constructor(subs_type:String,subs_validate:String,subs_price:String,subs_date:String)
    {
        this.subs_type=subs_type
        this.subs_validate=subs_validate
        this.subs_price=subs_price
        this.subs_date=subs_date
    }

}