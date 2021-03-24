package com.example.trobify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trobify.models.Chat
import com.example.trobify.adapters.ChatAdapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_list_of_chats.*
import java.util.*

class ListOfChats : AppCompatActivity() {
    private var db = Firebase.firestore
    private var userId : String = ""
    private var user : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_chats)

        intent.getStringExtra("user")?.let { userId = it }
        db.collection("users").document(userId.toString()).get()
            .addOnSuccessListener { e ->
                if (userId.isNotEmpty()) {
                    user = e.get("email").toString()
                    initViews()
                }
            }
    }

    private fun initViews(){

        listChatsRecyclerView.layoutManager = LinearLayoutManager(this)
        listChatsRecyclerView.adapter =
            ChatAdapter { chat -> chatSelected(chat)
            }

        val userRef = db.collection("users").document(userId.toString())

        userRef.collection("chats")
            .get()
            .addOnSuccessListener { chats ->
                val listChats = chats.toObjects(Chat::class.java)

                (listChatsRecyclerView.adapter as ChatAdapter).setData(listChats)
            }

        userRef.collection("chats")
            .addSnapshotListener { chats, error ->
                if(error == null){
                    chats?.let {
                        val listChats = it.toObjects(Chat::class.java)

                        (listChatsRecyclerView.adapter as ChatAdapter).setData(listChats)
                    }
                }
            }
    }

    private fun chatSelected(chat: Chat){
        val intent = Intent(this, ChatAct::class.java)

        intent.putExtra("chatId", chat.id.toString())
        intent.putExtra("user", user.toString())
        startActivity(intent)
    }

    protected fun newChat(otherUser : String){
        val chatId = UUID.randomUUID().toString()
        val users = listOf(user, otherUser)
        var otherUserId = ""
        val chat = Chat(
            id = chatId,
            name = "$otherUser",
            users = users
        )


        db.collection("users").whereEqualTo("email",otherUser.toString()).get()
            .addOnCompleteListener(){ task ->
                if(task.isSuccessful){
                    for(u in task.result){
                        otherUserId =u.id.toString()
                    }
                    db.collection("chats").document(chatId).set(chat)
                    db.collection("users").document(userId).collection("chats").document(chatId).set(chat)
                    db.collection("users").document(otherUserId).collection("chats").document(chatId).set(chat)

                    val intent = Intent(this, ChatAct::class.java)
                    intent.putExtra("chatId", chatId.toString())
                    intent.putExtra("user", user.toString())
                    startActivity(intent)
                }
            }
    }
}