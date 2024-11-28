package com.example.Patient

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthcare.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MySubscription : AppCompatActivity() {

    lateinit var addSubscription:ImageView
    lateinit var backSubscription:ImageView
    lateinit var nodataimage:ImageView
    lateinit var nodataText:TextView

    var payCon: Int? =null


    lateinit var databaseReference: DatabaseReference
    var PhoneNo:String?=null


    lateinit var adapterSubs: Adapter_Subscription
    lateinit var recyclerView: RecyclerView
    lateinit var subsArrayList:ArrayList<SubscriptionData>


    var phoneSub:String?=null

    var sub:Boolean=false


    @SuppressLint("ResourceAsColor", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_subscription)

        supportActionBar?.hide()

        addSubscription=findViewById(R.id.add_subscription)
        backSubscription=findViewById(R.id.back_subscription)
        nodataimage=findViewById(R.id.noimage)
        nodataText=findViewById(R.id.nodata)


        recyclerView=findViewById(R.id.subs_recycle_view)




//        var sharedPreferences:SharedPreferences=this.getSharedPreferences(PSignupActivity().PREFF, MODE_PRIVATE)
//        PhoneNo=sharedPreferences.getString("phoneNoS","error").toString()


        //Toast.makeText(this,"hi "+PhoneNo,Toast.LENGTH_LONG).show()


        addSubscription.setOnClickListener {

            val view: View =layoutInflater.inflate(R.layout.add_subscription,null)
            val dialog=BottomSheetDialog(this)
            dialog.setContentView(view)
            dialog.show()


            val subCard1=dialog.findViewById<RelativeLayout>(R.id.subscription_card1)
            val subCard2=dialog.findViewById<RelativeLayout>(R.id.subscription_card2)
            val subCard3=dialog.findViewById<RelativeLayout>(R.id.subscription_card3)
            val subCard4=dialog.findViewById<RelativeLayout>(R.id.subscription_card4)


            val subsNormal= dialog.findViewById<RelativeLayout>(R.id.subscription_normal)
            val subsSilver= dialog.findViewById<RelativeLayout>(R.id.subscription_silver)
            val subsGold= dialog.findViewById<RelativeLayout>(R.id.subscription_gold)
            val subsPlatinum= dialog.findViewById<RelativeLayout>(R.id.subscription_platinum)


            //set peice in button
            val price1=dialog.findViewById<TextView>(R.id.txt_price1)
            val price11= price1!!.text.toString()
            val price2=dialog.findViewById<TextView>(R.id.txt_price2)
            val price22= price2!!.text.toString()
            val price3=dialog.findViewById<TextView>(R.id.txt_price3)
            val price33= price3!!.text.toString()
            val price4=dialog.findViewById<TextView>(R.id.txt_price4)
            val price44= price4!!.text.toString()
            val btnAddSubscription= dialog.findViewById<TextView>(R.id.btn_add_subscription)





            subsNormal!!.setOnClickListener {

                payCon=1

                subCard1?.setBackgroundResource(R.drawable.subscription_shape3)
                subsNormal?.setBackgroundResource(R.drawable.subscription_shape2)

                subCard2?.setBackgroundResource(R.drawable.subscription_shape4)
                subsSilver?.setBackgroundResource(R.drawable.subscription_shape5)

                subCard3?.setBackgroundResource(R.drawable.subscription_shape4)
                subsGold?.setBackgroundResource(R.drawable.subscription_shape5)

                subCard4?.setBackgroundResource(R.drawable.subscription_shape4)
                subsPlatinum?.setBackgroundResource(R.drawable.subscription_shape5)


                btnAddSubscription!!.setText("Add Subscription "+price11+" ₹")


            }

            subsSilver!!.setOnClickListener {

                payCon=2

                subCard1?.setBackgroundResource(R.drawable.subscription_shape4)
                subsNormal?.setBackgroundResource(R.drawable.subscription_shape5)

                subCard2?.setBackgroundResource(R.drawable.subscription_shape3)
                subsSilver?.setBackgroundResource(R.drawable.subscription_shape2)

                subCard3?.setBackgroundResource(R.drawable.subscription_shape4)
                subsGold?.setBackgroundResource(R.drawable.subscription_shape5)

                subCard4?.setBackgroundResource(R.drawable.subscription_shape4)
                subsPlatinum?.setBackgroundResource(R.drawable.subscription_shape5)


                btnAddSubscription!!.setText("Add Subscription "+price22+" ₹")

            }

            subsGold!!.setOnClickListener {

                payCon=3

                subCard1?.setBackgroundResource(R.drawable.subscription_shape4)
                subsNormal.setBackgroundResource(R.drawable.subscription_shape5)

                subCard2?.setBackgroundResource(R.drawable.subscription_shape4)
                subsSilver?.setBackgroundResource(R.drawable.subscription_shape5)

                subCard3?.setBackgroundResource(R.drawable.subscription_shape3)
                subsGold?.setBackgroundResource(R.drawable.subscription_shape2)

                subCard4?.setBackgroundResource(R.drawable.subscription_shape4)
                subsPlatinum?.setBackgroundResource(R.drawable.subscription_shape5)


                btnAddSubscription!!.setText("Add Subscription "+price33+" ₹")


            }

            subsPlatinum!!.setOnClickListener {

                payCon=4


                subCard1?.setBackgroundResource(R.drawable.subscription_shape4)
                subsNormal.setBackgroundResource(R.drawable.subscription_shape5)

                subCard2?.setBackgroundResource(R.drawable.subscription_shape4)
                subsSilver?.setBackgroundResource(R.drawable.subscription_shape5)

                subCard3?.setBackgroundResource(R.drawable.subscription_shape4)
                subsGold?.setBackgroundResource(R.drawable.subscription_shape5)

                subCard4?.setBackgroundResource(R.drawable.subscription_shape3)
                subsPlatinum?.setBackgroundResource(R.drawable.subscription_shape2)


                btnAddSubscription!!.setText("Add Subscription "+price44+" ₹")


            }








            btnAddSubscription!!.setOnClickListener {



                /*databaseReference=FirebaseDatabase.getInstance().getReference("Subscriptions")
                databaseReference.child(PhoneNo).setValue()*/


                if (payCon==1)
                {
                    val txtPrice=findViewById<TextView>(R.id.txt_price1)
                    val intent=Intent(this@MySubscription, PaymentMethod::class.java)

                    intent.putExtra("subscription_price",price11)

                    startActivity(intent)


                }
                else if (payCon==2)
                {
                    val intent=Intent(this@MySubscription, PaymentMethod::class.java)

                    intent.putExtra("subscription_price",price22)

                    startActivity(intent)
                }
                else if (payCon==3)
                {
                    val intent=Intent(this@MySubscription, PaymentMethod::class.java)

                    intent.putExtra("subscription_price",price33)

                    startActivity(intent)
                }
                else if (payCon==4)
                {
                    val intent=Intent(this@MySubscription, PaymentMethod::class.java)

                    intent.putExtra("subscription_price",price44)

                    startActivity(intent)
                }
                else
                {
                    Toast.makeText(this,"Select subscription type...",Toast.LENGTH_SHORT).show()
                }

            }



            val cancel=dialog.findViewById<ImageView>(R.id.cancel_dialog)

            cancel!!.setOnClickListener {

                dialog.dismiss()
            }



        }


        backSubscription.setOnClickListener {

            val intent=Intent(this@MySubscription, PHomePage::class.java)
            startActivity(intent)
        }


        var sharedPreferences1:SharedPreferences=this.getSharedPreferences(PSignupActivity().PREFF1, MODE_PRIVATE)
        val phoneS=sharedPreferences1.getString("phoneNoS","error").toString()

        var sharedPreferences2:SharedPreferences=this.getSharedPreferences(PLoginActivity().PREF, MODE_PRIVATE)
        val phoneL=sharedPreferences2.getString("phoneNoL","error").toString()
        databaseReference=FirebaseDatabase.getInstance().getReference("Subscriptions")



        if(!phoneS.equals("error"))
        {
            phoneSub=phoneS
        }
        else
        {
            phoneSub=phoneL
        }



        subsArrayList= ArrayList()

        adapterSubs= Adapter_Subscription(subsArrayList)

        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.adapter=adapterSubs



        databaseReference.child(phoneSub!!).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                subsArrayList.clear()
                for(postSnapshot in snapshot.children)
                {
                    val currectSubs=postSnapshot.getValue(SubscriptionData::class.java)
                    subsArrayList.add(currectSubs!!)
                   // Toast.makeText(this@MySubscription,"hi ii iii"+subsArrayList,Toast.LENGTH_SHORT).show()

                }
                adapterSubs.notifyDataSetChanged()

                if (adapterSubs.itemCount == 0) {
                    nodataimage.visibility=View.VISIBLE
                    nodataText.visibility = View.VISIBLE
                    recyclerView.visibility = View.INVISIBLE
                } else {
                    nodataimage.visibility=View.INVISIBLE
                    nodataText.visibility = View.INVISIBLE
                    recyclerView.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MySubscription,"some error",Toast.LENGTH_SHORT).show()

            }


        })



    }
}