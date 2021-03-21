package com.example.trobify

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.type.DateTime
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*


class Chat : AppCompatActivity() {
    private var name : TextView? = null
    private var txtMessage : EditText? = null
    private var profilePicture : CircleImageView? = null
    private var btnSendPicture : ImageButton? = null
    private var btnSend : Button? = null

    private var adapter : MessagesAdapter? = null
    private var rvMessages : RecyclerView? = null
    private var profilePicture2 : String? = null

    private var db = Firebase.firestore
    private lateinit var auth : FirebaseAuth
    private var dbreference : CollectionReference = db.collection("Chat")

    private lateinit var database : FirebaseDatabase
    private lateinit var databaseReference : DatabaseReference
    private var storage : FirebaseStorage? = null
    private var storageReference : StorageReference? = null
    private var m : MessageReception? = null

    companion object {
        private const val PICTURE = 1
        private const val PROFILE = 2
    }

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        name = findViewById<View>(R.id.name) as? TextView
        txtMessage = findViewById<View>(R.id.txtMessage) as? EditText
        profilePicture = findViewById<View>(R.id.profilePicture) as? CircleImageView
        btnSendPicture = findViewById<View>(R.id.btnSendPicture) as? ImageButton
        btnSend = findViewById<View>(R.id.buttonSendMessage) as? Button

        adapter = MessagesAdapter(this)
        rvMessages = findViewById<View>(R.id.rvMessages) as? RecyclerView
        profilePicture2 = ""

        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("Chat")


        val layoutManager = LinearLayoutManager(this)
        storage = FirebaseStorage.getInstance()
        rvMessages?.layoutManager = layoutManager
        rvMessages?.adapter = adapter

        btnSend?.setOnClickListener {
            val chatMap = hashMapOf(
                "message" to txtMessage?.text.toString(),
                "user" to name?.text.toString(),
                "profile_picture" to profilePicture2,
                "type_message" to "1",
                "time" to ServerValue.TIMESTAMP
            )
            db.collection("Chat")
                .add(chatMap)
                .addOnSuccessListener { documentReference ->
                    var chatId = documentReference
                }
                .addOnFailureListener { e ->
                    Log.w("Error adding document", e)
                }
        }
        btnSendPicture?.setOnClickListener {
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.type = "image/jpeg"
            i.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
            startActivityForResult(
                Intent.createChooser(i, "Selecciona una foto"),
                Chat.Companion.PICTURE
            )
        }
        profilePicture?.setOnClickListener {
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.type = "image/jpeg"
            i.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
            startActivityForResult(
                Intent.createChooser(i, "Selecciona una foto"),
                Chat.Companion.PROFILE
            )
        }
        adapter?.registerAdapterDataObserver(object : AdapterDataObserver() {
            override fun onItemRangeInserted(
                positionStart : Int,
                itemCount : Int
            ) {
                super.onItemRangeInserted(positionStart, itemCount)
                setScrollbar()
            }
        })


        databaseReference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(
                dataSnapshot : DataSnapshot,
                s : String?
            ) {
                val m : MessageReception? = dataSnapshot.getValue(MessageReception::class.java)
                if (m != null) {
                    adapter!!.addMessage(m)
                }
            }

            override fun onChildChanged(
                dataSnapshot : DataSnapshot,
                s : String?
            ) {
            }

            override fun onChildRemoved(dataSnapshot : DataSnapshot) {}
            override fun onChildMoved(
                dataSnapshot : DataSnapshot,
                s : String?
            ) {
            }

            override fun onCancelled(databaseError : DatabaseError) {}
        })
        dbreference.addSnapshotListener { snapshots , e ->
            if (e != null) {  Log.w("Listen failed.", e); return@addSnapshotListener }
            if (snapshots != null) {
                for (dc in snapshots!!.documentChanges) {
                    when (dc.type) {
                        DocumentChange.Type.ADDED -> {
                            val messageM = dc.document.get("message")
                            val userM = dc.document.get("message")
                            val profile_pictureM = dc.document.get("message")
                            val type_messageM = dc.document.get("message")
                            val timeM  = dc.document.get("message")

                            //m = MessageReception(messageM.toString(), null , userM.toString() ,profile_pictureM.toString(), type_messageM.toString() , timeM.toString())
                        }
                    }
                }

                if (m != null) {
                    adapter!!.addMessage(m!!)

                } else {
                    Log.w("", "Current data: null")
                }
            }
        }
    }

    private fun setScrollbar() {
        rvMessages!!.scrollToPosition(adapter!!.getItemCount() - 1)
    }

    override fun onActivityResult(
        requestCode : Int,
        resultCode : Int,
        data : Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Chat.Companion.PICTURE && resultCode == Activity.RESULT_OK) {
            val u : Uri = data!!.data!!
            storageReference = storage?.getReference("imagenes_chat") //imagenes_chat
            val referencePicture  = u?.lastPathSegment?.let { storageReference?.child(it) }
            referencePicture?.putFile(u)?.addOnSuccessListener(
                this
            ) { taskSnapshot ->
                val u : String = taskSnapshot.metadata?.reference?.downloadUrl.toString()  //taskSnapshot.metadata?.reference?.downloadUrl?.result  //getDownloadUrl()
                val m = MessageSend(
                    "Paco te ha enviado una foto",
                    u.toString(),
                    name?.text.toString(),
                    profilePicture2,
                    "2",
                    ServerValue.TIMESTAMP
                )
                databaseReference?.push()?.setValue(m)
            }
        } else if (requestCode == Chat.Companion.PICTURE && resultCode == Activity.RESULT_OK) {
            val u = data?.data
            storageReference = storage?.getReference("foto_perfil") //imagenes_chat
            val referencePicture = u?.lastPathSegment?.let { storageReference?.child(it) }
            if (referencePicture != null) {
                referencePicture.putFile(u).addOnSuccessListener(
                    this
                ) { taskSnapshot ->
                    val u : String = taskSnapshot.getMetadata()?.reference?.downloadUrl.toString() //Uri = taskSnapshot.metadata?.reference?.downloadUrl?.result
                    profilePicture2 = u.toString()
                    val m = MessageSend(
                        "Paco ha actualizado su foto de perfil",
                        u.toString(),
                        name?.text.toString(),
                        profilePicture2,
                        "2",
                        ServerValue.TIMESTAMP
                    )
                    databaseReference?.push()?.setValue(m)
                    Glide.with(this@Chat).load(u.toString()).into(profilePicture)
                }
            }
        }
    }
}