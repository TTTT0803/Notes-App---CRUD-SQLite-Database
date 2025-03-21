package com.example.notessqlite

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notessqlite.databinding.ActivityUpdateBinding

class UpdateNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db: NotesDatabaseHelper
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)
        noteId = intent.getIntExtra("note_id", -1)

        if (noteId == -1) {
            finish()
            return
        }

        val note = db.getNoteByID(noteId)
        binding.updateTitleEditText.setText(note.title)
        binding.updateContentEditText.setText(note.content)

        binding.updateSaveButton.setOnClickListener {
            val newTitle = binding.updateTitleEditText.text.toString().trim()
            val newContent = binding.updateContentEditText.text.toString().trim()

            if (newTitle.isBlank() || newContent.isBlank()) {
                Toast.makeText(this, "Title and description cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updateNote = Note(noteId, newTitle, newContent)
            db.updateNote(updateNote)

            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
