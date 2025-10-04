package io.github.llm96.ebur128java.jna;

import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.DoubleByReference;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

public interface Ebur128Library extends Library {
  /**
   * Get library version number.
   *
   * @param major major version number of library
   * @param minor minor version number of library
   * @param patch patch version number of library
   */
  void ebur128_get_version(IntByReference major, IntByReference minor, IntByReference patch);

  /**
   * Initialize library state.
   *
   * @param channels   the number of channels
   * @param samplerate the sample rate
   * @param mode       see the mode enum for possible values
   * @return an initialized library state, or NULL on error
   */
  Pointer ebur128_init(int channels, long samplerate, int mode);

  /**
   * Destroy library state.
   *
   * @param st pointer to a library state pointer
   */
  void ebur128_destroy(PointerByReference st);

  /**
   * Set channel type.
   *
   * @param st             library state
   * @param channel_number zero based channel index
   * @param value          channel type from the "channel" enum
   * @return EBUR128_SUCCESS on success, EBUR128_ERROR_INVALID_CHANNEL_INDEX if invalid channel index
   */
  int ebur128_set_channel(Pointer st, int channel_number, int value);

  /**
   * Change library parameters.
   *
   * @param st         library state
   * @param channels   new number of channels
   * @param samplerate new sample rate
   * @return EBUR128_SUCCESS on success
   */
  int ebur128_change_parameters(Pointer st, int channels, long samplerate);

  /**
   * Set the maximum window duration.
   *
   * @param st     library state
   * @param window duration of the window in ms
   * @return EBUR128_SUCCESS on success
   */
  int ebur128_set_max_window(Pointer st, long window);

  /**
   * Set the maximum history.
   *
   * @param st      library state
   * @param history duration of history in ms
   * @return EBUR128_SUCCESS on success
   */
  int ebur128_set_max_history(Pointer st, long history);

  /**
   * Add frames to be processed (short samples).
   *
   * @param st     library state
   * @param src    array of source frames (channels must be interleaved)
   * @param frames number of frames (not number of samples!)
   * @return EBUR128_SUCCESS on success
   */
  int ebur128_add_frames_short(Pointer st, short[] src, long frames);

  /**
   * Add frames to be processed (int samples).
   *
   * @param st     library state
   * @param src    array of source frames (channels must be interleaved)
   * @param frames number of frames (not number of samples!)
   * @return EBUR128_SUCCESS on success
   */
  int ebur128_add_frames_int(Pointer st, int[] src, long frames);

  /**
   * Add frames to be processed (float samples).
   *
   * @param st     library state
   * @param src    array of source frames (channels must be interleaved)
   * @param frames number of frames (not number of samples!)
   * @return EBUR128_SUCCESS on success
   */
  int ebur128_add_frames_float(Pointer st, float[] src, long frames);

  /**
   * Add frames to be processed (double samples).
   *
   * @param st     library state
   * @param src    array of source frames (channels must be interleaved)
   * @param frames number of frames (not number of samples!)
   * @return EBUR128_SUCCESS on success
   */
  int ebur128_add_frames_double(Pointer st, double[] src, long frames);

  /**
   * Get global integrated loudness in LUFS.
   *
   * @param st  library state
   * @param out integrated loudness in LUFS
   * @return EBUR128_SUCCESS on success
   */
  int ebur128_loudness_global(Pointer st, DoubleByReference out);

  /**
   * Get global integrated loudness in LUFS across multiple instances.
   *
   * @param sts  array of library states
   * @param size length of sts
   * @param out  integrated loudness in LUFS
   * @return EBUR128_SUCCESS on success
   */
  int ebur128_loudness_global_multiple(Pointer[] sts, long size, DoubleByReference out);

  /**
   * Get momentary loudness (last 400ms) in LUFS.
   *
   * @param st  library state
   * @param out momentary loudness in LUFS
   * @return EBUR128_SUCCESS on success
   */
  int ebur128_loudness_momentary(Pointer st, DoubleByReference out);

  /**
   * Get short-term loudness (last 3s) in LUFS.
   *
   * @param st  library state
   * @param out short-term loudness in LUFS
   * @return EBUR128_SUCCESS on success
   */
  int ebur128_loudness_shortterm(Pointer st, DoubleByReference out);

  /**
   * Get loudness of the specified window in LUFS.
   *
   * @param st     library state
   * @param window window in ms to calculate loudness
   * @param out    loudness in LUFS
   * @return EBUR128_SUCCESS on success
   */
  int ebur128_loudness_window(Pointer st, long window, DoubleByReference out);

  /**
   * Get loudness range (LRA) of programme in LU.
   *
   * @param st  library state
   * @param out loudness range (LRA) in LU
   * @return EBUR128_SUCCESS on success
   */
  int ebur128_loudness_range(Pointer st, DoubleByReference out);

  /**
   * Get loudness range (LRA) in LU across multiple instances.
   *
   * @param sts  array of library states
   * @param size length of sts
   * @param out  loudness range (LRA) in LU
   * @return EBUR128_SUCCESS on success
   */
  int ebur128_loudness_range_multiple(Pointer[] sts, long size, DoubleByReference out);

  /**
   * Get maximum sample peak from all frames that have been processed.
   *
   * @param st             library state
   * @param channel_number channel to analyse
   * @param out            maximum sample peak in float format (1.0 is 0 dBFS)
   * @return EBUR128_SUCCESS on success
   */
  int ebur128_sample_peak(Pointer st, int channel_number, DoubleByReference out);

  /**
   * Get maximum sample peak from the last call to add_frames().
   *
   * @param st             library state
   * @param channel_number channel to analyse
   * @param out            maximum sample peak in float format (1.0 is 0 dBFS)
   * @return EBUR128_SUCCESS on success
   */
  int ebur128_prev_sample_peak(Pointer st, int channel_number, DoubleByReference out);

  /**
   * Get maximum true peak from all frames that have been processed.
   *
   * @param st             library state
   * @param channel_number channel to analyse
   * @param out            maximum true peak in float format (1.0 is 0 dBTP)
   * @return EBUR128_SUCCESS on success
   */
  int ebur128_true_peak(Pointer st, int channel_number, DoubleByReference out);

  /**
   * Get maximum true peak from the last call to add_frames().
   *
   * @param st             library state
   * @param channel_number channel to analyse
   * @param out            maximum true peak in float format (1.0 is 0 dBTP)
   * @return EBUR128_SUCCESS on success
   */
  int ebur128_prev_true_peak(Pointer st, int channel_number, DoubleByReference out);

  /**
   * Get relative threshold in LUFS.
   *
   * @param st  library state
   * @param out relative threshold in LUFS
   * @return EBUR128_SUCCESS on success
   */
  int ebur128_relative_threshold(Pointer st, DoubleByReference out);
}
