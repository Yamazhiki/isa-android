package com.isa.app.viewmodels.fragment

import com.isa.library.core.ViewModelComponent

interface DiscoveryViewModel : ViewModelComponent.ViewModel {
    interface Inputs {}
    interface Outputs
    class ViewModel : Inputs, Outputs, DiscoveryViewModel
}