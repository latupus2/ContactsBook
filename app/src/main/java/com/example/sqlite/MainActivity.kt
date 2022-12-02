package com.example.sqlite

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var searchText: SearchView
    private lateinit var buttonAdd: Button
    private lateinit var listText: RecyclerView

    private lateinit var adapter: RecyclerAdapter

    companion object {
        const val EXTRA_KEY = "EXTRA"
    }
    val REQUEST_CODE = 1
    val REQUEST_CODE2 = 3

    private val dbHelper = DBHelper(this)
    lateinit var list: MutableList<Contact>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchText = findViewById<SearchView>(R.id.searchText)
        buttonAdd = findViewById<Button>(R.id.buttonAdd)
        listText = findViewById<RecyclerView>(R.id.recyclerView)

        changeList()



        buttonAdd.setOnClickListener {
            val intent = Intent(this, ContactEditActivity::class.java)
            intent.putExtra(EXTRA_KEY, (-1).toString())
            startActivityForResult(intent, REQUEST_CODE)
           // startActivity(intent)
            //adapter.notifyItemInserted(dbHelper.getContacts().lastIndex)
        }

        searchText.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText.toString())
                return false
            }

        })
    }

    fun filter(text: String){
        if(text == ""){
            changeList()
        }else {
            list = (dbHelper.getContacts().filter { (it.name+" "+it.surname).contains(text, ignoreCase = true)}).toMutableList()
            changeListFiltered(list)
        }
    }
    fun changeListFiltered(filters: MutableList<Contact>){
        adapter = RecyclerAdapter(filters) {
            if(it != -1) {
                val intent = Intent(this, ContactActivity::class.java)
                intent.putExtra(EXTRA_KEY, it.toString())
                startActivityForResult(intent, REQUEST_CODE2)
                //startActivity(intent)
            }

        }
        adapter.notifyItemInserted(filters.lastIndex)
        listText.layoutManager = LinearLayoutManager(this)
        listText.adapter = adapter
    }

    fun changeList(){
        list = dbHelper.getContacts()
        adapter = RecyclerAdapter(list) {
            if(it != -1) {
                val intent = Intent(this, ContactActivity::class.java)
                intent.putExtra(EXTRA_KEY, it.toString())
                startActivityForResult(intent, REQUEST_CODE2)
                //startActivity(intent)
            }

        }
        adapter.notifyItemInserted(list.lastIndex)
        listText.layoutManager = LinearLayoutManager(this)
        listText.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        changeList()
    }
}