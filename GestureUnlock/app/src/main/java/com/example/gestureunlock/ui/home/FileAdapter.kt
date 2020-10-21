package com.example.gestureunlock.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gestureunlock.data.File
import com.example.gestureunlock.databinding.ListItemFileBinding

class FileAdapter(val clickListener: FileListener):
    ListAdapter<File, FileAdapter.ViewHolder>(FileDiffCallback()) {

    /*
    var data =  listOf<File>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }*/


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }


    class ViewHolder private constructor(val binding: ListItemFileBinding) : RecyclerView.ViewHolder(binding.root){
        val fileName: TextView = binding.fileName
        val createdTime: TextView = binding.createdTime
        val fileImage: ImageView = binding.fileImage

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)

                //val view = layoutInflater
                //    .inflate(R.layout.list_item_file, parent, false)

                val binding = ListItemFileBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

         fun bind(item: File, clickListener: FileListener) {
             binding.file = item
             binding.executePendingBindings()
             binding.clickListener = clickListener

//            val res = itemView.context.resources
//            fileName.text = item.fileName
//            createdTime.text = convertLongToDateString(item.createdTimeMilli)
//            fileImage.setImageResource(R.drawable.shared)
        }
    }
}


class FileDiffCallback : DiffUtil.ItemCallback<File>() {
    override fun areItemsTheSame(oldItem: File, newItem: File): Boolean {
        return oldItem.fileId == newItem.fileId
    }

    override fun areContentsTheSame(oldItem: File, newItem: File): Boolean {
        return oldItem == newItem
    }
}


class FileListener(val clickListener: (fileId: Long) -> Unit) {
    fun onClick(file: File) = clickListener(file.fileId)
}


