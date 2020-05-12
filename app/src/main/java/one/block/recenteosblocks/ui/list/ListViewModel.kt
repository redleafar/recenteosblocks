package one.block.recenteosblocks.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import kotlinx.coroutines.Job
import one.block.recenteosblocks.data.db.entities.Block
import one.block.recenteosblocks.data.db.entities.BlockchainInfo
import one.block.recenteosblocks.data.repositories.BlockRepository
import one.block.recenteosblocks.util.ApiException
import one.block.recenteosblocks.util.Constants.BLOCK_NUM_OR_ID
import one.block.recenteosblocks.util.Constants.NUMBER_OF_BLOCKS
import one.block.recenteosblocks.util.Coroutines
import one.block.recenteosblocks.util.Event

class ListViewModel(
    private val blockRepository: BlockRepository
) : ViewModel() {

    private lateinit var getBlockchainInfoJob: Job
    private lateinit var getBlockJob: Job
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
        getBlockchainInfoJob = Coroutines.ioThenMain(
            { blockRepository.getBlockchainInfo() },
            {
                _blockchainInfo.value = it
                getBlocksList()
            },
            { _showEventMessage.value = Event(it) }
        )
    }

    fun getBlocksList() {
        _blockchainInfo.value?.headBlockNum?.let { headBlockNum ->
            val requestBody = getRequestBody(
                BLOCK_NUM_OR_ID,
                headBlockNum
            )
            getLastNBlocks(NUMBER_OF_BLOCKS, requestBody)
        }
    }

    fun getLastNBlocks(numBlocks: Int, requestBody: JsonObject) {
        if (numBlocks > 0) {
            getBlockJob = Coroutines.ioThenMain(
                { blockRepository.getBlock(requestBody) },
                { addBlockAndGetPrevious(numBlocks - 1, it) },
                { _showEventMessage.value = Event(it) }
            )
        }
    }

    fun getRequestBody(key: String, headBlockNum: String) : JsonObject{
        val requestBody = JsonObject()
        requestBody.addProperty(key, headBlockNum)
        return requestBody
    }

    fun addBlockAndGetPrevious(numBlocks: Int, block: Block?) {
        block?.let {
            _block.value = it as Block?
            _block.value?.previous?.let {
                val newRequestBody = getRequestBody(
                    BLOCK_NUM_OR_ID,
                    _block.value?.previous!!
                )
                getLastNBlocks(numBlocks - 1, newRequestBody)
            }
        }
    }

    fun onItemClick(block: Block) {
        _onItemClickEvent.value = Event(block)
    }

    override fun onCleared() {
        super.onCleared()
        if (::getBlockchainInfoJob.isInitialized) getBlockchainInfoJob.cancel()
        if (::getBlockJob.isInitialized) getBlockJob.cancel()
    }
}