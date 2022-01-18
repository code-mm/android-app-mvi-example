package com.ms.app.base;


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.MavericksViewModel

import com.ms.app.R

import com.ms.app.avatar
import com.ms.app.util.ToDoEpoxyController

abstract class BaseFragment : Fragment(), MavericksView {
    protected lateinit var epoxyRecyclerView: EpoxyRecyclerView
    protected val epoxyController by lazy { epoxyController() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        epoxyController.onRestoreInstanceState(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_base, container, false).apply {
            epoxyRecyclerView = findViewById(R.id.epoxyRecyclerView)
            epoxyRecyclerView.setController(epoxyController)
        }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        epoxyController.onSaveInstanceState(outState)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun invalidate() {
        epoxyRecyclerView.requestModelBuild()

    }

    abstract fun epoxyController(): ToDoEpoxyController


    fun <T : MavericksState> EpoxyController.buildEpoxyRows(
        viewModel: MavericksViewModel<T>,
        datas: List<UIDataModel>
    ) {
        if (!datas.isEmpty()) {
            datas.forEach {
                when (it) {
                    is UIDataModel.AvatarDataModel -> {
                        avatar {
                            id("${it.id}")
                            src(it.resId)

                        }
                    }
                }
            }
        }
    }
}
