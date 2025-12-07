package com.jadecook.finalproject

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var tvQuote: TextView
    private lateinit var tvAuthor: TextView
    private lateinit var btnRefreshQuote: Button

    // Local fallback quotes so the screen ALWAYS shows something
    private val localQuotes: List<Quote> = listOf(
        Quote("The best way to predict the future is to create it.", "Peter Drucker"),
        Quote("Success is the sum of small efforts, repeated day in and day out.", "Robert Collier"),
        Quote("Discipline is the bridge between goals and accomplishment.", "Jim Rohn"),
        Quote("You don’t have to be great to start, but you have to start to be great.", "Zig Ziglar"),
        Quote("Your only limit is your mind.", "Unknown")
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvQuote = view.findViewById(R.id.tvQuote)
        tvAuthor = view.findViewById(R.id.tvAuthor)
        btnRefreshQuote = view.findViewById(R.id.btnRefreshQuote)

        // Show something immediately
        showRandomLocalQuote()

        btnRefreshQuote.setOnClickListener {
            fetchRandomQuote()
        }

        // Try network once on start
        fetchRandomQuote()
    }

    private fun showRandomLocalQuote() {
        val quote: Quote = localQuotes.random()
        tvQuote.text = quote.text
        tvAuthor.text = "- ${quote.author}"
    }

    private fun fetchRandomQuote() {
        // If fragment is not attached, bail out
        if (!isAdded) return

        tvQuote.text = "Loading motivation..."
        tvAuthor.text = ""

        viewLifecycleOwner.lifecycleScope.launch {
            // 🔹 Explicit type so Kotlin is happy
            val quotes: List<Quote>? = try {
                withContext(Dispatchers.IO) {
                    ApiClient.apiService.getQuotes()
                }
            } catch (e: Exception) {
                Log.e("HomeFragment", "Error fetching quote", e)
                null
            }

            if (!isAdded) return@launch

            if (!quotes.isNullOrEmpty()) {
                val randomQuote: Quote = quotes.random()
                val text: String = randomQuote.text?.takeIf { it.isNotBlank() }
                    ?: "Keep going — you’re doing great."
                val author: String = randomQuote.author?.takeIf { it.isNotBlank() }
                    ?: "Unknown"

                tvQuote.text = text
                tvAuthor.text = "- $author"
            } else {
                // Network failed or list empty → fallback
                showRandomLocalQuote()
            }
        }
    }
}
