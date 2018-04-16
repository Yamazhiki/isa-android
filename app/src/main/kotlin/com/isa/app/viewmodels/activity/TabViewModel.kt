package com.isa.app.viewmodels.activity

import com.isa.library.core.ViewModelComponent

interface TabViewModel : ViewModelComponent.ViewModel {
    val inputs: Inputs
    val outputs: Outputs

    interface Inputs {}
    interface Outputs {}

    class ViewModel : Inputs, Outputs, TabViewModel {
        override val inputs: Inputs
            get() = this
        override val outputs: Outputs
            get() = this

    }
}