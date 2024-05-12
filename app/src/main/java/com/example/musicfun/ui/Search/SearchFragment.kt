package com.example.musicfun.ui.Search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicfun.MyExoplayer
import com.example.musicfun.R
import com.example.musicfun.adapter.MusicAdapter
import com.example.musicfun.databinding.FragmentMusicBinding
import com.example.musicfun.databinding.FragmentSearchBinding
import com.example.musicfun.models.MusicModel
import com.example.musicfun.ui.Music.PlayerActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.Locale

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var musicList: List<MusicModel>
    private lateinit var musicAdapter: MusicAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root

    }

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
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search query submission here
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle search query text changes here
                filterList(newText)
                return true
            }
        })
    }




    private fun filterList(query: String?){
        if(query!=null) {
            val filteredList = ArrayList<MusicModel>()
            for(i in musicList){
                if(i.title.lowercase(Locale.ROOT).contains(query)){
                    filteredList.add(i)
                }
            }

            if(filteredList.isEmpty()){
                Toast.makeText(requireContext(), "No Music Found",Toast.LENGTH_LONG).show()
            }else{
                musicAdapter.setFilterList(filteredList)
            }
        }
    }

    private fun initRecyclerView(musicList: List<MusicModel>) {
        musicAdapter = MusicAdapter(musicList)
        musicAdapter.setOnClickListener(object : MusicAdapter.OnClickListener {
            override fun onClick(position: Int, model: MusicModel) {
                // Start playing the selected song
                MyExoplayer.startPlaying(requireContext(), model)
                val intent = Intent(requireContext(), PlayerActivity::class.java)
                startActivity(intent)
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