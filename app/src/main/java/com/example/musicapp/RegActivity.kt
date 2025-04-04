package com.example.musicapp

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class RegActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reg)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val animationBackground: LinearLayout = findViewById(R.id.animatedBackground)
        val animationDrawable = animationBackground.background as AnimationDrawable

        val Login: EditText = findViewById(R.id.loginInput)
        val Mail: EditText = findViewById(R.id.emailInput)
        val Password: EditText = findViewById(R.id.passwordInput)
        val RePassword: EditText = findViewById(R.id.passwordReInput)
        val regButton: Button = findViewById(R.id.regButton)
        val loginLink: TextView = findViewById(R.id.loginLink)

        regButton.setOnClickListener {
            val cond: Boolean = validateLogin(Login)&&validateMail(Mail)&&validatePassword(Password)&&validateRePassword(Password,RePassword)
            if(validateLogin(Login) || validateMail(Mail) || validatePassword(Password) || validateRePassword(Password,RePassword)){
                Toast.makeText(this,"Пользователь авторизован!", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"Пользователь НЕ авторизован!", Toast.LENGTH_LONG).show()
            }
        }

        loginLink.setOnClickListener{
            val intent = Intent(this,AuthActivity::class.java)
            startActivity(intent)
        }
    }

    // Устанавливаем параметры анимации
//        animationDrawable.setEnterFadeDuration(1000)
//        animationDrawable.setExitFadeDuration(1000)
//
//        // Запускаем анимацию
//        animationDrawable.start()


    fun validateLogin(Login: EditText): Boolean {

        val errTextView: TextView = findViewById(R.id.loginError)
        if (!validateInput(Login,errTextView)) { return false }
        if (!validateLength(Login,2,10,errTextView)) { return false }

        return true
    }

    fun validateMail(Mail: EditText): Boolean {

        val errTextView: TextView = findViewById(R.id.emailError)
        if (!validateInput(Mail,errTextView)) { return false }
        if (!checkEmail(Mail,errTextView)) { return false }

        return true
    }

    fun validatePassword(Password: EditText): Boolean {

        val errTextView: TextView = findViewById(R.id.passwordError)
        if (!validateInput(Password,errTextView)) { return false }
        if (!validateLength(Password,8,16,errTextView)) { return false }
        if (!checkPassword(Password,errTextView)) { return false }

        return true
    }

    fun validateRePassword(Password: EditText, RePassword: EditText): Boolean {

        val errTextView: TextView = findViewById(R.id.passwordReError)
        if (!validateInput(RePassword,errTextView)) { return false }
        if (!validateLength(RePassword,8,16,errTextView)) { return false }
        if (!checkRePassword(Password,RePassword,errTextView)) { return false }

        return true
    }


    fun MakeErrVisible(editText: EditText,errTextView: TextView, message: String){
        editText.backgroundTintList = ColorStateList.valueOf(Color.RED)
        errTextView.visibility = View.VISIBLE
        errTextView.text = message
    }

    fun MakeErrGone(editText: EditText, errTextView: TextView){
        editText.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
        errTextView.visibility = View.GONE
        errTextView.text = ""
    }

    fun validateInput(editText: EditText, errTextView: TextView): Boolean {
        val input = editText.text.toString().trim()

        return when {
            input.isEmpty() -> {
                MakeErrVisible(editText,errTextView,"Поле не может быть пустым")
                false
            }

            else -> {
                MakeErrGone(editText,errTextView)
                true
            }
        }
    }

    fun validateLength(editText: EditText, minLength: Int, maxLength: Int, errTextView: TextView): Boolean {
        val input = editText.text.toString().trim()

        return when {
            input.length < minLength -> {
                MakeErrVisible(editText,errTextView,"Минимум $minLength символов")
                false
            }

            input.length > maxLength -> {
                MakeErrVisible(editText,errTextView,"Максимум $maxLength символов")
                false
            }

            else -> {
                MakeErrGone(editText,errTextView)
                true}
        }
    }

    fun checkEmail(editText: EditText,errTextView: TextView): Boolean {
        val email = editText.text.toString().trim()
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+"

        return if (!email.matches(emailPattern.toRegex())) {
            MakeErrVisible(editText,errTextView,"Некорректный email")
            false
        } else {
            MakeErrGone(editText,errTextView)
            true}
    }

    fun checkPassword(editText: EditText,errTextView: TextView): Boolean {
        val password = editText.text.toString().trim()

        return when {

            !password.matches(Regex(".*[a-zA-Zа-яА-ЯёЁ].*[a-zA-Zа-яА-ЯёЁ].*[a-zA-Zа-яА-ЯёЁ].*[a-zA-Zа-яА-ЯёЁ].*")) -> {
                MakeErrVisible(editText,errTextView,"Нужно минимум 4 буквы")
                false
            }

            !password.matches(Regex(".*\\d.*")) -> {
                MakeErrVisible(editText,errTextView,"Нужна хотя бы 1 цифра")
                false
            }

            !password.matches(Regex("^[a-zA-Zа-яА-ЯёЁ0-9]*$")) -> {
                MakeErrVisible(editText,errTextView,"Только буквы и цифры")
                false
            }

            else -> {
                MakeErrGone(editText,errTextView)
                true
            }
        }
    }

    fun checkRePassword(PasswordText: EditText, RePasswordText: EditText,errTextView: TextView): Boolean {
        val Password = PasswordText.text.toString().trim()
        val RePassword = RePasswordText.text.toString().trim()


        return if (Password != RePassword) {
            MakeErrVisible(RePasswordText,errTextView,"Пароли не совпадают")
            false
        } else {
            MakeErrGone(RePasswordText,errTextView)
            true}
    }
}