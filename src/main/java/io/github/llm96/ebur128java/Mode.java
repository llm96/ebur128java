package io.github.llm96.ebur128java;

/**
 * Use these values in init() (or'ed).
 * Try to use the lowest possible modes that suit your needs, as performance will be better.
 */
public class Mode {
  /** can call loudnessMomentary */
  public static final int MODE_M = (1 << 0);

  /** can call loudnessShortterm */
  public static final int MODE_S = (1 << 1) | MODE_M;

  /** can call loudnessGlobal and relativeThreshold */
  public static final int MODE_I = (1 << 2) | MODE_M;

  /** can call loudnessRange */
  public static final int MODE_LRA = (1 << 3) | MODE_S;

  /** can call samplePeak */
  public static final int MODE_SAMPLE_PEAK = (1 << 4) | MODE_M;

  /** can call truePeak */
  public static final int MODE_TRUE_PEAK = (1 << 5) | MODE_M | MODE_SAMPLE_PEAK;

  /** uses histogram algorithm to calculate loudness */
  public static final int MODE_HISTOGRAM = (1 << 6);

  private Mode() {
  }
}
