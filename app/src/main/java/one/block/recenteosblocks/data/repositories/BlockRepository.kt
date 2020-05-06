package one.block.recenteosblocks.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import one.block.recenteosblocks.data.db.entities.Block
import one.block.recenteosblocks.data.db.entities.BlockchainInfo
import one.block.recenteosblocks.data.network.EosAPI
import one.block.recenteosblocks.data.network.SafeApiRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BlockRepository(
    private val eosApi: EosAPI
) : SafeApiRequest() {

    suspend fun getBlockchainInfo() = apiRequest {
         eosApi.getBlockchainInfo()
    }

    suspend fun getBlock(blockNumber: JsonObject) = apiRequest { eosApi.getBlock(blockNumber) }
}