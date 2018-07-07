package jp.cordea.midstream.ui.main

import com.xwray.groupie.databinding.BindableItem
import jp.cordea.midstream.R
import jp.cordea.midstream.databinding.MainListItemBinding
import javax.inject.Inject

class MainListItemModel(
)

class MainListItem @Inject constructor(
) : BindableItem<MainListItemBinding>() {
    lateinit var model: MainListItemModel

    override fun getLayout(): Int = R.layout.main_list_item

    override fun bind(binding: MainListItemBinding, position: Int) {
    }
}
