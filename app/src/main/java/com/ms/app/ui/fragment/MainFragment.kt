package com.ms.app.ui.fragment

import android.os.Bundle
import android.view.View
import com.airbnb.mvrx.*
import com.ms.app.R
import com.ms.app.base.BaseFragment
import com.ms.app.base.UIDataModel
import com.ms.app.util.simpleController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

data class MainState(val data: Async<List<UIDataModel>> = Uninitialized) : MavericksState

class MainViewModel(initialState: MainState) : MavericksViewModel<MainState>(initialState) {
    init {
        setState {
            copy(data = Loading())
        }
        GlobalScope.launch(Dispatchers.IO) {
            Thread.sleep(3000)
            GlobalScope.launch(Dispatchers.Main) {
                setState {
                    copy(
                        data = Success(
                            Arrays.asList(
                                UIDataModel.AvatarDataModel("avatar", R.mipmap.ic_launcher),
                                UIDataModel.Nickname("nickname", "我的名称")
                            )
                        )
                    )
                }
            }
        }
    }
}

class MainFragment : BaseFragment(), MavericksView {

    private val viewModel: MainViewModel by fragmentViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun invalidate() = withState(viewModel) { state ->
        epoxyController.requestModelBuild()

        withState(viewModel) { state ->
            when (state.data) {
                is Loading -> {

                }
                is Success -> {

                }
                is Fail -> {

                }
            }
        }
    }

    override fun epoxyController() = simpleController(viewModel) { state ->
        state.data.invoke()?.let { buildEpoxyRows(viewModel, it) }
    }
}