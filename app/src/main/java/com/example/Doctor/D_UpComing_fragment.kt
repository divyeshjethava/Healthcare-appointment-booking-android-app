package com.example.Doctor

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Patient.Adapter_UpComing
import com.example.Patient.AppointmentData
import com.example.Patient.PLoginActivity
import com.example.healthcare.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class D_UpComing_fragment : Fragment() {


    private lateinit var databaseRef: DatabaseReference
    private lateinit var upComingrecyclerView: RecyclerView
    private lateinit var appoList: ArrayList<AppointmentData>
    private lateinit var upComingAdapter: Adapter_DUpComing

    private lateinit var phoneNo:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view:View=inflater.inflate(R.layout.fragment_d__up_coming_fragment, container, false)

        val sharedPreferences: SharedPreferences =requireActivity().getSharedPreferences(PLoginActivity().DPREF, AppCompatActivity.MODE_PRIVATE)
        phoneNo=sharedPreferences.getString("dPhoneNoL","error").toString()



        appoList= ArrayList()
        upComingrecyclerView=view.findViewById(R.id.recycleView_D_Upcoming)
        databaseRef= FirebaseDatabase.getInstance().getReference("Appointments")

        upComingAdapter= Adapter_DUpComing(requireContext(),appoList)

        upComingrecyclerView.layoutManager= LinearLayoutManager(requireContext())
        upComingrecyclerView.adapter=upComingAdapter



        databaseRef.child("View Doctor").child(phoneNo!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                appoList.clear()
                for(postSnapshot in snapshot.children){
                    val currentAppo = postSnapshot.getValue(AppointmentData::class.java)

                    appoList.add(currentAppo!!)


                }
                upComingAdapter.notifyDataSetChanged()


                /*if (upComingAdapter.itemCount == 0) {
                    nodataimage.visibility=View.VISIBLE
                    nodataText.visibility = View.VISIBLE
                    upComingrecyclerView.visibility = View.INVISIBLE
                } else {
                    nodataimage.visibility=View.INVISIBLE
                    nodataText.visibility = View.INVISIBLE
                    upComingrecyclerView.visibility = View.VISIBLE
                }*/



            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        return view
    }

}