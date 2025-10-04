package io.github.llm96.ebur128java.jna;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.DoubleByReference;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

public class Ebur128 {
  private static Ebur128Library library;

  static {
    library = Native.loadLibrary("ebur128", Ebur128Library.class);
  }

  /**
   * Get library version number.
   *
   * @return version array [major, minor, patch]
   */
  public static int[] getVersion() {
    IntByReference major = new IntByReference();
    IntByReference minor = new IntByReference();
    IntByReference patch = new IntByReference();
    library.ebur128_get_version(major, minor, patch);
    return new int[]{major.getValue(), minor.getValue(), patch.getValue()};
  }

  /**
   * Initialize library state.
   *
   * @param channels   the number of channels
   * @param samplerate the sample rate
   * @param mode       see the mode enum for possible values
   * @return an initialized library state, or null on error
   */
  public static Pointer init(int channels, long samplerate, int mode) {
    return library.ebur128_init(channels, samplerate, mode);
  }

  /**
   * Destroy library state.
   *
   * @param state pointer to a library state
   */
  public static void destroy(Pointer state) {
    if (state != null) {
      PointerByReference stateRef = new PointerByReference(state);
      library.ebur128_destroy(stateRef);
    }
  }

  /**
   * Set channel type.
   *
   * @param state          library state
   * @param channelNumber  zero based channel index
   * @param value          channel type from the "channel" enum
   * @return error code
   */
  public static int setChannel(Pointer state, int channelNumber, int value) {
    return library.ebur128_set_channel(state, channelNumber, value);
  }

  /**
   * Change library parameters.
   *
   * @param state      library state
   * @param channels   new number of channels
   * @param samplerate new sample rate
   * @return error code
   */
  public static int changeParameters(Pointer state, int channels, long samplerate) {
    return library.ebur128_change_parameters(state, channels, samplerate);
  }

  /**
   * Set the maximum window duration.
   *
   * @param state  library state
   * @param window duration of the window in ms
   * @return error code
   */
  public static int setMaxWindow(Pointer state, long window) {
    return library.ebur128_set_max_window(state, window);
  }

  /**
   * Set the maximum history.
   *
   * @param state   library state
   * @param history duration of history in ms
   * @return error code
   */
  public static int setMaxHistory(Pointer state, long history) {
    return library.ebur128_set_max_history(state, history);
  }

  /**
   * Add frames to be processed (short samples).
   *
   * @param state  library state
   * @param src    array of source frames (channels must be interleaved)
   * @param frames number of frames (not number of samples!)
   * @return error code
   */
  public static int addFramesShort(Pointer state, short[] src, long frames) {
    return library.ebur128_add_frames_short(state, src, frames);
  }

  /**
   * Add frames to be processed (int samples).
   *
   * @param state  library state
   * @param src    array of source frames (channels must be interleaved)
   * @param frames number of frames (not number of samples!)
   * @return error code
   */
  public static int addFramesInt(Pointer state, int[] src, long frames) {
    return library.ebur128_add_frames_int(state, src, frames);
  }

  /**
   * Add frames to be processed (float samples).
   *
   * @param state  library state
   * @param src    array of source frames (channels must be interleaved)
   * @param frames number of frames (not number of samples!)
   * @return error code
   */
  public static int addFramesFloat(Pointer state, float[] src, long frames) {
    return library.ebur128_add_frames_float(state, src, frames);
  }

  /**
   * Add frames to be processed (double samples).
   *
   * @param state  library state
   * @param src    array of source frames (channels must be interleaved)
   * @param frames number of frames (not number of samples!)
   * @return error code
   */
  public static int addFramesDouble(Pointer state, double[] src, long frames) {
    return library.ebur128_add_frames_double(state, src, frames);
  }

  /**
   * Get global integrated loudness in LUFS.
   *
   * @param state library state
   * @return integrated loudness in LUFS, or Double.NEGATIVE_INFINITY on error
   */
  public static double loudnessGlobal(Pointer state) {
    DoubleByReference out = new DoubleByReference();
    int result = library.ebur128_loudness_global(state, out);
    if (result == 0) {
      return out.getValue();
    }
    return Double.NEGATIVE_INFINITY;
  }

  /**
   * Get global integrated loudness in LUFS across multiple instances.
   *
   * @param states array of library states
   * @return integrated loudness in LUFS, or Double.NEGATIVE_INFINITY on error
   */
  public static double loudnessGlobalMultiple(Pointer[] states) {
    DoubleByReference out = new DoubleByReference();
    int result = library.ebur128_loudness_global_multiple(states, states.length, out);
    if (result == 0) {
      return out.getValue();
    }
    return Double.NEGATIVE_INFINITY;
  }

