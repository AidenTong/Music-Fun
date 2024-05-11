package com.example.musicfun.ui.Music

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.musicfun.databinding.FragmentMusicBinding
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicfun.R
import com.example.musicfun.adapter.MusicAdapter
import com.example.musicfun.models.MusicModel
import com.google.firebase.firestore.FirebaseFirestore

class MusicFragment : Fragment() {

    private var _binding: FragmentMusicBinding? = null
    private val binding get() = _binding!!

//    private val musicList = listOf(
//        MusicModel("", "Love Story", "Taylor Swift", "R.drawable.love_story", "", ""),
////        MusicModel("", "Lol", "Jay Chou", R.drawable.mandarin, "", "" ),
////        MusicModel("", "Love Story", "Taylor Swift", R.drawable.love_story, "", ""),
////        MusicModel("", "Lol", "Jay Chou", R.drawable.mandarin, "", "" ),
////        MusicModel("", "Love Story", "Taylor Swift", R.drawable.love_story, "", ""),
////        MusicModel("", "Lol", "Jay Chou", R.drawable.mandarin, "", "" ),
////        MusicModel("", "Love Story", "Taylor Swift", R.drawable.love_story, "", ""),
////        MusicModel("", "Lol", "Jay Chou", R.drawable.mandarin, "", "" ),
////        MusicModel("", "Love Story", "Taylor Swift", R.drawable.love_story, "", ""),
////        MusicModel("", "Lol", "Jay Chou", R.drawable.mandarin, "", "" ),
////        MusicModel("", "Love Story", "Taylor Swift", R.drawable.love_story, "", ""),
////        MusicModel("", "Lol", "Jay Chou", R.drawable.mandarin, "", "" ),
////        MusicModel("", "Love Story", "Taylor Swift", R.drawable.love_story, "", ""),
////        MusicModel("", "Lol", "Jay Chou", R.drawable.mandarin, "", "" ),
//    )

    private lateinit var musicAdapter: MusicAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMusicBinding.inflate(inflater, container, false)
        getMusicFromFirebase()
        return binding.root
    }

    fun getMusicFromFirebase(){
        FirebaseFirestore.getInstance().collection("songs")
            .get().addOnSuccessListener {
                val musicList = it.toObjects(MusicModel::class.java)
                initRecyclerView(musicList)
            }
    }
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        initRecyclerView()
//    }

    private fun initRecyclerView(musicList: List<MusicModel>) {
        musicAdapter = MusicAdapter(musicList)
        binding.musicRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding.musicRecyclerView?.adapter = musicAdapter;

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}