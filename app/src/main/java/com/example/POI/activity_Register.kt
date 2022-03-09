package com.example.POI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class activity_Register : AppCompatActivity() {
    private  lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers:DatabaseReference
    private var firebaseUserID:String=""

    var firebaseUser: FirebaseUser?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth= FirebaseAuth.getInstance()
        val btnsignup=findViewById<Button>(R.id.btn_signup)

        btnsignup.setOnClickListener {
            registerUser()
            /*val intent= Intent(this@activity_Register, Home::class.java)
            startActivity(intent)
            finish()*/
        }


    }

    private fun registerUser(){
        //obtengo el texto de los edit text
        val username:String=findViewById<EditText>(R.id.editTextUserName).text.toString()
        val email:String=findViewById<EditText>(R.id.editTextEmailAddress).text.toString()
        val pass:String=findViewById<EditText>(R.id.editTextPassword).text.toString()

        if(username=="" || email=="" || pass==""){
            Toast.makeText(this, "No debes de dejar ningún campo vacío", Toast.LENGTH_SHORT).show()
        }
        else{
            mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener { task->
                    if(task.isSuccessful){
                        firebaseUserID=mAuth.currentUser!!.uid
                        refUsers=FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUserID)
                        val userHashMap=HashMap<String,Any>()
                        userHashMap["uid"]=firebaseUserID
                        userHashMap["username"]=username
                        userHashMap["profile"]="https://firebasestorage.googleapis.com/v0/b/messengerpoi2.appspot.com/o/profile.png?alt=media&token=dcbc6c9e-7bdb-4453-afac-4d9e6b913c38"
                        userHashMap["status"]="offline"
                        userHashMap["search"]=username.toLowerCase()
                        userHashMap["status"]="offline"
                        userHashMap["status"]="offline"

                        refUsers.updateChildren(userHashMap)
                            .addOnCompleteListener {
                                task->
                                if (task.isSuccessful){
                                    val intent=Intent(this@activity_Register, Home::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(intent)
                                    finish()
                                }
                                else{

                                }

                            }
                    }
                    else{
                        Toast.makeText(this, "Error: ${task.exception?.message.toString()}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }

    class User(uid:String, username:String,email:String, pass:String){

    }




}