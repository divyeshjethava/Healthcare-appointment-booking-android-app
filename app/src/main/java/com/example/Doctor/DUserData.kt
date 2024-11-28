package com.example.Doctor

class DUserData {
    var username : String? = null
    var email : String? = null
    var phoneNo : String ?=null
    var password : String?=null


    var doctorImg:String?=null
    var gender:String?=null
    var age:String?=null
    var birthDate:String?=null
    var department:String?=null
    var degree:String?=null
    var hospitalName:String?=null
    var experience:String?=null
    var location:String?=null
    var city:String?=null
    var aboutInfo:String?=null
    var certificateImg:String?=null

    var status:Int=0

    constructor()

    constructor(username: String, email:String, phoneNo:String, password:String,status: Int)
    {
        this.username=username
        this.email=email
        this.phoneNo= phoneNo
        this.password=password
        this.status=status

    }



   constructor(doctorImg: String,username: String,email: String,phoneNo: String,gender: String,age: String,birthDate: String,department: String,degree: String,hospitalName: String,experience :String,location: String,city: String,aboutInfo:String,certificateImg: String,status:Int)
   {
       this.doctorImg=doctorImg
       this.username=username
       this.email=email
       this.phoneNo=phoneNo
       this.gender=gender
       this.age=age
       this.birthDate=birthDate
       this.department=department
       this.degree=degree
       this.hospitalName=hospitalName
       this.experience=experience
       this.location=location
       this.city=city
       this.aboutInfo=aboutInfo
       this.certificateImg=certificateImg
       this.status=status
   }



    constructor(doctorImg: String, username: String, email: String, phoneNo: String, gender: String, age: String, birthDate: String, department: String, degree: String,hospitalName: String,experience :String, location: String, city: String,aboutInfo:String, certificateImg: String, password:String,status:Int)
    {
        this.doctorImg=doctorImg
        this.username=username
        this.email=email
        this.phoneNo=phoneNo
        this.gender=gender
        this.age=age
        this.birthDate=birthDate
        this.department=department
        this.degree=degree
        this.hospitalName=hospitalName
        this.experience=experience
        this.location=location
        this.city=city
        this.aboutInfo=aboutInfo
        this.certificateImg=certificateImg
        this.password=password
        this.status=status

    }


}