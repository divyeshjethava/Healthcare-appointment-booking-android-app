package com.example.Patient

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
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
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.Admin.Department_Data
import com.example.Doctor.DUserData
import com.example.healthcare.R
import com.google.firebase.database.*
import com.google.firebase.storage.StorageReference
import kotlin.io.path.fileVisitor


class Home_fragment : Fragment() {

    lateinit var homeName: TextView
    lateinit var databaseReference: DatabaseReference
    lateinit var databaseRef: DatabaseReference
    var name :String? = null
    var phoneLog :String?=null
    var phoneSign :String?=null
    var conPhone:String?=null


    lateinit var homePicture:ImageView


    lateinit var database : DatabaseReference
    lateinit var userlist :ArrayList<DUserData>
    lateinit var usersAdp : Adapter_Home

    lateinit var userlist1 :ArrayList<DUserData>
    lateinit var usersAdp1 : Adapter_Home

    lateinit var recycle: RecyclerView



    lateinit var database1 : DatabaseReference
    lateinit var deptList :ArrayList<Department_Data>
    lateinit var deptAdapter : Adapter_Dept

    lateinit var recycleDept: RecyclerView


    lateinit var txtSeeAll:TextView

    lateinit var searchDoctorH:EditText
    lateinit var edtCrossH:ImageView


    var ur: String ?=null

    //lateinit var imageLottie:LottieAnimationView
    var RUN="RUN"
    var RUN1="RUN1"


    private lateinit var txtSeeAllDoc:TextView

    lateinit var img:String


    private lateinit var searchDocHome: androidx.appcompat.widget.SearchView


    private lateinit var offerSlider:ImageSlider
    val remoteImage= mutableListOf<SlideModel>()








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
        view=inflater.inflate(R.layout.fragment_home_fragment, container, false)


        //imageLottie=view.findViewById(R.id.image_lottie)


        homeName=view.findViewById(R.id.userNameHome)
        homePicture=view.findViewById(R.id.home_picture)
        txtSeeAll=view.findViewById(R.id.txtSeeAll1)

//        searchDoctorH=view.findViewById(R.id.edtSearchDoctorH)
//        edtCrossH=view.findViewById(R.id.cross_home)


        txtSeeAllDoc=view.findViewById(R.id.txt_see_all_doc)

        searchDocHome=view.findViewById(R.id.search_doc_home)


        offerSlider=view.findViewById(R.id.offers_image_slider)




        //invisible lottie
       /* val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences(RUN, Context.MODE_PRIVATE)
        val isFirstRun = sharedPreferences.getBoolean(RUN1, true)

        if (isFirstRun) {

            Handler().postDelayed({
                imageLottie.visibility =View.INVISIBLE
            },2000)

            val editor = sharedPreferences.edit()
            editor.putBoolean(RUN1, false)
            editor.apply()
        }*/



        txtSeeAllDoc.setOnClickListener {

            val intent=Intent(requireContext(),See_All_Doctors::class.java)
            startActivity(intent)

        }




        //get phone no in login activity
        var shareprefrence1 : SharedPreferences = this.requireActivity().getSharedPreferences(
            PLoginActivity().PREF, AppCompatActivity.MODE_PRIVATE)
        phoneLog= shareprefrence1.getString("phoneNoL","error").toString()

        var shareprefrence2 : SharedPreferences =this.requireActivity().getSharedPreferences(
            PSignupActivity().PREFF,AppCompatActivity.MODE_PRIVATE)
        phoneSign= shareprefrence2.getString("phoneNoS","error").toString()



        if (!phoneLog.equals("error"))
        {
            conPhone=phoneLog
        }
        else
        {
            conPhone=phoneSign
        }


            databaseReference = FirebaseDatabase.getInstance().getReference("Patient")

