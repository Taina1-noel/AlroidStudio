package com.jadecook.finalproject

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class MusicFragment : Fragment() {

    private lateinit var rvPlaylists: RecyclerView
    private var mediaPlayer: MediaPlayer? = null
    private var currentPlaylist: Playlist? = null

    // Study playlists using sample MP3 streams (stays INSIDE the app)
    private val playlists = listOf(
        Playlist(
            title = "Lofi Beats for Studying",
            description = "Chill lofi hip hop to help you focus.",
            url = "https://www.samplelib.com/lib/preview/mp3/sample-3s.mp3"
        ),
        Playlist(
            title = "Deep Focus Ambient",
            description = "Soft ambient background for long sessions.",
            url = "https://www.samplelib.com/lib/preview/mp3/sample-6s.mp3"
        ),
        Playlist(
            title = "Piano Study Session",
            description = "Gentle piano pieces for reading & notes.",
            url = "https://www.samplelib.com/lib/preview/mp3/sample-9s.mp3"
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_music, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvPlaylists = view.findViewById(R.id.rvPlaylists)

        val adapter = PlaylistAdapter(playlists) { playlist ->
            onPlaylistClicked(playlist)
        }

        rvPlaylists.adapter = adapter
    }

    private fun onPlaylistClicked(playlist: Playlist) {
        // If same playlist is playing, toggle pause/play
        if (currentPlaylist?.url == playlist.url && mediaPlayer != null) {
            val mp = mediaPlayer!!
            if (mp.isPlaying) {
                mp.pause()
            } else {
                mp.start()
            }
            return
        }

        // Different playlist: stop current and start new
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(playlist.url)
            setOnPreparedListener { start() }
            prepareAsync()
        }

        currentPlaylist = playlist
        saveCurrentPlaylistTitle(playlist.title)
    }

    private fun saveCurrentPlaylistTitle(title: String) {
        val prefs = requireContext()
            .getSharedPreferences("studysync_prefs", Context.MODE_PRIVATE)
        prefs.edit().putString("current_playlist_title", title).apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
