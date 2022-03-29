package com.example.POI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.POI.AdapterClasses.ChatGrupalAdapter
import com.example.POI.AdapterClasses.ChatsAdapter
import com.example.POI.ModelClasses.Chat
import com.example.POI.ModelClasses.MensajeGrupal
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_message_chat.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MessageChatActivityGroup : AppCompatActivity() {

    var grupoId:String=""
    var firebaseUser: FirebaseUser?=null
   // private val listaMensajes = mutableListOf<MensajeGrupal>()
    var mChatList:List<MensajeGrupal>?=null
    lateinit var recycler_view_chats: RecyclerView
    var reference: DatabaseReference?=null
    var chatsAdapter: ChatGrupalAdapter?=null

    //referencia a la base de datos
    var database=FirebaseDatabase.getInstance()
    //crear una ramita en la bd que se llamará groups
    val groupRef=database.getReference("ChatGrupal")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_chat_group)

        intent=intent
        grupoId=intent.getStringExtra("visit_id").toString()
        firebaseUser= FirebaseAuth.getInstance().currentUser
        recycler_view_chats=findViewById(R.id.recycler_view_chats)
        recycler_view_chats.setHasFixedSize(true)
        var linearLayoutManager= LinearLayoutManager(applicationContext)
        linearLayoutManager.stackFromEnd=true
        recycler_view_chats.layoutManager=linearLayoutManager
        firebaseUser=FirebaseAuth.getInstance().currentUser
        recycler_view_chats.adapter = chatsAdapter
        send_message_btn.setOnClickListener {

            val message=findViewById<EditText>(R.id.text_message).text.toString()
            if(message==""){
                Toast.makeText(this, "Por favor, primero escribe un mensaje", Toast.LENGTH_SHORT).show()
            }
            else{
                enviarMensaje(MensajeGrupal("", message, firebaseUser!!.uid, ServerValue.TIMESTAMP,grupoId))
            }
            text_message.setText("")
        }

        attact_image_file_btn.setOnClickListener {
            val intent= Intent()
            intent.type="image/*"
            intent.action= Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent,"Pick Image"), 438)
        }

        recibirMensajes()
    }

    private fun recibirMensajes() {
        mChatList=ArrayList()
        (mChatList as ArrayList<MensajeGrupal>).clear()

        val chatgrupal=groupRef.child(grupoId).child("mensajes")
        chatgrupal.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                (mChatList as ArrayList<MensajeGrupal>).clear()
                for (snap in snapshot.children) {
                    var mensaje: MensajeGrupal = snap.getValue(MensajeGrupal::class.java) as MensajeGrupal //se mapea de un texto a un objeto, en este caso mensaje
                    if(mensaje.timeStamp !=""){
                        val dateFormater= SimpleDateFormat("dd/MM/yyyy - HH:mm:SS", Locale.getDefault())
                        val fecha=dateFormater?.format(mensaje?.timeStamp as Long)
                        mensaje.timeStamp=fecha!!
                    }
                    if (mensaje.de == firebaseUser!!.uid.toString())
                        mensaje.esMio = true
                        (mChatList as ArrayList<MensajeGrupal>).add(mensaje)

                }
                if ((mChatList as ArrayList<MensajeGrupal>).size>0) {
                    chatsAdapter?.notifyDataSetChanged()

                    recycler_view_chats.smoothScrollToPosition((mChatList as ArrayList<MensajeGrupal>).size - 1)
                    //moviendo el recycler view conforme se actualiza el contenido
                }
                chatsAdapter= ChatGrupalAdapter(this@MessageChatActivityGroup,(mChatList as ArrayList<MensajeGrupal>))
                recycler_view_chats.adapter=chatsAdapter
                //recycler_view_chats.adapter=ChatGrupalAdapter(this@SubG_main,listaGrupos)
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    private fun enviarMensaje(mensaje: MensajeGrupal) {
        //val groupRef=database.getReference("ChatGrupal")

        val mensajeFirebase = groupRef.child(grupoId)
            .child("mensajes").push()
        mensaje.id = mensajeFirebase.key ?: ""
        mensajeFirebase.setValue(mensaje)
    }

    /*
    * private fun recibirMensajes() {
        mChatList=ArrayList()
        val chatgrupal=groupRef.child(grupoId)
        chatgrupal.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                (mChatList as ArrayList<MensajeGrupal>).clear()
                for (snap in snapshot.children) {
                    val mensaje: MensajeGrupal = snap.getValue(MensajeGrupal::class.java) as MensajeGrupal //se mapea de un texto a un objeto, en este caso mensaje
                    if (mensaje.de == firebaseUser!!.uid.toString())
                        mensaje.esMio = true
                    (mChatList as ArrayList<MensajeGrupal>).add(mensaje)
                }
                if ((mChatList as ArrayList<MensajeGrupal>).size>0) {
                    chatsAdapter?.notifyDataSetChanged()
                    chatsAdapter= ChatGrupalAdapter(this@MessageChatActivityGroup,(mChatList as ArrayList<Chat>))
                    recycler_view_chats.smoothScrollToPosition((mChatList as ArrayList<MensajeGrupal>).size - 1) //moviendo el recycler view conforme se actualiza el contenido
                }
                //recycler_view_chats.adapter=ChatGrupalAdapter(this@SubG_main,listaGrupos)
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
    *
    * */


}