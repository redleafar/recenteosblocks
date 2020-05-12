package one.block.recenteosblocks.data.repositories

import com.google.gson.JsonObject
import one.block.recenteosblocks.data.network.EosAPI
import one.block.recenteosblocks.data.network.SafeApiRequest

class BlockRepository(
    private val eosApi: EosAPI
) : SafeApiRequest() {

    suspend fun getBlockchainInfo() = apiRequest {
         eosApi.getBlockchainInfo()
    }

    suspend fun getBlock(blockNumber: JsonObject) = apiRequest {
        eosApi.getBlock(blockNumber)
    }
}