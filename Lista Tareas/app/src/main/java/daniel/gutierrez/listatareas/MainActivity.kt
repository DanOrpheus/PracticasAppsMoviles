package daniel.gutierrez.listatareas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.room.Room

class MainActivity : AppCompatActivity() {
    lateinit var et_tarea: EditText
    lateinit var btn_agregar: Button
    lateinit var lv_tareas: ListView
    lateinit var list_tareas: ArrayList<String>
    lateinit var adapter: ArrayAdapter<String>

    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et_tarea = findViewById(R.id.et_tarea)
        btn_agregar = findViewById(R.id.btn_agregar)
        lv_tareas = findViewById(R.id.lv_tareas)

        list_tareas = ArrayList()
        //list_tareas.add("prueba")
        //list_tareas.add("prueba2")

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "tareas-db"
        ).build()


        adapter=ArrayAdapter(this, android.R.layout.simple_list_item_1, list_tareas)
        lv_tareas.adapter = adapter

        btn_agregar.setOnClickListener{
            var tarea_str = et_tarea.text.toString()

            if(!tarea_str.isNullOrEmpty()){

                //agrega la tarea a la BD
                var tarea = Tarea(desc = tarea_str)
                db.tareaDao().agregarTarea(tarea)

                list_tareas.add(tarea_str)
                adapter.notifyDataSetChanged()
                et_tarea.setText("")
            }else{
                Toast.makeText(this,"Llena el campo", Toast.LENGTH_SHORT).show()
            }
        }

        lv_tareas.onItemClickListener = AdapterView.OnItemClickListener{parent, view, position,id ->
            list_tareas.removeAt(position)
            adapter.notifyDataSetChanged()
        }

    }

    private fun cargarTareas(){
        var lista_db = db.tareaDao().obtenerTareas()
        for(tarea in lista_db){
            list_tareas.add(tarea.desc)
        }
    }
}