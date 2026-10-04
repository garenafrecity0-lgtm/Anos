package com.example.util

import android.content.Context
import android.content.SharedPreferences
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object BackgroundMusicManager {

    private const val TAG = "BackgroundMusicManager"
    private const val PREFS_NAME = "sensifire_music_prefs"
    private const val KEY_MUSIC_ENABLED = "bg_music_enabled"
    private const val KEY_MUSIC_VOLUME = "bg_music_volume"

    // High quality gaming/cinematic audio stream fallback
    private const val AUDIO_STREAM_URL = "https://cdn.pixabay.com/download/audio/2022/05/27/audio_1808fbf07a.mp3?filename=epic-dramatic-action-trailer-115984.mp3"

    private var mediaPlayer: MediaPlayer? = null
    private var prefs: SharedPreferences? = null

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _isMusicEnabled = MutableStateFlow(true)
    val isMusicEnabled: StateFlow<Boolean> = _isMusicEnabled.asStateFlow()

    private val _currentTrackTitle = MutableStateFlow("Indila - Tourner Dans Le Vide (Gaming Edit)")
    val currentTrackTitle: StateFlow<String> = _currentTrackTitle.asStateFlow()

    fun initialize(context: Context) {
        if (prefs == null) {
            prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            _isMusicEnabled.value = prefs?.getBoolean(KEY_MUSIC_ENABLED, true) ?: true
        }

        if (_isMusicEnabled.value) {
            startPlayback(context)
        }
    }

    fun startPlayback(context: Context) {
        try {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer().apply {
                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
                    )
                    isLooping = true
                    setVolume(0.5f, 0.5f)

                    setDataSource(context.applicationContext, Uri.parse(AUDIO_STREAM_URL))

                    setOnPreparedListener { mp ->
                        if (_isMusicEnabled.value) {
                            mp.start()
                            _isPlaying.value = true
                            Log.d(TAG, "Background music playback started successfully.")
                        }
                    }

                    setOnErrorListener { _, what, extra ->
                        Log.w(TAG, "MediaPlayer error: what=$what, extra=$extra")
                        _isPlaying.value = false
                        true
                    }

                    prepareAsync()
                }
            } else if (!mediaPlayer!!.isPlaying && _isMusicEnabled.value) {
                mediaPlayer?.start()
                _isPlaying.value = true
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to start background music", e)
            _isPlaying.value = false
        }
    }

    fun toggleMusic(context: Context) {
        val newState = !_isMusicEnabled.value
        _isMusicEnabled.value = newState
        prefs?.edit()?.putBoolean(KEY_MUSIC_ENABLED, newState)?.apply()

        if (newState) {
            if (mediaPlayer == null) {
                startPlayback(context)
            } else {
                mediaPlayer?.start()
                _isPlaying.value = true
            }
        } else {
            pausePlayback()
        }
    }

    fun pausePlayback() {
        try {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
            }
            _isPlaying.value = false
        } catch (e: Exception) {
            Log.w(TAG, "Error pausing playback", e)
        }
    }

    fun resumePlayback(context: Context) {
        if (_isMusicEnabled.value) {
            if (mediaPlayer != null) {
                try {
                    mediaPlayer?.start()
                    _isPlaying.value = true
                } catch (_: Exception) {
                    startPlayback(context)
                }
            } else {
                startPlayback(context)
            }
        }
    }

    fun release() {
        try {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
            _isPlaying.value = false
        } catch (e: Exception) {
            Log.w(TAG, "Error releasing MediaPlayer", e)
        }
    }
}
