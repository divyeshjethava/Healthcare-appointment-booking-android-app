package com.example.Admin

class Department_Data {

    var deptImg:String?=null
    var deptName:String?=null
    var deptDescription:String?=null
    var deptStatus:Int=0


    constructor()

    constructor(deptImg: String?, deptName: String?, deptDescription: String?,deptStatus:Int) {
        this.deptImg = deptImg
        this.deptName = deptName
        this.deptDescription = deptDescription
        this.deptStatus=deptStatus
    }

}