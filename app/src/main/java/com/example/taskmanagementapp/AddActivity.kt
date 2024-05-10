package com.example.taskmanagementapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.taskmanagementapp.MyDatabaseHelper
import com.example.taskmanagementapp.R
import android.content.Intent

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
        addButton = findViewById(R.id.add_button)
        addButton.setOnClickListener {
            val myDB = MyDatabaseHelper(this@AddActivity)
            myDB.addBook(
                titleInput.text.toString().trim(),
                authorInput.text.toString().trim(),
                pagesInput.text.toString().trim().toInt()
            )

            val intent = Intent(this@AddActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
