package com.isa.app.viewmodels.activity

import android.app.Fragment
import com.isa.app.R
import com.isa.app.views.fragment.DiscoverFragment
import com.isa.library.core.ViewModelComponent
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

interface RootViewModel : ViewModelComponent.ViewModel {
    val inputs: Inputs
    val outputs: Outputs

    interface Inputs {
        fun tabSelected(id: Int)
    }

    interface Outputs {
        val updateContent: Observable<Fragment>
    }

    class ViewModel : Inputs, Outputs, RootViewModel {
        private val didSelectedTabSubject = PublishSubject.create<Int>()


        override fun tabSelected(id: Int) = didSelectedTabSubject.onNext(id)

        override val updateContent: Observable<Fragment>
            get() {
                return didSelectedTabSubject.map {
                    when (it) {
                        R.id.navigation_home -> DiscoverFragment()
                        else -> DiscoverFragment()
                    }
                }
            }

        override val inputs: Inputs
            get() = this
        override val outputs: Outputs
            get() = this

    }
}