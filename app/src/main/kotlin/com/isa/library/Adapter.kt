package com.isa.library

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

class Adapter<M, V : RecyclerView.ViewHolder>(
        private val view: (viewGroup: ViewGroup, position: Int) -> V,
        private val config: (viewHolder: V, model: M) -> Unit) : RecyclerView.Adapter<V>() {

    private val mData = mutableListOf<M>()
    private val clickSubject = PublishSubject.create<Pair<Int, M>>()

    val selected: Observable<Pair<Int, M>>
        get() = clickSubject

    private fun add(models: List<M>) {
        val oSize = mData.size
        mData.addAll(models)
        notifyDataSetChanged()
        notifyItemRangeChanged(mData.size, mData.size - oSize)
    }

    fun bind(source: Observable<List<M>>): Disposable {
        return source.subscribe { add(it) }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): V {
        return view(p0, p1)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(p0: V, p1: Int) {
        config(p0, mData[p1])
        p0.itemView.setOnClickListener {
            clickSubject.onNext(Pair(p1, mData[p1]))
        }
    }
}