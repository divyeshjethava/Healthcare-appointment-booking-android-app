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
import com.example.Patient.PUserData
import com.example.healthcare.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class Admin_Patient_F : Fragment() {


    lateinit var databaseReference: DatabaseReference
    lateinit var patientList:ArrayList<PUserData>
    lateinit var patientAdapter:AAdapter_Patient

    lateinit var pRecycleView:RecyclerView

    lateinit var pNoImage:ImageView
    lateinit var pNoData:TextView

    lateinit var txtTotalPatient:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view:View
        view=inflater.inflate(R.layout.fragment_admin__patient_, container, false)


        pNoImage=view.findViewById(R.id.noImageP)
        pNoData=view.findViewById(R.id.noDataP)

        txtTotalPatient=view.findViewById(R.id.txt_totalPatient)

        patientList=ArrayList()
        pRecycleView=view.findViewById(R.id.admin_patient_recycle_view)
        databaseReference=FirebaseDatabase.getInstance().getReference()
        patientAdapter= AAdapter_Patient(requireContext(),patientList)

        pRecycleView.layoutManager = LinearLayoutManager(requireContext())
        pRecycleView.adapter= patientAdapter


        databaseReference.child("Patient").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                patientList.clear()
                for(postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(PUserData::class.java)

                    patientList.add(currentUser!!)

                }
                patientAdapter.notifyDataSetChanged()



                if(patientAdapter.itemCount==0)
                {
                    pNoImage.visibility=View.VISIBLE
                    pNoData.visibility=View.VISIBLE
                    pRecycleView.visibility=View.GONE
                }
                else
                {
                    pNoImage.visibility=View.GONE
                    pNoData.visibility=View.GONE
                    pRecycleView.visibility=View.VISIBLE
                }


                val totalPatient=patientList.size
                txtTotalPatient.setText("$totalPatient")
            }

            override fun onCancelled(error: DatabaseError) {
            }


        })

        return view
    }
}