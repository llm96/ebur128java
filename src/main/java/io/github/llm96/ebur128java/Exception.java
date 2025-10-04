package io.github.llm96.ebur128java;

public class Exception extends RuntimeException {
  private final int code;

  public Exception(String message, int code) {
    super(message);
    this.code = code;
  }

  public Exception(String message, int code, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
