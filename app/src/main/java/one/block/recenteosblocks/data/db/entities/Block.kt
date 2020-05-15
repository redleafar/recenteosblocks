package one.block.recenteosblocks.data.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Block(
    @SerializedName("id") var id: String? = null,
    @PrimaryKey(autoGenerate = false) @SerializedName("block_num") var blockNum: String,
    @SerializedName("timestamp") var timestamp: String? = null,
    @SerializedName("producer") var producer: String? = null,
    @SerializedName("confirmed") var confirmed: String? = null,
    @SerializedName("previous") var previous: String? = null,
    @SerializedName("transaction_mroot") var transactionMroot: String? = null,
    @SerializedName("action_mroot") var actionMroot: String? = null,
    @SerializedName("schedule_version") var scheduleVersion: String? = null,
    @SerializedName("ref_block_prefix") var refBlockPrefix: String? = null
) : Parcelable