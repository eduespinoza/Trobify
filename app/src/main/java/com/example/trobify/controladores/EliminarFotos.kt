package com.example.trobify.controladores

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.trobify.R
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.CarouselView


class EliminarFotos : AppCompatActivity() {


    val storage = FirebaseStorage.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eliminar_foto)

        val path =  intent.extras?.get("path") as String
        val urls = intent.extras?.get("urls") as ArrayList<String>
        showImages(urls , path)

        val buttonAtras = findViewById<Button>(R.id.buttonAtrasEliminarFoto)
        buttonAtras.setOnClickListener{

            finish()
        }
    }

    private fun showImages(urls : ArrayList<String>, path : String ){
        val carouselView = findViewById<CarouselView>(R.id.carouselEliminarFoto)

        carouselView.setImageListener{ position, imageView ->
            Picasso.get().load(urls.get(position)).into(imageView)
        }

        carouselView.setImageClickListener{ position ->
            //Toast.makeText(applicationContext, urls.get(position), Toast.LENGTH_SHORT).show()
            val builder = AlertDialog.Builder(this)
            builder.setMessage("¿Seguro que quieres eliminar esta imagen?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    delete(urls.get(position) , path)
                    urls.removeAt(position)
                    showImages(urls,path)
                }
                .setNegativeButton("No") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }
        carouselView.pageCount = urls.size
    }

    private fun delete(imagen : String , pathh : String){

        var file = imagen.substring(99,135)
        val path = storage.child(pathh+ "/" + file)

        Log.d("dimel" , path.toString())
        path.delete()
    }
}