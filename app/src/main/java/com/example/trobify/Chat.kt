package com.example.trobify

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import de.hdodenhof.circleimageview.CircleImageView


class Chat : AppCompatActivity() {
    private var profilePicture : CircleImageView? = null
    private var name : TextView? = null
    private var rvMessages : RecyclerView? = null
    private var txtMessage : EditText? = null
    private var btnSend : Button? = null
    private var adapter : MessagesAdapter? = null
    private var btnSendPicture : ImageButton? = null
    private var database : FirebaseDatabase? = null
    private var databaseReference : DatabaseReference? = null
    private var storage : FirebaseStorage? = null
    private var storageReference : StorageReference? = null
    private var profilePicture2 : String? = null
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        profilePicture = findViewById<View>(R.id.profilePicture) as? CircleImageView
        name = findViewById<View>(R.id.name) as? TextView
        rvMessages = findViewById<View>(R.id.rvMessages) as? RecyclerView
        txtMessage = findViewById<View>(R.id.txtMessage) as? EditText
        btnSend = findViewById<View>(R.id.sendButton) as? Button
        btnSendPicture = findViewById<View>(R.id.sendPicture) as? ImageButton
        profilePicture2 = ""
        database = FirebaseDatabase.getInstance()
        databaseReference = database!!.getReference("chat")
        storage = FirebaseStorage.getInstance()
        adapter = MessagesAdapter(this)
        val layoutManager = LinearLayoutManager(this)
        rvMessages!!.layoutManager = layoutManager
        rvMessages!!.adapter = adapter
        btnSend!!.setOnClickListener {
            databaseReference!!.push().setValue(
                MessageSend(
                    txtMessage!!.text.toString(),
                    name!!.text.toString(),
                    profilePicture2,
                    "1",
                    ServerValue.TIMESTAMP
                )
            )
            txtMessage!!.setText("")
        }
        btnSendPicture!!.setOnClickListener {
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.type = "image/jpeg"
            i.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
            startActivityForResult(
                Intent.createChooser(i, "Selecciona una foto"),
                Chat.Companion.PHOTO_SEND
            )
        }
        profilePicture!!.setOnClickListener {
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.type = "image/jpeg"
            i.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
            startActivityForResult(
                Intent.createChooser(i, "Selecciona una foto"),
                Chat.Companion.PHOTO_PERFIL
            )
        }
        adapter!!.registerAdapterDataObserver(object : AdapterDataObserver() {
            override fun onItemRangeInserted(
                positionStart : Int,
                itemCount : Int
            ) {
                super.onItemRangeInserted(positionStart, itemCount)
                setScrollbar()
            }
        })
        databaseReference!!.addChildEventListener(object : ChildEventListener {
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
        if (requestCode == Chat.Companion.PHOTO_SEND && resultCode == Activity.RESULT_OK) {
            val u = data!!.data
            storageReference = storage!!.getReference("imagenes_chat") //imagenes_chat
            val fotoReferencia = storageReference!!.child(u!!.lastPathSegment!!)
            fotoReferencia.putFile(u).addOnSuccessListener(
                this
            ) { taskSnapshot ->
                val u : Uri = taskSnapshot.metadata?.reference?.downloadUrl!!.result  //getDownloadUrl()
                val m = MessageSend(
                    "Kevin te ha enviado una foto",
                    u.toString(),
                    name!!.text.toString(),
                    profilePicture2,
                    "2",
                    ServerValue.TIMESTAMP
                )
                databaseReference!!.push().setValue(m)
            }
        } else if (requestCode == Chat.Companion.PHOTO_PERFIL && resultCode == Activity.RESULT_OK) {
            val u = data!!.data
            storageReference = storage!!.getReference("foto_perfil") //imagenes_chat
            val fotoReferencia = storageReference!!.child(u!!.lastPathSegment!!)
            fotoReferencia.putFile(u).addOnSuccessListener(
                this
            ) { taskSnapshot ->
                val u : Uri = taskSnapshot.metadata?.reference?.downloadUrl!!.result
                profilePicture2 = u.toString()
                val m = MessageSend(
                    "Kevin ha actualizado su foto de perfil",
                    u.toString(),
                    name!!.text.toString(),
                    profilePicture2,
                    "2",
                    ServerValue.TIMESTAMP
                )
                databaseReference!!.push().setValue(m)
                Glide.with(this@Chat).load(u.toString()).into(profilePicture)
            }
        }
    }

    companion object {
        private const val PHOTO_SEND = 1
        private const val PHOTO_PERFIL = 2
    }
}