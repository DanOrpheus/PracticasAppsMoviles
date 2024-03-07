package daniel.gutierrez.listatareas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var et_tarea: EditText
    lateinit var btn_agregar: Button
    lateinit var lv_tareas: ListView
    lateinit var list_tareas: ArrayList<String>
    lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et_tarea = findViewById(R.id.et_tarea)
        btn_agregar = findViewById(R.id.btn_agregar)
        lv_tareas = findViewById(R.id.lv_tareas)

        list_tareas = ArrayList()

        list_tareas.add("prueba")
        list_tareas.add("prueba2")
        adapter=ArrayAdapter(this, android.R.layout.simple_list_item_1, list_tareas)
        lv_tareas.adapter = adapter

        btn_agregar.setOnClickListener{
            var tarea = et_tarea.text.toString()

            if(!tarea.isNullOrEmpty()){
                list_tareas.add(tarea)
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
}