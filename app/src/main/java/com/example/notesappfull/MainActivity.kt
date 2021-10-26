package com.example.notesappfull

import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesappfull.Models.DBHelper
import com.example.notesappfull.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var dbHelper:DBHelper
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
         dbHelper= DBHelper(this)

        var notes=dbHelper.getNotes()
        binding.recyclerView.adapter=RVAdapter(notes,this)
        binding.recyclerView.layoutManager=LinearLayoutManager(this)
        binding.btnAddNote.setOnClickListener{
            val note=binding.edNote.text.toString()

                if (note.isNotEmpty())
                {
                    val status=dbHelper.addNote(note)
                    if (status!=-1L)
                    {
                        Toast.makeText(applicationContext, "note added", Toast.LENGTH_SHORT).show()
                        updateRecycler()
                        binding.edNote.text.clear()

                    }else
                    {
                        Toast.makeText(applicationContext, "error db", Toast.LENGTH_SHORT).show()
                    }
                }else
                {
                    Toast.makeText(applicationContext, "please enter text", Toast.LENGTH_SHORT).show()
                }
        }
    }
    fun updateRecycler(){
        var notes=dbHelper.getNotes()
        binding.recyclerView.adapter=RVAdapter(notes,this)
        binding.recyclerView.layoutManager=LinearLayoutManager(this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
    fun alertDialog(id:Int,note:String){

        val dialogBuild= AlertDialog.Builder(this)
        val lLayout= LinearLayout(this)
        val tvAlert= TextView(this)
        val edAlert= EditText(this)
        tvAlert.text=" Update Note  "
        edAlert.setSingleLine()
        edAlert.setText(note)
        lLayout.addView(tvAlert)
        lLayout.addView(edAlert)
        lLayout.setPadding(50,40,50,10)

        dialogBuild.setNegativeButton("cancel", DialogInterface.OnClickListener { _, _ ->

        })
        dialogBuild.setPositiveButton("save",DialogInterface.OnClickListener { _, _ ->
            dbHelper.updateOrDelete(id,edAlert.text.toString(),true)
            updateRecycler()
        })
        val aler=dialogBuild.create()
        aler.setView(lLayout)
        aler.show()
    }
}