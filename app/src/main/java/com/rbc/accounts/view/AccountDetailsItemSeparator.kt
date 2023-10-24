package com.rbc.accounts.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.rbc.accounts.R

class AccountDetailsItemSeparator(context: Context) : RecyclerView.ItemDecoration() {

    private val accountDecoration = ContextCompat.getDrawable(context, R.drawable.account_item_separator)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        parent.adapter?.let {
            parent.getChildAdapterPosition(view)
                .let { if (it == RecyclerView.NO_POSITION) return else it }
            outRect.bottom = accountDecoration?.intrinsicHeight!!
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        parent.adapter?.let { adapter ->
            parent.children.forEach { view ->
                val childAdapterPosition = parent.getChildAdapterPosition(view)
                    .let { if (it == RecyclerView.NO_POSITION) return else it }

                if (childAdapterPosition == adapter.itemCount-1) {
                    Unit
                } else {
                    accountDecoration?.drawSeparator(view, c)
                }
            }
        }
    }

    private fun Drawable.drawSeparator(view: View, canvas: Canvas) =
        apply {
            val left = view.left
            val top = view.bottom
            val right = view.right
            val bottom = top + intrinsicHeight
            bounds = Rect(left, top, right, bottom)
            draw(canvas)
        }
}