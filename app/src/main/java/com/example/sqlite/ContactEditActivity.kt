package com.example.sqlite

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ContactEditActivity : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var surname: EditText
    private lateinit var birthData: EditText
    private lateinit var phone: EditText

    private lateinit var cancel: Button
    private lateinit var save: Button

    private val dbHelper = DBHelper(this)

    companion object {
        const val RESULT_KEY = "result"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact_edit)

        val idText = intent.getStringExtra(MainActivity.EXTRA_KEY)
        val id = (idText.toString()).toInt()

        name = findViewById<EditText>(R.id.editTextName)
        surname = findViewById<EditText>(R.id.editTextSurname)
        birthData = findViewById<EditText>(R.id.editTextBirthData)
        phone = findViewById<EditText>(R.id.editTextPhone)

        cancel= findViewById<Button>(R.id.buttonCancel)
        save= findViewById<Button>(R.id.buttonSave)

        if(id != -1){
            val listContacts = dbHelper.getContacts()
            for (contact in listContacts) {
                if (contact.id == id) {
                    name.setText(contact.name)
                    surname.setText(contact.surname)
                    birthData.setText(contact.birthData)
                    phone.setText(contact.phoneNumber)
                    break
                }

            }
        }

        save.setOnClickListener {
            if(id == -1){
                if((name.text.toString() != "" || surname.text.toString() != "") && phone.text.toString() != ""){
                    dbHelper.addContact(name.text.toString(),surname.text.toString(),birthData.text.toString(),phone.text.toString())
                    finishActivity()
                }



            }else{
                if((name.text.toString() != "" || surname.text.toString() != "") && phone.text.toString() != "") {
                    dbHelper.updateContact(
                        id,
                        name.text.toString(),
                        surname.text.toString(),
                        birthData.text.toString(),
                        phone.text.toString(),
                        true
                    )
                }
                finishActivity()
            }

        }
        cancel.setOnClickListener {
            finish()
        }
    }

    fun finishActivity(){
        val returnIntent = Intent()
        returnIntent.putExtra(RESULT_KEY, "1")
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }
}