package io.github.llm96.ebur128java;

import com.sun.jna.Pointer;
import io.github.llm96.ebur128java.jna.Ebur128;

/**
 * High-level wrapper for EBUR128 state management and loudness measurement.
 * Provides simplified access to the underlying JNA library.
 */
public class State implements AutoCloseable {
  private Pointer state;
  private final int channels;
  private final long samplerate;
  private final int mode;

  /**
   * Create and initialize a new EBUR128 state.
   *
   * @param channels   the number of channels
   * @param samplerate the sample rate
   * @param mode       mode flags (see Mode constants)
   * @throws IllegalStateException if initialization fails
   */
  public State(int channels, long samplerate, int mode) {
    this.channels = channels;
    this.samplerate = samplerate;
    this.mode = mode;
    this.state = Ebur128.init(channels, samplerate, mode);
    if (this.state == null) {
      throw new IllegalStateException("Failed to initialize EBUR128 state");
    }
  }

  /**
   * Set channel type.
   *
   * @param channelNumber zero based channel index
   * @param channelType   channel type from Channel constants
   * @return error code (see Error constants)
   */
  public int setChannel(int channelNumber, int channelType) {
    checkState();
    return Ebur128.setChannel(state, channelNumber, channelType);
  }

  /**
   * Change library parameters.
   *
   * @param channels   new number of channels
   * @param samplerate new sample rate
   * @return error code (see Error constants)
   */
  public int changeParameters(int channels, long samplerate) {
    checkState();
    return Ebur128.changeParameters(state, channels, samplerate);
  }

  /**
   * Set the maximum window duration.
   *
   * @param window duration of the window in ms
   * @return error code (see Error constants)
   */
  public int setMaxWindow(long window) {
    checkState();
    return Ebur128.setMaxWindow(state, window);
  }

  /**
   * Set the maximum history.
   *
   * @param history duration of history in ms
   * @return error code (see Error constants)
   */
  public int setMaxHistory(long history) {
    checkState();
    return Ebur128.setMaxHistory(state, history);
  }

  /**
   * Add frames to be processed (short samples).
   *
   * @param src    array of source frames (channels must be interleaved)
   * @param frames number of frames (not number of samples!)
   * @return error code (see Error constants)
   */
  public int addFramesShort(short[] src, long frames) {
    checkState();
    return Ebur128.addFramesShort(state, src, frames);
  }

  /**
   * Add frames to be processed (int samples).
   *
   * @param src    array of source frames (channels must be interleaved)
   * @param frames number of frames (not number of samples!)
   * @return error code (see Error constants)
   */
  public int addFramesInt(int[] src, long frames) {
    checkState();
    return Ebur128.addFramesInt(state, src, frames);
  }

  /**
   * Add frames to be processed (float samples).
   *
   * @param src    array of source frames (channels must be interleaved)
   * @param frames number of frames (not number of samples!)
   * @return error code (see Error constants)
   */
  public int addFramesFloat(float[] src, long frames) {
    checkState();
    return Ebur128.addFramesFloat(state, src, frames);
  }

  /**
   * Add frames to be processed (double samples).
   *
   * @param src    array of source frames (channels must be interleaved)
   * @param frames number of frames (not number of samples!)
   * @return error code (see Error constants)
   */
  public int addFramesDouble(double[] src, long frames) {
    checkState();
    return Ebur128.addFramesDouble(state, src, frames);
  }

  /**
   * Get global integrated loudness in LUFS.
   *
   * @return integrated loudness in LUFS, or Double.NEGATIVE_INFINITY on error
   */
  public double getLoudnessGlobal() {
    checkState();
    return Ebur128.loudnessGlobal(state);
  }

  /**
   * Get momentary loudness (last 400ms) in LUFS.
   *
   * @return momentary loudness in LUFS, or Double.NEGATIVE_INFINITY on error
   */
  public double getLoudnessMomentary() {
    checkState();
    return Ebur128.loudnessMomentary(state);
  }

  /**
   * Get short-term loudness (last 3s) in LUFS.
   *
   * @return short-term loudness in LUFS, or Double.NEGATIVE_INFINITY on error
   */
  public double getLoudnessShortterm() {
    checkState();
    return Ebur128.loudnessShortterm(state);
  }

  /**
   * Get loudness of the specified window in LUFS.
   *
   * @param window window in ms to calculate loudness
   * @return loudness in LUFS, or Double.NEGATIVE_INFINITY on error
   */
  public double getLoudnessWindow(long window) {
    checkState();
    return Ebur128.loudnessWindow(state, window);
  }

  /**
   * Get loudness range (LRA) of programme in LU.
   *
   * @return loudness range (LRA) in LU, or Double.NEGATIVE_INFINITY on error
   */
  public double getLoudnessRange() {
    checkState();
    return Ebur128.loudnessRange(state);
  }

  /**
   * Get maximum sample peak from all frames that have been processed.
   *
   * @param channelNumber channel to analyse
   * @return maximum sample peak in float format (1.0 is 0 dBFS), or Double.NEGATIVE_INFINITY on error
   */
  public double getSamplePeak(int channelNumber) {
    checkState();
    return Ebur128.samplePeak(state, channelNumber);
  }

  /**
   * Get maximum sample peak from the last call to add_frames().
   *
   * @param channelNumber channel to analyse
   * @return maximum sample peak in float format (1.0 is 0 dBFS), or Double.NEGATIVE_INFINITY on error
   */
  public double getPrevSamplePeak(int channelNumber) {
    checkState();
    return Ebur128.prevSamplePeak(state, channelNumber);
  }

  /**
   * Get maximum true peak from all frames that have been processed.
   *
   * @param channelNumber channel to analyse
   * @return maximum true peak in float format (1.0 is 0 dBTP), or Double.NEGATIVE_INFINITY on error
   */
  public double getTruePeak(int channelNumber) {
    checkState();
    return Ebur128.truePeak(state, channelNumber);
  }

  /**
   * Get maximum true peak from the last call to add_frames().
   *
   * @param channelNumber channel to analyse
   * @return maximum true peak in float format (1.0 is 0 dBTP), or Double.NEGATIVE_INFINITY on error
   */
  public double getPrevTruePeak(int channelNumber) {
    checkState();
    return Ebur128.prevTruePeak(state, channelNumber);
  }

  /**
   * Get relative threshold in LUFS.
   *
   * @return relative threshold in LUFS, or Double.NEGATIVE_INFINITY on error
   */
  public double getRelativeThreshold() {
    checkState();
    return Ebur128.relativeThreshold(state);
  }

  /**
   * Check if the state is still valid.
   *
   * @return true if valid, false otherwise
   */
  public boolean isValid() {
    return state != null;
  }

  /**
   * Get the number of channels.
   *
   * @return number of channels
   */
  public int getChannels() {
    return channels;
  }

  /**
   * Get the sample rate.
   *
   * @return sample rate
   */
  public long getSamplerate() {
    return samplerate;
  }

  /**
   * Get the mode flags.
   *
   * @return mode flags
   */
  public int getMode() {
    return mode;
  }

  /**
   * Destroy the state and release resources.
   */
  @Override
  public void close() {
    if (state != null) {
      Ebur128.destroy(state);
      state = null;
    }
  }

  /**
   * Check if the state is valid, throw exception if not.
   *
   * @throws IllegalStateException if state is not valid
   */
  private void checkState() {
    if (state == null) {
      throw new IllegalStateException("EBUR128 state has been destroyed");
    }
  }
}
