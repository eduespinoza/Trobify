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
    private var user : String = ""
    private var otherUser : String = ""
    private var message : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_chats)

        intent.getStringExtra("user")?.let { user = it }
        intent.getStringExtra("otherUserId")?.let { otherUser = it }
        intent.getStringExtra("message")?.let { message = it }

        if( message != "" || otherUser != "") { newChat() }
        else { initViews() }
    }

    private fun initViews(){
        listChatsRecyclerView.layoutManager = LinearLayoutManager(this)
        listChatsRecyclerView.adapter =
            ChatAdapter { chat -> chatSelected(chat)
            }

        val userRef = db.collection("users").document(user.toString())

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

    private fun newChat(){
        val chatId = UUID.randomUUID().toString()
        var userName = ""
        var otherUserName = ""

        db.collection("users").document(user.toString()).get()
            .addOnCompleteListener(){ u ->
                userName = u.result.data?.get("name").toString() + " " + u.result.data?.get("surname").toString()

                db.collection("users").document(otherUser.toString()).get()
                    .addOnCompleteListener(){ o ->
                        otherUserName = o.result.data?.get("name").toString() + " " + o.result.data?.get("surname").toString()

                        val users = listOf(user, otherUser)
                        val chat = Chat( id = chatId, name = "$otherUserName y $userName", users = users )
                        val chat1 = Chat( id = chatId, name = "$otherUserName", users = users )
                        val chat2 = Chat(id = chatId, name = "$userName", users = users)

                        if(message != ""){ message = userName.toString() + " le hace una oferta de: " + message + "€"}

                        db.collection("chats").document(chatId).set(chat)
                        db.collection("users").document(user.toString()).collection("chats").document(chatId).set(chat1)
                        db.collection("users").document(otherUser.toString()).collection("chats").document(chatId).set(chat2)

                        val goChat = Intent(this, ChatAct::class.java)
                        goChat.putExtra("chatId", chatId.toString())
                        goChat.putExtra("user", user.toString())
                        goChat.putExtra("message",message.toString())

                        startActivity(goChat)
                    }
            }
    }

}
