package com.example.payfor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    lateinit var auth : FirebaseAuth
    var databaseReference : DatabaseReference? = null
    var database : FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")
        regiter()
    }

    private fun regiter(){
        val regButton = findViewById<Button>(R.id.saqbtn)
        val firstname = findViewById<EditText>(R.id.edit1)
        val lastname = findViewById<EditText>(R.id.edit2)
        val email = findViewById<EditText>(R.id.edit3)
        val password = findViewById<EditText>(R.id.edit4)

        regButton.setOnClickListener {
            if(TextUtils.isEmpty(firstname.text.toString())){
                firstname.error = "Iltimos Familiyangizni kiriting."
                return@setOnClickListener
            }else if (TextUtils.isEmpty(lastname.text.toString())){
                lastname.error = "Iltimos Ismingizni kiriting"
                return@setOnClickListener
            }else if (TextUtils.isEmpty(email.text.toString())){
                email.error = "Iltimos emailingizni kiriting"
                return@setOnClickListener
            }else if (TextUtils.isEmpty(password.text.toString())){
                password.error = "Iltimos parolingizni kiriting"
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email.text.toString(),password.text.toString())
                .addOnCompleteListener(){
                    if (it.isSuccessful){
                        val currentUser = auth.currentUser
                        val currentUserDB = databaseReference?.child(currentUser?.uid!!)
                        currentUserDB?.child("firstname")?.setValue(firstname.text.toString())
                        currentUserDB?.child("lastname")?.setValue(lastname.text.toString())
                        Toast.makeText(this, "Ro'yhatdan muvafaqqiyatli o'tdingiz", Toast.LENGTH_SHORT).show()
                        finish()
                    }else{
                        Toast.makeText(this, "Ro'yhatdan o'tish amalga oshmadi", Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }
}