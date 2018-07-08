package jp.cordea.midstream.ui.main

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import dagger.android.support.AndroidSupportInjection
import jp.cordea.midstream.databinding.MainFragmentBinding
import javax.inject.Inject
import javax.inject.Provider

class MainFragment : Fragment() {

    companion object {
        private const val REQUEST_AUTHORIZATION = 3

        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var viewModel: MainViewModel

    @Inject
    lateinit var listItem: Provider<MainListItem>

    private val adapter = GroupAdapter<ViewHolder>()

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = MainFragmentBinding.inflate(inflater, container, false).apply {
        recyclerView.adapter = adapter
    }.root

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.notifyStateChanged
                .observe(this, Observer {
                    when (it) {
                        is MainListState.BusDataChanged -> {
                        }
                        is MainListState.AuthRequired -> {
                            startActivityForResult(
                                    it.intent,
                                    REQUEST_AUTHORIZATION
                            )
                        }
                        is MainListState.ErrorOccurred -> {
                        }
                    }
                })
        viewModel.fetch()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_AUTHORIZATION && resultCode == RESULT_OK) {
            viewModel.fetch()
        }
    }
}
