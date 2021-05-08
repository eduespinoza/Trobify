package com.example.trobify.controladores

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trobify.adapters.MessageAdapter
import com.example.trobify.R
import com.example.trobify.models.Message
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_chat.*

class ChatAct : AppCompatActivity() {
    private var chatId : String = ""
    private var user : String = ""
    private var message : String = ""

    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        intent.getStringExtra("chatId")?.let { chatId = it }
        intent.getStringExtra("user")?.let { user = it }
        intent.getStringExtra("message")?.let { message = it }

        if(message != ""){ sendMessageOffer()}

        if(chatId.isNotEmpty() && user.isNotEmpty()) {
            initViews()
        }
    }

    private fun initViews(){
        messagesRecylerView.layoutManager = LinearLayoutManager(this)
        messagesRecylerView.adapter = MessageAdapter(user)

        val bBack = findViewById<ImageButton>(R.id.buttonGoBack)
        bBack.setOnClickListener {
            val goBack = Intent(this, ListOfChats::class.java)
            goBack.putExtra("user",user)
            startActivity(goBack)
        }

        sendMessageButton.setOnClickListener { sendMessage() }

        val chatRef = db.collection("chats").document(chatId)

        chatRef.collection("messages").orderBy("dob", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { messages ->
                val listMessages = messages.toObjects(Message::class.java)
                (messagesRecylerView.adapter as MessageAdapter).setData(listMessages)
            }

        chatRef.collection("messages").orderBy("dob", Query.Direction.ASCENDING)
            .addSnapshotListener { messages, error ->
                if(error == null){
                    messages?.let {
                        val listMessages = it.toObjects(Message::class.java)
                        (messagesRecylerView.adapter as MessageAdapter).setData(listMessages)
                    }
                }
            }
    }

    private fun sendMessage(){
        val message = Message(
            message = messageTextField.text.toString(),
            from = user.toString()
        )

        db.collection("chats").document(chatId).collection("messages").document().set(message)

        messageTextField.setText("")


    }

    private fun sendMessageOffer(){
        val message = Message(
            message = message.toString(),
            from = user.toString()
        )

        db.collection("chats").document(chatId).collection("messages").document().set(message)

        messageTextField.setText("")
        initViews()


    }
}