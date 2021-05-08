package com.example.trobify.controladores

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trobify.R
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
    private var inmueble : String = ""

    private var userName : String = ""
    private var otherUserName : String = ""
    private var chatId : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_chats)

        val bBack = findViewById<Button>(R.id.buttonAtras)
        bBack.setOnClickListener {
            val goBack = Intent(this, MainTrobify::class.java)
            goBack.putExtra("user",user)
            startActivity(goBack)
        }

        intent.getStringExtra("user")?.let { user = it }
        intent.getStringExtra("otherUserId")?.let { otherUser = it }
        intent.getStringExtra("message")?.let { message = it }
        intent.getStringExtra("inmueble")?.let { inmueble = it }

        println("---------------------------------------------- User: " + user + " OtherUser: " + otherUser + " Message: " + message + " Inmueble: " + inmueble  )

        if( message != "" || otherUser != "") { getData() }
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

    private fun getData(){
        println("--------------------------------- GETING DATA ---------------------")
        db.collection("users").document(user.toString()).get()
            .addOnCompleteListener() { u ->
                userName =
                    u.result.data?.get("name").toString() + " " + u.result.data?.get("surname")
                        .toString()

                db.collection("users").document(otherUser.toString()).get()
                    .addOnCompleteListener() { o ->
                        otherUserName = o.result.data?.get("name")
                            .toString() + " " + o.result.data?.get("surname").toString()

                        println("--------------------------------- GETING DATA COMPLETE ---------------------")
                        println("---------------------------------------------- User: " + userName + " OtherUser: " + otherUserName + " Message: " + message + " Inmueble: " + inmueble )

                        tryNewChat()
                    }
            }
    }

    private fun tryNewChat(){
        val chatName : String = inmueble + " con " + otherUserName + " y " + userName

        println("--------------------------------- SEARCHING EXISTING CHAT --------------------- chatName: " + chatName.toString())

        db.collection("chats").whereEqualTo("name" , chatName.toString()).get()
            .addOnSuccessListener { documents ->
                println("--------------------------------- SEARCHING DATA COMPLETE ---------------------")
                if(!documents.isEmpty){
                    for(document in documents){
                        chatId = document.id.toString()
                        val goChat = Intent(this, ChatAct::class.java)
                        goChat.putExtra("chatId", chatId.toString())
                        goChat.putExtra("user", user.toString())
                        goChat.putExtra("message",message.toString())
                        startActivity(goChat)
                    }
                }
                else{
                    println("--------------------------------- NO EXISTING CHAT, CREATING A NEW CHAT ---------------------")
                    newChat()
                }
            }
    }

    private fun newChat() {
        chatId = UUID.randomUUID().toString()
        println("--------------------------------- CREATING A NEW CHAT --------------------- CHAT ID: " + chatId.toString())
        val users = listOf(user, otherUser)
        val chat = Chat(
            id = chatId,
            name = "$inmueble con $otherUserName y $userName",
            users = users,
            inmuebleId = inmueble
        )
        val chat1 = Chat(
            id = chatId,
            name = "$otherUserName - $inmueble",
            users = users,
            inmuebleId = inmueble
        )
        val chat2 = Chat(
            id = chatId,
            name = "$userName - $inmueble",
            users = users,
            inmuebleId = inmueble
        )

        db.collection("chats").document(chatId).set(chat)
        db.collection("users").document(user.toString()).collection("chats").document(chatId)
            .set(chat1)
        db.collection("users").document(otherUser.toString()).collection("chats").document(chatId)
            .set(chat2)

        val goChat = Intent(this, ChatAct::class.java)
        goChat.putExtra("chatId", chatId.toString())
        goChat.putExtra("user", user.toString())
        goChat.putExtra("message", message.toString())

        startActivity(goChat)
    }
}
