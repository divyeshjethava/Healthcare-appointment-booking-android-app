package com.example.Patient

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.healthcare.Adapter_Cards
import com.example.healthcare.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.*
import java.text.DateFormat
import java.util.Calendar

class PaymentMethod : AppCompatActivity() {

    lateinit var backPayment:ImageView
    lateinit var addCard:ImageView
    lateinit var cancel:ImageView


    lateinit var btnRadioButton1:RadioButton
    lateinit var btnRadioButton2:RadioButton
    lateinit var btnRadioButton3:RadioButton
    lateinit var btnRadioButton4:RadioButton
    lateinit var btnRadioButton5:RadioButton


    lateinit var btnContinue:TextView


    var phoneLog :String?=null
    var phoneSign :String?=null
    var fPhone:String?=null


    //save card information
    lateinit var dataRef1:DatabaseReference

    //pay information store
    lateinit var dataRef2:DatabaseReference

    var subscription_price:String?=null
    var subscription_type:String?=null
    var subscription_validate:String?=null



    private lateinit var dataList: ArrayList<CardData>
    private lateinit var cardAdapter: Adapter_Cards

    lateinit var cardRecyclerView: RecyclerView


    var currentDate:String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_method)
        supportActionBar?.hide()

        backPayment=findViewById(R.id.back_Payment)
        addCard=findViewById(R.id.add_card)

        btnRadioButton1=findViewById(R.id.btn_radio1)
        btnRadioButton2=findViewById(R.id.btn_radio2)
        btnRadioButton3=findViewById(R.id.btn_radio3)
        btnRadioButton4=findViewById(R.id.btn_radio4)
        btnRadioButton5=findViewById(R.id.btn_radio5)

        btnContinue=findViewById(R.id.btn_pay_continue)




        //current Date
        val calender= Calendar.getInstance().time
        currentDate=DateFormat.getDateInstance(DateFormat.FULL).format(calender)



        //get phone no in login activity
        var shareprefrence1 : SharedPreferences = this.getSharedPreferences(PLoginActivity().PREF,MODE_PRIVATE)
        phoneLog= shareprefrence1.getString("phoneNoL","error").toString()

        var shareprefrence2 : SharedPreferences =this.getSharedPreferences(PSignupActivity().PREFF,MODE_PRIVATE)
        phoneSign= shareprefrence2.getString("phoneNoS","error").toString()


        if(phoneLog!="error")
        {
            fPhone=phoneLog
        }
        else
        {
            fPhone=phoneSign
        }






        //display data on recycle view
        /*cardRecyclerView=findViewById(R.id.card_recycle_view)
        dataList= ArrayList()
        cardAdapter= Adapter_Cards(this@PaymentMethod,dataList)
        cardRecyclerView.adapter=cardAdapter
        dataRef=FirebaseDatabase.getInstance().getReference("Cards")
        dataRef.child(phoneNo)


        dataRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                dataList.clear()
                for (cardSnapshot in snapshot.children){

                    val cardDataClass = cardSnapshot.getValue(CardData::class.java)
                    //if(cardDataClass != null)
                    //{
                        dataList.add(cardDataClass!!)
                    //}

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@PaymentMethod,"cancel",Toast.LENGTH_SHORT).show()
            }


        })*/





        backPayment.setOnClickListener {

            val intent=Intent(this@PaymentMethod, PHomePage::class.java)
            startActivity(intent)
        }

        //click + button
        addCard.setOnClickListener {

            val view:View=layoutInflater.inflate(R.layout.add_new_card_dialog,null)
            val bsDialog=BottomSheetDialog(this)
            bsDialog.setContentView(view)
            bsDialog.show()



            val edtCardNo=bsDialog.findViewById<EditText>(R.id.edt_No1)


            val edtHolderName=bsDialog.findViewById<EditText>(R.id.edt_holderName)
            val edtExpiryDate=bsDialog.findViewById<EditText>(R.id.edt_expiryDate)
            val edtCVV=bsDialog.findViewById<EditText>(R.id.edt_cvv)

            val btnAddCard=bsDialog.findViewById<TextView>(R.id.btn_add_card)




            btnAddCard?.setOnClickListener {

                //custom alert dialog
                val view= View.inflate(this@PaymentMethod, R.layout.custom_dialog3,null)
                val builder= AlertDialog.Builder(this@PaymentMethod)
                builder.setView(view)
                val dialog=builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)



                val cardNo= edtCardNo?.text?.trim().toString()


                val holderName= edtHolderName?.text?.toString()
                val expiryDate= edtExpiryDate?.text?.toString()
                val cvv= edtCVV?.text?.toString()



                val len1:Int=(cardNo.length)
                val len2:Int=cvv!!.length


                if(cardNo.isEmpty())
                {
                    edtCardNo?.setError("Enter card number...")
                }
                else if (holderName!!.isEmpty())
                {
                    edtHolderName?.setError("Enter holder name...")
                }
                else if (expiryDate!!.isEmpty())
                {
                    edtExpiryDate?.setError("Enter expiry date")
                }
                else if (cvv!!.isEmpty())
                {
                    edtCVV?.setError("Enter CVV..")
                }
                else if (len1!=16)
                {
                    edtCardNo?.setError("Please enter correct number...")
                }
                else if (len2!=3)
                {
                    edtCVV?.setError("Please enter correct number...")
                }
                else
                {



                    dataRef1=FirebaseDatabase.getInstance().getReference("Cards")
                    dataRef1.child(fPhone!!).child(cardNo).setValue(CardData(cardNo,holderName,expiryDate,cvv)).addOnCompleteListener {

                        if (it.isSuccessful)
                        {
                            Toast.makeText(this,"Successfully...",Toast.LENGTH_SHORT).show()
                            dialog.dismiss()
                            bsDialog.dismiss()
                        }
                    }

                }


            }



            cancel= bsDialog.findViewById(R.id.cancel_dialog)!!

            cancel.setOnClickListener {

                bsDialog.dismiss()
            }


        }


        btnRadioButton1.setOnClickListener {
            if(btnRadioButton1.isChecked)
            {
                btnRadioButton2.isChecked=false
                btnRadioButton3.isChecked=false
                btnRadioButton4.isChecked=false
                btnRadioButton5.isChecked=false
            }
        }

        btnRadioButton2.setOnClickListener {
            if(btnRadioButton2.isChecked)
            {
                btnRadioButton1.isChecked=false
                btnRadioButton3.isChecked=false
                btnRadioButton4.isChecked=false
                btnRadioButton5.isChecked=false
            }
        }

        btnRadioButton3.setOnClickListener {
            if(btnRadioButton3.isChecked)
            {
                btnRadioButton1.isChecked=false
                btnRadioButton2.isChecked=false
                btnRadioButton4.isChecked=false
                btnRadioButton5.isChecked=false
            }
        }

        btnRadioButton4.setOnClickListener {
            if(btnRadioButton4.isChecked)
            {
                btnRadioButton1.isChecked=false
                btnRadioButton2.isChecked=false
                btnRadioButton3.isChecked=false
                btnRadioButton5.isChecked=false
            }
        }

        btnRadioButton5.setOnClickListener {
            if(btnRadioButton5.isChecked)
            {
                btnRadioButton1.isChecked=false
                btnRadioButton2.isChecked=false
                btnRadioButton3.isChecked=false
                btnRadioButton4.isChecked=false
            }
        }






        //click continue btn and check the condition
        btnContinue.setOnClickListener {
            if (btnRadioButton1.isChecked)
            {
                payment()
            }
            else if(btnRadioButton2.isChecked)
            {
                payment()
            }
            else if(btnRadioButton3.isChecked)
            {
                payment()
            }
            else if(btnRadioButton4.isChecked)
            {
                payment()
            }
            else if(btnRadioButton5.isChecked)
            {
                payment()
            }
            else
            {
                Toast.makeText(this,"Select payment type...",Toast.LENGTH_SHORT).show()
            }

        }




    }

    private fun payment() {



        //show dialog
        val view:View=layoutInflater.inflate(R.layout.add_new_card_dialog,null)
        val bsDialog=BottomSheetDialog(this)
        bsDialog.setContentView(view)
        bsDialog.show()


        val edtCardNo=bsDialog.findViewById<EditText>(R.id.edt_No1)


        val edtHolderName=bsDialog.findViewById<EditText>(R.id.edt_holderName)
        val edtExpiryDate=bsDialog.findViewById<EditText>(R.id.edt_expiryDate)
        val edtCVV=bsDialog.findViewById<EditText>(R.id.edt_cvv)

        val btnAddCard=bsDialog.findViewById<TextView>(R.id.btn_add_card)



        subscription_price =intent.getStringExtra("subscription_price")
        btnAddCard?.setText("Pay "+subscription_price+"₹")

        if(subscription_price=="800")
        {
            subscription_type="Normal"
            subscription_validate="Validate of one week."
        }
        else if(subscription_price=="4,500")
        {
            subscription_type="Silver"
            subscription_validate="Validate of one month."
        }
        else if(subscription_price=="15,000")
        {
            subscription_type="Gold"
            subscription_validate="Validate of one year."
        }
        else if(subscription_price=="27,000")
        {
            subscription_type="Platinum"
            subscription_validate="Validate of two year."
        }
        else
        {
            Toast.makeText(this@PaymentMethod,"Invalid",Toast.LENGTH_LONG).show()
        }


        btnAddCard?.setOnClickListener {

            val cardNo= edtCardNo?.text?.trim().toString()

            val holderName= edtHolderName?.text?.toString()
            val expiryDate= edtExpiryDate?.text?.toString()
            val cvv= edtCVV?.text?.toString()



            val len1:Int=(cardNo.length)
            val len2:Int=cvv!!.length


            if(cardNo.isEmpty())
            {
                edtCardNo?.setError("Enter card number...")
            }
            else if (holderName!!.isEmpty())
            {
                edtHolderName?.setError("Enter holder name...")
            }
            else if (expiryDate!!.isEmpty())
            {
                edtExpiryDate?.setError("Enter expiry date")
            }
            else if (cvv!!.isEmpty())
            {
                edtCVV?.setError("Enter CVV..")
            }
            /*else if (len1 > 16)
            {
                edtCardNo?.setError("Please enter correct number...")
            }
            else if (len2 > 3)
            {
                edtCVV?.setError("Please enter correct number...")
            }*/
            else
            {

                //custom alert dialog
                val view= View.inflate(this@PaymentMethod, R.layout.custom_dialog3,null)
                val builder= AlertDialog.Builder(this@PaymentMethod)
                builder.setView(view)
                val dialog=builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

                dataRef1=FirebaseDatabase.getInstance().getReference("Cards")
                dataRef1.child(fPhone!!).child(cardNo).setValue(CardData(cardNo,holderName,expiryDate,cvv)).addOnCompleteListener {

                    if (it.isSuccessful)
                    {

                        dataRef2=FirebaseDatabase.getInstance().getReference("Subscriptions")
                        dataRef2.child(fPhone!!).child(subscription_type!!).setValue(
                            SubscriptionData(
                            subscription_type!!,subscription_validate!!,subscription_price!!,currentDate!!)
                        ).addOnCompleteListener {


                            if(it.isSuccessful)
                            {
                                Toast.makeText(this,"Successfully...",Toast.LENGTH_SHORT).show()

                                val intent=Intent(this@PaymentMethod, MySubscription::class.java)
                                startActivity(intent)

                                dialog.dismiss()
                                bsDialog.dismiss()
                            }

                        }


                    }
                }

            }


        }





        //cancel dialog
        cancel= bsDialog.findViewById(R.id.cancel_dialog)!!
        cancel.setOnClickListener {

            bsDialog.dismiss()
        }



    }


    //move to another editText
    /*class EditTextWatcher(private val view:View) : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {


            val text=s.toString()
            when(view.id)
            {
                R.id.edt_No1 -> if(text.length==4) edtCardNo2?.requestFocus()
                R.id.edt_No2 -> if(text.length==4) edtCardNo3?.requestFocus() else if (text.isEmpty()) edtCardNo1?.requestFocus()
                R.id.edt_No3 -> if(text.length==4) edtCardNo4?.requestFocus() else if (text.isEmpty()) edtCardNo2?.requestFocus()
                R.id.edt_No4 -> if(text.isEmpty()) edtCardNo3?.requestFocus()

            }
        }

    }*/


}