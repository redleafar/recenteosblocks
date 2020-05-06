package one.block.recenteosblocks.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import one.block.recenteosblocks.R
import one.block.recenteosblocks.data.network.EosAPI
import one.block.recenteosblocks.data.repositories.BlockRepository
import one.block.recenteosblocks.databinding.ActivityBlockDetailBinding

class BlockDetailActivity : AppCompatActivity() {

    private lateinit var factory: BlockDetailViewModelFactory
    private lateinit var viewModel: BlockDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        val binding: ActivityBlockDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_block_detail)
        val eosAPI = EosAPI()
        val blockRepository = BlockRepository(eosAPI)
        factory = BlockDetailViewModelFactory(blockRepository)
        viewModel = ViewModelProvider(this@BlockDetailActivity, factory).get(BlockDetailViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
    }
}
