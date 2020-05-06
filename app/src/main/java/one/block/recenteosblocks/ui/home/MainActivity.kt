package one.block.recenteosblocks.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import one.block.recenteosblocks.R
import one.block.recenteosblocks.databinding.ActivityMainBinding
import one.block.recenteosblocks.ui.list.ListActivity
import one.block.recenteosblocks.util.toast

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        initObservers()
    }

    private fun initViewModel() {
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this@MainActivity).get(HomeViewModel::class.java)
        binding.homeviewmodel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initObservers() {
        viewModel.goToListEvent.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                goToListActivity()
            }
        })
    }

    private fun goToListActivity() {
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
    }
}
