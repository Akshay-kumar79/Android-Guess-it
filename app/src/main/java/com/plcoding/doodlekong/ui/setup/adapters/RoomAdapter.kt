package com.plcoding.doodlekong.ui.setup.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.plcoding.doodlekong.data.remote.ws.Room
import com.plcoding.doodlekong.databinding.ItemRoomBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomAdapter @Inject constructor() : Adapter<RoomAdapter.RoomViewHolder>() {
    
    var rooms: List<Room> = emptyList()
        private set
    
    suspend fun updateDataSet(newDataset: List<Room>) = withContext(Dispatchers.Default) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return rooms.size
            }
            
            override fun getNewListSize(): Int {
                return newDataset.size
            }
            
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return rooms[oldItemPosition] == newDataset[newItemPosition]
            }
            
            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return rooms[oldItemPosition] == newDataset[newItemPosition]
            }
            
        })
        
        withContext(Dispatchers.Main) {
            rooms = newDataset
            diff.dispatchUpdatesTo(this@RoomAdapter)
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        return RoomViewHolder(
            ItemRoomBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    
    override fun getItemCount(): Int {
        return rooms.size
    }
    
    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.bind(rooms[position])
    }
    
    
    inner class RoomViewHolder(val binding: ItemRoomBinding) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(room: Room) {
            binding.apply {
                tvRoomName.text = room.name
                val playerCountText = "${room.playerCount}/${room.maxPlayers}"
                tvRoomPersonCount.text = playerCountText
                
                root.setOnClickListener {
                    onRoomClickListener?.let { click ->
                        click(room)
                    }
                }
            }
        }
        
    }
    
    private var onRoomClickListener: ((Room) -> Unit)? = null
    
    fun setOnRoomClickListener(listener: ((Room) -> Unit)) {
        onRoomClickListener = listener
    }
}