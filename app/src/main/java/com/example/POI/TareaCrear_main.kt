package com.example.POI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class TareaCrear_main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarea_crear_main)


        val TareaCrear = findViewById<Button>(R.id.btn_crearTarea)


        TareaCrear.setOnClickListener{
            val VentanaTareaVer: Intent = Intent(applicationContext,TareaVer_main::class.java)
            startActivity(VentanaTareaVer)

        }


    }
}