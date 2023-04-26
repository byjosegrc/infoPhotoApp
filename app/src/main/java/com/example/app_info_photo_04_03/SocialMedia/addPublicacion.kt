package com.example.app_info_photo_04_03.SocialMedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.app_info_photo_04_03.databinding.ActivityAddPublicacionBinding
import com.example.app_info_photo_04_03.model.Publicacion
import com.example.app_info_photo_04_03.pref.Prefs
import com.google.firebase.database.FirebaseDatabase

class addPublicacion : AppCompatActivity() {

        lateinit var binding: ActivityAddPublicacionBinding
        lateinit var prefs: Prefs
        lateinit var db: FirebaseDatabase

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityAddPublicacionBinding.inflate(layoutInflater)
            prefs = Prefs(this)

            //REAL TIME:

            db = FirebaseDatabase.getInstance("https://infophoto-2023-default-rtdb.europe-west1.firebasedatabase.app/")
            setContentView(binding.root)
            setListeners()
        }

        private fun setListeners() {
            binding.btnReset.setOnClickListener {
                binding.etPost.text.clear()
            }
            binding.btnAdd.setOnClickListener {
                añadirPost()
            }
        }

        private fun añadirPost() {
            var contenido = binding.etPost.text.toString().trim()
            if (contenido.isEmpty()) {
                binding.etPost.error = "Este campo no puede estar vacio!"
                binding.etPost.requestFocus()
                return
            }
            //No esta vacio, añadimos
            val post = Publicacion(contenido = contenido, autor = prefs.getEmail().toString())
            db.getReference("posts").child(post.fecha.toString()).setValue(post).addOnSuccessListener {
                finish()
            }
        }
    }