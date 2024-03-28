package com.example.musicfun.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicfun.itemDecoration.GridSpaceItemDecoration
import com.example.musicfun.R
import com.example.musicfun.SnapHelperOneByOne
import com.example.musicfun.adapter.CategoryAdapter
import com.example.musicfun.adapter.NewSongAdapter
import com.example.musicfun.adapter.PopularSongAdapter
import com.example.musicfun.databinding.FragmentHomeBinding
import com.example.musicfun.itemDecoration.PageIndicatorDecoration
import com.example.musicfun.layoutManager.ScaleCenterItemLayoutManager
import com.example.musicfun.models.CategoryModel
import com.example.musicfun.models.SongModel
import kotlin.math.abs

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var popularSongAdapter: PopularSongAdapter
    private lateinit var newSongAdapter: NewSongAdapter
    private val songList: List<SongModel> = listOf(
        SongModel("", "Love Story", R.drawable.love_story, "" ),
        SongModel("", "Love Story", R.drawable.love_story, "" ),
        SongModel("", "Love Story", R.drawable.love_story, "" ),
        SongModel("", "Love Story", R.drawable.love_story, "" ),
        SongModel("", "Love Story", R.drawable.love_story, "" ),
        SongModel("", "Love Story", R.drawable.love_story, "" ),

    )
    private val categoryList: List<CategoryModel> = listOf(
        CategoryModel("English", R.drawable.english ),
        CategoryModel("Mandarin",  R.drawable.mandarin),
        CategoryModel("K-pop",  R.drawable.k_pop),
        CategoryModel("Canto-pop",  R.drawable.conto_pop),
    )
    private val newSongsList: List<SongModel> = listOf(
        SongModel("", "Love Story", R.drawable.english, "" ),
        SongModel("", "Love Story", R.drawable.english, "" )
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
        setupPopularSongRecyclerView(songList)
        setupCategoryRecyclerView(categoryList)
        setupNewSongRecyclerView(newSongsList)
        return root
    }

    fun setupPopularSongRecyclerView(songList: List<SongModel>){
        popularSongAdapter = PopularSongAdapter(songList)
        binding.popularSongsRecyclerView.layoutManager = ScaleCenterItemLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
        binding.popularSongsRecyclerView.adapter = popularSongAdapter
        binding.popularSongsRecyclerView.addItemDecoration(PageIndicatorDecoration(songList.size))
        (binding.popularSongsRecyclerView.layoutManager as LinearLayoutManager).scrollToPosition(Int.MAX_VALUE/2 + abs(songList.size - 3))
        SnapHelperOneByOne().attachToRecyclerView(binding.popularSongsRecyclerView)
    }

    fun setupCategoryRecyclerView(categoryList : List<CategoryModel>){
        categoryAdapter = CategoryAdapter(categoryList)
        binding.categoriesRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        binding.categoriesRecyclerView.adapter = categoryAdapter
    }

    fun setupNewSongRecyclerView(newSongList : List<SongModel>){
        newSongAdapter = NewSongAdapter(newSongList)
        binding.newSongsRecyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.newSongsRecyclerView.adapter = newSongAdapter
        binding.newSongsRecyclerView.addItemDecoration(
            GridSpaceItemDecoration(2, getResources().getDimensionPixelSize(
            com.google.android.material.R.dimen.mtrl_card_spacing), true)
        )

    }
    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}