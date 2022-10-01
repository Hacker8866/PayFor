package com.example.payfor

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        loadData()
    }

    private fun loadData() {
        val user = auth.currentUser
        val userReference = databaseReference?.child(user?.uid!!)

        userReference?.addValueEventListener(object : ValueEventListener {
            val text1 = findViewById<TextView>(R.id.name1)
            val text2 = findViewById<TextView>(R.id.name2)
            override fun onDataChange(snapshot: DataSnapshot) {
                text1.text = "Familiya-> " + snapshot.child("firstname").value.toString()
                text2.text = "Ism-> " + snapshot.child("lastname").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        val logoutbtn = findViewById<Button>(R.id.chiqbtn)

        logoutbtn.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }
}