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
import com.example.musicfun.MyExoplayer
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

    private lateinit var musicList: List<MusicModel>
    private lateinit var musicAdapter: MusicAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMusicBinding.inflate(inflater, container, false)
        return binding.root
    }

//    fun getMusicFromFirebase(){
//        FirebaseFirestore.getInstance().collection("songs")
//            .get().addOnSuccessListener {
//                musicList = it.toObjects(MusicModel::class.java)
//                initRecyclerView(musicList)
//            }
//    }
fun getMusicFromFirebase() {
    FirebaseFirestore.getInstance().collection("songs")
        .get().addOnSuccessListener { querySnapshot ->
            val tempMusicList = mutableListOf<MusicModel>()
            for (document in querySnapshot) {
                val musicModel = document.toObject(MusicModel::class.java)
                if (musicModel.MP3.isNotEmpty()) {
                    tempMusicList.add(musicModel)
                }
            }
            musicList = tempMusicList
            initRecyclerView(musicList)
        }
}
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMusicFromFirebase()
    }

    private fun initRecyclerView(musicList: List<MusicModel>) {
        musicAdapter = MusicAdapter(musicList)
        musicAdapter.setOnClickListener(object : MusicAdapter.OnClickListener {
            override fun onClick(position: Int, model: MusicModel) {
                // Start playing the selected song
                MyExoplayer.startPlaying(requireContext(), model)
            }
        })
        binding.musicRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding.musicRecyclerView?.adapter = musicAdapter;

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}