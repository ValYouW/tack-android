package xyz.zedler.patrick.tack.util;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class VibratorUtil {

  private final Vibrator vibrator;

  public static final long TAP = 13;
  public static final long TICK = 20;
  public static final long TICK_HEAVY = 50;
  public static final long TACK = 50;
  public static final long TACK_HEAVY = 80;

  public VibratorUtil(Context context) {
    vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
  }

  public void vibrate(long duration) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      vibrator.vibrate(
          VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE)
      );
    } else {
      vibrator.vibrate(duration);
    }
  }

  public void vibrate(boolean emphasize, boolean heavyVibration) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      vibrator.vibrate(
          VibrationEffect.createOneShot(
              emphasize ? getTack(heavyVibration) : getTick(heavyVibration),
              heavyVibration ? 255 : VibrationEffect.DEFAULT_AMPLITUDE
          )
      );
    } else {
      vibrator.vibrate(emphasize ? getTack(heavyVibration) : getTick(heavyVibration));
    }
  }

  private long getTick(boolean heavyVibration) {
    return heavyVibration ? TICK_HEAVY : TICK;
  }

  private long getTack(boolean heavyVibration) {
    return heavyVibration ? TACK_HEAVY : TACK;
  }
}
