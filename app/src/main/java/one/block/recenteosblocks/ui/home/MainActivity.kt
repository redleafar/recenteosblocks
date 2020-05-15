package one.block.recenteosblocks.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import one.block.recenteosblocks.R
import one.block.recenteosblocks.databinding.ActivityMainBinding
import one.block.recenteosblocks.ui.list.ListActivity
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initObservers()
    }

    private fun initBinding() {
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.homeviewmodel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initObservers() {
        viewModel.goToListEvent.observe(this, Observer {
            it.hasBeenHandled()?.let {
                goToListActivity()
            }
        })
    }

    private fun goToListActivity() {
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
    }
}
