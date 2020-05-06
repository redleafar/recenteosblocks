package one.block.recenteosblocks.data.network

import com.google.gson.JsonObject
import one.block.recenteosblocks.data.db.entities.Block
import one.block.recenteosblocks.data.db.entities.BlockchainInfo
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface EosAPI {

    @GET("get_info")
    suspend fun getBlockchainInfo() : Response<BlockchainInfo>

    @Headers("Content-Type: application/json")
    @POST("get_block")
    suspend fun getBlock(
        @Body body: JsonObject
    ) : Response<Block>

    companion object {
        operator fun invoke() : EosAPI {
            return Retrofit.Builder()
                .baseUrl("https://eos.greymass.com/v1/chain/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(EosAPI::class.java)
        }
    }
}