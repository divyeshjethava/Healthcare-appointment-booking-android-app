package com.example.Patient

class PUserData {

    var image:String?=null
    var username : String? = null
    var email : String? = null
    var phoneNo :String ?=null
    var password:String?=null

    var gender : String?=null
    var age : String ?=null
    var birthDate : String ?=null
    var height : String ?=null
    var weight : String ?=null
    var city : String ?=null

    var pStatus:Int?=null


    constructor()

    constructor(username:String,email:String,phoneNo:String,password:String)
    {
        this.username=username
        this.email=email
        this.phoneNo= phoneNo
        this.password=password

    }


    constructor(image:String,username:String,email:String,phoneNo:String,gender:String,age:String,birthDate:String,height:String,weight:String,city:String,password:String,pStatus:Int)
    {
        this.image=image
        this.username=username
        this.email=email
        this.phoneNo= phoneNo
        this.gender=gender
        this.age=age
        this.birthDate=birthDate
        this.height=height
        this.weight=weight
        this.city=city
        this.password=password
        this.pStatus=pStatus

    }

}