package io.github.llm96.ebur128java;

/**
 * Use these values when setting the channel map with setChannel().
 * See definitions in ITU R-REC-BS 1770-4
 */
public class Channel {
  public static final int UNUSED = 0;
  public static final int LEFT = 1;
  public static final int Mp030 = 1;
  public static final int RIGHT = 2;
  public static final int Mm030 = 2;
  public static final int CENTER = 3;
  public static final int Mp000 = 3;
  public static final int LEFT_SURROUND = 4;
  public static final int Mp110 = 4;
  public static final int RIGHT_SURROUND = 5;
  public static final int Mm110 = 5;
  public static final int DUAL_MONO = 6;
  public static final int MpSC = 7;
  public static final int MmSC = 8;
  public static final int Mp060 = 9;
  public static final int Mm060 = 10;
  public static final int Mp090 = 11;
  public static final int Mm090 = 12;
  public static final int Mp135 = 13;
  public static final int Mm135 = 14;
  public static final int Mp180 = 15;
  public static final int Up000 = 16;
  public static final int Up030 = 17;
  public static final int Um030 = 18;
  public static final int Up045 = 19;
  public static final int Um045 = 20;
  public static final int Up090 = 21;
  public static final int Um090 = 22;
  public static final int Up110 = 23;
  public static final int Um110 = 24;
  public static final int Up135 = 25;
  public static final int Um135 = 26;
  public static final int Up180 = 27;
  public static final int Tp000 = 28;
  public static final int Bp000 = 29;
  public static final int Bp045 = 30;
  public static final int Bm045 = 31;

  private Channel() {
  }
}
