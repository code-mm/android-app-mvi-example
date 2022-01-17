package com.ms.app

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.*

data class MainState(val data: Data = Data.A("default")) : MavericksState

class MainViewModel(initialState: MainState) : MavericksViewModel<MainState>(initialState) {
    fun cplugplus(c: Int = 0) {
        setState {
            if (c % 2 == 0) {
                copy(data = Data.A("a"))
            } else {
                copy(data = Data.B("b"))
            }
        }
    }
}

class MainFragment : Fragment(R.layout.fragment_main), MavericksView {

    private val viewModel: MainViewModel by fragmentViewModel()

    var textView: TextView? = null;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textView = view.findViewById<TextView>(R.id.textView);
        val buttonPlusPlus = view.findViewById<Button>(R.id.buttonPlusPlus);

        var r = 0;
        buttonPlusPlus.setOnClickListener {
            viewModel.cplugplus(r++);
        }
    }

    override fun invalidate() = withState(viewModel) { state ->
        when (state.data) {
            is Data.A -> {
                textView!!.setText("Data.A:${state.data.desc}")
            }
            is Data.B -> {
                textView!!.setText("Data.B:${state.data.title}")
            }
        }
    }
}