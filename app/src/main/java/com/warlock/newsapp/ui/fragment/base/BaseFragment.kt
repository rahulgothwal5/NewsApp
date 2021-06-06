package com.warlock.newsapp.ui.fragment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.warlock.newsapp.BR
import com.warlock.newsapp.databinding.LayoutProgressBinding
import com.warlock.newsapp.util.SingleLiveObserver
import com.warlock.newsapp.util.Toast

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel> : Fragment(),
    View.OnClickListener {
    private lateinit var mViewDataBinding: T
    private lateinit var mProgressAndErrorView: LayoutProgressBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<T>(inflater, getLayoutId(), container, false).apply {
        mViewDataBinding = this
        mViewDataBinding.setVariable(getBindingVariable(), getViewModel())
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner
        mViewDataBinding.setVariable(BR.clickListener, this@BaseFragment)
        mViewDataBinding.executePendingBindings()
        addProgressLayout()
    }.root

    private fun setupSnackBar() {

    }

    /**
     * use to add loading view on fragment
     *  example :-
     *             getViewModel().loading.value = SingleLiveDataEvent(true) ->>>use to show loading
     *             getViewModel().loading.value = SingleLiveDataEvent(true) ->>>use to hide loading
     */
    private fun addProgressLayout() {
        mProgressAndErrorView = LayoutProgressBinding.inflate(LayoutInflater.from(requireContext()))
        mProgressAndErrorView.lifecycleOwner = viewLifecycleOwner
        (mViewDataBinding.root as ViewGroup).addView(
            mProgressAndErrorView.root,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        getViewModel().getLoading().observe(viewLifecycleOwner, SingleLiveObserver {
            mProgressAndErrorView.root.isVisible = it
        })

        getViewModel().snackBar.observe(viewLifecycleOwner, SingleLiveObserver {
            Toast.showSnackBar(requireActivity(), it ?: "")
        })
    }

    /**
     * @return layout resource id
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * Override for set binding variable
     * @return variable id
     */
    abstract fun getBindingVariable(): Int

    /**
     * Override for set view model
     * @return view model instance
     */
    abstract fun getViewModel(): V

    /**
     * Method for get View data binding
     */
    fun getViewDataBinding(): T {
        return mViewDataBinding
    }


}
