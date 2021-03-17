package com.example.trobify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView

class Chat : AppCompatActivity() {
    private lateinit var profilePicture: CircleImageView
    private lateinit var profileName: TextView
    private lateinit var rvMessage: RecyclerView
    private lateinit var txtMessage: EditText
    private lateinit var sendButton: Button

    //para probar el chat
    private lateinit var adapter : MessagesAdapter

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        profilePicture = findViewById<CircleImageView>(R.id.profilePicture)
        profileName = findViewById<TextView>(R.id.profileName)
        rvMessage = findViewById<RecyclerView>(R.id.rvMessages)
        txtMessage = findViewById<EditText>(R.id.txtMessage)
        sendButton= findViewById<Button>(R.id.sendButton)

        adapter = MessagesAdapter(this)
        var ly : LinearLayoutManager = LinearLayoutManager(this)


    }
}