package one.block.recenteosblocks.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import one.block.recenteosblocks.data.db.entities.Block
import one.block.recenteosblocks.data.repositories.BlockRepository

class BlockDetailViewModel(
    private val blockRepository: BlockRepository
) : ViewModel() {

    private val _block = MutableLiveData<Block>()

    val block : LiveData<Block>
        get() = _block

    fun getBlockDetail(block: Block) {
        _block.value = block
    }
}