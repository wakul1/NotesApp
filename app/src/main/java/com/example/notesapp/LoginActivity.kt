package com.example.notesapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var etEmail: EditText
    lateinit var etPass: EditText
    lateinit var BtLogin: Button
    lateinit var tvRegist: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        etEmail = findViewById(R.id.etEmail)
        etPass = findViewById(R.id.etPass)
        BtLogin = findViewById(R.id.BtLogin)
        tvRegist = findViewById(R.id.tvRegist)

        tvRegist.setOnClickListener(){
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
        }
        BtLogin.setOnClickListener(){
            val email = etEmail.text.toString()
            val pass = etPass.text.toString()
            auth = FirebaseAuth.getInstance()
            auth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener{
                    if (it.isSuccessful) {
                        val i = Intent(this, MainActivity::class.java)
                        startActivity(i)
                        //updateUI(user)
                    } else {
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        //updateUI(null)
                    }

                }

        }
    }
}