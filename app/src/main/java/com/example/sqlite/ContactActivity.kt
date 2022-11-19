package com.example.sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView

class ContactActivity : AppCompatActivity() {

    private lateinit var buttonBack: Button
    private lateinit var buttonEdit: Button
    private lateinit var buttonDelete: Button
    private lateinit var name: TextView
    private lateinit var surname: TextView
    private lateinit var birthData: TextView
    private lateinit var phone: TextView

    private val dbHelper = DBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact)
        val id = intent.getStringExtra(MainActivity.EXTRA_KEY)

        name = findViewById<TextView>(R.id.name)
        surname = findViewById<TextView>(R.id.surname)
        birthData = findViewById<TextView>(R.id.birthData)
        phone = findViewById<TextView>(R.id.phoneNumber)

        buttonBack = findViewById<Button>(R.id.buttonBack)

        val listContacts = dbHelper.getContacts()
        for (contact in listContacts) {
            if(contact.id.toString() == id){
                name.text = contact.name
                surname.text = contact.surname
                birthData.text = contact.birthData
                phone.text = contact.phoneNumber
                break
            }

        }
        buttonBack.setOnClickListener {
            finish()
        }
    }
}