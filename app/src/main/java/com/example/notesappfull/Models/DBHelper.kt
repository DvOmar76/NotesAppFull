package com.example.notesappfull.Models

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context):SQLiteOpenHelper(context,"Notes",null,1) {
    val dbWrite:SQLiteDatabase=writableDatabase
    val reade:SQLiteDatabase=readableDatabase
    override fun onCreate(p0: SQLiteDatabase?) {

        if (p0 != null) {
            p0.execSQL("create table Notes(id integer PRIMARY KEY,note text)")
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) { }

    fun addNote(text:String): Long {
        val contentValues=ContentValues()
        contentValues.put("note",text)
       return dbWrite.insert("Notes",null,contentValues)
    }

    fun getNotes(): ArrayList<Note> {
        val notes=ArrayList<Note>()
        val cursor:Cursor=reade.query("Notes",null,null,null,null,null,null)
        if(cursor.moveToFirst()){
            var id=cursor.getInt(cursor.getColumnIndex("id"))
            var note=cursor.getString(cursor.getColumnIndex("note"))
            notes.add(Note(id,note))
            while (cursor.moveToNext()){
                id=cursor.getInt(cursor.getColumnIndex("id"))
                note=cursor.getString(cursor.getColumnIndex("note"))
                notes.add(Note(id,note))
            }
        }

        cursor.close()
        return notes
    }
   fun updateOrDelete(id:Int, note:String?, update:Boolean): Int {
       val cv=ContentValues()
     if (update)
     {
         cv.put("note",note)
         return dbWrite.update("Notes",cv,"id=?", arrayOf(id.toString()))
     }
     else
     {
         return dbWrite.delete("Notes","id=?", arrayOf(id.toString()))
     }

   }
}