package com.example.sqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var enterText: EditText
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

        enterText = findViewById<EditText>(R.id.enterText)
        buttonAdd = findViewById<Button>(R.id.buttonAdd)
        listText = findViewById<RecyclerView>(R.id.recyclerView)

        //val list = mutableListOf<String>()
        //val adapter = RecyclerAdapter(list)

        adapter = RecyclerAdapter(list)  {
            // адаптеру передали обработчик удаления элемента
            if(it != -1){
            val name = list[it]
            list.removeAt(it)
            adapter.notifyItemRemoved(it)


            val listTod = dbHelper.getTodos()
            for (todo in listTod) {
                if(todo.title == name) {
                    dbHelper.removeTodo(todo.id)
                }
            }

        }
        }

        listText.layoutManager = LinearLayoutManager(this)
        listText.adapter = adapter

        val listTod = dbHelper.getTodos()
        for (todo in listTod) {
            list.add(todo.title)
            adapter.notifyItemInserted(list.lastIndex)
        }

        buttonAdd.setOnClickListener {
            dbHelper.addTodo(enterText.text.toString())
            val listTod = dbHelper.getTodos()
            val s = StringBuilder()
            for (todo in listTod) {
                s.append("${todo.id} ${todo.title}\n")
            }
            list.add(enterText.text.toString())
            adapter.notifyItemInserted(list.lastIndex)
            enterText.setText("")
        }

    }
}