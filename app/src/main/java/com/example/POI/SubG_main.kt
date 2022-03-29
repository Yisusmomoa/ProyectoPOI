package com.example.POI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.POI.AdapterClasses.GroupsAdapter
import com.example.POI.ModelClasses.Chat
import com.example.POI.ModelClasses.Grupo
import com.example.POI.ModelClasses.Users
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_crear_grupo.*
import kotlinx.android.synthetic.main.activity_sub_gmain.*
import kotlinx.android.synthetic.main.fragment_chats.*
import kotlin.math.log

class SubG_main : AppCompatActivity() {
    private lateinit var listaGrupos:ArrayList<Grupo>
    private lateinit var listaAlumnos:ArrayList<Users>
    //private var adapterGrupos=GroupsAdapter(listaGrupos)

    private val database = FirebaseDatabase.getInstance() //primero obtener la referencia a la base de datos
    //esta representando nuestra base de datos, con esto podemos hacer un insert, update, etc

    //nos crea una rama llamada chats, donde iran los mensajes
    private val groupRef = database.getReference("groups")
    private val alumnosGrupo=database.getReference("groups").child("alumnosGrupo")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_gmain)

        val rvGrupos=findViewById<RecyclerView>(R.id.rvGrupos)
        rvGrupos.layoutManager=LinearLayoutManager(this)
        rvGrupos.setHasFixedSize(true)
        listaGrupos= arrayListOf<Grupo>()
        listaAlumnos= arrayListOf<Users>()



        cargarGrupos()
        //rvGrupos.adapter=adapterGrupos

        val Subgrupo = this.findViewById<ImageButton>(R.id.btn_sgrupo)
        val Chat = this.findViewById<ImageButton>(R.id.btn_chats)
        val Perfil = this.findViewById<ImageButton>(R.id.btn_perfil)
        val Recompensa = this.findViewById<ImageButton>(R.id.btn_recompensa)
        val Tarea = this.findViewById<ImageButton>(R.id.btn_tarea)


/////Boton para mandar a llamar ventana de chats/////

        Chat.setOnClickListener{
            val VentanaChat: Intent = Intent(applicationContext,Chat_main::class.java)
            startActivity(VentanaChat)
        }
/////Boton para mandar a llamar ventana de perfil/////

        Perfil.setOnClickListener{
            val VentanaPerfil: Intent = Intent(applicationContext,Perfil_main::class.java)
            startActivity(VentanaPerfil)
        }
/////Boton para mandar a llamar ventana de perfil/////

        Recompensa.setOnClickListener{
            val VentanaRecompensa: Intent = Intent(applicationContext,Recompensa_main::class.java)
            startActivity(VentanaRecompensa)
        }
/////Boton para mandar a llamar ventana de perfil/////

        Tarea.setOnClickListener{
            val VentanaTarea: Intent = Intent(applicationContext,Tarea_main::class.java)
            startActivity(VentanaTarea)
        }

        btn_CrearGrupo.setOnClickListener {
            val ventanaCrearGrupo:Intent= Intent(this,activityCrearGrupo::class.java)
            startActivity(ventanaCrearGrupo)
        }

    }

    private fun cargarGrupos(){


        var mGroupsAlumnos:List<Users>?=null
        var hashMapArray = ArrayList<HashMap<String, Any?>>()

        groupRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (grupoSnapshot in snapshot.children){
                        Log.d("grupo", "onDataChange: ${grupoSnapshot} ")
                        val grupo: Grupo? =grupoSnapshot.getValue(Grupo::class.java)
                        listaGrupos.add(grupo!!)
                        //(mGroupsAlumnos as ArrayList<Users>).clear()
                    }
                    rvGrupos.adapter=GroupsAdapter(this@SubG_main,listaGrupos)
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

        /*
        groupRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (p0 in snapshot.children){
                    val grupo: Grupo? = p0.getValue(Grupo::class.java)
                    listaGrupos.add(grupo!!)
                }
                adapterGrupos.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })*/
    }
}