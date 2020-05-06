package one.block.recenteosblocks.data.db.entities

data class Block(
    var id: String? = null,
    var block_num: String? = null,
    var timestamp: String? = null,
    var producer: String? = null,
    var confirmed: String? = null,
    var previous: String? = null,
    var transaction_mroot: String? = null,
    var action_mroot: String? = null,
    var schedule_version: String? = null,
    var ref_block_prefix: String? = null
)