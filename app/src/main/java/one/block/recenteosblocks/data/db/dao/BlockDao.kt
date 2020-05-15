package one.block.recenteosblocks.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import one.block.recenteosblocks.data.db.entities.Block

@Dao
interface BlockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(block: Block) : Long

    @Query("SELECT * FROM block WHERE blockNum == :blockNum")
    suspend fun getBlock(blockNum: String): Block
}