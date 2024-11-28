package com.example.healthcare

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Doctor.DUserData
import com.google.firebase.database.*

class Setting_fragment : Fragment() {

    lateinit var database : DatabaseReference
    lateinit var userlist :ArrayList<DUserData>
    lateinit var usersAdp : Adapter_Chat

    lateinit var recycle: RecyclerView


    lateinit var searchDoctorC: EditText
    lateinit var edtCrossC: ImageView

    lateinit var dSearchView: androidx.appcompat.widget.SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View=inflater.inflate(R.layout.fragment_chat_fragment, container, false)


        dSearchView=view.findViewById(R.id.search_doc)

        val searchIcon: ImageView? = dSearchView.findViewById(androidx.appcompat.R.id.search_button)
        // Set the custom search icon
        val customSearchIcon = ContextCompat.getDrawable(requireContext(), R.drawable.search_doc_icon)
        dSearchView.setIconifiedByDefault(false)
        dSearchView.setIconified(false)
        dSearchView.isIconified = false
        dSearchView.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)?.setImageDrawable(customSearchIcon)
        // Adjust the size of the search icon
        searchIcon?.layoutParams?.width = (100)
        searchIcon?.layoutParams?.height = (100)
        // Apply layout changes
        searchIcon?.requestLayout()
        val searchText = dSearchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchText?.textSize = 15F

        // Change text color
        searchText?.setTextColor(Color.parseColor("#000000"))







        userlist= ArrayList()
        recycle=view.findViewById(R.id.chat_recycleView)
        database= FirebaseDatabase.getInstance().getReference()

        usersAdp= Adapter_Chat(requireContext(), userlist )

        recycle.layoutManager = LinearLayoutManager(requireContext())
        recycle.adapter= usersAdp

        database.child("Doctor").child("Add Doctor").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userlist.clear()
                for(postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(DUserData::class.java)
                    //if (mauth.currentUser?.uid != currentUser?.uid){
                        userlist.add(currentUser!!)
                  //  }

                }
                usersAdp.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }


        })



        dSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                searchList(newText)
                return true
            }


        })






//        searchDoctorC=view.findViewById(R.id.edtSearchDoctorC)
//        edtCrossC=view.findViewById(R.id.cross_chat)


        //clear text
        /*  searchDoctorC.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {

                if (s != null) {
                    if(s.isBlank()) {
                        edtCrossC.visibility=View.GONE
                    } else {
                        edtCrossC.visibility=View.VISIBLE
                        edtCrossC.setImageResource(R.drawable.close1)

                        edtCrossC.setOnClickListener {
                            searchDoctorC.setText("")
                        }
                    }
                }
            }

        })*/



        return view
    }
    fun searchList(text : String){

        val searchList=ArrayList<DUserData>()

        for (dataclass in userlist)
        {
            if(dataclass.username?.lowercase()?.contains(text.lowercase())==true)
            {
                searchList.add(dataclass)
            }
        }
        usersAdp.searchDataList(searchList)
    }
}