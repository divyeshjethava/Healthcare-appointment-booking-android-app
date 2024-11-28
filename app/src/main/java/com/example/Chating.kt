package com.example.healthcare

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.Patient.Message
import com.example.Patient.Message_Adapter
import com.example.Patient.PLoginActivity
import com.google.firebase.database.*

class
Chating : AppCompatActivity() {

    lateinit var edtMessage:EditText
    lateinit var btnSend:ImageView

    lateinit var dataRef:DatabaseReference
    lateinit var messageRecyclerView:RecyclerView
    lateinit var messageAdapter: Message_Adapter
    lateinit var imageD:ImageView
    lateinit var messageList: ArrayList<Message>


    lateinit var name:TextView
    lateinit var department:TextView


    var senderRoom: String? = null
    var receiveRoom: String? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chating)

        supportActionBar?.hide()

        edtMessage=findViewById(R.id.edtMessage)
        btnSend=findViewById(R.id.send)
        imageD=findViewById(R.id.c_image)

        name=findViewById(R.id.cname)
        department=findViewById(R.id.cDepartment)

        val name1=intent.getStringExtra("name")
        name.setText(""+name1)
        val department1=intent.getStringExtra("department")
        department.setText(""+department1)
        val img=intent.getStringExtra("image")
        Glide.with(this).load(img).into(imageD)



        edtMessage.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {

                if (s != null) {
                    if(s.isBlank()) {
                        btnSend.setImageResource(R.drawable.send)
                    }
                    else
                    {
                        btnSend.setImageResource(R.drawable.send1)
                    }
                }


            }


        })


        /*val shareD:SharedPreferences=getSharedPreferences(Chat_Adapter().PR, MODE_PRIVATE)
        var reveiverPhone=shareD.getString("Dphone","1231231231")*/
        var reveiverPhone = intent.getStringExtra("pho")

        val shareP:SharedPreferences=getSharedPreferences(PLoginActivity().PREF, MODE_PRIVATE)
        var phone1=shareP.getString("phone","1231231230")

        val data=FirebaseDatabase.getInstance().getReference("Patient")
        data.child(phone1!!).addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //  val img=homePicture.toString()
                //  val i=R.drawable.admin

                if(snapshot.exists())
                {
                   //val ur=snapshot.child("image").value as String


                    val ur="https://www.google.com/imgres?imgurl=https%3A%2F%2Fhips.hearstapps.com%2Fhmg-prod%2Fimages%2Fportrait-of-a-happy-young-doctor-in-his-clinic-royalty-free-image-1661432441.jpg%3Fcrop%3D0.66698xw%3A1xh%3Bcenter%2Ctop%26resize%3D1200%3A*&tbnid=l-f6eohbS5WvUM&vet=12ahUKEwjpp-rKrtGAAxUM5zgGHZ0eAEwQMygBegUIARD1AQ..i&imgrefurl=https%3A%2F%2Fwww.menshealth.com%2Fhealth%2Fa40971698%2F5-questions-your-doctor-wishes-youd-ask%2F&docid=nuUS4Tx-8chAsM&w=1200&h=1200&q=doctor&ved=2ahUKEwjpp-rKrtGAAxUM5zgGHZ0eAEwQMygBegUIARD1AQ"
                    Glide.with(this@Chating).load(ur).into(imageD)

                }







            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Chating,"error in image", Toast.LENGTH_LONG).show()

            }

        })



        val senderId = phone1
        dataRef = FirebaseDatabase.getInstance().getReference()


        senderRoom=reveiverPhone+ senderId
        receiveRoom=senderId+reveiverPhone


        messageRecyclerView=findViewById(R.id.chatRecycleView)



        messageList= ArrayList()
        messageAdapter= Message_Adapter(this,messageList)

        messageRecyclerView.layoutManager = LinearLayoutManager(this)
        messageRecyclerView.adapter = messageAdapter

        dataRef.child("chats").child(senderRoom!!).child("message")

            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (postSnapshot in snapshot.children){
                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })


        btnSend.setOnClickListener{

            val message=edtMessage.text.toString()
            val messageObject = Message(message,senderId)

            dataRef.child("chats").child(senderRoom!!).child("message").push()
                .setValue(messageObject).addOnSuccessListener {
                    dataRef.child("chats").child(receiveRoom!!).child("message").push()
                        .setValue(messageObject)
                }
            edtMessage.setText("")
        }
    }



}