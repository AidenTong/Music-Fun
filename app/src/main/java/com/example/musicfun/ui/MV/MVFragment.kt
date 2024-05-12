package com.example.musicfun.ui.MV

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
import com.example.musicfun.adapter.AlbumAdapter
import com.example.musicfun.adapter.CategoryAdapter
import com.example.musicfun.adapter.PopularSongAdapter
import com.example.musicfun.databinding.FragmentMvBinding
import com.example.musicfun.itemDecoration.PageIndicatorDecoration
import com.example.musicfun.layoutManager.ScaleCenterItemLayoutManager
import com.example.musicfun.models.AlbumModel
import com.example.musicfun.models.CategoryModel
import com.example.musicfun.models.SongModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.math.abs

class MVFragment : Fragment() {

    private var _binding: FragmentMvBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var popularSongAdapter: PopularSongAdapter
    private lateinit var albumAdapter: AlbumAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(MVViewModel::class.java)

        _binding = FragmentMvBinding.inflate(inflater, container, false)
        val root: View = binding.root

        getPopularSongs()
        getCategories()
        getAlbums()
        return root
    }

    fun getPopularSongs() {
        FirebaseFirestore.getInstance().collection("popular_songs")
            .get().addOnSuccessListener {
                val popularSongs = it.toObjects(SongModel::class.java)
                setupPopularSongRecyclerView(popularSongs)
            }
    }
    fun getCategories() {
        FirebaseFirestore.getInstance().collection("category")
            .get().addOnSuccessListener {
                val categoryList = it.toObjects(CategoryModel::class.java)
                setupCategoryRecyclerView(categoryList)
            }
    }

    fun getAlbums() {
        FirebaseFirestore.getInstance().collection("new_albums")
            .get().addOnSuccessListener {
                val albumList = it.toObjects(AlbumModel::class.java)
                setupAlbumRecyclerView(albumList)
            }
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

    fun setupAlbumRecyclerView(albumList : List<AlbumModel>){
        albumAdapter = AlbumAdapter(albumList)
        binding.newSongsRecyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.newSongsRecyclerView.adapter = albumAdapter
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