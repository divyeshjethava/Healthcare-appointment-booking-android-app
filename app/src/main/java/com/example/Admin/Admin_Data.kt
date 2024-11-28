package com.example.Admin

class Admin_Data {


    var aName:String?=null
    var aEmail:String?=null
    var aPhone:String?=null
    var aPassword:String?=null
    var aStatus:Int?=null


    constructor()

    constructor(aName:String,aEmail:String,aPhone:String,aPassword:String,aStatus:Int)
    {
        this.aName=aName
        this.aEmail=aEmail
        this.aPhone=aPhone
        this.aPassword=aPassword
        this.aStatus=aStatus
    }



}