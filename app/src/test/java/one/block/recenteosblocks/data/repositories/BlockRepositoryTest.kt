package one.block.recenteosblocks.data.repositories

import com.google.gson.JsonObject
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest
import one.block.recenteosblocks.data.db.AppDatabase
import one.block.recenteosblocks.data.db.dao.BlockDao
import one.block.recenteosblocks.data.db.entities.Block
import one.block.recenteosblocks.data.db.entities.BlockchainInfo
import one.block.recenteosblocks.data.network.EosAPI
import one.block.recenteosblocks.util.Constants.BLOCK_NUM_OR_ID
import one.block.recenteosblocks.util.getRequestBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

const val HEAD_BLOCK_NUM = "99999999"
const val BLOCK_ID = "000000000"
const val BLOCK_NUM = "99999999"
const val BLOCK_TIMESTAMP = "000000000"
const val BLOCK_PRODUCER = "PRODUCER"
const val BLOCK_CONFIRMED = "true"
const val BLOCK_PREVIOUS = "99999998"
const val BLOCK_TRANSACTION = "TRANSACTION"
const val BLOCK_ACTION = "ACTION"
const val BLOCK_SCHEDULE_VERSION = "SCHEDULE_VERSION"
const val BLOCK_REF_PREFIX = "BLOCK_REF_PREFIX"

class BlockRepositoryTest {

    @Mock
    private lateinit var eosApi: EosAPI

    @Mock
    private lateinit var db: AppDatabase

    @Mock
    private lateinit var dao: BlockDao

    private lateinit var blockchainInfo: BlockchainInfo
    private lateinit var blockRepository: BlockRepository
    private lateinit var block: Block
    private lateinit var requestBody: JsonObject

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        blockRepository = BlockRepository(eosApi, db)
        blockchainInfo = BlockchainInfo(HEAD_BLOCK_NUM)
        requestBody = getRequestBody(BLOCK_NUM_OR_ID, BLOCK_ID)
        block = Block(
            BLOCK_ID,
            BLOCK_NUM,
            BLOCK_TIMESTAMP,
            BLOCK_PRODUCER,
            BLOCK_CONFIRMED,
            BLOCK_PREVIOUS,
            BLOCK_TRANSACTION,
            BLOCK_ACTION,
            BLOCK_SCHEDULE_VERSION,
            BLOCK_REF_PREFIX
        )
    }

    @Test
    fun getBlockchainInfo() = runBlockingTest {
            whenever(eosApi.getBlockchainInfo()).thenReturn(Response.success(blockchainInfo))
            blockRepository.getBlockchainInfo()
            Mockito.verify(eosApi).getBlockchainInfo()
        }

    @Test
    fun getBlock() = runBlockingTest {
            whenever(eosApi.getBlock(requestBody)).thenReturn(Response.success(block))
            blockRepository.getBlock(requestBody)
            Mockito.verify(eosApi).getBlock(requestBody)
        }

    @Test
    fun saveBlock() = runBlockingTest {
            whenever(db.getBlockDao()).thenReturn(dao)
            whenever(dao.insertOrUpdate(block)).thenReturn(0)
            blockRepository.saveBlock(block)
            Mockito.verify(db).getBlockDao()
            Mockito.verify(dao).insertOrUpdate(block)
        }
}