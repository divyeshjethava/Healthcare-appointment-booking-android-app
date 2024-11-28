package com.example.Patient

class AppointmentData {

    var image:String?=null
    var doctorName:String?=null
    var patientName:String?=null
    var dDepartment:String?=null
    var dNumber:String?=null
    var pNumber:String?=null
    var appoType:String?=null
    var appoDate:String?=null
    var appoTime:String?=null

    //dName,opName,dDepartment,dNumber,opNumber,opAge,opGender,opOtherProblem,appointmentType,txtDate,conformTime

    var opAge:String?=null
    var opGender:String?=null
    var opProblem:String?=null

    constructor()



    constructor(image:String?,
                doctorName: String?,
                patientName:String?,
                dDepartment: String?,
                dNumber: String?,
                pNumber:String?,
                opAge:String?,
                opGender:String?,
                opProblem:String?,
                appoType: String?,
                appoDate: String?,
                appoTime: String?)
    {
        this.image=image
        this.doctorName=doctorName
        this.patientName=patientName
        this.dDepartment=dDepartment
        this.dNumber=dNumber
        this.pNumber=pNumber
        this.opAge=opAge
        this.opGender=opGender
        this.opProblem=opProblem
        this.appoType=appoType
        this.appoDate=appoDate
        this.appoTime=appoTime
    }


    constructor(
        image:String?,
        doctorName: String?,
        patientName: String?,
        dDepartment: String?,
        dNumber: String?,
        pNumber: String?,
        appoType: String?,
        appoDate: String?,
        appoTime: String?
    ) {
        this.image=image
        this.doctorName = doctorName
        this.patientName = patientName
        this.dDepartment = dDepartment
        this.dNumber = dNumber
        this.pNumber = pNumber
        this.appoType = appoType
        this.appoDate = appoDate
        this.appoTime = appoTime
    }
}