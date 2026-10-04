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

    // High Energy Brazilian Phonk / Montagem Drift Audio Sources
    private val AUDIO_SOURCES = listOf(
        "https://cdn.pixabay.com/download/audio/2023/04/06/audio_403c9d1c7a.mp3",
        "https://cdn.pixabay.com/download/audio/2022/11/06/audio_9748b625ca.mp3",
        "https://cdn.pixabay.com/download/audio/2023/09/24/audio_34b3dc04c0.mp3"
    )

    private var currentSourceIndex = 0
    private var mediaPlayer: MediaPlayer? = null
    private var prefs: SharedPreferences? = null

    @Volatile
    private var isPrepared = false

    @Volatile
    private var isPreparing = false

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _isMusicEnabled = MutableStateFlow(true)
    val isMusicEnabled: StateFlow<Boolean> = _isMusicEnabled.asStateFlow()

    private val _currentTrackTitle = MutableStateFlow("🇧🇷 Brazilian Phonk - Montagem Diamante")
    val currentTrackTitle: StateFlow<String> = _currentTrackTitle.asStateFlow()

    @Synchronized
    fun initialize(context: Context) {
        if (prefs == null) {
            prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            _isMusicEnabled.value = prefs?.getBoolean(KEY_MUSIC_ENABLED, true) ?: true
        }

        if (_isMusicEnabled.value) {
            startPlayback(context)
        }
    }

    @Synchronized
    fun startPlayback(context: Context) {
        if (isPreparing) {
            return
        }

        if (currentSourceIndex >= AUDIO_SOURCES.size) {
            currentSourceIndex = 0
        }
        val sourceUrl = AUDIO_SOURCES[currentSourceIndex]

        try {
            cleanupPlayer()
            isPreparing = true
            isPrepared = false

            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                isLooping = true
                setVolume(0.65f, 0.65f)

                setDataSource(context.applicationContext, Uri.parse(sourceUrl))

                setOnPreparedListener { mp ->
                    synchronized(this@BackgroundMusicManager) {
                        isPreparing = false
                        isPrepared = true
                        if (_isMusicEnabled.value) {
                            try {
                                mp.start()
                                _isPlaying.value = true
                                Log.d(TAG, "Brazilian Phonk playback started: $sourceUrl")
                            } catch (e: Exception) {
                                Log.w(TAG, "Error starting on prepared", e)
                                _isPlaying.value = false
                            }
                        }
                    }
                }

                setOnErrorListener { _, what, extra ->
                    synchronized(this@BackgroundMusicManager) {
                        Log.w(TAG, "MediaPlayer error ($what, $extra), switching source...")
                        isPreparing = false
                        isPrepared = false
                        _isPlaying.value = false
                        cleanupPlayer()
                    }
                    true
                }

                prepareAsync()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize Brazilian Phonk playback", e)
            isPreparing = false
            isPrepared = false
            _isPlaying.value = false
        }
    }

    @Synchronized
    fun toggleMusic(context: Context) {
        val newState = !_isMusicEnabled.value
        _isMusicEnabled.value = newState
        prefs?.edit()?.putBoolean(KEY_MUSIC_ENABLED, newState)?.apply()

        if (newState) {
            if (isPrepared && mediaPlayer != null) {
                try {
                    mediaPlayer?.start()
                    _isPlaying.value = true
                } catch (e: Exception) {
                    Log.w(TAG, "Error resuming player, restarting", e)
                    startPlayback(context)
                }
            } else if (!isPreparing) {
                startPlayback(context)
            }
        } else {
            pausePlayback()
        }
    }

    @Synchronized
    fun pausePlayback() {
        try {
            if (isPrepared && mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
            }
        } catch (e: Exception) {
            Log.w(TAG, "Error in pausePlayback", e)
        } finally {
            _isPlaying.value = false
        }
    }

    @Synchronized
    fun resumePlayback(context: Context) {
        if (!_isMusicEnabled.value) return

        if (isPrepared && mediaPlayer != null) {
            try {
                if (mediaPlayer?.isPlaying == false) {
                    mediaPlayer?.start()
                    _isPlaying.value = true
                }
            } catch (e: Exception) {
                Log.w(TAG, "Error resuming in onResume, restarting", e)
                startPlayback(context)
            }
        } else if (!isPreparing) {
            startPlayback(context)
        }
    }

    @Synchronized
    private fun cleanupPlayer() {
        try {
            isPrepared = false
            isPreparing = false
            mediaPlayer?.let { player ->
                if (player.isPlaying) {
                    player.stop()
                }
                player.reset()
                player.release()
            }
        } catch (e: Exception) {
            Log.w(TAG, "Error in cleanupPlayer", e)
        } finally {
            mediaPlayer = null
            _isPlaying.value = false
        }
    }

    @Synchronized
    fun release() {
        cleanupPlayer()
    }
}
