package com.example.taskmanagementapp

import MyDatabaseHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var addButton: FloatingActionButton
    private lateinit var emptyImageView: ImageView
    private lateinit var noDataTextView: TextView

    private lateinit var myDB: MyDatabaseHelper
    private lateinit var bookId: ArrayList<String>
    private lateinit var bookTitle: ArrayList<String>
    private lateinit var bookAuthor: ArrayList<String>
    private lateinit var bookPages: ArrayList<String>
    private var customAdapter: CustomAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        addButton = findViewById(R.id.deleteAll_button)
        emptyImageView = findViewById(R.id.empty_imageview)
        noDataTextView = findViewById(R.id.no_data)

        addButton.setOnClickListener {
            val intent = Intent(this@MainActivity, AddActivity::class.java)
            startActivity(intent)
        }

        myDB = MyDatabaseHelper(this@MainActivity)
        bookId = ArrayList()
        bookTitle = ArrayList()
        bookAuthor = ArrayList()
        bookPages = ArrayList()

        storeDataInArrays()

        customAdapter = CustomAdapter(this@MainActivity, this, bookId, bookTitle, bookAuthor, bookPages)
        recyclerView.adapter = customAdapter
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.delete_all){
            confirmDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete All?")
        builder.setMessage("Are you sure you want to delete all Data?")
        builder.setPositiveButton("Yes") { dialogInterface, i ->
            myDB.deleteAllData()
            recreate()
        }
        builder.setNegativeButton("No") { dialogInterface, i -> }

        builder.create().show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1 && resultCode == RESULT_OK){
            recreate()
        }
    }

    private fun storeDataInArrays() {
        val cursor = myDB.readAllData()
        if (cursor != null) {
            if(cursor.count == 0){
                emptyImageView.visibility = View.VISIBLE
                noDataTextView.visibility = View.VISIBLE
            } else {
                while (cursor.moveToNext()) {
                    bookId.add(cursor.getString(0))
                    bookTitle.add(cursor.getString(1))
                    bookAuthor.add(cursor.getString(2))
                    bookPages.add(cursor.getString(3))
                }
                emptyImageView.visibility = View.GONE
                noDataTextView.visibility = View.GONE
            }
        }
    }
}
