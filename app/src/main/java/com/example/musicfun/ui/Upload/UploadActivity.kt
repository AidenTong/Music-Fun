package com.example.musicfun.ui.Upload

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.musicfun.databinding.ActivityUploadBinding
import com.example.musicfun.models.SongModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnPausedListener
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.StorageReference
import kotlin.math.roundToInt

class UploadActivity : AppCompatActivity() {
    lateinit var binding: ActivityUploadBinding
    lateinit var songCollection: CollectionReference
    lateinit var uri: Uri
    lateinit var storage : StorageReference
    private val audio = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val audioSelectButton = binding.selectAudioBtn
        val audioUploadButton = binding.uploadAudioBtn
        storage = FirebaseStorage.getInstance().getReference("MP3")
        songCollection = FirebaseFirestore.getInstance().collection("songs")

        audioSelectButton.setOnClickListener{
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("audio/*")
            startActivityForResult(Intent.createChooser(intent, "Select Music Audio"), audio)
        }

        audioUploadButton.setOnClickListener {
            upload()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == audio && data != null) {
            uri = data.data!!
        }
    }

    fun upload() {
        val metaData = StorageMetadata.Builder()
            .setContentType("audio/mpeg")
            .build();
        val name = binding.name.text.toString()
        val uploadTask = storage.child("/${name}").putFile(uri, metaData)
        uploadTask.addOnProgressListener(com.google.firebase.storage.OnProgressListener{ taskSnapshot ->
            val progress = (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount).roundToInt()
            Toast.makeText(this, "Upload is " + progress + "% done", Toast.LENGTH_SHORT).show()
        }).addOnPausedListener(OnPausedListener{
            System.out.println("Upload is paused");
        }).addOnFailureListener(OnFailureListener{
            Toast.makeText(this, "Upload Failed", Toast.LENGTH_LONG).show()
        }).addOnSuccessListener(OnSuccessListener { taskSnapshot -> {}
            val id = "song_" + name
            val title = name
            val author = binding.author.text.toString()
            val downloadUrlTask = taskSnapshot.storage.downloadUrl
            downloadUrlTask.addOnSuccessListener { uri ->
                val downloadUrl = uri.toString()
                val song = SongModel(id, title, author, "", "", downloadUrl)

                songCollection.document(id).set(song)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Successfully uploaded", Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(
                            this,
                            "Upload failed: ${exception.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
        })
    }
}