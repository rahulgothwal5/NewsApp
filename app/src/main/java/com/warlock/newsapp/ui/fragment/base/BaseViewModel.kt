package com.warlock.newsapp.ui.fragment.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.warlock.newsapp.util.SingleLiveDataEvent

abstract class BaseViewModel : ViewModel() {
    private var loading: MutableLiveData<SingleLiveDataEvent<Boolean>> = MutableLiveData()
    var snackBar: MutableLiveData<SingleLiveDataEvent<String>> = MutableLiveData()

    fun getLoading() = loading

    fun setLoading(visible: Boolean) {
        loading.value = SingleLiveDataEvent(visible)
    }


}