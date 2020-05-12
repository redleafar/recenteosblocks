package one.block.recenteosblocks.ui.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list.progress_bar
import kotlinx.android.synthetic.main.activity_list.recycler_view
import one.block.recenteosblocks.R
import one.block.recenteosblocks.data.db.entities.Block
import one.block.recenteosblocks.data.db.entities.BlockchainInfo
import one.block.recenteosblocks.data.network.EosAPI
import one.block.recenteosblocks.data.repositories.BlockRepository
import one.block.recenteosblocks.databinding.ActivityListBinding
import one.block.recenteosblocks.ui.adapter.BlockAdapter
import one.block.recenteosblocks.ui.detail.BlockDetailActivity
import one.block.recenteosblocks.util.hide
import one.block.recenteosblocks.util.show
import one.block.recenteosblocks.util.toast

class ListActivity : AppCompatActivity() {

    private lateinit var factory: ListViewModelFactory
    private lateinit var viewModel: ListViewModel
    private lateinit var adapter: BlockAdapter
    private val onItemClick: (block: Block) -> Unit = { block ->
        viewModel.onItemClick(block)
    }

    private var list: MutableList<Block> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        initAdapter()
        initObservers()
        getBlockchainInfo()
    }

    fun initViewModel() {
        val binding: ActivityListBinding = DataBindingUtil.setContentView(this, R.layout.activity_list)
        val eosAPI = EosAPI()
        val blockRepository = BlockRepository(eosAPI)
        factory = ListViewModelFactory(blockRepository)
        viewModel = ViewModelProvider(this@ListActivity, factory).get(ListViewModel::class.java)
        binding.listviewmodel = viewModel
        binding.lifecycleOwner = this
    }

    fun initAdapter() {
        adapter = BlockAdapter(list, onItemClick)
        recycler_view.also {
            it.layoutManager = LinearLayoutManager(this)
            it.setHasFixedSize(true)
            it.adapter = adapter
            val dividerItemDecoration = DividerItemDecoration(
                recycler_view.context,
                DividerItemDecoration.VERTICAL
            )
            it.addItemDecoration(dividerItemDecoration)
        }
    }

    fun getBlockchainInfo() {
        progress_bar.show()
        viewModel.getBlockchainInfo()
    }

    fun initObservers() {
        viewModel.blockchainInfo.observe(this, Observer {
            progress_bar.hide()
        })

        viewModel.block.observe(this, Observer {
            list.add(it)
            recycler_view.adapter?.notifyDataSetChanged()
        })

        viewModel.onItemClickEvent.observe(this, Observer {
            val intent = BlockDetailActivity.getIntent(this, it.getContent())
            startActivity(intent)
        })

        viewModel.showEventMessage.observe(this, Observer {
            toast(it.getContent())
        })
    }
}
