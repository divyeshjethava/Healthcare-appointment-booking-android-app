package com.example.Patient

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.example.healthcare.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Book_Apointment : AppCompatActivity() {

    private var isToday = true

    lateinit var doctorBAImg:ImageView
    lateinit var txtDName:TextView
    lateinit var txtDDepartment:TextView

    //appointment type
    lateinit var online:TextView
    lateinit var inPerson:TextView

    //booking for
    lateinit var mySelf:TextView
    lateinit var otherPer:TextView

    //other person data
    private lateinit var edtName:EditText
    private lateinit var edtNumber:EditText
    private lateinit var edtAge:EditText
    private lateinit var edtOtherProblem:EditText

    private lateinit var time1:TextView
    private lateinit var time2:TextView
    private lateinit var time3:TextView
    private lateinit var time4:TextView
    private lateinit var time5:TextView
    private lateinit var time6:TextView
    private lateinit var time7:TextView


    private lateinit var ampm1:TextView
    private lateinit var ampm2:TextView
    private lateinit var ampm3:TextView
    private lateinit var ampm4:TextView
    private lateinit var ampm5:TextView
    private lateinit var ampm6:TextView
    private lateinit var ampm7:TextView



    private lateinit var backColor1: RelativeLayout
    private lateinit var backColor2: RelativeLayout
    private lateinit var backColor3: RelativeLayout
    private lateinit var backColor4: RelativeLayout
    private lateinit var backColor5: RelativeLayout
    private lateinit var backColor6: RelativeLayout
    private lateinit var backColor7: RelativeLayout

    private lateinit var timeB1:LinearLayout
    private lateinit var timeB2:LinearLayout
    private lateinit var timeB3:LinearLayout
    private lateinit var timeB4:LinearLayout
    private lateinit var timeB5:LinearLayout
    private lateinit var timeB6:LinearLayout
    private lateinit var timeB7:LinearLayout


    private lateinit var dot1:RelativeLayout
    private lateinit var dot2:RelativeLayout
    private lateinit var dot3:RelativeLayout
    private lateinit var dot4:RelativeLayout
    private lateinit var dot5:RelativeLayout
    private lateinit var dot6:RelativeLayout
    private lateinit var dot7:RelativeLayout


    private lateinit var innDot1:CardView
    private lateinit var innDot2:CardView
    private lateinit var innDot3:CardView
    private lateinit var innDot4:CardView
    private lateinit var innDot5:CardView
    private lateinit var innDot6:CardView
    private lateinit var innDot7:CardView



    var gender:String?=null



    //select date
    lateinit var imgSelectDate:ImageView
    lateinit var txtselectDate:TextView
    lateinit var txtShowDate:TextView
    var dateFormet1=SimpleDateFormat("dd MMMM YYYY", Locale.US)


    lateinit var otherPersonData:RelativeLayout
    var countTime:Int?=null


    var txtDate:String?=null

    var conformTime:String?=null
    var appointmentType:String?=null
    var bookingType:String?=null





    var phoneAppo:String?=null


    lateinit var databaseReference: DatabaseReference
    lateinit var btnNext:TextView



    //get current date
    var calender=Calendar.getInstance().time
    //var dateFormet2= DateFormat.getDateInstance(DateFormat.DEFAULT).format(calender)
    var systemDate=SimpleDateFormat("dd MMMM YYYY", Locale.US).format(calender)


    var dImg:String?=null
    var dNumber:String?=null
    var dName:String?=null
    var dDepartment:String?=null


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_apointment)

        supportActionBar?.hide()


        doctorBAImg=findViewById(R.id.doctor_picture)
        txtDName=findViewById(R.id.txt_ba_dName)
        txtDDepartment=findViewById(R.id.txt_ba_dDepartment)



        online=findViewById(R.id.txt_online)
        inPerson=findViewById(R.id.txt_in_person)

        mySelf=findViewById(R.id.txt_myself)
        otherPer=findViewById(R.id.txt_other)


        imgSelectDate=findViewById(R.id.img_select_date)
        txtselectDate=findViewById(R.id.txt_select_date)
        txtShowDate=findViewById(R.id.txt_show_date)

        otherPersonData=findViewById(R.id.other_person_data)


        btnNext=findViewById(R.id.btn_next)





        var sharedPreferences1: SharedPreferences =this.getSharedPreferences(PSignupActivity().PREFF1, MODE_PRIVATE)
        val phoneS=sharedPreferences1.getString("phoneNoS","error").toString()

        var sharedPreferences2: SharedPreferences =this.getSharedPreferences(PLoginActivity().PREF, MODE_PRIVATE)
        val phoneL=sharedPreferences2.getString("phoneNoL","error").toString()



        if(!phoneS.equals("error"))
        {
            phoneAppo=phoneS
        }
        else
        {
            phoneAppo=phoneL
        }



        dImg=intent.getStringExtra("dImg").toString()
        Glide.with(this@Book_Apointment).load(dImg).into(doctorBAImg)

        dName=intent.getStringExtra("dName").toString()
        txtDName.setText("Dr. "+dName)

        dDepartment=intent.getStringExtra("dDepartment").toString()
        txtDDepartment.setText(dDepartment)

        dNumber=intent.getStringExtra("dNumber").toString()







        imgSelectDate.setOnClickListener {

            selectDate()

        }
        txtShowDate.setOnClickListener {

            selectDate()

        }







        timeB1=findViewById(R.id.txt_bTime1)
        timeB2=findViewById(R.id.txt_bTime2)
        timeB3=findViewById(R.id.txt_bTime3)
        timeB4=findViewById(R.id.txt_bTime4)
        timeB5=findViewById(R.id.txt_bTime5)
        timeB6=findViewById(R.id.txt_bTime6)
        timeB7=findViewById(R.id.txt_bTime7)

        timeB1.isEnabled = false
        timeB2.isEnabled = false
        timeB3.isEnabled = false
        timeB4.isEnabled = false
        timeB5.isEnabled = false
        timeB6.isEnabled = false
        timeB7.isEnabled = false




        backColor1=findViewById(R.id.back_color1)
        backColor2=findViewById(R.id.back_color2)
        backColor3=findViewById(R.id.back_color3)
        backColor4=findViewById(R.id.back_color4)
        backColor5=findViewById(R.id.back_color5)
        backColor6=findViewById(R.id.back_color6)
        backColor7=findViewById(R.id.back_color7)

        val gradientDrawable=GradientDrawable()
        gradientDrawable.shape=GradientDrawable.RECTANGLE
        gradientDrawable.setColor(Color.parseColor("#EAEFF1"))
        gradientDrawable.cornerRadius=40f

        backColor1.background=gradientDrawable
        backColor2.background=gradientDrawable
        backColor3.background=gradientDrawable
        backColor4.background=gradientDrawable
        backColor5.background=gradientDrawable
        backColor6.background=gradientDrawable
        backColor7.background=gradientDrawable


        dot1=findViewById(R.id.dot_1)
        dot2=findViewById(R.id.dot_2)
        dot3=findViewById(R.id.dot_3)
        dot4=findViewById(R.id.dot_4)
        dot5=findViewById(R.id.dot_5)
        dot6=findViewById(R.id.dot_6)
        dot7=findViewById(R.id.dot_7)

        innDot1=findViewById(R.id.inner_dot1)
        innDot2=findViewById(R.id.inner_dot2)
        innDot3=findViewById(R.id.inner_dot3)
        innDot4=findViewById(R.id.inner_dot4)
        innDot5=findViewById(R.id.inner_dot5)
        innDot6=findViewById(R.id.inner_dot6)
        innDot7=findViewById(R.id.inner_dot7)


        time1=findViewById(R.id.timeT1)
        time2=findViewById(R.id.timeT2)
        time3=findViewById(R.id.timeT3)
        time4=findViewById(R.id.timeT4)
        time5=findViewById(R.id.timeT5)
        time6=findViewById(R.id.timeT6)
        time7=findViewById(R.id.timeT7)

        ampm1 = findViewById(R.id.ampm1)
        ampm2 = findViewById(R.id.ampm2)
        ampm3 = findViewById(R.id.ampm3)
        ampm4 = findViewById(R.id.ampm4)
        ampm5 = findViewById(R.id.ampm5)
        ampm6 = findViewById(R.id.ampm6)
        ampm7 = findViewById(R.id.ampm7)

        timeB1.setOnClickListener {




            conformTime=time1.text.toString()

            setBackground(backColor1,dot1,innDot1)




        }


        timeB2.setOnClickListener {



            conformTime=time2.text.toString()


            setBackground(backColor2,dot2,innDot2)




        }


        timeB3.setOnClickListener {



            conformTime=time3.text.toString()


            setBackground(backColor3,dot3,innDot3)



        }


        timeB4.setOnClickListener {



            conformTime=time4.text.toString()


            setBackground(backColor4,dot4,innDot4)



        }


        timeB5.setOnClickListener {



            conformTime=time5.text.toString()


            setBackground(backColor5,dot5,innDot5)



        }


        timeB6.setOnClickListener {



            conformTime=time6.text.toString()


            setBackground(backColor6,dot6,innDot6)



        }


        timeB7.setOnClickListener {



            conformTime=time7.text.toString()


            setBackground(backColor7,dot7,innDot7)



        }





        //appointment type select
        online.setOnClickListener {
            online.setTextColor(Color.BLACK)
            inPerson.setTextColor(Color.rgb(130, 217, 243))

            online.setBackgroundResource(R.drawable.button_shap5)
            inPerson.setBackgroundResource(R.drawable.button_shap3)


            appointmentType="Online"

            //Toast.makeText(this@Book_Apointment,"hi "+appointmentType,Toast.LENGTH_LONG).show()
        }
        inPerson.setOnClickListener {
            inPerson.setTextColor(Color.BLACK)
            online.setTextColor(Color.rgb(130, 217, 243))

            inPerson.setBackgroundResource(R.drawable.button_shap5)
            online.setBackgroundResource(R.drawable.button_shap3)


            appointmentType="In Person"

            //Toast.makeText(this@Book_Apointment,"hi "+appointmentType,Toast.LENGTH_LONG).show()

        }






        //booking type select
        mySelf.setOnClickListener {
            mySelf.setTextColor(Color.BLACK)
            otherPer.setTextColor(Color.rgb(130, 217, 243))

            mySelf.setBackgroundResource(R.drawable.button_shap5)
            otherPer.setBackgroundResource(R.drawable.button_shap3)

            otherPersonData.visibility=View.GONE

            bookingType="My Self"






            var pName1:String?=null
            var pNumber1:String?=null
            var pImg:String?=null
            databaseReference=FirebaseDatabase.getInstance().getReference("Patient")
            databaseReference.child(phoneAppo!!).get().addOnSuccessListener {

                pName1= it.child("username").value.toString()
                pNumber1=it.child("phoneNo").value.toString()
                pImg=it.child("image").value.toString()

            }


            btnNext.setOnClickListener {


                databaseReference=FirebaseDatabase.getInstance().getReference("Appointments")
                databaseReference.child("View Patient").child(phoneAppo!!).child(dNumber!!).setValue(AppointmentData(dImg,dName,pName1,dDepartment,dNumber,phoneAppo,appointmentType,txtDate,conformTime)).addOnCompleteListener {


                    if(it.isSuccessful)
                    {


                        databaseReference=FirebaseDatabase.getInstance().getReference("Appointments")
                        databaseReference.child("View Doctor").child(dNumber!!).child(phoneAppo!!).setValue(AppointmentData(pImg,dName,pName1,dDepartment,dNumber,phoneAppo,appointmentType,txtDate,conformTime)).addOnCompleteListener {


                            if(it.isSuccessful)
                            {
                                Toast.makeText(this@Book_Apointment,"Booked appointment successful...",Toast.LENGTH_LONG).show()
                                val intent= Intent(this@Book_Apointment, Book_Appointment_successful::class.java)
                                intent.putExtra("docName",dName)
                                intent.putExtra("patientName",pName1)
                                intent.putExtra("apDate",txtDate)
                                intent.putExtra("apTime",conformTime)
                                startActivity(intent)
                                finish()
                            }

                        }


                    }

                }


            }



        }
        otherPer.setOnClickListener {
            otherPer.setTextColor(Color.BLACK)
            mySelf.setTextColor(Color.rgb(130, 217, 243))

            otherPer.setBackgroundResource(R.drawable.button_shap5)
            mySelf.setBackgroundResource(R.drawable.button_shap3)

            otherPersonData.visibility=View.VISIBLE


            bookingType="Other Person"



            edtName=findViewById(R.id.edit_ba_name)
            edtNumber=findViewById(R.id.edit_ba_phone)
            edtAge=findViewById(R.id.edit_ba_age)
            edtOtherProblem=findViewById(R.id.edit_ba_other_problem)


            val txtMale=findViewById<TextView>(R.id.txt_ba_male)
            val txtFemale=findViewById<TextView>(R.id.txt_ba_female)
            val txtOther=findViewById<TextView>(R.id.txt_ba_other)


            txtMale.setOnClickListener {

                txtMale.setBackgroundResource(R.drawable.button_shap5)
                txtFemale.setBackgroundResource(R.drawable.button_shap3)
                txtOther.setBackgroundResource(R.drawable.button_shap3)
                txtMale.setTextColor(Color.BLACK)
                txtFemale.setTextColor(Color.rgb(130, 217, 243))
                txtOther.setTextColor(Color.rgb(130, 217, 243))

                gender="Male"
            }
            txtFemale.setOnClickListener {

                txtMale.setBackgroundResource(R.drawable.button_shap3)
                txtFemale.setBackgroundResource(R.drawable.button_shap5)
                txtOther.setBackgroundResource(R.drawable.button_shap3)
                txtFemale.setTextColor(Color.BLACK)
                txtMale.setTextColor(Color.rgb(130, 217, 243))
                txtOther.setTextColor(Color.rgb(130, 217, 243))

                gender="Female"
            }
            txtOther.setOnClickListener {

                txtMale.setBackgroundResource(R.drawable.button_shap3)
                txtOther.setBackgroundResource(R.drawable.button_shap3)
                txtOther.setBackgroundResource(R.drawable.button_shap5)
                txtOther.setTextColor(Color.BLACK)
                txtMale.setTextColor(Color.rgb(130, 217, 243))
                txtFemale.setTextColor(Color.rgb(130, 217, 243))

                gender="Other"



            }





            btnNext.setOnClickListener {
                var opName=edtName.text.toString()
                var opNumber=edtNumber.text.toString()
                var opAge=edtAge.text.toString()
                var opGender=gender.toString()
                var opOtherProblem=edtOtherProblem.text.toString()


                if (opName.isEmpty())
                {
                    edtName.setError("Enter name")
                }
                else if (opNumber.isEmpty())
                {
                    edtNumber.setError("Enter phone no")
                }
                else if (opAge.isEmpty())
                {
                    edtAge.setError("Enter Age")
                }
                else if (opGender.equals("null"))
                {
                    showToast("Select Gender")
                }
                else if(opOtherProblem.isEmpty())
                {
                    edtOtherProblem.setError("Enter other problem yes or no")
                }
                else if (!opNumber.length.equals(10))
                {
                    edtNumber.setError("Enter valid number")
                }
                else
                {
                    databaseReference=FirebaseDatabase.getInstance().getReference("Appointments")
                    databaseReference.child(phoneAppo!!).child(opNumber!!).setValue(AppointmentData(dImg,dName,opName,dDepartment,dNumber,opNumber,opAge,opGender,opOtherProblem,appointmentType,txtDate,conformTime)).addOnCompleteListener {


                        if(it.isSuccessful)
                        {


                            Toast.makeText(this@Book_Apointment,"Booked appointment successful...",Toast.LENGTH_LONG).show()
                            val intent= Intent(this@Book_Apointment, Book_Appointment_successful::class.java)
                            intent.putExtra("bNumber",opNumber)
                            startActivity(intent)
                            finish()
                        }

                    }


                }


            }


        }








    }









    private fun selectDate() {


        val getDate:Calendar=Calendar.getInstance()
        val datePicker=DatePickerDialog(this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,DatePickerDialog.OnDateSetListener{ view: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->

            val selectDate=Calendar.getInstance()
            selectDate.set(Calendar.YEAR,year)
            selectDate.set(Calendar.MONTH,month)
            selectDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            val date=dateFormet1.format(selectDate.time)
            txtShowDate.text=date

            txtDate=txtShowDate.text.toString()



            timeB1.isEnabled = true
            timeB2.isEnabled = true
            timeB3.isEnabled = true
            timeB4.isEnabled = true
            timeB5.isEnabled = true
            timeB6.isEnabled = true
            timeB7.isEnabled = true


            //get current date
            val calendar=Calendar.getInstance()
            val dateFormat=SimpleDateFormat("dd MMMM yyyy",Locale.US)
            val currentDate=dateFormat.format(calendar.time)

            val day = calendar.get(Calendar.DAY_OF_MONTH)
            isToday = day == dayOfMonth

            //showToast(isToday.toString())

            if (isToday) {
                showToast("${time1.text} ${ampm1.text}")

                validateTime(time1, ampm1, backColor1, timeB1)
                validateTime(time2, ampm2, backColor2, timeB2)
                validateTime(time3, ampm3, backColor3, timeB3)
                validateTime(time4, ampm4, backColor4, timeB4)
                validateTime(time5, ampm5, backColor5, timeB5)
                validateTime(time6, ampm6, backColor6, timeB6)
                validateTime(time7, ampm7, backColor7, timeB7)



                dot1.visibility=View.INVISIBLE
                dot2.visibility=View.INVISIBLE
                dot3.visibility=View.INVISIBLE
                dot4.visibility=View.INVISIBLE
                dot5.visibility=View.INVISIBLE
                dot6.visibility=View.INVISIBLE
                dot7.visibility=View.INVISIBLE


                innDot1.visibility=View.INVISIBLE
                innDot2.visibility=View.INVISIBLE
                innDot3.visibility=View.INVISIBLE
                innDot4.visibility=View.INVISIBLE
                innDot5.visibility=View.INVISIBLE
                innDot6.visibility=View.INVISIBLE
                innDot7.visibility=View.INVISIBLE


            }
            else
            {
                backColor1.setBackgroundResource(R.drawable.back_shap9)
                backColor2.setBackgroundResource(R.drawable.back_shap9)
                backColor3.setBackgroundResource(R.drawable.back_shap9)
                backColor4.setBackgroundResource(R.drawable.back_shap9)
                backColor5.setBackgroundResource(R.drawable.back_shap9)
                backColor6.setBackgroundResource(R.drawable.back_shap9)
                backColor7.setBackgroundResource(R.drawable.back_shap9)


                dot1.visibility=View.INVISIBLE
                dot2.visibility=View.INVISIBLE
                dot3.visibility=View.INVISIBLE
                dot4.visibility=View.INVISIBLE
                dot5.visibility=View.INVISIBLE
                dot6.visibility=View.INVISIBLE
                dot7.visibility=View.INVISIBLE


                innDot1.visibility=View.INVISIBLE
                innDot2.visibility=View.INVISIBLE
                innDot3.visibility=View.INVISIBLE
                innDot4.visibility=View.INVISIBLE
                innDot5.visibility=View.INVISIBLE
                innDot6.visibility=View.INVISIBLE
                innDot7.visibility=View.INVISIBLE
            }
            /*var result=currentDate!!.compareTo(txtDate!!)

            if(result > 0)
            {
                btnNext.isClickable=false
                Toast.makeText(this@Book_Apointment,"Please select right date",Toast.LENGTH_LONG).show()
            }
            else if (result < 0)
            {
                btnNext.isClickable=true
            }
            else
            {
                btnNext.isClickable=true
            }*/


            if(!date.equals(""))
            {
                imgSelectDate.visibility= View.INVISIBLE
                txtselectDate.visibility=View.INVISIBLE
            }
            else
            {
                imgSelectDate.visibility=View.VISIBLE
                txtselectDate.visibility=View.VISIBLE
            }



        },getDate.get(Calendar.YEAR),getDate.get(Calendar.MONTH),getDate.get(Calendar.DAY_OF_MONTH))
        datePicker.datePicker.minDate = System.currentTimeMillis() - 1000
        // Get the current date and time
        // Get the current date and time
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, 2)
        datePicker.datePicker.maxDate = calendar.timeInMillis
        datePicker.show()


    }


    private fun validateTime(textView: TextView, textViewAmPm: TextView, relativeLayout: RelativeLayout, linearLayout: LinearLayout){


        val currentTime = Calendar.getInstance()
        val currentHour = currentTime.get(Calendar.HOUR_OF_DAY) // Current hour in 24-hour format
        val currentMinute = currentTime.get(Calendar.MINUTE)

        val itemTime = textView.text.toString()
        val itemHour = itemTime.substringBefore(":").trim().toInt()
        val itemMinute = itemTime.substringAfter(":").trim().toInt()
        val amPm = textViewAmPm.text.trim().toString()

        val itemHour24 = if (amPm.equals("AM", ignoreCase = true)) {
            if (itemHour == 12) 0 else itemHour
        } else {
            if (itemHour == 12) itemHour else itemHour + 12
        }


        if (!(itemHour24 < currentHour || (itemHour24 == currentHour && itemMinute < currentMinute)) || !isToday) {
            linearLayout.isEnabled = true



        } else {
            linearLayout.isEnabled = false

            val gradientDrawable=GradientDrawable()
            gradientDrawable.shape=GradientDrawable.RECTANGLE
            gradientDrawable.setColor(Color.parseColor("#EAEFF1"))
            gradientDrawable.cornerRadius=40f
            relativeLayout.background=gradientDrawable
        }
}
    /*private fun replaceFragment(fragment : Fragment)
    {
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.fragment_view,fragment)
        fragmentTransaction.commit()
    }

    private fun removeFragment(fragment1 : Fragment)
    {
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()

        fragmentTransaction.remove(fragment1)
        fragmentTransaction.commit()
    }*/


    private fun setBackground(relativeLayout: RelativeLayout,dot: RelativeLayout,innerDot: CardView){

        if (timeB1.isEnabled) {
            backColor1.setBackgroundResource(R.drawable.back_shap9)
            dot1.visibility=View.INVISIBLE
            innDot1.visibility=View.INVISIBLE
        }
        if (timeB2.isEnabled) {
            backColor2.setBackgroundResource(R.drawable.back_shap9)
            dot2.visibility=View.INVISIBLE
            innDot2.visibility=View.INVISIBLE
        }
        if (timeB3.isEnabled) {
            backColor3.setBackgroundResource(R.drawable.back_shap9)
            dot3.visibility=View.INVISIBLE
            innDot3.visibility=View.INVISIBLE
        }
        if (timeB4.isEnabled) {
            backColor4.setBackgroundResource(R.drawable.back_shap9)
            dot4.visibility=View.INVISIBLE
            innDot4.visibility=View.INVISIBLE
        }
        if (timeB5.isEnabled) {
            backColor5.setBackgroundResource(R.drawable.back_shap9)
            dot5.visibility=View.INVISIBLE
            innDot5.visibility=View.INVISIBLE
        }
        if (timeB6.isEnabled) {
            backColor6.setBackgroundResource(R.drawable.back_shap9)
            dot6.visibility=View.INVISIBLE
            innDot6.visibility=View.INVISIBLE
        }
        if (timeB7.isEnabled) {
            backColor7.setBackgroundResource(R.drawable.back_shap9)
            dot7.visibility=View.INVISIBLE
            innDot7.visibility=View.INVISIBLE
        }


        relativeLayout.setBackgroundResource(R.drawable.back_shap14)
        dot.visibility=View.VISIBLE
        innerDot.visibility=View.VISIBLE
    }
    private fun showToast(message: String){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }
}