package com.isa.app.viewmodels.activity

import com.isa.library.core.ViewModelComponent

interface SettingsViewModel : ViewModelComponent.ViewModel {
    val inputs: Inputs
    val outputs: Outputs

    interface Inputs {}
    interface Outputs {}

    class ViewModel : Inputs, Outputs, SettingsViewModel {
        override val inputs: Inputs
            get() = this
        override val outputs: Outputs
            get() = this

    }
}