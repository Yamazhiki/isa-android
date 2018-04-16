package com.isa.app.views.activity

import android.os.Bundle
import android.util.Log
import com.ent.live.library.ext.ui
import com.isa.app.R
import com.isa.app.viewmodels.activity.LoginViewModel
import com.isa.library.core.ViewModelComponent
import com.isa.library.disposedBy
import com.isa.library.ext.toast
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : ViewModelComponent.ViewModelActivity<LoginViewModel.ViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun bindViewModel() {
        RxView.clicks(submit).subscribe { viewModel.inputs.login() }
        RxTextView.textChanges(name).subscribe { viewModel.inputs.username(it.toString()) }.disposedBy(disposeBag)
        RxTextView.textChanges(password).subscribe { viewModel.inputs.password(it.toString()) }.disposedBy(disposeBag)
        viewModel.outputs.result
                .ui()
                .subscribe {
                    Log.d("Users", it.toString())
                    toast(this, "登录失败")
                }
        viewModel.outputs.canLogin
                .subscribe { submit.isEnabled = it }
                .disposedBy(disposeBag)
    }
}