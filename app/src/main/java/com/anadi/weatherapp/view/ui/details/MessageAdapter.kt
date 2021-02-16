package com.anadi.weatherapp.view.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anadi.weatherapp.R
import com.anadi.weatherapp.databinding.*
import com.bumptech.glide.Glide

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.MessageHolder>() {

    var dataset = emptyList<Message.TextMessage>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        val binding = MessageTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MessageHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        holder.bind(dataset[position])
    }

    class MessageHolder(
            private val binding: MessageTextBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(message: Message.TextMessage) {
            Glide.with(binding.avatar)
                    .load(R.drawable.a04n)
                    .into(binding.avatar)

            binding.author.text = message.author
            binding.text.text = message.text
        }
    }
}
