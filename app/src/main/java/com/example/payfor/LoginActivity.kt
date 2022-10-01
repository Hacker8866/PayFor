package com.example.payfor

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val curUser = auth.currentUser
        if (curUser != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        login()
    }

    private fun login() {
        val login = findViewById<Button>(R.id.kirbtn)
        val email = findViewById<EditText>(R.id.edit11)
        val password = findViewById<EditText>(R.id.edit22)

        login.setOnClickListener {
            if (TextUtils.isEmpty(email.text.toString())) {
                email.error = "Iltimos emailingizni kiriting"
                return@setOnClickListener
            } else if (TextUtils.isEmpty(password.text.toString())) {
                password.error = "Iltimos parolingizni kiriting"
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener() {
                    if (it.isSuccessful) {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Tizimga kirishda xatolik", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        val reg = findViewById<TextView>(R.id.reg)
        reg.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }
    }
}