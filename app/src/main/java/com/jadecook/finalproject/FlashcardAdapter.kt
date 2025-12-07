package com.jadecook.finalproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FlashcardAdapter(
    private val cards: MutableList<Flashcard>,
    private val onClick: (Flashcard) -> Unit,
    private val onLongClick: (Flashcard) -> Unit
) : RecyclerView.Adapter<FlashcardAdapter.FlashcardViewHolder>() {

    inner class FlashcardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvFront: TextView = itemView.findViewById(R.id.tvFront)
        private val tvBack: TextView = itemView.findViewById(R.id.tvBack)

        fun bind(card: Flashcard) {
            tvFront.text = card.front
            tvBack.text = card.back

            itemView.setOnClickListener { onClick(card) }
            itemView.setOnLongClickListener {
                onLongClick(card)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashcardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_flashcard, parent, false)
        return FlashcardViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlashcardViewHolder, position: Int) {
        holder.bind(cards[position])
    }

    override fun getItemCount(): Int = cards.size

    fun addCard(card: Flashcard) {
        cards.add(0, card)
        notifyItemInserted(0)
    }

    fun removeCard(card: Flashcard) {
        val index = cards.indexOfFirst { it.id == card.id }
        if (index != -1) {
            cards.removeAt(index)
            notifyItemRemoved(index)
        }
    }
}
