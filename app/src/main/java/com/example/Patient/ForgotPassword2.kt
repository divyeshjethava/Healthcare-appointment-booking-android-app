package com.example.Patient

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.healthcare.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class ForgotPassword2 : AppCompatActivity() {

    lateinit var backPageF:ImageView

    lateinit var o1:EditText
    lateinit var o2:EditText
    lateinit var o3:EditText
    lateinit var o4:EditText
    lateinit var o5:EditText
    lateinit var o6:EditText
    lateinit var auth:FirebaseAuth

    lateinit var resendOtp:TextView
    lateinit var leftTime:TextView
    var resendEnabled:Boolean=false
    var resendTime:Int=60


    lateinit var verify:TextView
    lateinit var mo_no:TextView
    lateinit var OTP:String//
    lateinit var resentToken:PhoneAuthProvider.ForceResendingToken//
    lateinit var Pnum:String//



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password2)

        supportActionBar?.hide()


        backPageF=findViewById(R.id.imageView3)
        auth=FirebaseAuth.getInstance()

        o1=findViewById(R.id.edtOPTNo1)
        o2=findViewById(R.id.edtOPTNo2)
        o3=findViewById(R.id.edtOPTNo3)
        o4=findViewById(R.id.edtOPTNo4)
        o5=findViewById(R.id.edtOPTNo5)
        o6=findViewById(R.id.edtOPTNo6)

        mo_no=findViewById(R.id.num)


        resendOtp=findViewById(R.id.resend_Otp)
        leftTime=findViewById(R.id.txt_left_time)


        verify=findViewById(R.id.btnVerify)
        addTextWatcher()


        startCountDownTimer()



        /*val no  = intent.getStringExtra("phoneNo1")
        mo_no.setText(no)*/



        o1.addTextChangedListener(TextW(o1))
        o2.addTextChangedListener(TextW(o2))
        o3.addTextChangedListener(TextW(o3))
        o4.addTextChangedListener(TextW(o4))
        o5.addTextChangedListener(TextW(o5))
        o6.addTextChangedListener(TextW(o6))

        OTP=intent.getStringExtra("otp").toString()//
        resentToken=intent.getParcelableExtra("token")!!//
        Pnum=intent.getStringExtra("pn")!!//

        mo_no.setText(Pnum)


        resendOtp.setOnClickListener {

            Toast.makeText(this@ForgotPassword2,"hiiii",Toast.LENGTH_LONG).show()

            val options= PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(Pnum)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(callback)
                .setForceResendingToken(resentToken)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)

            startCountDownTimer()

        }




        //back page intent
        backPageF.setOnClickListener {

            val  intent=Intent(this@ForgotPassword2, ForgotPassword1::class.java)
            startActivity(intent)
        }




        verify.setOnClickListener {

            val typeOtp=(o1.text.toString()+o2.text.toString()+o3.text.toString()+o4.text.toString()+o5.text.toString()+o6.text.toString())

            if (!typeOtp.isEmpty())
            {
                if (typeOtp.length.equals(6))
                {
                    val credential:PhoneAuthCredential=PhoneAuthProvider.getCredential(OTP,typeOtp)
                    signInWith(credential)
                }
                else
                {
                    Toast.makeText(this@ForgotPassword2,"Please enter the correct OTP",Toast.LENGTH_LONG).show()
                }
            }
            else
            {
                Toast.makeText(this@ForgotPassword2,"Please enter the OTP",Toast.LENGTH_LONG).show()
            }
        }
    }

    val callback =object :PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
        override fun onVerificationCompleted( credential: PhoneAuthCredential) {
            signInWith(credential)
        }

        override fun onVerificationFailed(p0: FirebaseException) {

        }
        override fun onCodeSent(verificationId:String, token:PhoneAuthProvider.ForceResendingToken){

            OTP=verificationId
            resentToken=token

        }



    }


    private fun signInWith(credential: PhoneAuthCredential){
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful){

                val intent =Intent(this@ForgotPassword2, ForgotPassword3::class.java)
                startActivity(intent)

                finish()

            }else{
                Toast.makeText(this@ForgotPassword2,"some error",Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun addTextWatcher(){
        o1.addTextChangedListener(TextW(o1))
        o2.addTextChangedListener(TextW(o2))
        o3.addTextChangedListener(TextW(o3))
        o4.addTextChangedListener(TextW(o4))
        o5.addTextChangedListener(TextW(o5))
        o6.addTextChangedListener(TextW(o6))
    }



    inner class TextW(private val view: View):TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {


            val text=s.toString()
            when(view.id){

                R.id.edtOPTNo1 -> if(text.length==1) o2.requestFocus()
                R.id.edtOPTNo2 -> if(text.length==1) o3.requestFocus() else if(text.isEmpty()) o1.requestFocus()
                R.id.edtOPTNo3 -> if(text.length==1) o4.requestFocus() else if(text.isEmpty()) o2.requestFocus()
                R.id.edtOPTNo4 -> if(text.length==1) o5.requestFocus() else if(text.isEmpty()) o3.requestFocus()
                R.id.edtOPTNo5 -> if(text.length==1) o6.requestFocus() else if(text.isEmpty()) o4.requestFocus()
                R.id.edtOPTNo6 -> if(text.isEmpty()) o5.requestFocus()

            }
        }

    }


    private fun startCountDownTimer()
    {
        resendEnabled=false
        resendOtp.isClickable=resendEnabled
        resendOtp.setTextColor(Color.parseColor("#808080"))

        val countDownTimer=object :CountDownTimer((resendTime * 1000).toLong(),1000)
        {
            override fun onTick(millisUntilFinished: Long) {

                val secondsLeft = millisUntilFinished / 1000

                leftTime.text="($secondsLeft) :"+" left"
            }

            override fun onFinish() {
                resendEnabled=true
                resendOtp.isClickable=resendEnabled
                resendOtp.setTextColor(Color.parseColor("#008DB8"))
            }
        }.start()
    }

}