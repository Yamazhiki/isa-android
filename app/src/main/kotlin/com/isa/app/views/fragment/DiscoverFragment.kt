package com.isa.app.views.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ent.live.library.ui.SimpleRecycleViewHolder
import com.isa.app.R
import com.isa.app.viewmodels.fragment.DiscoveryViewModel
import com.isa.app.views.activity.LoginActivity
import com.isa.library.Adapter
import com.isa.library.core.ViewModelComponent
import com.isa.library.data.SimpleRecycleItem
import com.isa.library.disposedBy
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_discover.*

class DiscoverFragment : ViewModelComponent.ViewModelFragment<DiscoveryViewModel.ViewModel>() {

    private val adapter: Adapter<SimpleRecycleItem, SimpleRecycleViewHolder> by lazy {
        Adapter<SimpleRecycleItem, SimpleRecycleViewHolder>(
                { v, _ ->
                    SimpleRecycleViewHolder(LayoutInflater.from(activity).inflate(R.layout.recycle_simpleicon_subtitle, v, false))
                },
                { v, m ->
                    v.icon.setImageResource(m.icon)
                    v.title.text = m.title
                    v.subTitle.text = m.subTitle
                }
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        recycleView.layoutManager = LinearLayoutManager(activity)
        recycleView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        recycleView.adapter = adapter
        activity.actionBar.title = "发现"
        super.onActivityCreated(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_discover, container)
    }

    override fun bindViewModel() {
        val data = Observable.just(
                listOf(SimpleRecycleItem(R.drawable.ic_dashboard_48px, "后羿", "大河之剑天上来"),
                        SimpleRecycleItem(R.drawable.ic_dashboard_48px, "嫦娥", "你是我第"))
        )
        adapter.bind(data).disposedBy(disposeBag)
        adapter.selected.subscribe {
            when (it.first) {
                0 -> {
                    val intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                }
                else -> {
                }
            }
        }
    }
}