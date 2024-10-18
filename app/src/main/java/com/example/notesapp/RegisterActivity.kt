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

class RegisterActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var etEmail: EditText
    lateinit var etPass: EditText
    lateinit var BtRegist: Button
    lateinit var tvLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        etEmail = findViewById(R.id.etEmail)
        etPass = findViewById(R.id.etPass)
        BtRegist = findViewById(R.id.BtRegist)
        tvLogin = findViewById(R.id.tvLogin)

        tvLogin.setOnClickListener(){
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }

        BtRegist.setOnClickListener(){
            val email = etEmail.text.toString()
            val pass = etPass.text.toString()
            auth = FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener{
                if (it.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    auth.getCurrentUser()?.sendEmailVerification()
                    Toast.makeText(
                        baseContext,
                        "Success, Check your email for verification",
                        Toast.LENGTH_SHORT,
                    ).show()
                    val user = auth.currentUser
                    val i = Intent(this, LoginActivity::class.java)
                    startActivity(i)
                    //updateUI(user)
                } else {
                    Toast.makeText(
                        baseContext,
                        "Account creation failed",
                        Toast.LENGTH_SHORT,
                    ).show()
                    //updateUI(null)
                }
            }
        }
    }
}