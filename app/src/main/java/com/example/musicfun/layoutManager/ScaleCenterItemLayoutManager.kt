package com.example.musicfun.layoutManager

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ScaleCenterItemLayoutManager(private val context: Context, private val orientation: Int, private val reverseLayout: Boolean) : LinearLayoutManager(context, orientation, reverseLayout) {

    override fun checkLayoutParams(lp: RecyclerView.LayoutParams): Boolean {
//        lp.width = width/2
        return true
    }

    override fun onLayoutCompleted(state: RecyclerView.State) {
        super.onLayoutCompleted(state)
        scaleMiddleItem()
    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
        if(getOrientation() == RecyclerView.HORIZONTAL) {
            scaleMiddleItem()
            return scrolled
        } else {
            return 0
        }
    }

    private fun scaleMiddleItem() {
        val mid = width/2.0f
        val d1 = 0.9f * mid
        for(i in 0..childCount){
            val child = getChildAt(i)
            if (child != null) {
                val childMid = (getDecoratedRight(child) + getDecoratedLeft(child))/ 2.0f
                val d = d1.coerceAtMost(Math.abs(mid - childMid))
                val scale = 1f - 0.15f * d/d1
                child.scaleX = scale
                child.scaleY = scale
            }
        }
    }
}