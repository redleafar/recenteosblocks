package one.block.recenteosblocks.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import one.block.recenteosblocks.data.repositories.BlockRepository

@Suppress("UNCHECKED_CAST")
class ListViewModelFactory(
    private val blockRepository: BlockRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListViewModel(blockRepository) as T
    }
}