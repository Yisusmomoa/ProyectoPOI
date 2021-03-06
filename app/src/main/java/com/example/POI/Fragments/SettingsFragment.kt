package com.example.POI.Fragments

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.example.POI.ModelClasses.Users
import com.example.POI.R
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_settings.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var usersReference:DatabaseReference?=null
    var firebaseUser:FirebaseUser?=null
    private val RequestCode=438
    private var imageUri: Uri?=null
    private var storageRef:StorageReference?=null
    private var coverChecker:String?=null

    /*
    *
    *
1
rules_version = '2';
2
service firebase.storage {
3
  match /b/{bucket}/o {
4
    match /{allPaths=**} {
5
      allow read, write: if false;
6
    }
7
  }
8
}

    * */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_settings, container, false)

        firebaseUser=FirebaseAuth.getInstance().currentUser
        usersReference=FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
        storageRef=FirebaseStorage.getInstance().reference.child("User Images")
        usersReference!!.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    val user:Users?=p0.getValue(Users::class.java)
                    if (context!=null){
                        view.username_settings.text=user!!.getUsername()
                        Picasso.get().load(user.getProfile()).into(view.profile_image_settings)
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })

        view.profile_image_settings.setOnClickListener {
            pickImage()
        }

        return view
    }

    private fun pickImage() {
        val intent=Intent()
        intent.type="image/*"
        intent.action=Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, RequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==RequestCode&&resultCode==Activity.RESULT_OK && data!!.data != null){
            imageUri=data.data
            Toast.makeText(context,"uploading......", Toast.LENGTH_LONG).show()
            uploadImageToDatabase()
        }
    }

    private fun uploadImageToDatabase() {
        val progressBar=ProgressDialog(context)
        progressBar.setMessage("Image is uploading, please wait.....")
        progressBar.show()

        if(imageUri!=null){
            val fileRef=storageRef!!.child(System.currentTimeMillis().toString()+".jpg")

            var uploadTask:StorageTask<*>
            uploadTask=fileRef.putFile(imageUri!!)

            uploadTask.continueWithTask<Uri?>(Continuation<UploadTask.TaskSnapshot,Task<Uri>> { task ->
                if(!task.isSuccessful){
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation fileRef.downloadUrl
            }).addOnCompleteListener {
                task->
                if (task.isSuccessful){
                    val downloadUrl=task.result
                    val url=downloadUrl.toString()
                    val mapProfileImg=HashMap<String,Any>()
                    mapProfileImg["profile"]=url
                    usersReference!!.updateChildren(mapProfileImg)

                    progressBar.dismiss()
                }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}