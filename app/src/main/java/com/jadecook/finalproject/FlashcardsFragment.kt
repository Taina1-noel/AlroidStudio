package com.jadecook.finalproject

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class FlashcardsFragment : Fragment() {

    private lateinit var rvFlashcards: RecyclerView
    private lateinit var btnAddFlashcard: MaterialButton
    private lateinit var adapter: FlashcardAdapter

    private val cards = mutableListOf(
        Flashcard(1, "What is a firewall?", "A network security device that monitors and filters traffic."),
        Flashcard(2, "What is the CIA triad?", "Confidentiality, Integrity, Availability")
    )

    private var nextId = 3

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_flashcards, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvFlashcards = view.findViewById(R.id.rvFlashcards)
        btnAddFlashcard = view.findViewById(R.id.btnAddFlashcard)

        // Example progress numbers – you can later compute real values
        val totalHoursStudied = 45.5
        val currentStreakDays = 7
        val examProgressPercent = 65

        val tvTotalHoursValue = view.findViewById<TextView>(R.id.tvTotalHoursValue)
        val tvCurrentStreakValue = view.findViewById<TextView>(R.id.tvCurrentStreakValue)
        val tvExamProgressValue = view.findViewById<TextView>(R.id.tvExamProgressValue)
        val progressExam = view.findViewById<ProgressBar>(R.id.progressExam)

        tvTotalHoursValue.text = String.format("%.1f hours", totalHoursStudied)
        tvCurrentStreakValue.text = "$currentStreakDays Days"
        tvExamProgressValue.text = "$examProgressPercent%"
        progressExam.progress = examProgressPercent

        adapter = FlashcardAdapter(
            cards,
            onClick = { card -> onCardClicked(card) },
            onLongClick = { card -> onCardLongClicked(card) }
        )
        rvFlashcards.adapter = adapter

        btnAddFlashcard.setOnClickListener {
            showAddCardDialog()
        }
    }

    private fun onCardClicked(card: Flashcard) {
        Toast.makeText(
            requireContext(),
            "Q: ${card.front}\nA: ${card.back}",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun onCardLongClicked(card: Flashcard) {
        val options = arrayOf("Edit", "Delete")

        AlertDialog.Builder(requireContext())
            .setTitle("Choose action")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> showEditCardDialog(card)
                    1 -> {
                        adapter.removeCard(card)
                        Toast.makeText(requireContext(), "Card deleted", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .show()
    }

    private fun showAddCardDialog() {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_edit_flashcard, null)

        val etFront = dialogView.findViewById<EditText>(R.id.etFront)
        val etBack = dialogView.findViewById<EditText>(R.id.etBack)

        AlertDialog.Builder(requireContext())
            .setTitle("New Flashcard")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val front = etFront.text.toString().trim()
                val back = etBack.text.toString().trim()

                if (front.isNotEmpty() && back.isNotEmpty()) {
                    val newCard = Flashcard(nextId++, front, back)
                    adapter.addCard(newCard)
                } else {
                    Toast.makeText(requireContext(), "Both fields are required", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showEditCardDialog(card: Flashcard) {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_edit_flashcard, null)

        val etFront = dialogView.findViewById<EditText>(R.id.etFront)
        val etBack = dialogView.findViewById<EditText>(R.id.etBack)

        etFront.setText(card.front)
        etBack.setText(card.back)

        AlertDialog.Builder(requireContext())
            .setTitle("Edit Flashcard")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val newFront = etFront.text.toString().trim()
                val newBack = etBack.text.toString().trim()

                if (newFront.isNotEmpty() && newBack.isNotEmpty()) {
                    card.front = newFront
                    card.back = newBack
                    rvFlashcards.adapter?.notifyDataSetChanged()
                } else {
                    Toast.makeText(requireContext(), "Both fields are required", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
