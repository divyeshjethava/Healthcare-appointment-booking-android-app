package com.example.Patient

class Book_Appointment_Data {

    var appo_Date:String?=null
    var appo_Time:String?=null
    var appo_Type:String?=null
    var bookingType:String?=null

    constructor()
    constructor(appo_Date: String?, appo_Time: String?, appo_Type: String?, bookingType: String?) {
        this.appo_Date = appo_Date
        this.appo_Time = appo_Time
        this.appo_Type = appo_Type
        this.bookingType = bookingType
    }


}