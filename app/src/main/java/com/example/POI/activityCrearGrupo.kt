package com.example.POI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.example.POI.ModelClasses.Grupo
import com.example.POI.ModelClasses.SubGrupo
import com.example.POI.ModelClasses.Users
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import kotlinx.android.synthetic.main.activity_crear_grupo.*

class activityCrearGrupo : AppCompatActivity() {

    //referencia a la base de datos
    var database=FirebaseDatabase.getInstance()
    //crear una ramita en la bd que se llamará groups
    val groupRef=database.getReference("groups")
    var mGroupsAlumnos:List<Users>?=null
    var mGroupsSubGrupos:List<SubGrupo>?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_grupo)

        val spinerGrupos=findViewById<Spinner>(R.id.ComboGruposCarreras)

        Btn_CrearGrupo.setOnClickListener {
            val groupName:String=findViewById<EditText>(R.id.txt_NombreGrupo).text.toString()
            if (groupName==""){
                Toast.makeText(this, "No debes dejar ningún campo vacío", Toast.LENGTH_SHORT).show()
            }
            else{
                mGroupsSubGrupos=ArrayList()
                mGroupsAlumnos=ArrayList()
                val grupoFirebase=groupRef.push()
                txt_NombreGrupo.text.clear()

                val grupo=Grupo("",groupName,ServerValue.TIMESTAMP )
                grupo.setUID(grupoFirebase.key?:"")
                grupoFirebase.setValue(grupo)
            }
        }


    }
}