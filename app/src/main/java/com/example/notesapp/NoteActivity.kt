package com.example.notesapp

import android.os.Bundle
import android.view.inputmethod.InputBinding
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NoteActivity : AppCompatActivity() {
    lateinit var EtTitle: EditText
    lateinit var EtContent: EditText
    lateinit var BtSaveNote: ImageButton
    val firestore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_note)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        EtTitle = findViewById(R.id.EtTitle)
        EtContent = findViewById(R.id.EtContent)
        BtSaveNote = findViewById(R.id.BtSaveNote)

        BtSaveNote.setOnClickListener {
            val title = EtTitle.text.toString()
            val content = EtContent.text.toString()
            if (title.isNotEmpty() || content.isNotEmpty()) {
                EtTitle.setError("Title is required")
            }
            val note = hashMapOf(
                "title" to title,
                "content" to content
            )
            firestore.collection("notes").document(title).set(note)
                .addOnSuccessListener {
                    Toast.makeText(this, "Note added", Toast.LENGTH_LONG).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to add", Toast.LENGTH_LONG).show()
                }
        }
    }
}