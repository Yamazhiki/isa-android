package com.ent.live.app.viewmodels.fragment

import com.isa.library.core.ViewModelComponent

interface CommentViewModel : ViewModelComponent.ViewModel {

    val inputs: Inputs
    val outputs: Outputs

    interface Inputs {}
    interface Outputs {}

    class ViewModel : Inputs, Outputs, CommentViewModel {
        override val inputs: Inputs
            get() = this
        override val outputs: Outputs
            get() = this

    }
}