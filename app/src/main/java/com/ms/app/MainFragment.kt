package com.ms.app

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

data class MainState(val data: Async<List<DataModel>> = Uninitialized) : MavericksState

class MainViewModel(initialState: MainState) : MavericksViewModel<MainState>(initialState) {
    fun getDatas() {
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
                                DataModel.AvatarDataModel(R.mipmap.ic_launcher),
                                DataModel.Nickname("我的名称")
                            )
                        )
                    )
                }
            }
        }
    }
}

class MainFragment : Fragment(R.layout.fragment_main), MavericksView {

    private val viewModel: MainViewModel by fragmentViewModel()

    var textView: TextView? = null
    var imageViewAvatar: ImageView? = null
    var progressBar: ProgressBar? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView = view.findViewById(R.id.textView);
        imageViewAvatar = view.findViewById(R.id.imageViewAvatar);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar!!.visibility = View.GONE
        val buttonPlusPlus = view.findViewById<Button>(R.id.buttonPlusPlus);
        buttonPlusPlus.setOnClickListener {
            viewModel.getDatas();
        }
    }

    override fun invalidate() = withState(viewModel) { state ->
        when (state.data) {
            is Loading -> {
                progressBar!!.visibility = View.VISIBLE
            }
            is Success -> {
                progressBar!!.visibility = View.GONE
                var data = state.data.invoke()

                data.forEach {
                    when (it) {
                        is DataModel.AvatarDataModel -> {
                            imageViewAvatar!!.setImageResource(it.id)
                            imageViewAvatar!!.visibility = View.VISIBLE
                        }
                        is DataModel.Nickname -> {
                            textView!!.text = "${it.nickname}"
                        }
                    }
                }
            }
        }
    }
}