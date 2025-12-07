package com.jadecook.finalproject

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton

class TimerFragment : Fragment() {

    private lateinit var tvTime: TextView
    private lateinit var btnStartPause: MaterialButton
    private lateinit var btnReset: MaterialButton
    private lateinit var tvCurrentlyPlaying: TextView

    // 25 minutes in milliseconds
    private val pomodoroDurationMs = 25L * 60L * 1000L

    private var remainingTimeMs = pomodoroDurationMs
    private var isRunning = false
    private var countDownTimer: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvTime = view.findViewById(R.id.tvTime)
        btnStartPause = view.findViewById(R.id.btnStartPause)
        btnReset = view.findViewById(R.id.btnReset)
        tvCurrentlyPlaying = view.findViewById(R.id.tvCurrentlyPlaying)

        updateTimeText()
        updateCurrentlyPlaying()

        btnStartPause.setOnClickListener {
            if (isRunning) {
                pauseTimer()
            } else {
                startTimer()
            }
        }

        btnReset.setOnClickListener {
            resetTimer()
        }
    }

    private fun startTimer() {
        countDownTimer?.cancel()

        countDownTimer = object : CountDownTimer(remainingTimeMs, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTimeMs = millisUntilFinished
                updateTimeText()
            }

            override fun onFinish() {
                remainingTimeMs = 0L
                updateTimeText()
                isRunning = false
                btnStartPause.text = "Start"
            }
        }.start()

        isRunning = true
        btnStartPause.text = "Pause"
    }

    private fun pauseTimer() {
        countDownTimer?.cancel()
        isRunning = false
        btnStartPause.text = "Start"
    }

    private fun resetTimer() {
        countDownTimer?.cancel()
        remainingTimeMs = pomodoroDurationMs
        updateTimeText()
        isRunning = false
        btnStartPause.text = "Start"
    }

    private fun updateTimeText() {
        val totalSeconds = remainingTimeMs / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        tvTime.text = String.format("%02d:%02d", minutes, seconds)
    }

    private fun updateCurrentlyPlaying() {
        val prefs = requireContext()
            .getSharedPreferences("studysync_prefs", Context.MODE_PRIVATE)

        val title = prefs.getString("current_playlist_title", null)

        tvCurrentlyPlaying.text = if (title.isNullOrEmpty()) {
            "Currently Playing: None"
        } else {
            "Currently Playing: $title"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
    }
}
