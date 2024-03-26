package com.example.musicfun.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicfun.R
import com.example.musicfun.adapter.CategoryAdapter
import com.example.musicfun.adapter.PopularSongAdapter
import com.example.musicfun.databinding.FragmentHomeBinding
import com.example.musicfun.models.CategoryModel
import com.example.musicfun.models.SongModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var popularSongAdapter: PopularSongAdapter
    private val categoryList: List<CategoryModel> = listOf(
        CategoryModel("English", R.drawable.english ),
        CategoryModel("Mandarin",  R.drawable.mandarin),
        CategoryModel("K-pop",  R.drawable.k_pop),
        CategoryModel("Canto-pop",  R.drawable.conto_pop),
    )
    private val songList: List<SongModel> = listOf(
        SongModel("", "Love Story", R.drawable.love_story, "" )
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        setupCategoryRecyclerView(categoryList)
        setupPopularSongRecyclerView(songList)
        return root
    }

    fun setupCategoryRecyclerView(categoryList : List<CategoryModel>){
        categoryAdapter = CategoryAdapter(categoryList)
        binding.categoriesRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        binding.categoriesRecyclerView.adapter = categoryAdapter
    }

    fun setupPopularSongRecyclerView(songList: List<SongModel>){
        popularSongAdapter = PopularSongAdapter(songList)
        binding.popularSongsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        binding.popularSongsRecyclerView.adapter = popularSongAdapter
    }
    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}