package one.block.recenteosblocks.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import one.block.recenteosblocks.data.db.entities.Block
import one.block.recenteosblocks.data.db.entities.BlockchainInfo
import one.block.recenteosblocks.data.repositories.BlockRepository
import one.block.recenteosblocks.util.Constants.BLOCK_NUM_OR_ID
import one.block.recenteosblocks.util.Constants.NUMBER_OF_BLOCKS
import one.block.recenteosblocks.util.Event
import one.block.recenteosblocks.util.getRequestBody
import java.io.IOException

class ListViewModel(
    private val blockRepository: BlockRepository
) : ViewModel() {

    private val _blockchainInfo = MutableLiveData<BlockchainInfo>()
    private val _block = MutableLiveData<Block>()
    private val _onItemClickEvent = MutableLiveData<Event<Block>>()
    private val _showEventMessage = MutableLiveData<Event<String>>()

    val blockchainInfo : LiveData<BlockchainInfo>
        get() = _blockchainInfo

    val block : LiveData<Block>
        get() = _block

    val onItemClickEvent : LiveData<Event<Block>>
        get() = _onItemClickEvent

    val showEventMessage : LiveData<Event<String>>
        get() = _showEventMessage

    fun getBlockchainInfo() {
        viewModelScope.launch {
            try {
                val blockChainInfo =
                    withContext(viewModelScope.coroutineContext) {
                        blockRepository.getBlockchainInfo()
                    }
                _blockchainInfo.value = blockChainInfo
                getBlocksList()
            } catch (networkException: IOException) {
                _showEventMessage.value = Event(networkException.toString())
            }
        }
    }

    private fun getBlocksList() {
        _blockchainInfo.value?.headBlockNum?.let { headBlockNum ->
            val requestBody = getRequestBody(
                BLOCK_NUM_OR_ID,
                headBlockNum
            )
            getLastNBlocks(NUMBER_OF_BLOCKS, requestBody)
        }
    }

    private fun getLastNBlocks(numBlocks: Int, requestBody: JsonObject) {
        if (numBlocks > 0) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val block = viewModelScope.async {
                        blockRepository.getBlock(requestBody)
                    }.await()
                    saveBlockAndGetPrevious(numBlocks - 1, block)
                } catch (networkException: IOException) {
                    _showEventMessage.value = Event(networkException.toString())
                }
            }
        }
    }

    private fun saveBlockAndGetPrevious(numBlocks: Int, block: Block?) {
        block?.let {
            _block.postValue(it)
            viewModelScope.launch {
                blockRepository.saveBlock(it)
            }
            _block.value?.previous?.let { previousBlockId ->
                val newRequestBody = getRequestBody(
                    BLOCK_NUM_OR_ID,
                    previousBlockId
                )
                getLastNBlocks(numBlocks - 1, newRequestBody)
            }
        }
    }

    fun onItemClick(block: Block) {
        _onItemClickEvent.value = Event(block)
    }
}