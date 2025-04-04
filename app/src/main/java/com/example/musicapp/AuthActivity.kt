package com.example.musicapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_auth)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val Login: EditText = findViewById(R.id.loginInput)
        val Password: EditText = findViewById(R.id.passwordInput)
        val enterButton: Button = findViewById(R.id.enterButton)
        val regLink: TextView = findViewById(R.id.regLink)

        enterButton.setOnClickListener{
            val MainIntent = Intent(this,MainActivity::class.java)
            startActivity(MainIntent)
        }

        regLink.setOnClickListener{
            val RegIntent = Intent(this,RegActivity::class.java)
            startActivity(RegIntent)
        }


        fun validateInput(editText: EditText): Boolean {
            val input = editText.text.toString().trim()

            return when {
                input.isEmpty() -> {
                    editText.error = "Поле не может быть пустым"
                    false
                }

                else -> true
            }
        }
    }
}