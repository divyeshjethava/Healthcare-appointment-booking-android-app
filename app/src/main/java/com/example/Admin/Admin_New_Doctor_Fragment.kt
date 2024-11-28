package com.example.Admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Doctor.DUserData
import com.example.healthcare.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class Admin_New_Doctor_Fragment : Fragment() {


    lateinit var database : DatabaseReference
    lateinit var userlist :ArrayList<DUserData>
    lateinit var usersAdp : AAdapter_New_D


    lateinit var recycle: RecyclerView

    var totalNewDoctor:Int=0

    lateinit var txtTotalND: TextView



    lateinit var nodataimage: ImageView
    lateinit var nodataText:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View
        view=inflater.inflate(R.layout.fragment_admin__new__doctor, container, false)


        txtTotalND=view.findViewById(R.id.txt_totalNewD)


        userlist= ArrayList()
        recycle=view.findViewById(R.id.new_d_recycle_view)
        nodataimage=view.findViewById(R.id.noimage)
        nodataText=view.findViewById(R.id.nodata)


        database= FirebaseDatabase.getInstance().getReference()


        usersAdp= AAdapter_New_D(requireContext(),userlist)


        recycle.layoutManager = LinearLayoutManager(requireContext())
        recycle.adapter= usersAdp

        database.child("Doctor").child("New Doctor").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userlist.clear()
                for(postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(DUserData::class.java)
                    //if (mauth.currentUser?.uid != currentUser?.uid){
                    userlist.add(currentUser!!)
                    //  }*/

                }
                usersAdp.notifyDataSetChanged()



                if (usersAdp.itemCount == 0) {
                    nodataimage.visibility=View.VISIBLE
                    nodataText.visibility = View.VISIBLE
                    recycle.visibility = View.INVISIBLE
                } else {
                    nodataimage.visibility=View.INVISIBLE
                    nodataText.visibility = View.INVISIBLE
                    recycle.visibility = View.VISIBLE
                }



                //totalAddDoctor=snapshot.childrenCount.toInt()
                totalNewDoctor=userlist.size
                txtTotalND.setText("$totalNewDoctor")
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })



        return view
    }


}