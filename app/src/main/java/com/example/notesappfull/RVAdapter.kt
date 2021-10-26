package com.example.notesappfull

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesappfull.Models.DBHelper
import com.example.notesappfull.Models.Note
import com.example.notesappfull.databinding.ItemRowBinding

class RVAdapter(val notes:ArrayList<Note>,val mainActivity: MainActivity):RecyclerView.Adapter<RVAdapter.RVHolder>() {
    class RVHolder(view: View):RecyclerView.ViewHolder(view){
        val binding=ItemRowBinding.bind(view)
        val dbHelper=DBHelper(binding.root.context)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVAdapter.RVHolder {
        return RVHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.item_row,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: RVAdapter.RVHolder, position: Int) {

       with(holder) {
           val id =notes[position].id
           val note =notes[position].note
         binding.textView.text=note

           binding.imageButton.setOnClickListener {
              val status= dbHelper.updateOrDelete(id,null,false)
               if (status!=-1){
                   Toast.makeText(mainActivity.applicationContext , "item is deleted", Toast.LENGTH_SHORT).show()
                   mainActivity.updateRecycler()

               }
           }

           binding.imageButton2.setOnClickListener {


                mainActivity.alertDialog(id,note)

           }

        }
    }

    override fun getItemCount()=notes.size
}