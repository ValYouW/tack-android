/*
 * This file is part of Tack Android.
 *
 * Tack Android is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Tack Android is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Tack Android. If not, see http://www.gnu.org/licenses/.
 *
 * Copyright (c) 2020-2024 by Patrick Zedler
 */

package xyz.zedler.patrick.tack.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import xyz.zedler.patrick.tack.Constants.DEF
import xyz.zedler.patrick.tack.util.MetronomeUtil
import xyz.zedler.patrick.tack.util.TempoTapUtil

class MainViewModel(private val metronomeUtil: MetronomeUtil? = null) : ViewModel() {

  private val tempoTapUtil = TempoTapUtil()
  private val _tempo = MutableLiveData(metronomeUtil?.tempo ?: DEF.TEMPO)
  private val _isPlaying = MutableLiveData(metronomeUtil?.isPlaying ?: false)
  private val _beatModeVibrate = MutableLiveData(
    metronomeUtil?.isBeatModeVibrate ?: DEF.BEAT_MODE_VIBRATE
  )
  private val _alwaysVibrate = MutableLiveData(
    metronomeUtil?.isAlwaysVibrate ?: DEF.ALWAYS_VIBRATE
  )

  val tempo: LiveData<Int> = _tempo
  val isPlaying: LiveData<Boolean> = _isPlaying
  val beatModeVibrate: LiveData<Boolean> = _beatModeVibrate
  val alwaysVibrate: LiveData<Boolean> = _alwaysVibrate

  fun changeTempo(tempo: Int) {
    metronomeUtil?.tempo = tempo
    _tempo.value = tempo
  }

  fun onTempoTap() {
    if (tempoTapUtil.tap()) {
      changeTempo(tempoTapUtil.tempo)
    }
  }

  fun onTempoCardSwipe(tempo: Int) {
    metronomeUtil?.tempo = tempo
  }

  fun onPlayingChange(playing: Boolean) {
    metronomeUtil?.isPlaying = playing
    _isPlaying.value = playing
  }

  fun togglePlaying() {
    val playing = metronomeUtil?.isPlaying ?: true
    metronomeUtil?.isPlaying = !playing
    _isPlaying.value = !playing
  }

  fun toggleBeatModeVibrate() {
    val beatModeVibrate = metronomeUtil?.isBeatModeVibrate ?: DEF.BEAT_MODE_VIBRATE
    metronomeUtil?.isBeatModeVibrate = !beatModeVibrate
    _beatModeVibrate.value = !beatModeVibrate
  }

  fun changeAlwaysVibrate(alwaysVibrate: Boolean) {
    metronomeUtil?.isAlwaysVibrate = alwaysVibrate
    _alwaysVibrate.value = alwaysVibrate
  }
}