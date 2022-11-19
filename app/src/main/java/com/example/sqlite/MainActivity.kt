package com.example.sqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var searchText: SearchView
    private lateinit var buttonAdd: Button
    private lateinit var listText: RecyclerView

    private val list = mutableListOf<String>()
    private lateinit var adapter: RecyclerAdapter

    companion object {
        const val EXTRA_KEY = "EXTRA"
    }

    private val dbHelper = DBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchText = findViewById<SearchView>(R.id.searchText)
        buttonAdd = findViewById<Button>(R.id.buttonAdd)
        listText = findViewById<RecyclerView>(R.id.recyclerView)

        adapter = RecyclerAdapter(dbHelper.getContacts()) {
            val intent = Intent(this, ContactActivity::class.java)
            intent.putExtra(EXTRA_KEY, it.toString())
            startActivity(intent)

        }


        listText.layoutManager = LinearLayoutManager(this)
        listText.adapter = adapter

        val listContacts = dbHelper.getContacts()
        adapter.notifyItemInserted(dbHelper.getContacts().lastIndex)
        /*for (contact in listContacts) {
            list.add(contact.name + " " + contact.surname)
            adapter.notifyItemInserted(list.lastIndex)
        }*/

        /*buttonAdd.setOnClickListener {
            dbHelper.addContact(enterText.text.toString(), "", "", "")
            val listTod = dbHelper.getContacts()
            val s = StringBuilder()
            for (todo in listTod) {
                s.append("${todo.id} ${todo.name}\n")
            }
            list.add(enterText.text.toString())
            adapter.notifyItemInserted(list.lastIndex)
            enterText.setText("")
        }*/

    }
}