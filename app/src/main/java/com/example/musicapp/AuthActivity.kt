package com.example.musicapp

import SessionManager
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
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
        val togglePasswordVisibility: ImageView = findViewById(R.id.togglePasswordVisibility)

        val sessionManager = SessionManager(this)
        val token = sessionManager.getAuthToken()

        if (token == null) {

            enterButton.setOnClickListener {
                val db = DbHelper(this, null)
                val isReg: Boolean =
                    db.getUser(Login.text.toString().trim(), Password.text.toString().trim())

                if (isReg) {
                    sessionManager.saveAuthToken("user_jwt_token_here")
                    val MainIntent = Intent(this, MainActivity::class.java)
                    startActivity(MainIntent)
                } else {
                    Toast.makeText(this, "Пользователь не найден!", Toast.LENGTH_LONG).show()
                }
            }

            regLink.setOnClickListener {
                val RegIntent = Intent(this, RegActivity::class.java)
                startActivity(RegIntent)
            }

            setupPasswordToggle(Password,togglePasswordVisibility);

        }
        else {
            val MainIntent = Intent(this, MainActivity::class.java)
            startActivity(MainIntent)
        }
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

    fun setupPasswordToggle(editText: EditText, toggleImageView: ImageView) {
        var isPasswordVisible = false

        toggleImageView.setOnClickListener {
            isPasswordVisible = !isPasswordVisible

            if (isPasswordVisible) {
                // Показываем пароль
                editText.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                toggleImageView.setImageResource(R.drawable.eye_visible)
            } else {
                // Скрываем пароль (точки)
                editText.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                toggleImageView.setImageResource(R.drawable.eye_invisible)
            }
        }
    }

}