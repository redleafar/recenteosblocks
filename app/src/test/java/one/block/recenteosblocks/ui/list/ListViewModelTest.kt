package one.block.recenteosblocks.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.JsonObject
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import one.block.recenteosblocks.data.db.entities.Block
import one.block.recenteosblocks.data.db.entities.BlockchainInfo
import one.block.recenteosblocks.data.repositories.BLOCK_NUM
import one.block.recenteosblocks.data.repositories.BlockRepository
import one.block.recenteosblocks.util.Constants.BLOCK_NUM_OR_ID
import one.block.recenteosblocks.util.getRequestBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

const val HEAD_BLOCK_NUM = "99999999"
const val PREVIOUS_BLOCK_ID = "000000000"

class ListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var blockRepository: BlockRepository

    @Mock
    private lateinit var blockchainInfo: BlockchainInfo

    @Mock
    private lateinit var block: Block

    private lateinit var listViewModel: ListViewModel
    private lateinit var requestBody: JsonObject

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        listViewModel = ListViewModel(blockRepository)
        Dispatchers.setMain(Dispatchers.Unconfined)
        requestBody = getRequestBody(BLOCK_NUM_OR_ID, BLOCK_NUM)

        runBlockingTest {
            whenever(blockRepository.getBlockchainInfo()).thenReturn(blockchainInfo)
            whenever(blockchainInfo.headBlockNum).thenReturn(HEAD_BLOCK_NUM)
            whenever(blockRepository.getBlock(requestBody)).thenReturn(block)
            whenever(block.previous).thenReturn(PREVIOUS_BLOCK_ID)
        }
    }

    @Test
    fun getBlockchainInfo() = runBlockingTest {
        listViewModel.getBlockchainInfo()
        makeVerifications()
        makeAsserts()
    }

    private fun makeVerifications() {
        runBlockingTest {
            verify(blockRepository).getBlockchainInfo()
            verify(blockRepository).getBlock(requestBody)
            verify(blockRepository).saveBlock(block)
        }
    }

    private fun makeAsserts() {
        runBlockingTest {
            assert(listViewModel.blockchainInfo.value != null)
            assert(listViewModel.block.value != null)
        }
    }

    @ExperimentalCoroutinesApi
    @After
    fun cleanUp() {
        Dispatchers.resetMain()
    }
}