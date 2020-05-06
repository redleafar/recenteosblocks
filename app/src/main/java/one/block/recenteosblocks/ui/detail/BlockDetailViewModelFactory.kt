package one.block.recenteosblocks.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import one.block.recenteosblocks.data.repositories.BlockRepository

@Suppress("UNCHECKED_CAST")
class BlockDetailViewModelFactory(
    private val blockRepository: BlockRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun < T: ViewModel> create(modelClass: Class<T>): T {
        return BlockDetailViewModel(blockRepository) as T
    }
}