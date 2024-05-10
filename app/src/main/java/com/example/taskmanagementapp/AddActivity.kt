package com.example.taskmanagementapp

import MyDatabaseHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AddActivity : AppCompatActivity() {

    private lateinit var titleInput: EditText
    private lateinit var authorInput: EditText
    private lateinit var pagesInput: EditText
    private lateinit var addButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        titleInput = findViewById(R.id.title_input)
        authorInput = findViewById(R.id.author_input)
        pagesInput = findViewById(R.id.pages_input)
        addButton = findViewById(R.id.deleteAll_button)

        addButton.setOnClickListener {
            val title = titleInput.text.toString().trim()
            val author = authorInput.text.toString().trim()
            val pagesText = pagesInput.text.toString().trim()

            if (title.isNotEmpty() && author.isNotEmpty() && pagesText.isNotEmpty()) {
                val pages = pagesText.toIntOrNull()
                if (pages != null && pages > 0) {
                    val myDB = MyDatabaseHelper(this@AddActivity)
                    myDB.addBook(title, author, pages)
                    Toast.makeText(this@AddActivity, "Book added successfully!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@AddActivity, "Please enter a valid number of pages.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@AddActivity, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
