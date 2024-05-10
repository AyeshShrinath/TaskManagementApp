package com.example.taskmanagementapp

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class UpdateActivity : AppCompatActivity() {

    private lateinit var title_input: EditText
    private lateinit var author_input: EditText
    private lateinit var pages_input: EditText
    private lateinit var update_button: Button
    private lateinit var delete_button: Button

    private var id: String? = null
    private var title: String? = null
    private var author: String? = null
    private var pages: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        title_input = findViewById(R.id.title_input2)
        author_input = findViewById(R.id.author_input2)
        pages_input = findViewById(R.id.pages_input2)
        update_button = findViewById(R.id.update_button)
        delete_button = findViewById(R.id.delete_button)

        // First we call this
        getAndSetIntentData()

        // Set actionbar title after getAndSetIntentData method
        supportActionBar?.title = title

        update_button.setOnClickListener {
            // And only then we call this
            val myDB = MyDatabaseHelper(this@UpdateActivity)
            title = title_input.text.toString().trim()
            author = author_input.text.toString().trim()
            pages = pages_input.text.toString().trim()
            myDB.updateData(id!!, title!!, author!!, pages!!)
        }

        delete_button.setOnClickListener {
            confirmDialog()
        }
    }

    private fun getAndSetIntentData() {
        if (intent.hasExtra("id") && intent.hasExtra("title") &&
            intent.hasExtra("author") && intent.hasExtra("pages")) {
            // Getting Data from Intent
            id = intent.getStringExtra("id")
            title = intent.getStringExtra("title")
            author = intent.getStringExtra("author")
            pages = intent.getStringExtra("pages")

            // Setting Intent Data
            title_input.setText(title)
            author_input.setText(author)
            pages_input.setText(pages)
            Log.d("stev", "$title $author $pages")
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun confirmDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete $title ?")
        builder.setMessage("Are you sure you want to delete $title ?")
        builder.setPositiveButton("Yes") { dialogInterface, _ ->
            val myDB = MyDatabaseHelper(this@UpdateActivity)
            myDB.deleteOneRow(id!!)
            finish()
        }
        builder.setNegativeButton("No") { _, _ -> }

        builder.create().show()
    }
}
