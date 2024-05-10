package com.example.taskmanagementapp

import MyDatabaseHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class UpdateActivity : AppCompatActivity() {

    private lateinit var titleInput: EditText
    private lateinit var authorInput: EditText
    private lateinit var pagesInput: EditText
    private lateinit var updateButton: Button
    private lateinit var deleteButton: Button
    private var rowId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        titleInput = findViewById(R.id.title_input2)
        authorInput = findViewById(R.id.author_input2)
        pagesInput = findViewById(R.id.pages_input2)
        updateButton = findViewById(R.id.update_button)
        deleteButton = findViewById(R.id.delete_button)

        val intent = intent
        rowId = intent.getStringExtra("id")
        titleInput.setText(intent.getStringExtra("title"))
        authorInput.setText(intent.getStringExtra("author"))
        pagesInput.setText(intent.getStringExtra("pages"))

        updateButton.setOnClickListener {
            val title = titleInput.text.toString().trim()
            val author = authorInput.text.toString().trim()
            val pagesString = pagesInput.text.toString().trim()

            if (title.isNotEmpty() && author.isNotEmpty() && pagesString.isNotEmpty()) {
                val pages = pagesString.toInt()
                val myDB = MyDatabaseHelper(this)
                myDB.updateData(rowId!!, title, author, pages)
                Toast.makeText(this, "Updated Successfully!", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
                finish()
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        deleteButton.setOnClickListener {
            val myDB = MyDatabaseHelper(this)
            myDB.deleteOneRow(rowId!!)
            Toast.makeText(this, "Deleted Successfully!", Toast.LENGTH_SHORT).show()
            setResult(RESULT_OK)
            finish()
        }
    }
}
