package com.example.musicfun

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class GridSpaceItemDecoration(
    private val spanCount: Int,
    private val spacing: Int,
    private val includeEdge: Boolean
) :
    ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position: Int = parent.getChildAdapterPosition(view)
        val column: Int = position % spanCount

        outRect.left = if (column == 1) spacing else 0
        outRect.right = if (column == 0) spacing else 0
        outRect.top = if (position < spanCount) 0 else spacing
        outRect.bottom = spacing
    }
}