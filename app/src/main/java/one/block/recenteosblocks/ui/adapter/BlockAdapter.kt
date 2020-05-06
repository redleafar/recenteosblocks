package one.block.recenteosblocks.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import one.block.recenteosblocks.R
import one.block.recenteosblocks.data.db.entities.Block
import one.block.recenteosblocks.databinding.RecyclerviewBlockBinding

class BlockAdapter(
    private val blocks: List<Block>
) : RecyclerView.Adapter<BlockAdapter.BlockViewHolder>() {

    override fun getItemCount() = blocks.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BlockViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recyclerview_block,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: BlockViewHolder, position: Int) {
        holder.recyclerViewBlockBinding.block = blocks[position]
    }

    inner class BlockViewHolder(
        val recyclerViewBlockBinding: RecyclerviewBlockBinding
    ) : RecyclerView.ViewHolder(recyclerViewBlockBinding.root)


}