            databaseReference.addValueEventListener(object  :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {


                    val n=snapshot.child(conPhone!!).child("username").value
                    homeName.setText(""+n+" 👋")

                    //set image
                    img=snapshot.child(conPhone!!).child("image").value.toString()

                    if (isAdded) {
                        Glide.with(requireContext()).load(img).into(homePicture)
                    }

                }


                override fun onCancelled(error: DatabaseError) {

                }

            })



        homePicture.setOnClickListener {

            lateinit var dialog: Dialog


            val view= View.inflate(requireContext(), R.layout.large_image_dialog,null)
            val builder= AlertDialog.Builder(requireContext())
            builder.setView(view)
            dialog=builder.create()

            val largeImage=view.findViewById<ImageView>(R.id.large_picture)

            Glide.with(requireContext()).load(img).into(largeImage)

            //dialog.setCanceledOnTouchOutside(false)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.show()

        }

        //val userId=FirebaseAuth.getInstance().currentUser!!.uid
        //Toast.makeText(requireContext(),"ho "+userId,Toast.LENGTH_LONG).show()







        deptList= ArrayList()
        recycleDept=view.findViewById(R.id.recycleview_patient_dept)
        database1= FirebaseDatabase.getInstance().getReference("Departments")


        deptAdapter= Adapter_Dept(requireContext(),deptList)


        recycleDept.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        recycleDept.adapter= deptAdapter

        database1.child("Status 1").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                deptList.clear()
                for(postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(Department_Data::class.java)
                    //if (mauth.currentUser?.uid != currentUser?.uid){
                    deptList.add(currentUser!!)
                    //  }*/

                }
                deptAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })









        userlist= ArrayList()
        recycle=view.findViewById(R.id.home_recycleView)
        database= FirebaseDatabase.getInstance().getReference()


        usersAdp= Adapter_Home(requireContext(),userlist)


        recycle.layoutManager = LinearLayoutManager(requireContext())
        recycle.adapter= usersAdp

        database.child("Doctor").child("Add Doctor").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userlist.clear()
                for(postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(DUserData::class.java)
                    //if (mauth.currentUser?.uid != currentUser?.uid){
                    userlist.add(currentUser!!)
                    //  }*/

                }
                usersAdp.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })





        //back page intent
        txtSeeAll.setOnClickListener {

            val intent=Intent(requireContext(), Departments::class.java)
            startActivity(intent)
        }







        val searchIcon: ImageView? = searchDocHome.findViewById(androidx.appcompat.R.id.search_button)
        // Set the custom search icon
        val customSearchIcon = ContextCompat.getDrawable(requireContext(), R.drawable.search_doc_icon)
        searchDocHome.setIconifiedByDefault(false)
        searchDocHome.setIconified(false)
        searchDocHome.isIconified = false
        searchDocHome.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)?.setImageDrawable(customSearchIcon)
        // Adjust the size of the search icon
        searchIcon?.layoutParams?.width = (100)
        searchIcon?.layoutParams?.height = (100)
        // Apply layout changes
        searchIcon?.requestLayout()
        val searchText = searchDocHome.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchText?.textSize = 15F

        // Change text color
        searchText?.setTextColor(Color.parseColor("#000000"))


        searchDocHome.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                searchList(newText)
                return true

            }


        })





        //clear text
//        searchDoctorH.addTextChangedListener(object : TextWatcher{
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//
//                if (s != null) {
//                    if(s.isBlank()) {
//                        edtCrossH.setImageResource(R.drawable.close2)
//                    } else {
//                        edtCrossH.setImageResource(R.drawable.close1)
//
//                        edtCrossH.setOnClickListener {
//                            searchDoctorH.setText("")
//                        }
//                    }
//                }
//            }
//
//        })




        databaseRef=FirebaseDatabase.getInstance().getReference("Offers")
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {


                    if(snapshot.exists())
                    {

                        offerSlider.visibility=View.VISIBLE


                        for(data in snapshot.children)
                        {
                            val url=data.child("offerImg").getValue(String::class.java).toString()
                            val title=data.child("offerTitle").getValue(String::class.java).toString()
                            val status=data.child("offerStatus").getValue(String::class.java).toString()


                            if (url != null && title != null && status != "0") {
                                remoteImage.add(SlideModel(url, title, ScaleTypes.FIT))


                                offerSlider.setImageList(remoteImage,ScaleTypes.FIT)

                                offerSlider.setItemClickListener(object : ItemClickListener {
                                    override fun doubleClick(position: Int) {

                                    }

                                    override fun onItemSelected(i: Int) {
                                        //Toast.makeText(context, remoteImage.get(i).toString(),Toast.LENGTH_SHORT).show()

                                        Toast.makeText(context, title,Toast.LENGTH_SHORT).show()

                                    }
                                })




                            }

                        }
                    }
                    else
                    {
                        offerSlider.visibility=View.GONE
                    }



                }

                override fun onCancelled(error: DatabaseError) {
                }

            })





        return view
    }



    /* private fun setEditImage() {
         storageReference=FirebaseStorage.getInstance().getReference(phone!!).child("image")
         val localFile=File.createTempFile("temp","jpg")
         storageReference.getFile(localFile).addOnCompleteListener{

             val bitmap=BitmapFactory.decodeFile(localFile.absolutePath)
             homePicture.setImageBitmap(bitmap)

         }
     }*/



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