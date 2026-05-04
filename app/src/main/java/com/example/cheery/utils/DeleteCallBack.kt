package com.example.cheery.utils

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class DeleteCallBack(private val onDelete: (Int) -> Unit,   // 左滑删除
                     private val onPin: (Int) -> Unit       // 右滑置顶
    ): ItemTouchHelper.Callback() {
    //支持左右侧滑
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags=0
        val swipeFlags= ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return makeMovementFlags(dragFlags,swipeFlags)
    }
    //不支持拖拽
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean =false
    //具体效果
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position=viewHolder.adapterPosition
        when(direction){
            ItemTouchHelper.LEFT-> onDelete(position)
            ItemTouchHelper.RIGHT -> onPin(position)
        }
    }
    //关闭长安拖拽
    override fun isLongPressDragEnabled(): Boolean =false
    //打开侧滑
    override fun isItemViewSwipeEnabled(): Boolean =true
}