  /**
   * Get momentary loudness (last 400ms) in LUFS.
   *
   * @param state library state
   * @return momentary loudness in LUFS, or Double.NEGATIVE_INFINITY on error
   */
  public static double loudnessMomentary(Pointer state) {
    DoubleByReference out = new DoubleByReference();
    int result = library.ebur128_loudness_momentary(state, out);
    if (result == 0) {
      return out.getValue();
    }
    return Double.NEGATIVE_INFINITY;
  }

  /**
   * Get short-term loudness (last 3s) in LUFS.
   *
   * @param state library state
   * @return short-term loudness in LUFS, or Double.NEGATIVE_INFINITY on error
   */
  public static double loudnessShortterm(Pointer state) {
    DoubleByReference out = new DoubleByReference();
    int result = library.ebur128_loudness_shortterm(state, out);
    if (result == 0) {
      return out.getValue();
    }
    return Double.NEGATIVE_INFINITY;
  }

  /**
   * Get loudness of the specified window in LUFS.
   *
   * @param state  library state
   * @param window window in ms to calculate loudness
   * @return loudness in LUFS, or Double.NEGATIVE_INFINITY on error
   */
  public static double loudnessWindow(Pointer state, long window) {
    DoubleByReference out = new DoubleByReference();
    int result = library.ebur128_loudness_window(state, window, out);
    if (result == 0) {
      return out.getValue();
    }
    return Double.NEGATIVE_INFINITY;
  }

  /**
   * Get loudness range (LRA) of programme in LU.
   *
   * @param state library state
   * @return loudness range (LRA) in LU, or Double.NEGATIVE_INFINITY on error
   */
  public static double loudnessRange(Pointer state) {
    DoubleByReference out = new DoubleByReference();
    int result = library.ebur128_loudness_range(state, out);
    if (result == 0) {
      return out.getValue();
    }
    return Double.NEGATIVE_INFINITY;
  }

  /**
   * Get loudness range (LRA) in LU across multiple instances.
   *
   * @param states array of library states
   * @return loudness range (LRA) in LU, or Double.NEGATIVE_INFINITY on error
   */
  public static double loudnessRangeMultiple(Pointer[] states) {
    DoubleByReference out = new DoubleByReference();
    int result = library.ebur128_loudness_range_multiple(states, states.length, out);
    if (result == 0) {
      return out.getValue();
    }
    return Double.NEGATIVE_INFINITY;
  }

  /**
   * Get maximum sample peak from all frames that have been processed.
   *
   * @param state         library state
   * @param channelNumber channel to analyse
   * @return maximum sample peak in float format (1.0 is 0 dBFS), or Double.NEGATIVE_INFINITY on error
   */
  public static double samplePeak(Pointer state, int channelNumber) {
    DoubleByReference out = new DoubleByReference();
    int result = library.ebur128_sample_peak(state, channelNumber, out);
    if (result == 0) {
      return out.getValue();
    }
    return Double.NEGATIVE_INFINITY;
  }

  /**
   * Get maximum sample peak from the last call to add_frames().
   *
   * @param state         library state
   * @param channelNumber channel to analyse
   * @return maximum sample peak in float format (1.0 is 0 dBFS), or Double.NEGATIVE_INFINITY on error
   */
  public static double prevSamplePeak(Pointer state, int channelNumber) {
    DoubleByReference out = new DoubleByReference();
    int result = library.ebur128_prev_sample_peak(state, channelNumber, out);
    if (result == 0) {
      return out.getValue();
    }
    return Double.NEGATIVE_INFINITY;
  }

  /**
   * Get maximum true peak from all frames that have been processed.
   *
   * @param state         library state
   * @param channelNumber channel to analyse
   * @return maximum true peak in float format (1.0 is 0 dBTP), or Double.NEGATIVE_INFINITY on error
   */
  public static double truePeak(Pointer state, int channelNumber) {
    DoubleByReference out = new DoubleByReference();
    int result = library.ebur128_true_peak(state, channelNumber, out);
    if (result == 0) {
      return out.getValue();
    }
    return Double.NEGATIVE_INFINITY;
  }

  /**
   * Get maximum true peak from the last call to add_frames().
   *
   * @param state         library state
   * @param channelNumber channel to analyse
   * @return maximum true peak in float format (1.0 is 0 dBTP), or Double.NEGATIVE_INFINITY on error
   */
  public static double prevTruePeak(Pointer state, int channelNumber) {
    DoubleByReference out = new DoubleByReference();
    int result = library.ebur128_prev_true_peak(state, channelNumber, out);
    if (result == 0) {
      return out.getValue();
    }
    return Double.NEGATIVE_INFINITY;
  }

  /**
   * Get relative threshold in LUFS.
   *
   * @param state library state
   * @return relative threshold in LUFS, or Double.NEGATIVE_INFINITY on error
   */
  public static double relativeThreshold(Pointer state) {
    DoubleByReference out = new DoubleByReference();
    int result = library.ebur128_relative_threshold(state, out);
    if (result == 0) {
      return out.getValue();
    }
    return Double.NEGATIVE_INFINITY;
  }
}
