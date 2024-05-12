package com.example.musicfun.remote;

import com.example.musicfun.models.MusicModel
import com.example.musicfun.other.Constants.SONG_COLLECTION
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.tasks.await


public class MusicDatabase {
    private val firestore = FirebaseFirestore.getInstance()
    private val songCollection = firestore.collection(SONG_COLLECTION)

    suspend fun getAllSongs(): List<MusicModel> {
        return try{
            songCollection.get().await().toObjects(MusicModel::class.java)
        } catch (e: Exception){
            emptyList();
        }
    }
}

