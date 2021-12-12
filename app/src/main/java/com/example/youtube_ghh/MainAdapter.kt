package com.example.youtube_ghh

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.youtube_ghh.databinding.RvItemBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer

class MainAdapter(private var videoList:Array<Array<String>> ,  private val player: YouTubePlayer):RecyclerView.Adapter<MainAdapter.ItemHolder>() {
    class ItemHolder(val binding: RvItemBinding ):RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(RvItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val selectedVideo = videoList[position]
        holder.binding.apply {
            btnTitle.text = selectedVideo[0]

        }
        holder.binding.btnTitle.setOnClickListener {
            player.loadVideo(selectedVideo[1],0F)
        }
    }

    override fun getItemCount() = videoList.size

}