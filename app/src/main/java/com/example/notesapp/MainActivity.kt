package com.example.notesapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    lateinit var rvNotes: RecyclerView
    lateinit var notesList: ArrayList<NoteData>
    private var db = Firebase.firestore

    override fun onResume() {
        super.onResume()
        rvNotes = findViewById(R.id.rvNotes)
        rvNotes.layoutManager = LinearLayoutManager(this)
        notesList = arrayListOf()
        db = FirebaseFirestore.getInstance()
        db.collection("notes").get()
            .addOnSuccessListener {
                if (!it.isEmpty){
                    for (data in it.documents){
                        val note: NoteData? = data.toObject(NoteData::class.java)
                        if (note != null){
                            notesList.add(note)
                        }
                    }
                    rvNotes.adapter = NoteAdapter(notesList)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to fetch data", Toast.LENGTH_LONG).show()
            }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btAddNote = findViewById<FloatingActionButton>(R.id.BtAddNote)
        rvNotes = findViewById(R.id.rvNotes)
        btAddNote.setOnClickListener {
            val i = Intent(this, NoteActivity::class.java)
            startActivity(i)
        }

    }
}