package one.block.recenteosblocks.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import kotlinx.coroutines.Job
import one.block.recenteosblocks.data.db.entities.Block
import one.block.recenteosblocks.data.db.entities.BlockchainInfo
import one.block.recenteosblocks.data.repositories.BlockRepository
import one.block.recenteosblocks.util.Constants.BLOCK_NUM_OR_ID
import one.block.recenteosblocks.util.Constants.NUMBER_OF_BLOCKS
import one.block.recenteosblocks.util.Coroutines

class ListViewModel(
    private val blockRepository: BlockRepository
) : ViewModel() {

    private lateinit var getBlockchainInfoJob: Job
    private lateinit var getBlockJob: Job
    private val _blockchainInfo = MutableLiveData<BlockchainInfo>()
    private val _block = MutableLiveData<Block>()

    val blockchainInfo : LiveData<BlockchainInfo>
        get() = _blockchainInfo

    val block : LiveData<Block>
        get() = _block

    fun getBlockchainInfo() {
        getBlockchainInfoJob = Coroutines.ioThenMain(
            {
                blockRepository.getBlockchainInfo()
            },
            {
                _blockchainInfo.value = it
                getBlocksList()
            }
        )
    }

    fun getBlocksList() {
        val requestBody = getRequestBody(
            BLOCK_NUM_OR_ID,
            _blockchainInfo.value?.head_block_num!!
        )
        getLastNBlocks(NUMBER_OF_BLOCKS, requestBody)
    }

    fun getLastNBlocks(nBlocks: Int, requestBody: JsonObject) {
        var newRequestBody: JsonObject
        if (nBlocks > 0) {
            getBlockJob = Coroutines.ioThenMain(
                {
                    blockRepository.getBlock(requestBody)
                },
                {
                    it?.let {
                        _block.value = it
                        newRequestBody = getRequestBody(
                            BLOCK_NUM_OR_ID,
                            _block.value?.previous!!
                        )
                        getLastNBlocks(nBlocks - 1, newRequestBody)
                    }
                }
            )
        }
    }

    fun getRequestBody(key: String, headBlockNum: String) : JsonObject{
        val requestBody = JsonObject()
        requestBody.addProperty(key, headBlockNum)
        return requestBody
    }

    override fun onCleared() {
        super.onCleared()
        if (::getBlockchainInfoJob.isInitialized) getBlockchainInfoJob.cancel()
        if (::getBlockJob.isInitialized) getBlockJob.cancel()
    }
}