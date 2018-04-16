package com.isa.app.views.activity

import android.os.Bundle
import com.isa.app.R
import com.isa.app.library.AppEnvironment
import com.isa.app.library.EnvironmentType
import com.isa.app.viewmodels.activity.RootViewModel
import com.isa.library.core.ViewModelComponent
import com.isa.library.disposedBy
import com.isa.library.ext.replaceFragment
import com.jakewharton.rxbinding2.support.design.widget.RxBottomNavigationView
import kotlinx.android.synthetic.main.activity_root.*

class RootActivity : ViewModelComponent.ViewModelActivity<RootViewModel.ViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

        AppEnvironment.switchEnv(EnvironmentType.DEV)
    }

    override fun bindViewModel() {
        RxBottomNavigationView.itemSelections(navigation)
                .subscribe { viewModel.inputs.tabSelected(it.itemId) }
                .disposedBy(disposeBag)

        viewModel.outputs.updateContent
                .subscribe { replaceFragment(R.id.container, it) }
                .disposedBy(disposeBag)
    }
}
