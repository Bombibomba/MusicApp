package com.example.musicapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DbHelper(val context: Context, val factory: SQLiteDatabase.CursorFactory?):SQLiteOpenHelper(context,"MusicApp",factory,1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE users (id int PRIMARY KEY, login TEXT, email TEXT, password TEXT)"
        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    fun addUser(user: User){
        val values = ContentValues()
        values.put("login",user.login)
        values.put("email", user.email)
        values.put("password", user.password)

        val db = this.writableDatabase
        db.insert("users",null,values)
        db.close()
    }

    fun getUser(login: String, password: String): Boolean{
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(
                "SELECT * FROM users WHERE login=? AND password=?",
                arrayOf(login, password)
            )
            return cursor.moveToFirst()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        } finally {
            cursor?.close()
            db.close()
        }
    }
}