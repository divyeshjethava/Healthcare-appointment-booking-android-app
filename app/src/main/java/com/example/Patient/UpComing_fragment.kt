package com.example.Patient

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthcare.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class UpComing_fragment : Fragment() {


    lateinit var databaseRef: DatabaseReference
    lateinit var upComingrecyclerView: RecyclerView
    lateinit var appoList: ArrayList<AppointmentData>
    lateinit var upComingAdapter: Adapter_UpComing


    var phoneLog:String?=null
    var phoneSign:String?=null


    var conformPhone:String?=null



    lateinit var nodataimage: ImageView
    lateinit var nodataText: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {

        val view:View
        view=inflater.inflate(R.layout.fragment_up_coming_fragment, container, false)


        nodataimage=view.findViewById(R.id.noimage)
        nodataText=view.findViewById(R.id.nodata)



        //get phone no in login activity
        var shareprefrence1 : SharedPreferences = this.requireActivity().getSharedPreferences(
            PLoginActivity().PREF, AppCompatActivity.MODE_PRIVATE)
        phoneLog= shareprefrence1.getString("phoneNoL","error").toString()

        var shareprefrence2 : SharedPreferences =this.requireActivity().getSharedPreferences(
            PSignupActivity().PREFF, AppCompatActivity.MODE_PRIVATE)
        phoneSign= shareprefrence2.getString("phoneNoS","error").toString()



        if(!phoneSign.equals("error"))
        {
            conformPhone=phoneSign
        }
        else
        {
            conformPhone=phoneLog
        }


        //get current date
        var calender= Calendar.getInstance().time
        var systemDate= SimpleDateFormat("dd MMMM YYYY", Locale.US).format(calender)




        appoList= ArrayList()
        upComingrecyclerView=view.findViewById(R.id.upComing_recycleView)
        databaseRef=FirebaseDatabase.getInstance().getReference("Appointments")

        upComingAdapter= Adapter_UpComing(requireContext(),appoList)

        upComingrecyclerView.layoutManager=LinearLayoutManager(requireContext())
        upComingrecyclerView.adapter=upComingAdapter



        databaseRef.child("View Patient").child(conformPhone!!).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                appoList.clear()
                for(postSnapshot in snapshot.children){
                    val currentAppo = postSnapshot.getValue(AppointmentData::class.java)

                    appoList.add(currentAppo!!)


                }
                upComingAdapter.notifyDataSetChanged()


                if (upComingAdapter.itemCount == 0) {
                    nodataimage.visibility=View.VISIBLE
                    nodataText.visibility = View.VISIBLE
                    upComingrecyclerView.visibility = View.INVISIBLE
                } else {
                    nodataimage.visibility=View.INVISIBLE
                    nodataText.visibility = View.INVISIBLE
                    upComingrecyclerView.visibility = View.VISIBLE
                }



            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        return view
    }
}