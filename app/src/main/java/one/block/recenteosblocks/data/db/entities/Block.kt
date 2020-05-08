package one.block.recenteosblocks.data.db.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Block(
    @SerializedName("id") var id: String? = null,
    @SerializedName("block_num") var blockNum: String? = null,
    @SerializedName("timestamp") var timestamp: String? = null,
    @SerializedName("producer") var producer: String? = null,
    @SerializedName("confirmed") var confirmed: String? = null,
    @SerializedName("previous") var previous: String? = null,
    @SerializedName("transaction_mroot") var transactionMroot: String? = null,
    @SerializedName("action_mroot") var actionMroot: String? = null,
    @SerializedName("schedule_version") var scheduleVersion: String? = null,
    @SerializedName("ref_block_prefix") var refBlockPrefix: String? = null
) : Parcelable