package one.block.recenteosblocks.data.db.entities

import com.google.gson.annotations.SerializedName

data class BlockchainInfo(
    @SerializedName("head_block_num") var headBlockNum: String
)