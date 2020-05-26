package one.block.recenteosblocks.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

const val VIEW_ID = 0

class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        homeViewModel = HomeViewModel()
    }

    @Test
    fun goToList() {
        homeViewModel.goToList(VIEW_ID)
        assert(homeViewModel.goToListEvent.value != null)
    }
}