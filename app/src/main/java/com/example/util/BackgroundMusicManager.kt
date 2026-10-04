package com.example.util

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import com.example.data.model.AuthConstants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object BackgroundMusicManager {

    private const val TAG = "BackgroundMusicManager"
    private const val PREFS_NAME = "sensifire_music_prefs"
    private const val KEY_MUSIC_ENABLED = "bg_music_enabled"

    // Direct streaming sources for Indila - Tourner dans le vide
    private val AUDIO_SOURCES = listOf(
        "https://archive.org/download/IndilaTournerDansLeVide/Indila%20-%20Tourner%20Dans%20Le%20Vide.mp3",
        "https://raw.githubusercontent.com/sensifire-assets/audio/main/indila_tourner_dans_le_vide.mp3",
        "https://cdn.pixabay.com/download/audio/2022/05/27/audio_1808fbf07a.mp3"
    )

    private var currentSourceIndex = 0
    private var mediaPlayer: MediaPlayer? = null
    private var prefs: SharedPreferences? = null

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _isMusicEnabled = MutableStateFlow(true)
    val isMusicEnabled: StateFlow<Boolean> = _isMusicEnabled.asStateFlow()

    private val _currentTrackTitle = MutableStateFlow("Indila - Tourner Dans Le Vide")
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
        if (currentSourceIndex >= AUDIO_SOURCES.size) {
            currentSourceIndex = 0
        }
        val sourceUrl = AUDIO_SOURCES[currentSourceIndex]

        try {
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                isLooping = true
                setVolume(0.6f, 0.6f)

                setDataSource(context.applicationContext, Uri.parse(sourceUrl))

                setOnPreparedListener { mp ->
                    if (_isMusicEnabled.value) {
                        mp.start()
                        _isPlaying.value = true
                        Log.d(TAG, "Indila - Tourner Dans Le Vide playback started successfully from $sourceUrl")
                    }
                }

                setOnErrorListener { _, what, extra ->
                    Log.w(TAG, "MediaPlayer error on source $sourceUrl (what=$what, extra=$extra), attempting next source...")
                    _isPlaying.value = false
                    currentSourceIndex = (currentSourceIndex + 1) % AUDIO_SOURCES.size
                    tryNextSource(context)
                    true
                }

                prepareAsync()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to start Indila audio playback", e)
            _isPlaying.value = false
        }
    }

    private fun tryNextSource(context: Context) {
        try {
            if (_isMusicEnabled.value && currentSourceIndex < AUDIO_SOURCES.size) {
                val nextUrl = AUDIO_SOURCES[currentSourceIndex]
                mediaPlayer?.reset()
                mediaPlayer?.setDataSource(context.applicationContext, Uri.parse(nextUrl))
                mediaPlayer?.prepareAsync()
            }
        } catch (e: Exception) {
            Log.w(TAG, "Fallback source error", e)
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

    fun openAudiomackTrack(context: Context) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(AuthConstants.AUDIOMACK_SONG_URL))
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.w(TAG, "Could not open Audiomack link", e)
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
